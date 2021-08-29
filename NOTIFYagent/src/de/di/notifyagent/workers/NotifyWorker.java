package de.di.notifyagent.workers;

import de.di.dokinform.util.DateFormat;
import de.di.notifyagent.app.Application;
import elo.di.notifyagent.elo.ELOClient;
import de.di.notifyagent.handlers.MailQueueHandler;
import de.di.notifyagent.jobs.Schedulable;
//import de.elo.ix.client.EditInfoC;
import de.elo.ix.client.IXExceptionC;
import de.elo.ix.client.IXExceptionData;
import de.elo.ix.client.LockC;
import de.elo.ix.client.UserInfo;
import de.elo.ix.client.UserInfoC;
import de.elo.ix.client.UserTask;
import de.elo.ix.client.WFCollectNode;
import de.elo.ix.client.WFDiagram;
import de.elo.ix.client.WFDiagramC;
import de.elo.ix.client.WFEditNode;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.Logger;
import de.elo.ix.client.WFNode;
import de.elo.ix.client.WFTypeC;
import de.elo.utils.net.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.naming.ldap.Control;

/**
 *
 * @author A. Sopicki
 */
public class NotifyWorker extends Worker {

    private String template = "";    
    private String from;
    private String subject;
    private String messageTemplate = null;
    private Logger logger = null;
    private String nodeName = "";
    private final String JAVA_CLIENT = "JC: ";
    private final String WEB_CLIENT = " | WC: ";

    public NotifyWorker(Schedulable schedulable, Properties settings) {
        super(schedulable, settings);
        logger = Logger.getLogger(getClass());
    }

    @Override
    protected void doWork() {
        if (messageTemplate == null) {
            logger.error("Unable to read template file");
            schedulable.abort();
            shutdown();
            return;
        }

        ELOClient client = schedulable.getEloClient();

        logger.trace("Checking for template " + template);

        try {
            
            if (!client.isWorkflowTemplate(template)) {
                logger.error("Template " + template + " not found in ELO. Aborting.");
                schedulable.abort();
                shutdown();
                return;
            } else {
                logger.trace("Template is ok.");
            }
        } catch (de.elo.utils.net.RemoteException rex) {
            try {
                IXExceptionData data = client.parseException(rex.getMessage());                
                if (data.getExceptionType() == IXExceptionC.NOT_FOUND) {
                    logger.error("Template " + template + " not found in ELO. Aborting.");
                } else {
                    logger.error("An exception occured while checking the workflow template " + template, rex);
                }
            } catch (Exception ex) {
                logger.error("An exception occured while checking the workflow template " + template, rex);  
            } finally {                               
                schedulable.abort();
                shutdown();
                return;
            }
        } catch (Exception ex) {
            logger.error("An exception occured while checking the workflow template " + template, ex);            
            schedulable.abort();
            shutdown();
            return;
        }
        
        
//        UserInfo[] infos = null;
        UserInfo[] members;
        try {
            logger.info("Getting all members of ELO user group " + settings.getProperty("Application.Groupname"));
            members = client.getGroupMembers(new String[]{settings.getProperty("Application.Groupname")});
//            infos = client.getUsers();
            logger.trace("Got " + members.length + " users/groups from ELO");
        } catch (Exception ex) {
            logger.error("An exception occured while retrieving the users", ex);
            schedulable.abort();
            shutdown();
            return;
        }

        handleWorkflows(members, client);
        logger.trace("Finished checking workflows for users");
        schedulable.finished();
        shutdown();
    }

    @Override
    protected void init() {
        template = settings.getProperty("NotifyUser.template", "");

        logger.trace("Template: " + template);
        logger.trace("Profile: " + schedulable.getProfile().getProfileName());
        String limit = settings.getProperty("NotifyUser.maxWorkflows");

        try {
            maxWorkflows = Integer.parseInt(limit);
        } catch (NumberFormatException nfex) {
        }

        subject = settings.getProperty("NotifyUser.subject");
        from = settings.getProperty("NotifyUser.from");
        nodeName = settings.getProperty("NotifyUser.nodeName", "");
        String templateFileName = settings.getProperty("NotifyUser.messageTemplate");

        messageTemplate = readTemplateFile(new File(templateFileName));
    }

    private void handleWorkflows(UserInfo[] infos, ELOClient client) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String nodeList = "";
        String nodeIdList = "";
        if (infos != null) {
            for (UserInfo user : infos) {
                logger.info("\t\t------ User: " + user.getName() + " ------");
                String[] props = user.getUserProps();
                String email = props[UserInfoC.PROP_NAME_EMAIL];
                
                Date nodeTime = null;
                Date currentTime = null;
//                String flag = props[PROP_E4];
//                if (user.getType() == UserInfoC.TYPE_GROUP){
//                    logger.debug("\tIgnoring group: " + user.getName());
//                    continue;
//                }
                if (email != null && email.length() > 0) {
                    String userName = user.getName();
                    //logger.info("\tChecking for workflows with template " + template + " for user " + userName);
                    logger.info("\t\tChecking for workflows for user " + userName);

                    java.util.List<WFDiagram> workflows = null;
                    int numberOfWorkflows = 0;
                    int numberOfActiveNodes = 0;
                    StringBuffer workflowList = null;  
                    String fixedNodes = "";   
                    String criticalReport = "";
                    boolean criticalArea = false;
                            
                    try {                        
                        //workflows = client.getWorkflows(template, userName, maxWorkflows);
                        
                        // filter only through username
                        workflows = client.getAllActiveWorkflows(userName, maxWorkflows);    
                       
                        if (workflows.isEmpty()) {
                            logger.info("\t\tUser " + userName + " has no active workflows. Skipping.");
                            logger.info("\t\t------ END USER ------");
                            continue;
                        }

                        logger.trace("\t\tGot " + workflows.size() + " possible workflows, now check active nodes...");

                        workflowList = new StringBuffer("<ul>");
                       //for (WFDiagram workflow : workflows) {
                            
                        //for(int i = 0; i < workflows.size(); i++) {

                            //filter any workflows which don't match the template (except for adhoc workflows)
//                            if (template.length() > 0 && (workflow.getTemplateName().length() == 0
//                                    || !workflow.getTemplateName().equalsIgnoreCase(template))) {
//                                logger.info("template = " + template);
//                                logger.info("wokflow.name = " + workflow.getName());
//                                logger.info(" workflow.getTemplateName() = " + workflow.getTemplateName());
//                                logger.info("!workflow.getTemplateName().equalsIgnoreCase(template) = " + !workflow.getTemplateName().equalsIgnoreCase(template));
//                                continue;
//                            }

                            //@TODO: Make this configurable
                            //filter adhoc workflows
//                            if (workflow.getTemplateId() == WFDiagramC.TEMPLATE_ID_ADHOC) {
//                                logger.info("workflow.getTemplateId() = " + workflow.getTemplateId() + " and WFDiagramC.TEMPLATE_ID_ADHOC = " + WFDiagramC.TEMPLATE_ID_ADHOC);
//                                continue;
//                            }                            
                            //WFCollectNode[] activeNodes = client.getActiveNodes(workflow.getName(), workflow.getObjId(), userName);
                            
                            // Filter by nodename in profile file                         
                            List<UserTask> activeNodes = new ArrayList<UserTask>();
                            if (!nodeName.isEmpty() && !nodeName.contains(",")) {
                                    logger.debug("\t\tFilter by nodeName: " + nodeName);
                                    activeNodes = client.getActiveNodes(userName, nodeName, 0, 0, maxWorkflows, DateFormat.toIsoDate(new java.util.Date()));                                    
                                    for(int i = 0; i < activeNodes.size(); i++) {
                                        if(activeNodes.size() == 1) {
                                            fixedNodes += activeNodes.get(i).getWfNode().getNodeName();                                         
                                        }                                        
//                                        else if(i == (activeNodes.size()-1) ) {
//                                            fixedNodes += activeNodes.get(i).getWfNode().getNodeName();
//                                        }
//                                        else {
//                                            fixedNodes += activeNodes.get(i).getWfNode().getNodeName() + ", ";
//                                        }
                                    }
                            }
                            // Several nodes. NodeName in the config-file musst have the format nodename=node1,node2,node3
                            else if(!nodeName.isEmpty() && nodeName.contains(",")) {
                                String[] nodes = nodeName.split(",");                                                           
                                List<UserTask> temp = null;
                                for(int i = 0; i < nodes.length; i++) {                                    
                                    temp = client.getActiveNodes(userName, nodes[i], 0, 0, maxWorkflows, DateFormat.toIsoDate(new java.util.Date()));
                                    for(UserTask node : temp) {
                                        activeNodes.add(node);
                                    }
                                }
                            }
                            else {
                                activeNodes = client.getActiveNodes(userName, 0, 0, maxWorkflows, DateFormat.toIsoDate(new java.util.Date()));
                            }                                                        
                            
                            // log-debug getactivenodes....
                            logger.debug("\t\tChecking for nodes for user: " + userName); // + " with the nodename: " + nodeName);
                            logger.debug("\t\tCurrently active nodes: ");
                            for(int i = 0; i < activeNodes.size(); i++) {
                                logger.debug("\t\t+" + activeNodes.get(i).getWfNode().getNodeName() + "\n");
                            }                           
                            //@TODO: Make this configurable
                            //check that the user has at least one active node in the workflow
//                            if (activeNodes == null || /*activeNodes == 0*/activeNodes.isEmpty()) {
//                                logger.debug("No active nodes found in workflow"); //: " + workflow.getName()); // in workflows : " + workflows.get(i).getName()); // + workflow.getName());                                   
//                                continue;
//                            }                        

                            String inUseDate = null;
                            WFCollectNode lastNode = null;                      
                            List<UserTask> inCurrentTemplateActiveNodes = new ArrayList<UserTask>();                                                                                    
                            WFDiagram wf = null;                            
                            
                            int wfId = 0;
                            List<WFDiagram> wfs = new ArrayList<WFDiagram>();
                            
                            /*WFCollectNode node : activeNodes (old version)*/                                                                          
                            for (UserTask activeNode : activeNodes) {                    
                                
                                String flowId = Integer.toString(activeNode.getWfNode().getFlowId());                               
                                String currentNodeUser = activeNode.getWfNode().getUserName();
                                // current template through the flowId
                                //wf = client.indexClient.ix.checkoutWorkFlow(client.clientInfo, flowId, WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
                                wf = client._connection.ix().checkoutWorkFlow(flowId, WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO); 
                                
                                // +++
                                wfId = wf.getId();
                                if(wf.getTemplateName().equals(template)) {
                                    if(wfs.isEmpty()) {
                                        wfs.add(wf);
                                    }
                                    else {
                                        if(wfs.get(wfs.size()-1).getId() != wfId) {
                                            wfs.add(wf);
                                        }
                                    }
                                }
                                // +++
                                
                                boolean isUserName = currentNodeUser.equals(userName);
                                String templateOfCurrentNode = wf.getTemplateName();                                                               
                                
                                if(!templateOfCurrentNode.equals(template)) {
                                    logger.debug("\t\tNode '" + activeNode.getWfNode().getNodeName() + "' not from template '"+ template +"', skip");                                      
                                    continue;
                                }      
                               if(!isUserName) {
                                    logger.debug("\t\tUser '" + currentNodeUser + "' is not the current performed user '"+ userName +"', skip");                                      
                                }                                
                                numberOfActiveNodes++; 
                                inCurrentTemplateActiveNodes.add(activeNode);                                                             
                                
                                // Zum Probieren eingeführt!!!!!!!!!!!!!!!!!!
                                // @TODO: getting this info from the jar
                                WFEditNode editNode = client.beginEditWorkflowNode(/*node.getFlowId(), node.getNodeId()*/
                                        activeNode.getWfNode().getFlowId(), activeNode.getWfNode().getNodeId());                                

                                // forward to successor
                                WFNode[] succNodes = editNode.getSuccNodes();

                                if (inUseDate == null) {
                                    //inUseDate = node.getInUseDateIso();
                                    inUseDate = activeNode.getWfNode().getInUseDateIso();
                                    //lastNode = node;
                                    lastNode = activeNode.getWfNode();
                                } else if (/*node*/activeNode.getWfNode().getInUseDateIso().compareTo(inUseDate) > 0) {
                                    inUseDate = /*node*/activeNode.getWfNode().getInUseDateIso();
                                    lastNode = /*node*/activeNode.getWfNode();
                                }                                                             
                            }
                            //                     
                            if (numberOfActiveNodes == 0) {
                                logger.info("\t\tUser " + userName + " has no active nodes to report. Skipping.");
                                logger.info("\t\t------ END USER ------\n");
                                continue;
                            }

                            if (lastNode == null) {
                                logger.debug("\t\t------------->LastNode is null");
                                continue;
                            }
                            
                            // user = 1, groupe = 0
                            if(user.getType() == 1) {
                                logger.debug("\t\t------------->Usertype is : User");
                            } else if(user.getType() == 0) {
                                logger.debug("\t\t------------->Usertype is : Groupe");
                            }                            
                            
//                            if (user.getType() == 1) {
//                                /*if (lastNode.getUserId() != user.getId()) {
//                                    logger.debug("\t\t------------->LastNode.getUserId() = " + lastNode.getUserId() + " and != user.getId = " + user.getId());
//                                    continue;
//                                }*/
//                                // Filter by nodename in profile...
//                                if (!nodeName.isEmpty() && !lastNode.getNodeName().contains(nodeName)) {
//                                    logger.debug("\t\tNodeName: " + lastNode.getNodeName() + " doesn't contain: " + nodeName);
//                                    continue;
//                                }
//                            }                    
                            // ++numberOfWorkflows;                            

                            int wfIndex = 0;
                            boolean newList = false;
                            boolean firstNode = true;
                            boolean newWF = true;
                            
                            nodeList = "";      
                            nodeIdList = "";
                            
                            WFDiagram currentFW = null;
                            workflowList.append("<li>");                                                        
                                                                 
                            for (UserTask activeNode : inCurrentTemplateActiveNodes) {  
                                if(newList) {
                                    workflowList.append("<li>");
                                    newList = false;
                                }
                                if(newWF) {
                                    currentFW = wfs.get(wfIndex);
                                    ++numberOfWorkflows;
                                    newWF = false;
                                }
                                // logger.info("------------->NotifyUser.AfterHours = " + ProfileManager.afterHours + " hours");                                
                                Calendar nodeDate = Calendar.getInstance();
                                String datePart = activeNode.getWfNode().getActivateDateIso();                                
                                int year = Integer.valueOf(datePart.substring(0, 4));
                                int monate = Integer.valueOf(datePart.substring(4, 6));
                                int day = Integer.valueOf(datePart.substring(6, 8));
                                int hours = Integer.valueOf(datePart.substring(8, 10));
                                int minute = Integer.valueOf(datePart.substring(10, 12));
                                int second = Integer.valueOf(datePart.substring(12, 14));
                                monate--;
                                nodeDate.set(year, monate, day, hours, minute, second);                                

                                logger.debug("\t\t------------->JobName for node (" + activeNode.getWfNode().getNodeName() + ") = " + 
                                        schedulable.getProfile().getProfileName() + " and afterHours = " + schedulable.getProfile().getAfterHours());

                                int afterHours = Integer.valueOf(schedulable.getProfile().getAfterHours());
                                nodeDate.add(Calendar.HOUR, afterHours);
                                nodeTime = (Date) nodeDate.getTime();                                  
                                currentTime = new Date();                                
                                // critical area when afterhours is set -> red border
                                if(afterHours != 0) {
                                    logger.debug("\t\t\t\tCurrent time: " + currentTime + ", node time after " + afterHours + ": " + nodeTime);
                                    if (nodeTime.before(currentTime)) {                                                                     
                                        if (firstNode) {
                                            nodeList += /*node*/activeNode.getWfNode().getNodeName();
                                            nodeIdList += activeNode.getWfNode().getNodeId();
                                            firstNode = false;                                        
                                            if(inCurrentTemplateActiveNodes.indexOf(activeNode) == (inCurrentTemplateActiveNodes.size()-1) ) {
                                                closeWFList(includeJavaclientURL, includeWebclientURL, workflowList, currentFW, nodeIdList, nodeList);                                            
                                            }
                                        } else {                                        
                                            if(currentFW.getId() == activeNode.getWfNode().getFlowId()) {
                                                nodeList += ", " + /*node*/activeNode.getWfNode().getNodeName();
                                                nodeIdList += "," + activeNode.getWfNode().getNodeId();
                                                if(inCurrentTemplateActiveNodes.indexOf(activeNode) == (inCurrentTemplateActiveNodes.size()-1) ) {
                                                    closeWFList(includeJavaclientURL, includeWebclientURL, workflowList, currentFW, nodeIdList, nodeList);                                            
                                                }
                                            } else {                                               
                                                wfIndex++;
                                                newWF = true;
                                                closeWFList(includeJavaclientURL, includeWebclientURL, workflowList, currentFW, nodeIdList, nodeList);
                                                // if the active node is the last node in the list
                                                if(inCurrentTemplateActiveNodes.indexOf(activeNode) == (inCurrentTemplateActiveNodes.size()-1) ) {
                                                    ++numberOfWorkflows;
                                                    workflowList.append("<li>");
                                                     closeWFList(includeJavaclientURL, includeWebclientURL, workflowList, 
                                                             wfs.get(wfIndex), Integer.toString(activeNode.getWfNode().getNodeId()), activeNode.getWfNode().getNodeName());                                                                                                                                              
                                                } else {
                                                    nodeList = activeNode.getWfNode().getNodeName();
                                                    newList = true;
                                                }
                                            }
                                        }
                                    } 
                                }
                                else if(afterHours == 0) {
                                    if (firstNode) {
                                            nodeList += /*node*/activeNode.getWfNode().getNodeName();
                                            nodeIdList += activeNode.getWfNode().getNodeId();
                                            firstNode = false;                                        
                                            if(inCurrentTemplateActiveNodes.indexOf(activeNode) == (inCurrentTemplateActiveNodes.size()-1) ) {
                                                closeWFList(includeJavaclientURL, includeWebclientURL, workflowList, currentFW, nodeIdList, nodeList);                                            
                                            }
                                        } else {                                        
                                            if(currentFW.getId() == activeNode.getWfNode().getFlowId()) {
                                                nodeList += ", " + /*node*/activeNode.getWfNode().getNodeName();
                                                nodeIdList += "," + activeNode.getWfNode().getNodeId();
                                                if(inCurrentTemplateActiveNodes.indexOf(activeNode) == (inCurrentTemplateActiveNodes.size()-1) ) {
                                                    closeWFList(includeJavaclientURL, includeWebclientURL, workflowList, currentFW, nodeIdList, nodeList);                                            
                                                }
                                            } else {                                               
                                                wfIndex++;
                                                newWF = true;
                                                closeWFList(includeJavaclientURL, includeWebclientURL, workflowList, currentFW, nodeIdList, nodeList);
                                                // if the active node is the last node in the list
                                                if(inCurrentTemplateActiveNodes.indexOf(activeNode) == (inCurrentTemplateActiveNodes.size()-1) ) {
                                                    ++numberOfWorkflows;
                                                    workflowList.append("<li>");
                                                     closeWFList(includeJavaclientURL, includeWebclientURL, workflowList, 
                                                             wfs.get(wfIndex), Integer.toString(activeNode.getWfNode().getNodeId()), activeNode.getWfNode().getNodeName());                                                                                                                                              
                                                } else {
                                                    nodeList = activeNode.getWfNode().getNodeName();
                                                    newList = true;
                                                }
                                            }
                                        }
                                }
                                // no critical area -> blue border
//                                else if(nodeTime.after(currentTime)) {                                                                                                          
//                                    if (firstNode) {
//                                        nodeList += /*node*/activeNode.getWfNode().getNodeName();
//                                        nodeIdList += activeNode.getWfNode().getNodeId();
//                                        firstNode = false;                                        
//                                        if(inCurrentTemplateActiveNodes.indexOf(activeNode) == (inCurrentTemplateActiveNodes.size()-1) ) {
//                                            closeWFList(includeJavaclientURL, includeWebclientURL, workflowList, currentFW, nodeIdList, nodeList);
//                                        }
//                                    } else {                                        
//                                        if(currentFW.getId() == activeNode.getWfNode().getFlowId()) {
//                                            nodeList += ", " + /*node*/activeNode.getWfNode().getNodeName();
//                                            nodeIdList += "," + activeNode.getWfNode().getNodeId();
//                                            if(inCurrentTemplateActiveNodes.indexOf(activeNode) == (inCurrentTemplateActiveNodes.size()-1) ) {
//                                                closeWFList(includeJavaclientURL, includeWebclientURL, workflowList, currentFW, nodeIdList, nodeList);
//                                            }
//                                        } else {                                               
//                                            wfIndex++;
//                                            newWF = true;
//                                            closeWFList(includeJavaclientURL, includeWebclientURL, workflowList, currentFW, nodeIdList, nodeList);
//                                            // if the active node is the last node in the list
//                                            if(inCurrentTemplateActiveNodes.indexOf(activeNode) == (inCurrentTemplateActiveNodes.size()-1) ) {
//                                                ++numberOfWorkflows;
//                                                workflowList.append("<li>");
//                                                closeWFList(includeJavaclientURL, includeWebclientURL, workflowList, 
//                                                         wfs.get(wfIndex), Integer.toString(activeNode.getWfNode().getNodeId()), activeNode.getWfNode().getNodeName());                                                
//                                            } else {
//                                                nodeList = activeNode.getWfNode().getNodeName();
//                                                newList = true;
//                                            }
//                                        }
//                                    }                                    
//                                }                                
                            }                                 
                            workflowList.append("</ul>");                                

                    } catch (de.elo.utils.net.RemoteException rex) {
                        logger.warn("\t\tUnable to get workflows for user " + user.getName(), rex);
                        logger.trace("\t\t------ END USER ------\n");
                        continue;
                    } catch (Exception ex) {
                        logger.warn("\t\tUnable to get workflows for user " + user.getName() + ". Aborting.", ex);
                        break;
                    }

                    if (numberOfWorkflows == 0) {
                        logger.info("\t\tUser " + userName + " has no workflows to report. Skipping.");
                        logger.info("\t\t------ END USER ------\n");
                        continue;
                    }

                    logger.trace("\t\t\tFound " + numberOfWorkflows + " workflows for the user");

                    String msgText = messageTemplate;
                    
//                    String msgText = "";
//                    if(criticalArea) {
//                        msgText = readTemplateFile(new File(settings.getProperty("NotifyUser.messageTemplate.critical")));             
//                    }
//                    else {
//                        msgText = messageTemplate;
//                    }

                    msgText = msgText.replace("{##workflowlist##}", workflowList);
                    msgText = msgText.replace("{##workflow##}", template);
                    msgText = msgText.replace("{##user##}", user.getName());
                    msgText = msgText.replace("{##timestamp##}", format.format(new java.util.Date()));
                    msgText = msgText.replace("{##numberofworkflows##}", Integer.toString(numberOfWorkflows));
                    msgText = msgText.replace("{##nodes##}", fixedNodes);                                        
                    
                    //moveFile(nodeList, msgText, email, criticalArea);
                    
                    try {
                        if (!schedulable.getProfile().getAfterHours().equals("0")) {
                            if (nodeList != "") {
                                MailQueueHandler handler = new MailQueueHandler(settings, msgText.toString(), email, from, subject);
                                handler.initialDir = new File(settings.getProperty("NotifyUser.messageTemplate")).getParentFile();
                                logger.debug("------------->JobName = " + schedulable.getProfile().getProfileName());
                                handler.handleSchedulable(schedulable);
                            }        
                        } else {
                            MailQueueHandler handler = new MailQueueHandler(settings, msgText.toString(), email, from, subject);
                            handler.initialDir = new File(settings.getProperty("NotifyUser.messageTemplate")).getParentFile();
                            logger.debug("------------->JobName = " + schedulable.getProfile().getProfileName());                            
                            handler.handleSchedulable(schedulable);
                        }
                    } catch (Exception ex) {
                        logger.error("An exception occured while handling the input files", ex);
                        schedulable.abort();
                        shutdown();
                        return;
                    }

                    logger.info("\t\t------ END USER ------\n");
                } else {
                    logger.debug("\t\tNo email notification for " + user.getName());
                    logger.trace("\t\t------ END USER ------\n");
                }
            }
        }
    }
    
    private void moveFile(String nodeList, String msgText, String email, boolean criticalArea) {
        try {
            if (!schedulable.getProfile().getAfterHours().equals("0")) {
                if (nodeList != "") {
                    MailQueueHandler handler = new MailQueueHandler(settings, msgText.toString(), email, from, subject);
                    handler.initialDir = new File(settings.getProperty("NotifyUser.messageTemplate")).getParentFile();
                    logger.debug("------------->JobName = " + schedulable.getProfile().getProfileName());
                    handler.handleSchedulable(schedulable);
                }        
            } else {
                MailQueueHandler handler = new MailQueueHandler(settings, msgText.toString(), email, from, subject);
                handler.initialDir = new File(settings.getProperty("NotifyUser.messageTemplate")).getParentFile();
                logger.debug("------------->JobName = " + schedulable.getProfile().getProfileName());
                handler.handleSchedulable(schedulable);
            }
        } catch (Exception ex) {
            logger.error("An exception occured while handling the input files", ex);
            schedulable.abort();
            shutdown();
            return;
        }
    }
    
    private void closeWFList(boolean jc, boolean wc, StringBuffer wfList, WFDiagram wf, String ids, String nodes) {
        if (jc && !wc) {            
            wfList.append(toJavaclientDocUrl(Integer.toString(wf.getId()), ids, nodes));
        } else if (jc && wc) {
            wfList.append(JAVA_CLIENT);
            wfList.append(toJavaclientDocUrl(Integer.toString(wf.getId()), ids, nodes));
            wfList.append(WEB_CLIENT);
            wfList.append(toWebclientDocUrl(Integer.toString(wf.getId()), ids, nodes));
        } else {
            wfList.append(nodes);
        }
        wfList.append("&nbsp;(");
        wfList.append(wf.getName());
        wfList.append(")</li>");
    }
                   
}

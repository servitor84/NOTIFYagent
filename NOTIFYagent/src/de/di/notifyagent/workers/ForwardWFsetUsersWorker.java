/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.di.notifyagent.workers;

import elo.di.notifyagent.elo.ELOClient;
import de.di.notifyagent.jobs.Schedulable;
import de.elo.ix.client.EditInfoC;
import de.elo.ix.client.FindByIndex;
import de.elo.ix.client.FindInfo;
import de.elo.ix.client.FindOptions;
import de.elo.ix.client.FindResult;
import de.elo.ix.client.IXClient;
import de.elo.ix.client.ObjKey;
import de.elo.ix.client.SearchModeC;
import de.elo.ix.client.Sord;
import de.elo.ix.client.SordC;
import de.elo.ix.client.SordZ;
import de.elo.ix.client.WFDiagram;
import de.elo.ix.client.WFDiagramC;
import de.elo.ix.client.WFEditNode;
import de.elo.ix.client.WFNode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

/**
 *
 * @author Rahman
 */
public class ForwardWFsetUsersWorker extends Worker {

    private Logger logger = null;
    private String USERS_META, DATE_META, EXACT_NAME, TEMPLATE_NAME, NODE_NAMES, MASK_NAME, DOC_STATE, DOC_STATE_VALUE;
    private String isSordSpecified;
    private boolean contains = true;
    private List<String> nodeNames;
    
    protected ResourceBundle bundle = ResourceBundle.getBundle("de/di/notifyagent/app/resources/ForwardWFsetUsersWorker", Locale.getDefault());
    
    // +++ +++
    private IXClient indexClient;
    // +++ +++

    public ForwardWFsetUsersWorker(Schedulable schedulable, Properties settings) {
        super(schedulable, settings);
        logger = Logger.getLogger(getClass());
    }

    private String getUserNameReplace(Sord sord) {
        String userFound = null;
        StringTokenizer st = new StringTokenizer(USERS_META, "|");
        while (st.hasMoreTokens()) {
            String newUserName = schedulable.getEloClient().getKey(sord, st.nextToken());
            if (newUserName != null && !newUserName.isEmpty()) {
                userFound = newUserName;
                break;
            }
        }
        return userFound;
    }

    private List<String> getNodeNames() {
        logger.debug("Entering getNodeNames");
        if (nodeNames == null) {
            logger.debug("Found nodeNames : " + NODE_NAMES);
            nodeNames = new ArrayList<String>();
            StringTokenizer st = new StringTokenizer(NODE_NAMES, "|");
            while (st.hasMoreTokens()) {
                String nodeName = st.nextToken();
                logger.debug("adding nodeName: " + nodeName);
                nodeNames.add(nodeName);
            }
        }
        logger.debug("Returning nodeNames.length: " + nodeNames.size());
        return nodeNames;
    }

    @Override
    protected void doWork() {
        //TODO : validate existance in ELO of users,metadata,etc
        if (USERS_META.isEmpty()) {
            logger.error(schedulable.getProfile().getProfileName() + ": Incorrect profile configuration. No specified user(s) found. Property: ForwardWFsetUsers.Users");
        }
        if (NODE_NAMES.isEmpty()) {
            logger.error(schedulable.getProfile().getProfileName() + ": No specified nodeNames. Property: ForwardWFsetUsers.Nodenames");
        }
        if (EXACT_NAME.equals("TRUE")) {
            contains = false;
        }
        if (MASK_NAME.isEmpty()) {
            logger.warn(schedulable.getProfile().getProfileName() + ": No maskName provided. Property: ForwardWFsetUsers.maskName");
        }
        if (TEMPLATE_NAME.isEmpty()) {
            logger.error(schedulable.getProfile().getProfileName() + ": No template defined. Property: ForwardWFsetUsers.template");
        }
        if (!MASK_NAME.isEmpty() && schedulable.getEloClient().checkoutDocMask(MASK_NAME) == null) {
            logger.error(schedulable.getProfile().getProfileName() + ": No mask found in ELO with maskName: " + MASK_NAME);
        } else {
            ELOClient client = schedulable.getEloClient();
            
            // 06.12.2018 - Template existiert nicht, Info in Log schreiben
            boolean found = false;
            List<String> templates = client.getTemplateNames();            
            for(String template : templates) {
                if(TEMPLATE_NAME.equals(template)) {
                    found = true;
                }
            }
            // +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++
            
            try {
                logger.info("Job for profile: " + schedulable.getProfile().getProfileName() + " started. Using templateName for search: " + TEMPLATE_NAME + " ,userName: " + client.getUserName());
                // 06.12.2018 - Template existiert nicht, Info in Log schreiben
                if(found == false) {
                    logger.info("Chosen template in the profile file (" + TEMPLATE_NAME + ") does not exist in ELO");
                }
                // +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++
                // Search only workflows for specified objects
                if(isSordSpecified.equals("TRUE"))
                {                    
                    // Documents with mask (10 - Vertragsdokumente) and specified values for indexfields (DATEWV und VTSTATUS)                                        
                    String curentDate   = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());                                                
                    FindInfo fi         = new FindInfo();
                    FindByIndex fbi     = new FindByIndex();                                         
                    ObjKey[] keys       = new ObjKey[] { new ObjKey(), new ObjKey() };
                    
                    String activ = bundle.getString("checkbox.contractstate.active");
                    String completed = bundle.getString("checkbox.contractstate.beendet");
                    String dismiss = bundle.getString("checkbox.contractstate.gekuendigt");
                    String state = "";
                    if(DOC_STATE_VALUE.equals("0")) { state = activ; }
                    else if(DOC_STATE_VALUE.equals("1")) { state = completed; }
                    else if(DOC_STATE_VALUE.equals("2")) { state = dismiss; }
                    
                    keys[0].setName(DATE_META);
                    keys[0].setData(new String[] {"..."+curentDate});
                    keys[1].setName(DOC_STATE);                    
                    keys[1].setData(new String[] {state});
                    
                    logger.info(schedulable.getProfile().getProfileName() + ": Workflows for objects with specified indexfields. " + "DATEWV <= " + "(" + new Date() + ")" + ", VTSTATUS = " + DOC_STATE_VALUE);
                                                            
                    fbi.setObjKeys(keys);
                    fbi.setMaskId(MASK_NAME);                                        
                    fi.setFindByIndex(fbi);
                    
                    List<Sord> sords = client.getSords(fi);
                    if(sords.isEmpty())
                    {
                        logger.info(schedulable.getProfile().getProfileName() + ": no objects with the specified indexfields "
                                + "( Mask: " + MASK_NAME + ", Field1: " + keys[0].getData() + ", Field2: " + keys[1].getData());
                        return;
                    }
                    
                    for(Sord sord : sords)
                    {
                        List<WFDiagram> workflows = client.getWorkflowBySord(sord, TEMPLATE_NAME);                        
                        if(workflows.isEmpty())
                        {
                            logger.info(schedulable.getProfile().getProfileName() + ": no workflows for " + sord.getId() + " - Skip");                                                            
                            continue;
                        }
                        logger.info(schedulable.getProfile().getProfileName() + ": Found " + workflows.size() + " workflows For " + sord.getId() + ". Processing them one by one");                        
                        for (WFDiagram wFDiagram : workflows) {                            
                            try {
                                logger.debug(schedulable.getProfile().getProfileName() + ": \tChecking workflow, id: " + wFDiagram.getId());                            
                                if (!sord.getMaskName().equals(MASK_NAME) && !MASK_NAME.isEmpty()) {
                                    logger.debug(schedulable.getProfile().getProfileName() + ": \tDifferent sordMask than configured. sord.maskName: " + sord.getMaskName() + " ,configured: " + MASK_NAME);
                                }
                                else {                           
                                    if (getUserNameReplace(sord) == null) 
                                    {
                                        logger.debug(schedulable.getProfile().getProfileName() + ": \tNo suitable user found to put on nodes");
                                    }
                                    else
                                    {
                                        boolean changed = false;
                                        WFNode nodeTask = client.getNode(wFDiagram, "{" + schedulable.getProfile().getProfileName() + "}", true);
                                        if (nodeTask.getEnterDateIso().equals("") || !nodeTask.getExitDateIso().equals("")) {
                                            logger.debug(schedulable.getProfile().getProfileName() + ": \tNo active node found.");
                                            continue;
                                        }
                                        logger.info(schedulable.getProfile().getProfileName() + ": \tProcessing workflow, id: " + wFDiagram.getId() + ". "
                                                + "Document id: " + sord.getId() + " ,document name: " + sord.getName());
                                        for (String nodeName : getNodeNames()) {
                                            WFNode wfNode = client.getNode(wFDiagram, nodeName, contains);
                                            logger.debug(schedulable.getProfile().getProfileName() + ": \t\tlooking for node with name: " + nodeName);
                                            if (wfNode != null && wfNode.getExitDateIso().equals("")) {
                                                logger.debug(schedulable.getProfile().getProfileName() + ": \t\tProcessing node with nodeId: " + wfNode.getId());
                                                wfNode.setUserName(getUserNameReplace(sord));
                                                changed = true;
                                            } else {
                                                logger.debug(schedulable.getProfile().getProfileName() + ": \t\tNo node found.");
                                            }
                                        }

                                        List<WFNode> succNodes = client.getSuccNodes(wFDiagram, nodeTask.getId());
                                        if (succNodes.size() != 1) {
                                            logger.info(schedulable.getProfile().getProfileName() + ": \tIncorect number of succNodes: " + succNodes.size());
                                        } else {
                                            if (changed) {
                                                client.checkinWorkflow(wFDiagram, WFDiagramC.mbAll);
                                            }
                                            WFEditNode wFEditNode = client.beginEditWorkflowNode(wFDiagram.getId(), nodeTask.getId());

                                            client.endEditWorkflowNode(wFEditNode, new int[]{succNodes.get(0).getId()});
                                            logger.info(schedulable.getProfile().getProfileName() + ": \tFinished processing succesfully. wf:id: " + wFDiagram.getId());
                                        }                                
                                    }
                                }
                            } 
                            catch (Exception ex) 
                            {
                                logger.error("Error in processing wf:id: " + wFDiagram.getId());
                                logger.error(ex, ex);
                            }                            
                        }                        
                    }                                                                                
                }
                // +++ END +++ 
                
                // Search in all workflows
                else
                {                    
                    List<WFDiagram> workflows = client.getWorkflows(TEMPLATE_NAME, client.getUserName());
                    logger.info(schedulable.getProfile().getProfileName() + ": Found " + workflows.size() + " workflows.Processing them one by one");
                    for (WFDiagram wFDiagram : workflows) {
                        try {
                            logger.debug(schedulable.getProfile().getProfileName() + ": \tChecking workflow, id: " + wFDiagram.getId());
                            String curentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                            Sord sord = client.getSord(wFDiagram.getObjId(), EditInfoC.mbAll);
                            if (!sord.getMaskName().equals(MASK_NAME) && !MASK_NAME.isEmpty()) {
                                logger.debug(schedulable.getProfile().getProfileName() + ": \tDifferent sordMask than configured. sord.maskName: " + sord.getMaskName() + " ,configured: " + MASK_NAME);
                            } else {
                                String valueData = client.getKey(sord, DATE_META);
                                //                        logger.debug("Found data with: " + valueData);
                                //String curentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                                if (valueData == null && !DATE_META.isEmpty()) {
                                    logger.debug(schedulable.getProfile().getProfileName() + ": \tNo field with name: " + DATE_META + " found");
                                    continue;
                                } else if (DATE_META.isEmpty()) {
                                    valueData = "";
                                }
                                if (getUserNameReplace(sord) == null) {
                                    logger.debug(schedulable.getProfile().getProfileName() + ": \tNo suitable user found to put on nodes");                                    
                                } else if (valueData.isEmpty() || valueData.compareTo(curentDate) < 0) {
                                    boolean changed = false;
                                    //                            logger.debug("entered condition");
                                    WFNode nodeTask = client.getNode(wFDiagram, "{" + schedulable.getProfile().getProfileName() + "}", true);
                                    //should be replaced with current node
                                    if (nodeTask.getEnterDateIso().equals("") || !nodeTask.getExitDateIso().equals("")) {
                                        logger.debug(schedulable.getProfile().getProfileName() + ": \tNo active node found.");
                                        continue;
                                    }
                                    logger.info(schedulable.getProfile().getProfileName() + ": \tProcessing workflow, id: " + wFDiagram.getId() + ". Document id: " + sord.getId() + " ,document name: " + sord.getName());
                                    for (String nodeName : getNodeNames()) {
                                        WFNode wfNode = client.getNode(wFDiagram, nodeName, contains);
                                        logger.debug(schedulable.getProfile().getProfileName() + ": \t\tlooking for node with name: " + nodeName);
                                        if (wfNode != null && wfNode.getExitDateIso().equals("")) {
                                            logger.debug(schedulable.getProfile().getProfileName() + ": \t\tProcessing node with nodeId: " + wfNode.getId());
                                            wfNode.setUserName(getUserNameReplace(sord));
                                            changed = true;
                                        } else {
                                            logger.debug(schedulable.getProfile().getProfileName() + ": \t\tNo node found.");
                                        }
                                    }

                                    List<WFNode> succNodes = client.getSuccNodes(wFDiagram, nodeTask.getId());
                                    if (succNodes.size() != 1) {
                                        logger.info(schedulable.getProfile().getProfileName() + ": \tIncorect number of succNodes: " + succNodes.size());
                                    } else {
                                        if (changed) {
                                            client.checkinWorkflow(wFDiagram, WFDiagramC.mbAll);
                                        }
                                        WFEditNode wFEditNode = client.beginEditWorkflowNode(wFDiagram.getId(), nodeTask.getId());

                                        client.endEditWorkflowNode(wFEditNode, new int[]{succNodes.get(0).getId()});
                                        logger.info(schedulable.getProfile().getProfileName() + ": \tFinished processing succesfully. wf:id: " + wFDiagram.getId());
                                    }
                                }
                            }

                        } catch (Exception ex) {
                            logger.error("Error in processing wf:id: " + wFDiagram.getId());
                            logger.error(ex, ex);
                        }
                    }    
                }
            } catch (Exception ex) {
                logger.error("Error in processing task.");
                logger.error(ex, ex);
            }
        }
        schedulable.finished();
        shutdown();
    }

    @Override
    protected void init() {
        USERS_META = settings.getProperty("ForwardWFsetUsers.Users", "");
        NODE_NAMES = settings.getProperty("ForwardWFsetUsers.Nodenames", "");
        EXACT_NAME = settings.getProperty("ForwardWFsetUsers.Exactname", "FALSE");
        MASK_NAME = settings.getProperty("ForwardWFsetUsers.maskName", "");
        DATE_META = settings.getProperty("ForwardWFsetUsers.Date", "");
        TEMPLATE_NAME = settings.getProperty("ForwardWFsetUsers.template", "");
        isSordSpecified = settings.getProperty("ForwardWFsetUsers.isSordSpecified", "");
        DOC_STATE = settings.getProperty("ForwardWFsetUsers.docState", "");
        DOC_STATE_VALUE = settings.getProperty("ForwardWFsetUsers.docStateValue", "");
    }
}

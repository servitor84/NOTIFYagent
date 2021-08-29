/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.di.notifyagent.workers;

import elo.di.notifyagent.elo.ELOClient;
import de.di.notifyagent.jobs.Schedulable;
import de.elo.ix.client.EditInfoC;
import de.elo.ix.client.Sord;
import de.elo.ix.client.UserTask;
import de.elo.ix.client.UserTaskPriorityC;
import de.elo.ix.client.WFDiagram;
import de.elo.ix.client.WFDiagramC;
import de.elo.ix.client.WFEditNode;
import de.elo.ix.client.WFNode;
import de.elo.utils.net.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Rahman
 */
public class ForwardWFstartWorker extends Worker {

    private Logger logger = null;
    private String USERNAME_META, DATE_META, TEMPLATE_NAME, NODE_USER, MASK_NAME;

    public ForwardWFstartWorker(Schedulable schedulable, Properties settings) {
        super(schedulable, settings);
        logger = Logger.getLogger(getClass());
    }

    @Override
    protected void doWork() {
        ELOClient client = schedulable.getEloClient();
        if (USERNAME_META.isEmpty()) {
            logger.error(schedulable.getProfile().getProfileName() + ": Empty or null userName found in properties. Using key: ForwardWFstart.User");
        }
        if (DATE_META.isEmpty()) {
            logger.error(schedulable.getProfile().getProfileName() + ": Empty or null date found in properties. Using key: ForwardWFstart.Date");
        }
        if (!MASK_NAME.isEmpty() && schedulable.getEloClient().checkoutDocMask(MASK_NAME) == null) {
            logger.error(schedulable.getProfile().getProfileName() + ": No mask found in ELO with maskName: " + MASK_NAME);
        } else {
            try {
                logger.info("Job for profile: " + schedulable.getProfile().getProfileName() + " started. Using templateName for search: " + TEMPLATE_NAME + " ,userName: " + client.getUserName());
                List<WFDiagram> workflows = client.getWorkflows(TEMPLATE_NAME, NODE_USER);
                for (WFDiagram wFDiagram : workflows) {
                    try {
                        logger.debug(schedulable.getProfile().getProfileName() + ":\t Checking workflow, id: " + wFDiagram.getId() + " ,sord.id: " + wFDiagram.getObjId() + " ,sord.name:" + wFDiagram.getObjName());
                        WFNode wFNode = client.getNode(wFDiagram, "{" + schedulable.getProfile().getProfileName() + "}", true);
                        if (wFNode != null && !wFNode.getEnterDateIso().equals("") && wFNode.getExitDateIso().equals("")) {
                            Sord sord = client.getSord(wFDiagram.getObjId(), EditInfoC.mbAll);
                            if (!sord.getMaskName().equals(MASK_NAME) && !MASK_NAME.isEmpty()) {
                                logger.debug(schedulable.getProfile().getProfileName() + ":\tDifferent sordMask than configured. sord.maskName: " + sord.getMaskName() + " ,configured: " + MASK_NAME);
                            } else {
                                String valueData = client.getKey(sord, DATE_META);
                                logger.debug(schedulable.getProfile().getProfileName() + ":\tFound date" + valueData + " for sordkey: " + DATE_META);

                                String curentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

                                if (valueData == null) {
                                    logger.debug(schedulable.getProfile().getProfileName() + ":\tNo field with name: " + DATE_META + " found");
                                    continue;
                                }
                                if (valueData.isEmpty() || valueData.compareTo(curentDate) < 0) {

                                    String userReplace = client.getKey(sord, USERNAME_META);
                                    if (userReplace == null) {
                                        logger.debug(schedulable.getProfile().getProfileName() + ":\tNo field with group: " + USERNAME_META + " found.Ignoring request. Check config file if group is correct.");
                                    }
                                    logger.debug(schedulable.getProfile().getProfileName() + ":\t Found userReplace:" + userReplace);
                                    List<WFNode> succNodes = client.getSuccNodes(wFDiagram, wFNode.getId());
                                    if (succNodes.size() != 1) {
                                        logger.debug(schedulable.getProfile().getProfileName() + ":\tIncorect number of succNodes: " + succNodes.size());
                                    } else {
                                        logger.info(schedulable.getProfile().getProfileName() + ": \tProcessing workflow, id: " + wFDiagram.getId() + ". Document name:");
                                        succNodes.get(0).setUserName(userReplace);
                                        client.checkinWorkflow(wFDiagram, WFDiagramC.mbAll);
                                        WFEditNode wFEditNode = client.beginEditWorkflowNode(wFDiagram.getId(), wFNode.getId());
                                        client.endEditWorkflowNode(wFEditNode, new int[]{succNodes.get(0).getId()});
                                        logger.info(schedulable.getProfile().getProfileName() + ": \tFinished processing succesfully. wf:id: " + wFDiagram.getId());

                                    }
                                }
                            }
                        } else {
                            logger.info(schedulable.getProfile().getProfileName() + ": No triggered nodes found for wfId: " + wFDiagram.getId());
                        }
                    } catch (Exception ex) {
                        logger.error("Error in processing wf:id: " + wFDiagram.getId());
                        logger.error(ex, ex);
                    }
                }
            } catch (RemoteException ex) {
                logger.error(schedulable.getProfile().getProfileName() + ": Error in retrieving tasks for curent user: ", ex);
            }
        }
        schedulable.finished();
        shutdown();

    }

    @Override
    protected void init() {
        USERNAME_META = settings.getProperty("ForwardWFstart.User", "");
        DATE_META = settings.getProperty("ForwardWFstart.Date", "");
        TEMPLATE_NAME = settings.getProperty("ForwardWFstart.template", "");
        NODE_USER = schedulable.getEloClient().getUserName();
        MASK_NAME = settings.getProperty("ForwardWFstart.maskName", "");
        logger.debug("Using user indexField: " + USERNAME_META + " ,date indexField: " + DATE_META);

    }
}

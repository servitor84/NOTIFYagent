/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.di.notifyagent.workers;

import elo.di.notifyagent.elo.ELOClient;
import de.di.notifyagent.jobs.Schedulable;
import de.elo.ix.client.EditInfoC;
import de.elo.ix.client.Sord;
import de.elo.ix.client.SordC;
import de.elo.ix.client.WFDiagram;
import de.elo.ix.client.WFEditNode;
import de.elo.ix.client.WFNode;
import de.elo.utils.net.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Rahman
 */
public class ForwardWFendWorker
        extends Worker {

    private Logger logger = null;
    private String DATE_META, STATUS_META, STATUS_VALUE, TEMPLATE_NAME, NODE_USER, MASK_NAME;

    public ForwardWFendWorker(Schedulable schedulable, Properties settings) {
        super(schedulable, settings);
        logger = Logger.getLogger(getClass());
    }

    @Override
    protected void doWork() {
        ELOClient client = schedulable.getEloClient();
        if (STATUS_META.isEmpty()) {
            logger.error(schedulable.getProfile().getProfileName() + ": Empty or null STATUS_META found in properties. Using key: Foward.User");
        }
        if (DATE_META.isEmpty()) {
            logger.error(schedulable.getProfile().getProfileName() + ": Empty or null date found in properties. Using key: Foward.Date");
        }
        if (STATUS_VALUE.isEmpty()) {
            logger.error(schedulable.getProfile().getProfileName() + ": Empty or null date found in properties. Using key: ForwardWFend.Status");
        }
        if (!MASK_NAME.isEmpty() && schedulable.getEloClient().checkoutDocMask(MASK_NAME) == null) {
            logger.error(schedulable.getProfile().getProfileName() + ": No mask found in ELO with maskName: " + MASK_NAME);
        } else {
            try {
                logger.info("Job for profile: " + schedulable.getProfile().getProfileName() + " started. Using templateName for search: " + TEMPLATE_NAME + " ,userName: " + client.getUserName());
                List<WFDiagram> workflows = client.getWorkflows(TEMPLATE_NAME, NODE_USER);
                logger.info(schedulable.getProfile().getProfileName() + ": Found " + workflows.size() + " workflows.Processing them one by one");
                for (WFDiagram wFDiagram : workflows) {
                    logger.debug(schedulable.getProfile().getProfileName() + ":\t Checking workflow, id: " + wFDiagram.getId());
                    WFNode wFNode = client.getNode(wFDiagram, "{" + schedulable.getProfile().getProfileName() + "}", true);
                    if (wFNode != null && !wFNode.getEnterDateIso().equals("") && wFNode.getExitDateIso().equals("")) {
                        Sord sord = client.getSord(wFDiagram.getObjId(), EditInfoC.mbAll);
                        if (!sord.getMaskName().equals(MASK_NAME) && !MASK_NAME.isEmpty()) {
                            logger.debug(schedulable.getProfile().getProfileName() + ":\tDifferent sordMask than configured. sord.maskName: " + sord.getMaskName() + " ,configured: " + MASK_NAME);
                        } else {
                            String valueData = client.getKey(sord, DATE_META);
                            logger.debug(schedulable.getProfile().getProfileName() + ":\tFound date" + valueData +" for sordkey: " + DATE_META);
                            String curentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                            if (DATE_META.isEmpty()) {
                                valueData = "";
                            }
                            if (valueData == null) {
                                logger.debug(schedulable.getProfile().getProfileName() + ":\tNo field with name: " + DATE_META + " found");
                                continue;
                            }
                            if (valueData.isEmpty() || valueData.compareTo(curentDate) < 0) {
                                logger.info(schedulable.getProfile().getProfileName() + ": \tProcessing workflow, id: " + wFDiagram.getId() + ". Document name:");

                                List<WFNode> succNodes = client.getSuccNodes(wFDiagram, wFNode.getId());
                                if (succNodes.size() != 1) {
                                    logger.debug(schedulable.getProfile().getProfileName() + ": \tIncorect number of succNodes: " + succNodes.size());
                                } else {
                                    WFEditNode wFEditNode = client.beginEditWorkflowNode(wFDiagram.getId(), wFNode.getId());
                                    client.endEditWorkflowNode(wFEditNode, new int[]{succNodes.get(0).getId()});
                                    client.setKey(sord, STATUS_META, STATUS_VALUE);
                                    client.checkinSord(sord, SordC.mbAll);
                                    logger.info(schedulable.getProfile().getProfileName() + ": \tFinished processing succesfully.");
                                }
                            }
                        }
                    } else {
                        logger.debug(schedulable.getProfile().getProfileName() + ":No triggered nodes found for wfId: " + wFDiagram.getId());
                    }
                }
            } catch (RemoteException ex) {
                logger.error(schedulable.getProfile().getProfileName() + ":Error in retrieving tasks for curent user: ", ex);
            }
        }
        schedulable.finished();
        shutdown();
    }

    @Override
    protected void init() {
        STATUS_META = settings.getProperty("ForwardWFend.StatusField", "");
        DATE_META = settings.getProperty("ForwardWFend.Date", "");
        STATUS_VALUE = settings.getProperty("ForwardWFend.Status", "");
        TEMPLATE_NAME = settings.getProperty("ForwardWFend.template", "");
        NODE_USER = schedulable.getEloClient().getUserName();
        MASK_NAME = settings.getProperty("ForwardWFend.maskName", "");

    }
}

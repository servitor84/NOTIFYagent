package de.di.notifyagent.workers;

import de.di.dokinform.notifyabo.DocWriter;
import de.di.dokinform.notifyabo.StatusDoc;
import de.di.dokinform.notifyabo.SubscriptionDoc;

import elo.di.notifyagent.elo.ELOClient;
import de.di.notifyagent.handlers.MailQueueHandler;
import de.di.notifyagent.jobs.Schedulable;
import de.elo.ix.client.EditInfoC;
import de.elo.ix.client.FindResult;
import de.elo.ix.client.Sord;
import de.elo.ix.client.SordC;
import java.io.File;
import de.elo.utils.net.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author A. Sopicki
 */
public class NewDocumentsWorker extends Worker {

    private Logger logger = null;
    private String from;
    private String messageTemplate = null;
    private SubscriptionDoc subscription = null;
    private File templateFolder = null;

    public NewDocumentsWorker(Schedulable schedulable, Properties settings) {
        super(schedulable, settings);

        logger = Logger.getLogger(getClass());
    }

    @Override
    protected void doWork() {
        FindResult result = null;
        Sord[] sords = null;
        Sord sord = null;
        ELOClient client = schedulable.getEloClient();
        SimpleDateFormat timestamp = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        DocWriter writer = new DocWriter(client);
        StatusDoc status = null;

        try {
            status = client.getStatus(subscription.getUser(), subscription.getName());
        } catch (RemoteException rex) {
            logger.warn("Error while retrieving status for subscription", rex);
            schedulable.abort();
            shutdown();
            return;
        }

        try {
            sord = client.getSord(subscription.getWatchObjId(), EditInfoC.mbAll);
        } catch (RemoteException rex) {
            try {
                status.setLastRun(new Date());
                status.setExitCode(StatusDoc.COM_ERROR);

                writer.writeStatus(status);
            } catch (Exception ex) {
                logger.debug("An rror occured while saving the status", ex);
            }

            logger.warn("Folder to check for new documents is not available", rex);
            schedulable.abort();
            shutdown();
            return;
        }

        if (sord == null) {
            try {
                status.setLastRun(new Date());
                status.setExitCode(StatusDoc.FOLDER_MISSING);

                writer.writeStatus(status);
            } catch (Exception ex) {
                logger.debug("An rror occured while saving the status", ex);
            }

            logger.warn("Folder to check for new documents not found");
            schedulable.abort();
            shutdown();
            return;
        }

        try {
            result = client.getNewDocuments(subscription.getWatchObjId(), subscription.isRecursive());
        } catch (RemoteException rex) {
            try {
                status.setLastRun(new Date());
                status.setExitCode(StatusDoc.COM_ERROR);

                writer.writeStatus(status);
            } catch (Exception ex) {
                logger.debug("An rror occured while saving the status", ex);
            }

            logger.warn("Unable to get documents from ELO", rex);
            schedulable.abort();
            shutdown();
            return;
        }

        boolean hasResults = false;

        sords = result.getSords();

        if (sords != null && sords.length > 0) {
            hasResults = true;
        }

        StringBuilder documentList = new StringBuilder("<ul>");
        int nrOfDocuments = 0;

        Calendar cal = GregorianCalendar.getInstance();
        Date lastRun = status.getLastRun();

        if (lastRun == null) {
            lastRun = new Date();
        }

        cal.setTime(lastRun);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        lastRun = cal.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

        String mask = subscription.getMask();
        int offset = 0;
        while (result != null && hasResults) {

            for (Sord s : sords) {
                try {
                    Date creationDate = format.parse(s.getIDateIso());

                    //not a document
                    if (s.getType() < SordC.LBT_DOCUMENT
                            || s.getType() > SordC.LBT_DOCUMENT_MAX) {
                        continue;
                    }

                    String maskName = s.getMaskName();

                    //ELO6
//          try {
//            maskName = client.getMaskName(s.getMask());
//          } catch(RemoteException rex) {
//            
//          }

                    //new document
                    if (lastRun.compareTo(creationDate) <= 0) {
                        if (mask != null && mask.length() > 0
                                && maskName != null && maskName.compareTo(mask) != 0) {
                            continue;
                        }

                        documentList.append("<li>");

                        if (includeURL) {
                            documentList.append(
                                    toDocUrl(s.getGuid(), s.getName()));
                        } else {
                            documentList.append(s.getName());
                        }
                        documentList.append("</li>");
                        ++nrOfDocuments;
                    }
                } catch (java.text.ParseException pex) {
                    logger.debug("Parse error", pex);
                }
            }

            if (result.isMoreResults()) {
                offset += sords.length;

                sords = null;
                try {
                    result = client.getDocuments(result, offset);
                } catch (RemoteException rex) {
                    try {
                        status.setLastRun(new Date());
                        status.setExitCode(StatusDoc.COM_ERROR);

                        writer.writeStatus(status);
                    } catch (Exception ex) {
                        logger.debug("An rror occured while saving the status", ex);
                    }

                    logger.warn("Unable to get documents from ELO", rex);
                    schedulable.abort();
                    shutdown();
                    return;
                }

                if (result != null) {
                    sords = result.getSords();
                }

                if (sords != null && sords.length > 0) {
                    hasResults = true;
                } else {
                    hasResults = false;
                }
            } else {
                hasResults = false;
            }
        }

        documentList.append("</ul>");

        if (nrOfDocuments == 0) {
            try {
                status.setLastRun(new Date());
                status.setExitCode(StatusDoc.NO_ERROR);

                writer.writeStatus(status);
            } catch (Exception ex) {
                logger.debug("An rror occured while saving the status", ex);
            }

            logger.debug("No new documents");
            schedulable.finished();
            shutdown();
            return;
        }

        String msgText = messageTemplate;
        String email = null;

        try {
            email = client.getUserEmail(subscription.getUser());
        } catch (RemoteException rex) {
            try {
                status.setLastRun(new Date());
                status.setExitCode(StatusDoc.MAIL_ERROR);

                writer.writeStatus(status);
            } catch (Exception ex) {
                logger.debug("An error occured while saving the status", ex);
            }

            logger.warn("Unable to get email address for user " + subscription.getUser(), rex);
            schedulable.abort();
            shutdown();
            return;
        }

        if (email == null) {
            try {
                status.setLastRun(new Date());
                status.setExitCode(StatusDoc.MAIL_ERROR);

                writer.writeStatus(status);
            } catch (Exception ex) {
                logger.debug("An error occured while saving the status", ex);
            }

            logger.warn("No email address set for user " + subscription.getUser()
                    + " or email notification deactivated");
            schedulable.abort();
            shutdown();
            return;
        }

        msgText = msgText.replace("{##documentlist##}", documentList);
        msgText = msgText.replace("{##user##}", "Administrator");
        msgText = msgText.replace("{##timestamp##}", timestamp.format(new java.util.Date()));
        msgText = msgText.replace("{##folder##}", sord.getName());
        msgText = msgText.replace("{##numberofdocuments##}", Integer.toString(nrOfDocuments));
        msgText = msgText.replace("{##subscription##}", subscription.getName());
        msgText = msgText.replace("{##mask##}", mask);

        try {
            logger.debug("New documents found. Sending mail.");

            MailQueueHandler handler = new MailQueueHandler(settings, msgText.toString(),
                    email, from, subscription.getSubject());
            handler.setTemplateFolder(templateFolder);
            handler.handleSchedulable(schedulable);

            logger.debug("Mail complete.");
        } catch (Exception ex) {
            try {
                status.setLastRun(new Date());
                status.setExitCode(StatusDoc.MAIL_ERROR);

                writer.writeStatus(status);
            } catch (Exception ex2) {
                logger.debug("An rror occured while saving the status", ex2);
            }

            logger.error("An exception occured while sending the new documents mail ", ex);
            schedulable.abort();
            shutdown();
            return;
        }

        try {
            status.setLastRun(new Date());
            status.setExitCode(StatusDoc.NO_ERROR);

            writer.writeStatus(status);
        } catch (Exception ex) {
            logger.debug("An rror occured while saving the status", ex);
        }

        schedulable.finished();
        shutdown();
    }

    @Override
    protected void init() {
        from = settings.getProperty("NewDocuments.from");

        templateFolder = new File(settings.getProperty("Templates.NewDocuments.TemplateFolder"));

        messageTemplate = readTemplateFile(
                new File(
                templateFolder,
                settings.getProperty("Templates.NewDocuments.TemplateFile")));

        subscription = schedulable.getSubscription();
    }
}

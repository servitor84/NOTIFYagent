package de.di.notifyagent.handlers;

import de.di.dokinform.mailsender.ImageFile;
import de.di.dokinform.mailsender.Metafile;
import de.di.dokinform.mailsender.MetafileWriter;
import de.di.notifyagent.jobs.Schedulable;
import de.di.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author A. Sopicki
 */
public class MailQueueHandler extends BaseSchedulableHandler {

    public File initialDir;
    private static Properties settings = null;
    private static File mailQueueDir = null;
    private static String signalFileExtension = null;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");
    private MetafileWriter writer = new MetafileWriter();
    private String body;
    private String recipients;
    private String from;
    private String subject;
    private File templateFolder = null;

    public MailQueueHandler(Properties props,
            String text,
            String recipients,
            String from,
            String subject) {

        super();

        logger = Logger.getLogger(getClass());

        body = text;
        this.recipients = recipients;
        this.from = from;
        this.subject = subject;

        if (settings == null) {
            settings = props;

            mailQueueDir = new File(settings.getProperty("Directories.MailQueue", ""));
            signalFileExtension = settings.getProperty("MailQueue.SignalFileExtension").toLowerCase();
        }
    }

    @Override
    public void handleSchedulable(Schedulable s) throws HandlerException {
        Date schedulableDate = new Date();

        String jobName = s.getProfile().getProfileName() + "_" + format.format(schedulableDate);
//        logger.debug("------------->JobName = " + jobName);
        try {
            logger.info("\t\tMoving files to mail queue directory. (Job: " + jobName + ")");

            setupMailQueue(s, jobName);

            logger.info("\t\tAll files moved to mail queue directory. (Job: " + jobName + ")");
        } catch (Exception ex) {
            logger.error("Error while moving files to mail queue directory!", ex);
            throw new HandlerException();
        }

        super.handleSchedulable(s);
    }

    private void setupMailQueue(Schedulable schedulable, String jobName) throws IOException {

        String basename = jobName;

        File jobDir = new File(mailQueueDir, basename);

        int i = 1;
        //make sure not to overwrite existing mail queue directory
        while (jobDir.exists()) {
            jobDir = new File(mailQueueDir, basename + "_" + i);

            if (i > 100000) { //give up eventually
                throw new IOException("Max number of job directories reached for profile "
                        + schedulable.getProfile().getProfileName());
            }
            i++;
            System.out.println("i value in MailQueueHandler.setupMailQueue: " + i);
        }

        File signalFile = new File(jobDir, jobDir.getName() + signalFileExtension);
        logger.debug("\t\tDirName = " + jobDir.getName());

        //hier wird Job-Ordner erzeugt
        if (!jobDir.mkdir()) {
            throw new IOException("Unable to create directory " + jobDir
                    + " for the new mail queue job.");
        }

        List<File> fileList = new java.util.LinkedList<File>();

        try {
            //hier wir eine leere SIG-Datei erzeugt
            if (!signalFile.createNewFile()) {
                throw new IOException("Unable to create signal file in mail queue directory!");
            }

            Metafile metafile = new Metafile();
            metafile.setSendAsHTML(Boolean.TRUE);
            metafile.setBody(body);
            metafile.setRecipients(recipients);
            metafile.setFrom(from);
            metafile.setSubject(subject);
            /*TODO: write an image file to the template*/
            /*@if it's decided to do this from the mailqueuehandler*/
            /**/

            logger.debug("Initial dir location: " + initialDir.getAbsolutePath());
            for (File children : initialDir.listFiles()) {
                /*if the file contains the cid */
                if (body.contains("cid:" + children.getName())) {
                    logger.debug("Found inline image: " + children.getAbsolutePath());
//                    FileUtils.openFileWrite(jobDir + "\\" + children.getName(), "", true);
                    logger.debug("After opening the image");
                    FileUtils.copyFileUsingStream(children, new File(jobDir + "\\" + children.getName()));
                     logger.debug("After copying the image content");
                    ImageFile imageFile = new ImageFile(initialDir, children.getName());
                    imageFile.setContentID(children.getName());
                    imageFile.setSend(true);
                    metafile.addImageFile(imageFile);
                }
            }
            logger.debug("Writing the metafile on hardLocation");
            //write the meta file
            writer.write(jobDir, metafile);

            //remove the signal file
            if (!signalFile.delete()) {
                throw new IOException("Unable to remove the signal file from the directory " + jobDir);
            }
        } catch (Exception ex) {
            cleanUp(jobDir, signalFile, fileList);

            throw new IOException("Unable to copy files to mail queue directory!", ex);
        }
    }

    private void cleanUp(File jobDir, File signalFile, List<File> fileList) {
        //try to remove any files which have already been moved to the mail queue directory
        for (File file : fileList) {
            if (file.compareTo(signalFile) == 0) {
                continue;
            }

            File destFile = new File(jobDir, file.getName());
            if (destFile.exists()) {
                try {
                    destFile.delete();
                } catch (Exception ex2) {
                }
            }
        }

        if (signalFile.exists()) {
            signalFile.delete();
        }

        jobDir.delete();
    }

    public File getTemplateFolder() {
        return templateFolder;
    }

    public void setTemplateFolder(File templateFolder) {
        this.templateFolder = templateFolder;
    }
}

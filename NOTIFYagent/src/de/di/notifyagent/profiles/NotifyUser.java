package de.di.notifyagent.profiles;

import de.di.notifyagent.jobs.NotifyJob;
import de.di.notifyagent.jobs.Schedulable;
import java.util.Properties;

/**
 *
 * @author A. Sopicki
 */
public class NotifyUser extends BaseProfile {

    public NotifyUser() {
    }

    private String afterHours;

    @Override
    public Schedulable createJob(int id, java.util.Properties props) {
        return new NotifyJob(id, props);
    }

    @Override
    public void init(Properties props) throws ProfileException {
        String prop = props.getProperty("NotifyUser.template");

        if (prop == null || prop.length() == 0) {
            throw new ProfileException("Missing property NotifyUser.template");
        }

        prop = props.getProperty("NotifyUser.from");

        if (prop == null || prop.length() == 0) {
            throw new ProfileException("Missing property NotifyUser.from");
        }

        prop = props.getProperty("NotifyUser.subject");

        if (prop == null || prop.length() == 0) {
            throw new ProfileException("Missing property NotifyUser.subject");
        }

        prop = props.getProperty("NotifyUser.maxWorkflows");

        if (prop == null || prop.length() == 0) {
            throw new ProfileException("Missing property NotifyUser.maxWorkflows");
        }

        try {
            int maxWorkflows = Integer.parseInt(prop);
        } catch (NumberFormatException nfe) {
            throw new ProfileException("Illegal number of workflows in profile");
        }

        prop = props.getProperty("NotifyUser.messageTemplate");

        if (prop == null || prop.length() == 0) {
            throw new ProfileException("Missing property NotifyUser.messageTemplate");
        }
       
        java.io.File templateFile = new java.io.File(prop);

        if (!templateFile.canRead()) {
            throw new ProfileException("Message template file " + prop + " not readable");
        }

         // SL
        prop = props.getProperty("SendImmediately");
        if (prop == null || prop.length() == 0) {
            throw new ProfileException("Missing property NotifyUser.SendImmediately");
        }
        
        workerProperties = new Properties();

        for (String name : props.stringPropertyNames()) {
//            if (name.startsWith(getClass().getSimpleName())) {
            workerProperties.setProperty(name, props.getProperty(name));
//            }
//            if (name.equals("Interval")) {
//            workerProperties.setProperty(name, props.getProperty(name));
//            }
        }
    }

    @Override
    public String getAfterHours() {
        return afterHours;
    }

    @Override
    public void setAfterHours(String afterHours) {
        this.afterHours = afterHours;
    }

}

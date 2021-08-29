package de.di.notifyagent.profiles;

import de.di.notifyagent.jobs.Schedulable;
import de.di.notifyagent.jobs.TaskListJob;
import java.util.Properties;

/**
 *
 * @author A. Sopicki
 */
public class TaskList extends BaseProfile {

    private String afterHours;
    
  public void init(Properties props) throws ProfileException {
    String prop = props.getProperty("TaskList.priorities");

    if ( prop == null || prop.length() == 0 ) {
      throw new ProfileException("Missing property TaskList.priorities");
    }

    if ( !prop.matches("[ABC]{1,3}")) {
      throw new ProfileException("Unknown priority in property TaskList.priorities");
    }

    prop = props.getProperty("TaskList.from");

    if ( prop == null || prop.length() == 0 ) {
      throw new ProfileException("Missing property TaskList.from");
    }

    prop = props.getProperty("TaskList.subject");

    if ( prop == null || prop.length() == 0 ) {
      throw new ProfileException("Missing property TaskList.subject");
    }

    prop = props.getProperty("TaskList.maxWorkflows");

    if ( prop == null || prop.length() == 0 ) {
      throw new ProfileException("Missing property TaskList.maxWorkflows");
    }

    try {
      int maxWorkflows = Integer.parseInt(prop);
    } catch(NumberFormatException nfe) {
      throw new ProfileException("Illegal number of workflows in profile");
    }


    prop = props.getProperty("TaskList.messageTemplate");

    if ( prop == null || prop.length() == 0 ) {
      throw new ProfileException("Missing property TaskList.messageTemplate");
    }

    java.io.File templateFile = new java.io.File(prop);

    if ( !templateFile.canRead() ) {
      throw new ProfileException("Message template file "+prop+" not readable");
    }


    workerProperties = new Properties();

    for(String name: props.stringPropertyNames()) {
      if ( name.startsWith(getClass().getSimpleName())) {
        workerProperties.setProperty(name, props.getProperty(name));
      }
    }
  }

    @Override
  public Schedulable createJob(int id, Properties props) {
    return new TaskListJob(id, props);
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


package de.di.notifyagent.jobs;

import de.di.notifyagent.workers.TaskListWorker;
import java.util.Properties;

/**
 *
 * @author A. Sopicki
 */
public class TaskListJob extends Job {

  public TaskListJob(int id, Properties settings) {
    super(id);

    worker = new TaskListWorker(this, settings);
  }

}

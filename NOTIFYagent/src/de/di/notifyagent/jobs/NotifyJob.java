
package de.di.notifyagent.jobs;

import de.di.notifyagent.workers.NotifyWorker;
import java.util.Properties;

/**
 *
 * @author A. Sopicki
 */
public class NotifyJob extends Job {

  public NotifyJob(int id, Properties settings) {
    super(id);

    worker = new NotifyWorker(this, settings);
  }

  
}

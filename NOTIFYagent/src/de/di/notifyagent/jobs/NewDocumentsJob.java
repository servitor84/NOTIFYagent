
package de.di.notifyagent.jobs;

import de.di.notifyagent.workers.NewDocumentsWorker;
import java.util.Properties;

/**
 *
 * @author A. Sopicki
 */
public class NewDocumentsJob extends Job {

  public NewDocumentsJob(int id, Properties settings) {
    super(id);

    worker = new NewDocumentsWorker(this, settings);
  }
}

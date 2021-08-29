
package de.di.notifyagent.jobs;

import de.di.notifyagent.workers.NewFoldersWorker;
import java.util.Properties;

/**
 *
 * @author A. Sopicki
 */
public class NewFoldersJob extends Job {

  public NewFoldersJob(int id, Properties settings) {
    super(id);

    worker = new NewFoldersWorker(this, settings);
  }
}

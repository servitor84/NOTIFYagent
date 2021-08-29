package de.di.notifyagent.jobs;

import de.di.dokinform.recovery.Id;
import elo.di.notifyagent.elo.ELOClient;
import de.di.notifyagent.profiles.NotifyUser;
import de.di.notifyagent.profiles.Profile;
import java.util.Properties;

/**
 *
 * @author A. Sopicki
 */
public class SchedulableFactory {

  private Id nextJobId = new Id();
  
  private Properties settings;

  private ELOClient client;

  public SchedulableFactory(Properties settings, ELOClient client) {
    this.settings = settings;

    this.client = client;
  }

  public Schedulable createJob(Profile p) throws IllegalArgumentException {

    Schedulable s = null;

    s = p.createJob(nextJobId.nextId(), settings);
    s.setEloClient(client);

    return s;
  }

  public void setNextJobId(int i) {
    nextJobId.setId(i);
  }

  public int getLastJobId() {
    return nextJobId.getLastId();
  }
}

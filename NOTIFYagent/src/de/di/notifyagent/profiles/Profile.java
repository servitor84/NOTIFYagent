
package de.di.notifyagent.profiles;

import elo.di.notifyagent.elo.ELOClient;
import de.di.notifyagent.jobs.Schedulable;
import java.util.Properties;

/**
 *
 * @author A. Sopicki
 */
public interface Profile {

  public String getProfileName();

  public void setProfileName(String name);
  
  public String getAfterHours();
  
  public void setAfterHours(String afterHours);

  public void init(java.util.Properties props) throws ProfileException;

  public void setClient(ELOClient client);

  public Properties getWorkerProperties();

  public String getRunPattern();

  public void setRunPattern(String pattern);

  public void setTest(String test);
  
  public String getTest();
  
  public Schedulable createJob(int id, java.util.Properties props);

}

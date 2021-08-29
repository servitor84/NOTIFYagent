
package de.di.notifyagent.profiles;

import de.di.notifyagent.jobs.NewDocumentsJob;
import de.di.notifyagent.jobs.Schedulable;
import java.util.Properties;

/**
 *
 * @author A. Sopicki
 */
public class NewDocuments extends BaseProfile {

  public void init(Properties props) throws ProfileException {
    workerProperties = new Properties();

    for(String name: props.stringPropertyNames()) {
      if ( name.startsWith(getClass().getSimpleName())) {
        workerProperties.setProperty(name, props.getProperty(name));
      }

      //@TODO: Get template from ELO
      if ( name.startsWith("Templates.NewDocuments") ) {
        workerProperties.setProperty(name, props.getProperty(name));
      }
      else{
          workerProperties.setProperty(name, props.getProperty(name));
      }
    }

    for(String name: props.stringPropertyNames()) {
      if ( name.startsWith("doc.")) {
        workerProperties.setProperty(name, props.getProperty(name));
      }
    }
    
  }

  public Schedulable createJob(int id, Properties props) {
    Schedulable s = new NewDocumentsJob(id, props);
    s.setSubscription(getSubscription());

    return s;
  }

    @Override
    public String getAfterHours() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setAfterHours(String afterHours) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

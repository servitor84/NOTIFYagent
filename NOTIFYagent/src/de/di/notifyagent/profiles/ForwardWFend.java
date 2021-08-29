/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.di.notifyagent.profiles;

import de.di.notifyagent.jobs.ForwardWFendJob;
import de.di.notifyagent.jobs.Schedulable;
import java.util.Properties;

/**
 *
 * @author Rahman
 */
public class ForwardWFend extends BaseProfile {

    private String afterHours;
    

    @Override
    public String getAfterHours() {
        return afterHours;
    }

    @Override
    public void setAfterHours(String afterHours) {
        this.afterHours = afterHours;
    }
   
    @Override
    public void init(Properties props) throws ProfileException {
        workerProperties = new Properties();

        for (String name : props.stringPropertyNames()) {
            if (name.startsWith(getClass().getSimpleName())) {
                workerProperties.setProperty(name, props.getProperty(name));
            }
            else {
                workerProperties.setProperty(name, props.getProperty(name));
            }
        }
    }

    @Override
    public Schedulable createJob(int id, Properties props) {
        return new ForwardWFendJob(id, props);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.di.notifyagent.profiles;

import de.di.notifyagent.jobs.Schedulable;
import java.util.Properties;

/**
 *
 * @author Rahman
 */
public class TestProfile extends BaseProfile {

    @Override
    public String getAfterHours() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setAfterHours(String afterHours) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init(Properties props) throws ProfileException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Schedulable createJob(int id, Properties props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.di.notifyagent.jobs;

import de.di.notifyagent.workers.ForwardWFstartWorker;
import java.util.Properties;

/**
 *
 * @author Rahman
 */
public class ForwardWFstartJob extends Job {

    public ForwardWFstartJob(int id, Properties settings) {
        super(id);
        worker = new ForwardWFstartWorker(this, settings);

    }
}

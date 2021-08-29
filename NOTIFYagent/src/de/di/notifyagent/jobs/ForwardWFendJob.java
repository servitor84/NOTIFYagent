/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.di.notifyagent.jobs;

import de.di.notifyagent.workers.ForwardWFendWorker;
import java.util.Properties;

/**
 *
 * @author Rahman
 */
public class ForwardWFendJob extends Job{
    public ForwardWFendJob(int id, Properties settings) {
        super(id);
        worker = new ForwardWFendWorker(this, settings);

    }
}

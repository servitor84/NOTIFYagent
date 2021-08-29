/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.di.notifyagent.jobs;

import de.di.notifyagent.workers.ForwardWFsetUsersWorker;
import java.util.Properties;

/**
 *
 * @author Rahman
 */
public class ForwardWFsetUsersJob extends Job {

    public ForwardWFsetUsersJob(int id, Properties settings) {
        super(id);
        worker = new ForwardWFsetUsersWorker(this, settings);

    }
}

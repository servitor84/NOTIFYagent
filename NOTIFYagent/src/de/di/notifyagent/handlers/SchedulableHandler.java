package de.di.notifyagent.handlers;

import de.di.notifyagent.jobs.Schedulable;

/**
 *
 * @author A. Sopicki
 */
public interface SchedulableHandler {

    public void handleSchedulable(Schedulable s) throws HandlerException;

    public void setNextHandler(SchedulableHandler next);

    public SchedulableHandler getNextHandler();
}

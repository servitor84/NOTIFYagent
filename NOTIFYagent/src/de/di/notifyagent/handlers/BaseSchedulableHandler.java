
package de.di.notifyagent.handlers;

import de.di.notifyagent.jobs.Schedulable;
import org.apache.log4j.Logger;

/**
 * Abstract class implementing basic functions for a SchedulableHandler.
 *
 * @author A. Sopicki
 */
public abstract class BaseSchedulableHandler implements SchedulableHandler {

  protected SchedulableHandler nextHandler;

  protected Logger logger;

  protected BaseSchedulableHandler() {
  }


  @Override
  public final void setNextHandler(SchedulableHandler next) {
    nextHandler = next;
  }

  @Override
  public final SchedulableHandler getNextHandler() {
    return nextHandler;
  }

  @Override
  public void handleSchedulable(Schedulable s) throws HandlerException {
    if ( nextHandler != null ) {
      nextHandler.handleSchedulable(s);
    }
  }

}

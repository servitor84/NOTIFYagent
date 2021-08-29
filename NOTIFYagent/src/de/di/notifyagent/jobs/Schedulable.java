/*
 * Schedulable.java
 *
 * Created on 18. Januar 2007, 11:54
 *
 */
package de.di.notifyagent.jobs;

import de.di.dokinform.notifyabo.SubscriptionDoc;

import elo.di.notifyagent.elo.ELOClient;
import de.di.notifyagent.profiles.Profile;
import de.di.notifyagent.workers.Worker;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author A. Sopicki
 */
public interface Schedulable extends Delayed {

    /**
     *
     * Enumeration for the status code of the job
     *
     * @author A. Sopicki
     */
    public enum Status {

        DONE(0), CREATED(1), QUEUED(2), WORK_IN_PROGRESS(3), DEFERRED(80),
        ABORTED(100), FAILED(101);
        private int _value;

        Status(int value) {
            _value = value;
        }

        public int getValue() {
            return _value;
        }
    }

    public enum Progress {

        NEW(0), IN_PROGRESS(1), CHECK_COMPLETE(2), IMPORT_COMPLETE(3), COMPLETE(100);
        private int _value;

        Progress(int value) {
            _value = value;
        }

        public int getValue() {
            return _value;
        }
    }

    public int getId();

    public void finished();

    public void abort();

    public boolean isFinished();

    public Status getStatus();

    public void setDelay(long d, TimeUnit unit);

    public void setStatus(Status status);

    public int getErrorCount();

    public void increaseErrorCount();

    public Progress getProgressStatus();

    public void setProgressStatus(Progress p);

    public long getStartTime();

    public void setStartTime(long time);

    public void addSchedulableListener(SchedulableListener listener);

    public void removeSchedulableListener(SchedulableListener listener);

    public String getUID();

    public Worker getWorker();

    public ELOClient getEloClient();

    public void setEloClient(ELOClient c);

    public void setProfile(Profile p);

    public Profile getProfile();

    public SubscriptionDoc getSubscription();

    public void setSubscription(SubscriptionDoc doc);
}

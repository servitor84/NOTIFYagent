/*
 * Created on 28.04.2006
 *
 */
package de.di.notifyagent.jobs;

import de.di.dokinform.notifyabo.SubscriptionDoc;
import elo.di.notifyagent.elo.ELOClient;
import de.di.notifyagent.profiles.Profile;
import de.di.notifyagent.workers.Worker;
import java.util.ArrayList;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public abstract class Job implements Schedulable {

    /**
     * Id of the job
     */
    private int id = 0;
    /**
     * Creation time of the job for timeout checks
     */
    private long timestamp = System.currentTimeMillis();
    /**
     * Return code of the job
     */
    private Status returnCode = Status.CREATED;
    private String uid = null;
    private ArrayList<SchedulableListener> listeners = new ArrayList<SchedulableListener>();
    private int errorCount = 0;
    /**
     * Timestamp for scheduling a job
     */
    private long executionTime = System.currentTimeMillis();
    private TimeUnit unit = TimeUnit.MILLISECONDS;
    private Progress progress = Schedulable.Progress.NEW;
    private long startTime = 0L;
    private ELOClient eloClient;
    private Profile profile;
    protected Worker worker;

    public Job(int id) {
        this.id = id;


        uid = (new Integer(id).toString()) + "_" + executionTime;
    }

    /**
     * Returns true if every switch of the job has been
     * updated or the job has been aborted and false otherwise.
     * @return true if the job has been aborted or finished
     */
    @Override
    public boolean isFinished() {
        if (returnCode != Schedulable.Status.CREATED) {
            return true;
        }

        return false;
    }

    @Override
    public Status getStatus() {
        return returnCode;
    }

    @Override
    public void setStatus(Status status) {
        returnCode = status;
    }

    /**
     * Returns the id of the job
     * @return the id of the job
     */
    @Override
    public int getId() {
        return id;
    }

    public String getUID() {
        return uid;
    }

    /**
     * Returns the creation time of the job
     * @return the creation time of the job
     */
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void finished() {
        fireJobFinished();
    }

    protected void fireJobFinished() {
        returnCode = Status.DONE;
        for (SchedulableListener l : listeners) {
            l.finished(this);
        }

        listeners.clear();
    }

    @Override
    public void addSchedulableListener(SchedulableListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeSchedulableListener(SchedulableListener listener) {
        listeners.remove(listener);
    }

    @Override
    public String toString() {
        return getUID();
    }

    @Override
    public int getErrorCount() {
        return errorCount;
    }

    @Override
    public void increaseErrorCount() {
        errorCount++;
    }

    @Override
    public void abort() {
        fireJobAborted();
    }

    protected void fireJobAborted() {
        setStatus(Schedulable.Status.ABORTED);

        for (SchedulableListener l : listeners) {
            l.finished(this);
        }

        listeners.clear();
    }

    @Override
    public Progress getProgressStatus() {
        return progress;
    }

    @Override
    public void setProgressStatus(Progress p) {
        progress = p;
    }

    @Override
    public long getDelay(TimeUnit u) {
        return u.convert(executionTime - System.currentTimeMillis(), unit);
    }

    @Override
    public int compareTo(Delayed other) {
        long d = other.getDelay(unit);
        long delay = executionTime - System.currentTimeMillis();

        if (d < delay) {
            return 1;
        }

        if (d > delay) {
            return -1;
        }

        return 0;
    }

    @Override
    public void setDelay(long d, TimeUnit tu) {
        executionTime = System.currentTimeMillis() + unit.convert(d, tu);
    }

    @Override
    public long getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(long time) {
        startTime = time;
    }

    /**
     * @return the eloClient
     */
    @Override
    public ELOClient getEloClient() {
        return eloClient;
    }

    /**
     * @param eloClient the eloClient to set
     */
    @Override
    public void setEloClient(ELOClient eloClient) {
        this.eloClient = eloClient;
    }

    /**
     * @return the profile
     */
    @Override
    public Profile getProfile() {
        return profile;
    }

    /**
     * @param profile the profile to set
     */
    @Override
    public void setProfile(Profile profile) {
        this.profile = profile;

        getWorker().addWorkerProperties(profile.getWorkerProperties());
    }

    @Override
    public Worker getWorker() {
        return worker;
    }

    @Override
    public SubscriptionDoc getSubscription() {
        return null;
    }

    @Override
    public void setSubscription(SubscriptionDoc doc) {
    }
}

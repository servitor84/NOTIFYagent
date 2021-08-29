package de.di.notifyagent.app;

import de.di.dokinform.scheduler.AbstractTask;
import de.di.dokinform.scheduler.Executor;
import de.di.dokinform.scheduler.PatternException;
import de.di.dokinform.scheduler.Scheduler;
import de.di.dokinform.scheduler.Task;
import de.di.notifyagent.jobs.Schedulable;
import de.di.notifyagent.jobs.SchedulableFactory;
import de.di.notifyagent.jobs.SchedulableListener;
import de.di.notifyagent.jobs.Schedulable.Status;
import de.di.notifyagent.profiles.Profile;
import de.di.notifyagent.profiles.ProfileManager;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 * The dispatcher will schedule the export of the data to the SAP-MAP database.
 *
 * @author A. Sopicki
 */
public class Dispatcher extends Thread implements SchedulableListener, Executor {
    
    private Properties settings = null;    
    private volatile boolean running = true;
    
    private volatile boolean idle = false;
    private SchedulableFactory schedulableFac = null;
    private Logger logger = Logger.getLogger(Dispatcher.class);
    private int pollTime = 5000;
    
    private static final Object mutex = new Object();
    
    private HashMap<String, Schedulable> jobList = new HashMap<String, Schedulable>();

    /**
     * Different states of the Dispatcher:
     *
     * Idle: Not processing any workflows Processing: Processing workflows
     * Finished: Shutting down application
     */
    public enum State {
        
        Idle, Processing, Finished
    }
    
    private State state = State.Idle;
    
    private Scheduler scheduler;
    
    private ProfileManager profileManager;

    /**
     * Singleton constructor
     *
     * @param scheduler the scheduler to use for scheduling jobs
     * @sa Dispatcher.getDispatcher(Scheduler scheduler)
     */
    Dispatcher(Properties settings, SchedulableFactory fac, ProfileManager manager) throws ApplicationException {
        
        super("DispatcherThread");
        logger.debug("Creating dispatcher");
        this.schedulableFac = fac;
        logger.debug("Creating dispatcher");
        this.settings = settings;
        logger.debug("After se");
        //face un scheduler object
        logger.debug("before scheduler");
        scheduler = new Scheduler(this);
        profileManager = manager;
        logger.debug("before init");
        init();
    }
    
    @Override
    public void run() {
        logger.trace(getClass().getName() + ": entering run()");
        
        scheduler.start();
                
        while (state != State.Finished) {               
            idle = true && running; //mark thread as idle            
            try {
                if (idle) {
                    Thread.sleep(pollTime);
                }
            } catch (InterruptedException ex) {
            }
            idle = false;            
        }         
        
        scheduler.shutdown();
        
        synchronized (mutex) {
            for (Schedulable job : jobList.values()) {
                job.getWorker().shutdown();
                
                try {
                    if (job.getWorker().isAlive()) {
                        job.getWorker().join(500);
                    }
                } catch (InterruptedException iex) {
                    
                }
            }            
            jobList.clear();
        }        
        try {
            scheduler.join(500);
        } catch (InterruptedException iex) {
            
        }        
        scheduler = null;        
        logger.trace(getClass().getName() + ": leaving run()");
        //cleanup
        settings = null;
        logger = null;
    }
    
    public synchronized void shutdown() {
        running = false;
        state = State.Finished;

        //if thread is idle send an interrupt
        if (idle) {
            this.interrupt();
        }
    }
    
    public State getProcessingState() {
        return state;
    }
    
    @Override
    public void execute(Task t) {
        ProfileTask pTask = (ProfileTask) t;
        Profile p = pTask.getProfile();
        
        if (state == State.Finished) {
            return;
        }
        synchronized (mutex) {
            try {
                Schedulable s = schedulableFac.createJob(p);
                
                jobList.put(p.getProfileName(), s);
                s.setProfile(p);
                s.addSchedulableListener(this);
                if (state != State.Finished) {
                    state = State.Processing;
                    s.getWorker().start();
                    // restrict the jobs creation => only once
//                    try {
//                        join(60000);
//                    } catch (InterruptedException ex) {
//                        java.util.logging.Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
//                    }                    
                }
            } catch (IllegalArgumentException iaex) {
                logger.error("Unable to handle profile " + p.getProfileName()
                        + " because of an exception. Stopping dispatcher.", iaex);
                
                shutdown();
            }
        }
        
    }

    //------------------------------ SchedualbleListener interface ---------------------------
    @Override
    public void finished(Schedulable s) {
        String profileName = s.getProfile().getProfileName();
        
        Status status = s.getStatus();
        if (status == Schedulable.Status.DONE && logger != null) {
            logger.info("Job for profile " + profileName + " finished successfully.");
        } else if (status == Schedulable.Status.FAILED || status == Schedulable.Status.ABORTED) {
            if (logger != null) {
                logger.warn("An error occured while processing profile " + profileName);
            }
        }
        
        if (state != State.Finished) {
            synchronized (mutex) {
                jobList.remove(profileName);
                
                if (jobList.isEmpty()) {
                    state = State.Idle;
                }
            }
        }
    }
    
    private void init() throws ApplicationException {
        logger.debug("init");
        try {
            for (Profile p : this.profileManager.getProfiles()) {
                logger.debug("Adding : " + p.getProfileName() + "pattern: " + p.getRunPattern());
                this.scheduler.addTask(p.getRunPattern(), new ProfileTask(p));
            }
        } catch (PatternException pex) {
            this.logger.error("Pattern error detected", pex);
            
            throw new ApplicationException("An error occured during dispatcher initialisation");
        }
        logger.debug("after init");
    }
    
    private final class ProfileTask extends AbstractTask {
        
        private final Profile profile;
        private long interval;
        private long lastRuntime;
        private int type;
        
        ProfileTask(Profile p) {
            profile = p;
            
            logger.debug("In ProfileTask constructor");
            setInterval(Integer.parseInt(p.getWorkerProperties().getProperty("Interval", "0")) * 1000);
            logger.debug("setting interval to: " + Long.parseLong(p.getWorkerProperties().getProperty("Interval", "0")) * 1000);
            setType(Integer.parseInt(p.getWorkerProperties().getProperty("Type", "0")));
            //logger.debug("setting type to: " + type);
            logger.debug("setting type to: " + Integer.parseInt(p.getWorkerProperties().getProperty("Type", "0")));
        }
        
        public Profile getProfile() {
            return profile;
        }
        
        //@Override
        public long getInterval() {
            return interval;
        }
        
        //@Override
        public void setInterval(long interval) {
            this.interval = interval;
        }
        
        //@Override
        public long getLastRuntime() {
            return lastRuntime;
        }
        
        //@Override
        public void setLastRuntime(long l) {
            lastRuntime = l;
        }

        //@Override
        public void setType(int type) {
            this.type = type;
        }

        //@Override
        public int getType() {
            return type;
        }
        
    }
}

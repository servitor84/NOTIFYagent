package de.di.notifyagent.profiles;

import de.di.dokinform.notifyabo.SubscriptionDoc;
import elo.di.notifyagent.elo.ELOClient;
import java.util.Properties;

/**
 *
 * @author A. Sopicki
 */
public abstract class BaseProfile implements Profile {

    protected String profileName;
    private ELOClient client;
    protected Properties workerProperties;
    private String runPattern;
    private SubscriptionDoc subscription;
    private String test;
    
    public String getTest() {
        return test;
    }
    
    public void setTest(String test) {
        this.test = test;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String name) {
        profileName = name;
    }

    public ELOClient getClient() {
        return client;
    }

    public void setClient(ELOClient client) {
        this.client = client;
    }

    public Properties getWorkerProperties() {
        return workerProperties;
    }

    public String getRunPattern() {
        return runPattern;
    }

    /**
     * @param runPattern the runPattern to set
     */
    public void setRunPattern(String runPattern) {
        this.runPattern = runPattern;
    }

    /**
     * @return the subscription
     */
    public SubscriptionDoc getSubscription() {
        return subscription;
    }

    /**
     * @param subscription the subscription to set
     */
    public void setSubscription(SubscriptionDoc subscription) {
        this.subscription = subscription;
    }
}

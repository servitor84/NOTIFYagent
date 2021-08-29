package de.di.notifyagent.profiles;

import elo.di.notifyagent.elo.ELOClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import org.apache.log4j.Logger;

// <editor-fold defaultstate="collapsed" desc="constructor-documentation">  
/**
 *
 * @author A. Sopicki
 */
//</editor-fold>
public class ProfileManager {

    private static final Logger logger = Logger.getLogger(ProfileManager.class);

    private final java.util.HashMap<String, Profile> profiles = new java.util.HashMap<String, Profile>();
    private final ELOClient client;
    private final java.util.HashMap<String, Profile> abonnements = new java.util.HashMap<String, Profile>();

    public ProfileManager(ELOClient client) {
        this.client = client;
    }

    public java.util.Collection<Profile> getProfiles() {
        return profiles.values();
    }

    public void loadProfiles(File profilesDir) {
        File[] profileFiles = profilesDir.listFiles(new ProfileFilenameFilter());

        for (File profileFile : profileFiles) {
            try {
                Properties props = new Properties();
                FileInputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(profileFile);

                    props.load(inputStream);
                } catch (IOException ioex) {
                    this.logger.debug("An exception occured while loading profile " + profileFile.getName(), ioex);
                    throw new ProfileException("Unable to load profile " + profileFile.getName());
                } finally {
                    try {
                        inputStream.close();
                    } catch (Exception ex) {
                    }
                }
                String profileName = props.getProperty("ProfileName", "");
                String action = props.getProperty("Action", "");
                String sendDays = props.getProperty("SendDays", "");
                String sendHour = props.getProperty("SendHour", "");
                String afterHours = props.getProperty("NotifyUser.AfterHours", "0");             
                boolean immediately = Boolean.parseBoolean(props.getProperty("SendImmediately", "FALSE"));
                // Testcolumn
//                String testMode = props.getProperty("SendImmediately", "xx");
                
                

                if (profileName.length() == 0) {
                    throw new ProfileException("Missing profile name in profile file " + profileFile.getName());
                }

                if (action.length() == 0) {
                    throw new ProfileException("Missing action for profile file " + profileFile.getName());
                }

                if (sendDays.length() == 0) {
                    throw new ProfileException("Missing property SendDays for profile file " + profileFile.getName());
                }
                if (sendHour.length() == 0) {
                    throw new ProfileException("Missing property SendHour for profile file " + profileFile.getName());
                }

                if (this.profiles.containsKey(profileName)) {
                    throw new ProfileException("Profile " + profileName + " already exists! Duplicate profile found" + " in file " + profileFile.getName());
                }

                Profile profile = null;
                try {
                    Class c = Class.forName("de.di.notifyagent.profiles." + action);

                    profile = (Profile) c.newInstance();
                } catch (ClassNotFoundException cnfe) {
                    this.logger.debug("Unable to load class for action " + action, cnfe);
                    throw new ProfileException("Action " + action + " not supported in profile file" + profileFile.getName());
                } catch (IllegalAccessException iae) {
                    this.logger.debug("Unable to instantiate profile for action " + action, iae);
                    throw new ProfileException("Action " + action + " not supported in profile file" + profileFile.getName());
                } catch (InstantiationException ie) {
                    this.logger.debug("Unable to instantiate profile for action " + action, ie);
                    throw new ProfileException("Action " + action + " not supported in profile file" + profileFile.getName());
                } catch (ClassCastException cce) {
                    this.logger.debug("Implementing class for action " + action + " must implement the profile interface", cce);

                    throw new ProfileException("Action " + action + " not supported in profile file" + profileFile.getName());
                } catch (Exception ex) {
                    this.logger.debug("An exception occured while creating the profile for action " + action, ex);
                    throw new ProfileException("Unable to load profile from file" + profileFile.getName());
                }

                profile.setProfileName(profileName);
                profile.setAfterHours(afterHours);                    
                
                if (immediately) {
                    profile.setRunPattern("@once");                                    
                } else {
                    profile.setRunPattern(toRunPattern(
                                            sendDays, 
                                            sendHour, 
                                            profileFile.getName()));                                                            
                }

                profile.setClient(this.client);
                try {
                    profile.init(props);
                } catch (ProfileException pfe) {
                    this.logger.warn("Unable to initialize profile from file" + profileFile.getName());
                    throw pfe;
                }

                this.profiles.put(profileName, profile);                
            } catch (Exception ex) {
                logger.warn("Error ocured in profile file: " + profileFile.getName());
                logger.warn(ex);
            }
        }
    }

    public int getProfileCount() {
        if (profiles == null) {
            return 0;
        }

        return profiles.size();
    }

    public Profile getProfile(String name) {
        return profiles.get(name);
    }

    private String toRunPattern(String sendDays, String sendHour, String fileName) throws ProfileException {
        String pattern = "0 ";

        String[] hours = sendHour.split(";");
        int h = 0;

        for (String hour : hours) {
            try {
                h = Integer.parseInt(hour);
                if (h < 0 || h > 23) {
                    throw new ProfileException("Illegal value '" + sendHour + "' for SendHour found in profile file "
                            + fileName + ". Values must be between 0 and 23.");
                }
//                pattern += hour + " * * ";
            } catch (NumberFormatException nfe) {
                throw new ProfileException("Illegal value '" + sendHour + "' for SendHour found in profile file "
                        + fileName);
            }
        }

         pattern += sendHour.replace(";", ",") + " * * ";
        
        String[] days = sendDays.split(";");

        for (String day : days) {
            int value = 0;
            try {
                value = Integer.parseInt(day);

                if (value < 1 || value > 7) {
                    throw new ProfileException("Illegal value '" + sendDays + "' for SendDays found in profile file "
                            + fileName + ". Values must be between 1 and 7.");
                }
            } catch (NumberFormatException nfe) {
                throw new ProfileException("Illegal value '" + sendDays + "' for SendDys found in profile file "
                        + fileName);
            }
        }

        pattern += sendDays.replace(';', ',');

        return pattern;
    }
    
    public static void main(String[] args) throws ProfileException {
        ProfileManager pm = new ProfileManager(null);
        System.out.println(pm.toRunPattern("1", "7;6;8", "test"));
                
    }
}

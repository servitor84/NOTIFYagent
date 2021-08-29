package de.di.notify.gui;

/**
 *
 * @author E. Rahman
 */
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;

public class GUILogger {

    private static final String LOG4J_CONFIG_FILE = "log4j-5.xml";
    private static final String MEIN_LOGGER_NAME = "GUILogger";
    private static final String MESSAGES_RESBUNDLE = "messages";
    private static ResourceBundle messagesResBundle;
    private static GUILogger guiLogger;
    private static Logger log4jLogger = Logger.getRootLogger();
    private static String LOGGER_NAME = "";

    private GUILogger() {
        init();
    }

    private synchronized void init() {
        try {
            DOMConfigurator.configureAndWatch(LOG4J_CONFIG_FILE, 60 * 1000);
            log4jLogger = Logger.getLogger(MEIN_LOGGER_NAME);
            log4jLogger.setResourceBundle(messagesResBundle);
            LOGGER_NAME = ResourceBundle.getBundle("de/di/notify/resources/product").getString("logger.name");
        } catch (MissingResourceException ex) {
            System.err.println("Fehler: '" + MESSAGES_RESBUNDLE + "'-.properties-Datei fehlt!");
        } catch (Exception ex) {
            System.err.println("Fehler bei Logger-Initialisierung!");
        }
    }

    public static synchronized GUILogger getInstance() {
        if (guiLogger == null) {
            guiLogger = new GUILogger();
        }
        return guiLogger;
    }

    public synchronized void log(String id) {
        PatternLayout layout = new PatternLayout("%d{dd.MM.yyyy HH:mm:ss} %-5p [%t]: %m%n");
        DailyRollingFileAppender dailyRollingFileAppender = null;
        try {
            dailyRollingFileAppender = new DailyRollingFileAppender(layout, "C:/DOKinform/logs/" + LOGGER_NAME + ".log", "'.'yyyy-MM-dd_HH-mm" /*true*/);
            log4jLogger.addAppender(dailyRollingFileAppender);
            log4jLogger.setLevel(Level.ALL);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), "GUILogger.log can not be found", "Error Message", JOptionPane.WARNING_MESSAGE);
        }
        log4jLogger.info(id);
        log4jLogger.removeAppender(dailyRollingFileAppender);
    }

    public boolean isEnabledFor(Level level) {
        return log4jLogger.isEnabledFor(level);
    }
}

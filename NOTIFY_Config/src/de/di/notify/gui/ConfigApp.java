package de.di.notify.gui;

import de.di.dokinform.elo.ELOClientNG;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The main class of the application.
 */
public class ConfigApp extends SingleFrameApplication {

    private static String bundleName; //= "notify/de/di/notify/gui/resources/ConfigApp";
    private static String bundleName1; //= "notify/de/di/notify/resources/product";
    private static GUILogger logger = GUILogger.getInstance();
    Config config = null;
    public static ELOClientNG client;
    public static String productName;
    
    public ConfigApp() {
        File file = new File("notify/de/di/notify/gui/resources/ConfigApp");
        try {
            FileInputStream fis = new FileInputStream(file);
            bundleName = "notify/de/di/notify/gui/resources/ConfigApp";
            bundleName1 = "notify/de/di/notify/resources/product";
        } catch (FileNotFoundException ex) {
            bundleName = "contractmanager/de/di/notify/gui/resources/ConfigApp";
            bundleName1 = "contractmanager/de/di/notify/resources/product";
        }
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        System.out.println("### Enter Startup");
        BasicConfigurator.configure();
        org.apache.log4j.Logger.getRootLogger().setLevel(Level.WARN);
        System.out.println("### Loading Config file ...");
        config = loadConfig();    
        System.out.println("### Config file loaded!");
        try {
            System.out.println("### ELOClientNG Constructor Call ...");
            client = new ELOClientNG(config);
            System.out.println("### ELOClientNG Constructor Called!");
        } catch (Exception ex) {
            String error = "Configuration file can't be loaded!";
            logger.log(error);
            logger.log(ex.getMessage());            
        }
        show(new ConfigView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     * @param root
     */
    @Override
    protected void configureWindow(java.awt.Window root) {               
        getMainFrame().setIconImage(getContext().getResourceMap(getClass()).getImageIcon("fav.icon").getImage());
        getMainFrame().setTitle(java.util.ResourceBundle.getBundle(bundleName).getString("Application.title") + ' '
                + java.util.ResourceBundle.getBundle(bundleName1).getString("app.version") + " Build("
                + java.util.ResourceBundle.getBundle(bundleName1).getString("app.build") + ")");
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of ERPGUIApp
     */
    public static ConfigApp getApplication() {
        return Application.getInstance(ConfigApp.class);
    }

    public Config getConfig() {
        return config;
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        Date timeStamp = new Date();
        SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String message = "The ConfigurationGUI is started at " + date.format(timeStamp) + "\n";
        message += "From user : " + System.getProperty("user.name") + "\n";           
        try {
            java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
            message += "Host machine : " + localMachine.getHostName();
        } catch (Exception ex) {
        }
        logger.log(message);        
        launch(ConfigApp.class, args);
    }

    void saveConfig() throws Exception {
        File configFile = null;

        OutputStream configStream = null;

        try {
            configFile = new File("../conf/config.properties");

            if (configFile.canWrite() == false) {
                throw new Exception(java.util.ResourceBundle.getBundle(
                        bundleName).
                        getString("noWriteAccess.text"));
            }
        } catch (Exception ex) {
            throw new Exception(java.util.ResourceBundle.getBundle(
                    bundleName).
                    getString("unableToWriteFile.text"));
        }

        try {
            configStream = new FileOutputStream(configFile);
        } catch (FileNotFoundException fex) {
            throw new Exception(java.util.ResourceBundle.getBundle(
                    bundleName).
                    getString("canNotWriteFile.text"));
        }


        try {
            this.config.store(configStream, "Configuration file");
        } catch (Exception ioex) {
            throw new Exception(java.util.ResourceBundle.getBundle(
                    bundleName).
                    getString("errorOnWritingFile.text"));
        }

    }

    private Config loadConfig() {
        Config props = new Config();

        Logger log = Logger.getAnonymousLogger();        
        
        File configFile = null;
        
        // +++       
//        Logger log = Logger.getRootLogger(); 
//        
//        FileAppender fa = new FileAppender();
//        fa.setName("FileLogger");
//        fa.setFile("C:/DOKinform/logs/mylog.log");
//        fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"/*"%d{dd.MM.yyyy HH:mm:ss} %-5p [%t]: %m%n"*/));
//        fa.setThreshold(Level.ALL);
//        fa.setAppend(true);
//        fa.activateOptions();
//                
//        log.addAppender(fa);          
        // +++

        InputStream configStream = null;
        try {
            configFile = new File("../conf/config.properties");            
           
            if (configFile.canRead() == false) {
                logger.log(java.util.ResourceBundle.getBundle(bundleName).getString("fileUnreadable.text"));
                
                //TODO: Handle error
                return props;
            }
        } catch (Exception ex) {
            //TODO: Handle exception

            log.info(ex.getMessage());
            return props;
        }

        try {
            configStream = new FileInputStream(configFile);
        } catch (FileNotFoundException fex) {
            //TODO: Handle exception
            return props;
        }


        try {
            props.load(configStream);
            // PWD: Decode ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            String password = props.getProperty("IndexServer.Password");
            String pattern = "^((\\d){1,})([-]{1}(\\d){1,}){1,}";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher matcher = p.matcher(password);
            if(matcher.matches()) {
//                de.elo.utils.sec.DesEncryption des = new de.elo.utils.sec.DesEncryption();
//                password = des.decrypt(password);
            }
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++SL 17.03.2020
            props.setProperty("IndexServer.Password", password);  
        } catch (Exception ioex) {
            //TODO: Handle exception
        }

        return props;
    }
}

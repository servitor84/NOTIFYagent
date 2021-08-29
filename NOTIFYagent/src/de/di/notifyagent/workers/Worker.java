package de.di.notifyagent.workers;

import de.di.notifyagent.jobs.Schedulable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import de.di.dokinform.elo.ELOClientNG;
/**
 * The Worker class processes Schedulable objects
 * and runs as a seperate thread. It gets its Schedulable
 * object assigned from the scheduler.
 * 
 * @author A. Sopicki
 */
public abstract class Worker extends Thread {

  protected static final int PROP_E4 = 7;
  private Logger logger = null;
  protected Schedulable schedulable = null;
  /** Flag indicating that the Worker is currently idle */
  private volatile boolean idle = false;
  /** Flag indicating that the Worker is still active */
  private volatile boolean running = true;
  protected Properties settings = new Properties();
  
  protected int maxWorkflows = 50;

  protected boolean includeURL = false;  
  // +++
  protected boolean includeJavaclientURL = false;  
  protected boolean includeWebclientURL = false;  
  
  public Worker(Schedulable schedulable, Properties settings) {
//    super(schedulable.getProfile().getProfileName());
    
    
    this.settings.putAll(settings);
    logger = Logger.getLogger(getClass());
    this.schedulable = schedulable;

    includeURL = Boolean.parseBoolean(settings.getProperty("Application.InsertJavaclientURL", "FALSE"));
    // +++
    includeJavaclientURL = Boolean.parseBoolean(settings.getProperty("Application.InsertJavaclientURL", "FALSE"));
    includeWebclientURL = Boolean.parseBoolean(settings.getProperty("Application.InsertWebclientURL", "FALSE"));
  }

  @Override
  public void run() {            
      
    init();
              
    //main loop for the thread
    while (running) {
      doWork();
      try {
        idle = true;
        Thread.sleep(60000);
        idle = false;
      } catch (InterruptedException interrupt) {
        idle = false;
        logger.info("Exception interrupt. " + interrupt.getMessage());
      } catch (Exception e) {
        idle = false;
        logger.info("Exception. " + e.getMessage());
      }
    }

    //cleanup
    schedulable = null;
//    manager = null;
    settings = null;
    logger = null;
  }

  /**
   * Will stop the Worker as soon as possible
   */
  public void shutdown() {
    running = false;
    //only intterupt while in idle mode
    if (idle) {
      interrupt();
    }

  }

  /**
   * Worker has been shutdown?
   * @return true if the worker is still running and false if it has been shutdown
   */
  protected boolean isRunning() {
    return running;
  }

  /**
   * Worker is still active?
   * @return true if the worker thread is still active and false otherwise
   */
  public boolean isActive() {
    return (running && isAlive());
  }

  public void addWorkerProperties(Properties props) {
    settings.putAll(props);
  }

  protected abstract void doWork();

  /**
   * Initialise some variables with values from the configuration file
   */
  protected abstract void init();

  protected String readTemplateFile(File file) {
    String messageTemplate = null;
    logger.debug("Reading message template from file "+file);

    FileReader fileReader = null;
    BufferedReader bufferReader = null;

    try {
      fileReader = new FileReader(file);
      bufferReader = new BufferedReader(fileReader);

      String line = bufferReader.readLine();
      StringBuilder buf = new StringBuilder();

      while(line != null) {
        buf.append(line);
        buf.append("\n");

        line = bufferReader.readLine();
      }

      messageTemplate = buf.toString();
      bufferReader.close();
      fileReader.close();
    } catch(java.io.IOException ioex) {
      logger.debug("IO error while reading template file", ioex);
      try {
        bufferReader.close();
      } catch(Exception ex) {

      }
      try {
        fileReader.close();
      } catch(Exception ex) {

      }
      messageTemplate = null;
    }

    return messageTemplate;
  }

  protected String toDocUrl(String objId, String text) {
    StringBuilder builder = new StringBuilder();
    builder.append("<a href=\"elodms://");
    builder.append(objId);
    builder.append("\" >");
    builder.append(text);
    builder.append("</a>");

    return builder.toString();
  }
  
  // +++
  protected String toJavaclientDocUrl(String flowId, String nodeIds, String text) {    
    StringBuilder builder = new StringBuilder();
    
    if(nodeIds.contains(",")) {
        String[] nodeIdsParts = nodeIds.split(",");
        String[] textParts = text.split(",");
        
        for(int i = 0; i < nodeIdsParts.length; i++) {
            builder.append("<a href=\"elodms://wf/");
            builder.append(flowId);
            builder.append("/");
            builder.append(nodeIdsParts[i]);
            builder.append("\" >");
            builder.append(textParts[i]);
            builder.append("</a>");
            if(i != nodeIdsParts.length -1) {
                builder.append(", ");
            }            
        }
        
    } else {
        builder.append("<a href=\"elodms://wf/");
        builder.append(flowId);
        builder.append("/");
        builder.append(nodeIds);
        builder.append("\" >");
        builder.append(text);
        builder.append("</a>");
    } 
    return builder.toString();
  }
  
  protected String toWebclientDocUrl(String flowId, String nodeIds, String text) {    
    StringBuilder builder = new StringBuilder();
    String webclientURL = settings.getProperty("Application.WebclientURL");
    webclientURL = webclientURL.replace("\\", "");
    
    if(nodeIds.contains(",")) {
        String[] nodeIdsParts = nodeIds.split(",");
        String[] textParts = text.split(",");
        
        for(int i = 0; i < nodeIdsParts.length; i++) {
            builder.append("<a href=\"");
            builder.append(webclientURL);
            //builder.append("\"");
            builder.append(flowId);
            builder.append("/");
            builder.append(nodeIdsParts[i]);
            builder.append("\" >");
            builder.append(textParts[i]);
            builder.append("</a>");
            if(i != nodeIdsParts.length -1) {
                builder.append(", ");
            }            
        }
        
    } else {
        builder.append("<a href=\"");
        builder.append(webclientURL);
        //builder.append("\"");
        builder.append(flowId);
        builder.append("/");
        builder.append(nodeIds);
        builder.append("\" >");
        builder.append(text);
        builder.append("</a>");
    } 
    return builder.toString();
  }

}

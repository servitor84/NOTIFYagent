package de.di.notifyagent.app;

/**
 * Main class to start the application from the command line
 *
 * @author A. Sopicki
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      Application app = new Application();
      try {
        //start the application
        app.start();

        //wait till the end of the application
        app.join();

        ////show error messages if start up fails
        java.util.List<String> status = app.getErrorStatus();

        if (status.size() > 0) {
          for (String msg : status) {
            System.out.println(msg);
          }
        }
      } catch (InterruptedException ex) {
      }
    }

}

package de.di.notifyagent.app;

/**
 *
 * @author alex
 */
public class ApplicationException extends Exception {

    /**
     * Creates a new instance of <code>ApplicationException</code> without detail message.
     */
    public ApplicationException() {
    }


    /**
     * Constructs an instance of <code>ApplicationException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ApplicationException(String msg) {
        super(msg);
    }
}

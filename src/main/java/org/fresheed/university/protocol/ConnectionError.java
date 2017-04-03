package org.fresheed.university.protocol;

/**
 * Created by fresheed on 03.04.17.
 */
public class ConnectionError extends Exception {
    public ConnectionError(String msg){
        super(msg);
    }
    public ConnectionError(String msg, Throwable cause){
        super(msg, cause);
    }
}

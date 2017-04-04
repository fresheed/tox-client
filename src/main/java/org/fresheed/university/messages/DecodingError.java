package org.fresheed.university.messages;

/**
 * Created by fresheed on 03.04.17.
 */
public class DecodingError extends Exception {
    public DecodingError(String msg){
        super(msg);
    }
    public DecodingError(String msg, Throwable cause){
        super(msg, cause);
    }
}

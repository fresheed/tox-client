package org.fresheed.university.transfer;

public class DataChannelError extends  Exception{
    public DataChannelError(String msg){
        super(msg);
    }
    public DataChannelError(String msg, Throwable cause){
        super(msg, cause);
    }
}

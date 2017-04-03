package org.fresheed.university.messages;

/**
 * Created by fresheed on 03.04.17.
 */
abstract public class ToxMessage {
    protected final byte[] content;

    public ToxMessage(byte[] content){
        this.content=content;
    }

    public byte[] getContent(){
        return content;
    }
}

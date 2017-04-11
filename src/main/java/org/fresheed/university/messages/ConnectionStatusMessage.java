package org.fresheed.university.messages;

import org.fresheed.university.drivers.ConnectionDriver;

/**
 * Created by fresheed on 09.04.17.
 */
abstract public class ConnectionStatusMessage {
    protected final byte[] content;
    private final int connection_id;

    protected ConnectionStatusMessage(int connection_id, int message_type){
        this.connection_id=connection_id;
        this.content=new byte[]{(byte)message_type, (byte)connection_id};
    }

    abstract protected int getMessageType();

    public int getConnectionId(){
        return connection_id;
    }
}

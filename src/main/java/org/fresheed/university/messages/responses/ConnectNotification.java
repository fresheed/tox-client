package org.fresheed.university.messages.responses;

import org.fresheed.university.drivers.ResponseVisitor;

/**
 * Created by fresheed on 09.04.17.
 */
public class ConnectNotification extends ConnectionStatusMessage implements ToxIncomingMessage {
    public static final int TYPE_CONNECT_NOTIFICATION=0x02;

    protected ConnectNotification(int connection_id){
        super(connection_id, TYPE_CONNECT_NOTIFICATION);
    }

    public ConnectNotification(byte[] raw){
        this(raw[1]);
    }

    @Override
    protected int getMessageType() {
        return TYPE_CONNECT_NOTIFICATION;
    }

    @Override
    public void accept(ResponseVisitor visitor) {
        visitor.visitConnectNotification(this);
    }
}

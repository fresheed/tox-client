package org.fresheed.university.messages.responses;

import org.fresheed.university.drivers.ResponseVisitor;
import org.fresheed.university.messages.ConnectionStatusMessage;

/**
 * Created by fresheed on 09.04.17.
 */
public class DisconnectNotification extends ConnectionStatusMessage implements ToxIncomingMessage {
    public static final int TYPE_DISCONNECT_NOTIFICATION=0x03;

    protected DisconnectNotification(int connection_id){
        super(connection_id, TYPE_DISCONNECT_NOTIFICATION);
    }

    public DisconnectNotification(byte[] raw){
        this(raw[1]);
    }

    @Override
    protected int getMessageType() {
        return TYPE_DISCONNECT_NOTIFICATION;
    }

    @Override
    public void accept(ResponseVisitor visitor) {
        visitor.visitDisconnectNotification(this);
    }
}

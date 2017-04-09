package org.fresheed.university.messages.responses;

import org.abstractj.kalium.keys.PublicKey;
import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.drivers.ResponseVisitor;
import org.fresheed.university.messages.PingMessage;
import org.fresheed.university.messages.RoutingMessage;

/**
 * Created by fresheed on 04.04.17.
 */
public class RoutingResponse extends RoutingMessage implements ToxIncomingMessage {
    public static final int TYPE_ROUTING_RESPONSE=0x01;
    private final int connection_id;

    public RoutingResponse(byte[] raw) {
        this(new PublicKey(ArrayUtils.subarray(raw, 2, raw.length)), raw[1]);
    }

    public RoutingResponse(PublicKey target_peer, int connection_id) {
        super(target_peer);
        this.connection_id=connection_id;
    }

    @Override
    public void accept(ResponseVisitor visitor) {
        visitor.visitRoutingResponse(this);
    }

    public int getConnectionId(){
        return connection_id;
    }

    public boolean isAccepted(){
        return (connection_id!=0);
    }

    @Override
    protected int getMessageType() {
        return TYPE_ROUTING_RESPONSE;
    }
}
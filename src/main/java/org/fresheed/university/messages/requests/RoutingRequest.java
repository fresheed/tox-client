package org.fresheed.university.messages.requests;

import org.abstractj.kalium.keys.PublicKey;
import org.fresheed.university.drivers.ResponseVisitor;
import org.fresheed.university.messages.RoutingMessage;

/**
 * Created by fresheed on 09.04.17.
 */
public class RoutingRequest extends RoutingMessage implements ToxOutgoingMessage {
    public static final int TYPE_ROUTING_REQUEST=0x00;

    public RoutingRequest(PublicKey target_peer){
        super(target_peer);
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    @Override
    protected int getMessageType() {
        return TYPE_ROUTING_REQUEST;
    }
}

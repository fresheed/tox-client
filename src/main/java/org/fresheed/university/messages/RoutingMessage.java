package org.fresheed.university.messages;

import org.abstractj.kalium.keys.PublicKey;
import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.messages.datatypes.ToxDataType;
import org.fresheed.university.messages.datatypes.Uint64;
import org.fresheed.university.messages.datatypes.Uint8;
import org.fresheed.university.messages.requests.ToxOutgoingMessage;
import org.fresheed.university.messages.responses.ToxIncomingMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fresheed on 04.04.17.
 */
abstract public class RoutingMessage{
    protected final byte[] content;
    private final PublicKey target_peer;

    public RoutingMessage(PublicKey target_peer){
        this.target_peer=target_peer;
        byte[] header=new byte[]{(byte)getMessageType()};
        this.content=ArrayUtils.addAll(header, target_peer.toBytes());
    }

    abstract protected int getMessageType();

    public PublicKey getTargetPeer(){
        return target_peer;
    }
}

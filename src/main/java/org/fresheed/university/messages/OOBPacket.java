package org.fresheed.university.messages;

import org.abstractj.kalium.keys.PublicKey;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;

/**
 * Created by fresheed on 09.04.17.
 */
abstract public class OOBPacket {
    protected final byte[] content;
    private final byte[] data;
    protected final PublicKey another_peer;

    protected OOBPacket(PublicKey another_peer, int message_type, byte[] data){
        this.another_peer=another_peer;
        byte[] header= ArrayUtils.addAll(new byte[]{(byte)message_type}, another_peer.toBytes());
        this.content=ArrayUtils.addAll(header, data);
        this.data=data;
    }

    abstract protected int getMessageType();

    public byte[] getData(){
        return data;
    }

}

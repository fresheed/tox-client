package org.fresheed.university.messages.requests;

import org.abstractj.kalium.keys.PublicKey;
import org.fresheed.university.messages.OOBPacket;

/**
 * Created by fresheed on 09.04.17.
 */
public class OOBSend extends OOBPacket implements ToxOutgoingMessage {
    public static final int MESSAGE_TYPE_OOBSEND=0x06;

    public OOBSend(PublicKey recipient, byte[] data){
        super(recipient, MESSAGE_TYPE_OOBSEND, data);
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    @Override
    protected int getMessageType() {
        return MESSAGE_TYPE_OOBSEND;
    }
}

package org.fresheed.university.messages.responses;

import org.abstractj.kalium.keys.PublicKey;
import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.drivers.ResponseVisitor;
import org.fresheed.university.messages.OOBPacket;

/**
 * Created by fresheed on 09.04.17.
 */
public class OOBRecv extends OOBPacket implements ToxIncomingMessage {
    public static final int MESSAGE_TYPE_OOBRECV=0x07;

    public OOBRecv(PublicKey sender, byte[] data){
        super(sender, MESSAGE_TYPE_OOBRECV, data);
    }

    public OOBRecv(byte[] raw){
        this(new PublicKey(ArrayUtils.subarray(raw, 1, 33)),
                ArrayUtils.subarray(raw, 33, raw.length));
    }

    @Override
    public void accept(ResponseVisitor visitor) {
        visitor.visitOOBRecv(this);
    }

    @Override
    protected int getMessageType() {
        return 0;
    }
}

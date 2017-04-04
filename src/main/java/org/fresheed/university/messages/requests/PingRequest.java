package org.fresheed.university.messages.requests;

import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.drivers.ResponseVisitor;
import org.fresheed.university.messages.PingMessage;
import org.fresheed.university.messages.datatypes.ToxDataType;
import org.fresheed.university.messages.datatypes.Uint64;
import org.fresheed.university.messages.datatypes.Uint8;
import org.fresheed.university.messages.responses.ToxIncomingMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fresheed on 04.04.17.
 */
public class PingRequest extends PingMessage{
    public static final int TYPE_PING_REQUEST =0x04;

    public PingRequest(long ping_id) {
        super(ping_id);
    }

    public PingRequest(byte[] raw) {
        super(raw);
    }

    @Override
    protected int getMessageType() {
        return TYPE_PING_REQUEST;
    }

    @Override
    public void accept(ResponseVisitor visitor) {
        visitor.visitPingRequest(this);
    }
}

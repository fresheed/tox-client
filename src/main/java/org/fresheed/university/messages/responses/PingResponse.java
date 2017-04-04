package org.fresheed.university.messages.responses;

import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.drivers.ResponseVisitor;
import org.fresheed.university.messages.PingMessage;
import org.fresheed.university.messages.datatypes.ToxDataType;
import org.fresheed.university.messages.datatypes.Uint64;
import org.fresheed.university.messages.datatypes.Uint8;
import org.fresheed.university.messages.requests.ToxOutgoingMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fresheed on 04.04.17.
 */
public class PingResponse extends PingMessage{
    public static final int TYPE_PING_RESPONSE=0x05;

    public PingResponse(byte[] raw) {
        super(raw);
    }

    public PingResponse(long ping_id) {
        super(ping_id);
    }

    @Override
    public void accept(ResponseVisitor visitor) {
        visitor.visitPingResponse(this);
    }

    @Override
    protected int getMessageType() {
        return TYPE_PING_RESPONSE;
    }
}
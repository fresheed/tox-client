package org.fresheed.university.messages.responses;

import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.drivers.ResponseVisitor;
import org.fresheed.university.messages.datatypes.ToxDataType;
import org.fresheed.university.messages.datatypes.Uint64;
import org.fresheed.university.messages.datatypes.Uint8;
import org.fresheed.university.messages.requests.ToxRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fresheed on 04.04.17.
 */
public class PingResponse implements ToxIncomingMessage, ToxRequest {
    public static final int MESSAGE_TYPE_ID=0x05;
    private static final int TOTAL_SIZE=1+8;
    private final long ping_id;
    private final byte[] content;

    public PingResponse(byte[] raw) {
        if (raw.length != TOTAL_SIZE){
            throw new IllegalArgumentException("PingResponse must be 9 bytes long");
        }
        ping_id=new Uint64(ArrayUtils.subarray(raw, 1, raw.length)).getValue();
        System.out.println("ping id "+ping_id);
        content=raw;
    }

    public PingResponse(long ping_id) {
        List<ToxDataType> items=new ArrayList<>();
        items.add(new Uint8(MESSAGE_TYPE_ID));
        items.add(new Uint64(ping_id));
        byte[] resulting=new byte[0];
        for (ToxDataType item: items){
            resulting=ArrayUtils.addAll(resulting, item.getBytes());
        }
        content=resulting;
        this.ping_id=ping_id;
    }

    public long getPingId(){
        return ping_id;
    }

    @Override
    public void accept(ResponseVisitor visitor) {
        visitor.visitPingResponse(this);
    }

    @Override
    public byte[] getContent() {
        return content;
    }
}
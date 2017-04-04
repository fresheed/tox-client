package org.fresheed.university.messages;

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
abstract public class PingMessage  implements ToxOutgoingMessage, ToxIncomingMessage {
    private static final int TOTAL_SIZE=1+8;
    private final byte[] content;
    private final long ping_id;

    public PingMessage(long ping_id) {
        List<ToxDataType> items=new ArrayList<>();
        items.add(new Uint8(getMessageType()));
        items.add(new Uint64(ping_id));
        byte[] resulting=new byte[0];
        for (ToxDataType item: items){
            resulting= ArrayUtils.addAll(resulting, item.getBytes());
        }
        content=resulting;
        this.ping_id=ping_id;
    }

    public PingMessage(byte[] raw) {
        if (raw.length != TOTAL_SIZE){
            throw new IllegalArgumentException("PingResponse must be 9 bytes long");
        }
        if (raw[0] != getMessageType()){
            throw new IllegalArgumentException("Wrong message type - perhaps swapped request and response");
        }
        ping_id=new Uint64(ArrayUtils.subarray(raw, 1, raw.length)).getValue();
        content=raw;
    }

    public long getPingId(){
        return ping_id;
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    abstract protected int getMessageType();

}

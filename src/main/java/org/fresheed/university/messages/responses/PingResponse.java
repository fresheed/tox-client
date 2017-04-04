package org.fresheed.university.messages.responses;

import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.messages.datatypes.ToxDataType;
import org.fresheed.university.messages.datatypes.Uint64;
import org.fresheed.university.messages.datatypes.Uint8;
import org.fresheed.university.messages.requests.ToxRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fresheed on 04.04.17.
 */
public class PingResponse implements ToxResponse {
    public static final int MESSAGE_TYPE_ID=0x05;
    private static final int TOTAL_SIZE=1+8;
    private final long ping_id;

    public PingResponse(byte[] raw) {
        if (raw.length != TOTAL_SIZE){
            throw new IllegalArgumentException("PingResponse must be 9 bytes long");
        }
        ping_id=new Uint64(ArrayUtils.subarray(raw, 1, raw.length)).getValue();
    }

    public long getPingId(){
        return ping_id;
    }
}
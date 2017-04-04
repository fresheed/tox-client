package org.fresheed.university.messages.requests;

import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.messages.datatypes.ToxDataType;
import org.fresheed.university.messages.datatypes.Uint64;
import org.fresheed.university.messages.datatypes.Uint8;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fresheed on 04.04.17.
 */
public class PingRequest implements ToxRequest {
    public static final int MESSAGE_TYPE_ID=0x04;
    private final byte[] content;

    public PingRequest(int ping_id) {
        List<ToxDataType> items=new ArrayList<>();
        items.add(new Uint8(MESSAGE_TYPE_ID));
        items.add(new Uint64(ping_id));
        byte[] resulting=new byte[0];
        for (ToxDataType item: items){
            resulting=ArrayUtils.addAll(resulting, item.getBytes());
        }
        content=resulting;
    }

    @Override
    public byte[] getContent() {
        return content;
    }
}

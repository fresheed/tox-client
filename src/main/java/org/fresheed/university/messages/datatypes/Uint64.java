package org.fresheed.university.messages.datatypes;

import java.nio.ByteBuffer;

/**
 * Created by fresheed on 04.04.17.
 */
public class Uint64 extends ToxDataType {
    private final long value;

    public Uint64(long value){
        this.value=value;
    }

    public Uint64(byte[] raw){
        ByteBuffer buffer = ByteBuffer.wrap(raw);
        value=buffer.getLong();
    }

    public byte[] getBytes(){
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(value);
        return buffer.array();
    }

    @Override
    public long getValue(){
        return value;
    }
}

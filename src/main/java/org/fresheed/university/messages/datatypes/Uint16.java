package org.fresheed.university.messages.datatypes;

/**
 * Created by fresheed on 04.04.17.
 */
public class Uint16 extends ToxDataType {
    private final int value;

    public Uint16(int value){
        if (value<0 || value>0xFFFF){
            throw new IllegalArgumentException("Uint16's value must be in range 0..0xFFFF");
        }
        this.value=value;
    }

    public Uint16(byte[] raw){
        if (raw.length != 2){
            throw new IllegalArgumentException("Uint16 must contain exactly 2 bytes");
        }
        this.value=(raw[0]<<8)+raw[1];
    }

    public byte[] getBytes(){
        byte[] repr=new byte[2];
        repr[0]=(byte)((value >> 8) & 0xFF);
        repr[1]=(byte)(value & 0xFF);
        return repr;
    }

    @Override
    public long getValue() {
        return value;
    }
}

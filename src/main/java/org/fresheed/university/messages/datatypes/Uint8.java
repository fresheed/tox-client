package org.fresheed.university.messages.datatypes;

/**
 * Created by fresheed on 04.04.17.
 */
public class Uint8 extends ToxDataType {
    private final int value;

    public Uint8(int value){
        if (value<0 || value>255){
            throw new IllegalArgumentException("Uint8's value must be in range 0..255");
        }
        this.value=value;
    }

    public byte[] getBytes(){
        byte[] repr=new byte[1];
        repr[0]=(byte)(value & 0xFF);
        return repr;
    }

    @Override
    public long getValue() {
        return value;
    }
}

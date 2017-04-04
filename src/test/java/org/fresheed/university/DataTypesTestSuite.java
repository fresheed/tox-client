package org.fresheed.university;

import static org.junit.Assert.*;

import org.fresheed.university.messages.datatypes.Uint16;
import org.fresheed.university.messages.datatypes.Uint64;
import org.fresheed.university.messages.datatypes.Uint8;
import org.junit.Test;

/**
 * Created by fresheed on 04.04.17.
 */
public class DataTypesTestSuite{

    @Test
    public void shouldRepresentZeroAsZeroUint8(){
        byte expected=0;
        byte[] repr=new Uint8(0).getBytes();
        assertEquals(1, repr.length);
        assertEquals(expected, repr[0]);
    }

    @Test
    public void shouldRepresentNumberAsAccordingUint8(){
        byte expected=10;
        byte[] repr=new Uint8(10).getBytes();
        assertEquals(1, repr.length);
        assertEquals(expected, repr[0]);
    }

    @Test
    public void shouldRepresentLargeByteAsAccordingUint8(){
        byte expected=(byte)(-1);
        byte[] repr=new Uint8(255).getBytes();
        assertEquals(1, repr.length);
        assertEquals(expected, repr[0]);
    }

    @Test
    public void shouldRepresentZeroAsZeroUint16(){
        byte[] repr=new Uint16(0).getBytes();
        assertEquals(2, repr.length);
        assertEquals(0, repr[0]);
        assertEquals(0, repr[1]);
    }

    @Test
    public void shouldRepresentNumberAsAccordingUint16(){
        byte[] repr=new Uint16(0x1020).getBytes();
        assertEquals(2, repr.length);
        assertEquals((byte)0x10, repr[0]);
        assertEquals((byte)0x20, repr[1]);
    }

    @Test
    public void shouldRepresentLargeNumberAsAccordingUint16(){
        byte[] repr=new Uint16(0xFFEE).getBytes();
        assertEquals(2, repr.length);
        assertEquals((byte)0xFF, repr[0]);
        assertEquals((byte)0xEE, repr[1]);
    }


    @Test
    public void shouldRepresentZeroAsZeroUint64(){
        byte[] repr=new Uint64(0).getBytes();
        assertEquals(8, repr.length);
        assertArrayEquals(new byte[8], repr);
    }

    @Test
    public void shouldRepresentNumberAsAccordingUint64(){
        byte[] repr=new Uint64(0x1020304050607080L).getBytes();
        assertEquals(8, repr.length);
        assertEquals((byte)0x10, repr[0]);
        assertEquals((byte)0x80, repr[7]);
    }

    @Test
    public void shouldRepresentLargeNumberAsAccordingUint64(){
        byte[] repr=new Uint64(0xFFFFFFFFFFFFFFFFL).getBytes();
        assertEquals(8, repr.length);
        assertEquals((byte)0xFF, repr[0]);
        assertEquals((byte)0xFF, repr[7]);
    }

}

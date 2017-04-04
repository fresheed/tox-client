package org.fresheed.university.messages.datatypes;

import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;

/**
 * Created by fresheed on 04.04.17.
 */
abstract public class ToxDataType {
    abstract public byte[] getBytes();
    abstract public long getValue();
}


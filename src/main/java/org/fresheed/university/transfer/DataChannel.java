package org.fresheed.university.transfer;

/**
 * Created by fresheed on 02.04.17.
 */
public interface DataChannel {
    byte[] receive(int size) throws DataChannelError;
    void send(byte[] data) throws DataChannelError;
    void close() throws DataChannelError;
    boolean isActive();
}

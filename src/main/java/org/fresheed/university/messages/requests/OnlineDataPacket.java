package org.fresheed.university.messages.requests;

/**
 * Created by fresheed on 09.04.17.
 */
public class OnlineDataPacket implements ToxOutgoingMessage {
    private final byte[] content;

    public OnlineDataPacket(int connection_id){
        content=new byte[]{0x18, (byte)connection_id};
    }

    @Override
    public byte[] getContent() {
        return content;
    }
}

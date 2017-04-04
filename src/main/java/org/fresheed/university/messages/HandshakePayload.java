package org.fresheed.university.messages;

import org.abstractj.kalium.keys.PublicKey;
import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.drivers.ResponseVisitor;
import org.fresheed.university.messages.requests.ToxOutgoingMessage;
import org.fresheed.university.messages.responses.ToxIncomingMessage;
import org.fresheed.university.protocol.LocalPeer;
import org.fresheed.university.protocol.RemotePeer;

/**
 * Created by fresheed on 03.04.17.
 */
public class HandshakePayload implements ToxOutgoingMessage, ToxIncomingMessage {
    public static final int PAYLOAD_SIZE=56;
    private final byte[] content;
    private final PublicKey key;
    private final byte[] nonce;

    public HandshakePayload(LocalPeer local, byte[] initial_nonce){
        this(local.getPublicKey(), initial_nonce);
    }

    public HandshakePayload(RemotePeer remote, byte[] initial_nonce){
        this(remote.getPublicKey(), initial_nonce);
    }

    private HandshakePayload(PublicKey key, byte[] nonce){
        this.key=key;
        this.nonce=nonce;
        content=ArrayUtils.addAll(key.toBytes(), nonce);
    }

    public static HandshakePayload decodeResponse(byte[] raw) throws DecodingError{
        if (raw.length != PAYLOAD_SIZE){
            throw new DecodingError("Payload must have exactly 56 bytes");
        }
        PublicKey key=new PublicKey(ArrayUtils.subarray(raw, 0, 32));
        byte[] nonce=ArrayUtils.subarray(raw, 32, raw.length);
        return new HandshakePayload(key, nonce);
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    public PublicKey getKey(){
        return key;
    }

    public byte[] getNonce(){
        return nonce;
    }

    @Override
    public void accept(ResponseVisitor visitor) {
        throw new RuntimeException("This class should not be visited");
    }
}

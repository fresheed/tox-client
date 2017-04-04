package org.fresheed.university.messages.responses;

import org.abstractj.kalium.crypto.Box;
import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.drivers.ResponseVisitor;
import org.fresheed.university.encryption.EncryptionUtils;
import org.fresheed.university.messages.DecodingError;
import org.fresheed.university.messages.HandshakePayload;
import org.fresheed.university.protocol.LocalPeer;
import org.fresheed.university.protocol.RemotePeer;

/**
 * Created by fresheed on 04.04.17.
 */
public class HandshakeResponse implements ToxIncomingMessage {
    private static final int KEY_LENGTH=32;
    private static final int NONCE_LENGTH=24;

    private final HandshakePayload payload;

    public HandshakeResponse(byte[] raw_bytes, LocalPeer local, RemotePeer remote){
        byte[] temp_nonce=ArrayUtils.subarray(raw_bytes, 0, EncryptionUtils.NONCE_SIZE);

        byte[] encrypted=ArrayUtils.subarray(raw_bytes, EncryptionUtils.NONCE_SIZE, raw_bytes.length);
        Box box=new Box(remote.getPublicKey(), local.getPrivateKey());
        byte[] decrypted=box.decrypt(temp_nonce, encrypted);
        try {
            payload=HandshakePayload.decodeResponse(decrypted);
        } catch (DecodingError err) {
            throw new IllegalArgumentException("Error on decoding handshake response from server", err);
        }
    }

    public HandshakePayload getPayload(){
        return payload;
    }

    @Override
    public void accept(ResponseVisitor visitor) {
        throw new RuntimeException("This message type should not be processed by visitor");
    }
}

package org.fresheed.university.messages.requests;

import org.abstractj.kalium.crypto.Box;
import org.fresheed.university.messages.HandshakePayload;
import org.fresheed.university.protocol.LocalPeer;
import org.fresheed.university.protocol.RemotePeer;

import static org.apache.commons.lang3.ArrayUtils.addAll;
import static org.fresheed.university.encryption.EncryptionUtils.generateNonce;

/**
 * Created by fresheed on 03.04.17.
 */
public class HandshakeRequest implements ToxOutgoingMessage {
    private final byte[] content;

    public HandshakeRequest(LocalPeer client, RemotePeer server, HandshakePayload payload) {
        byte[] one_time_nonce=generateNonce();
        byte[] header=addAll(client.getPublicKey().toBytes(), one_time_nonce);

        Box box=new Box(server.getPublicKey(), client.getPrivateKey());
        byte[] encrypted_payload=box.encrypt(one_time_nonce, payload.getContent());

        byte[] full_message=addAll(header, encrypted_payload);
        content=full_message;
    }

    @Override
    public byte[] getContent() {
        return content;
    }
}

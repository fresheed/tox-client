package org.fresheed.university.messages;

import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.encryption.PeerBox;
import org.fresheed.university.protocol.LocalPeer;
import org.fresheed.university.protocol.RemotePeer;

import static org.apache.commons.lang3.ArrayUtils.addAll;
import static org.fresheed.university.encryption.EncryptionUtils.generateNonce;

/**
 * Created by fresheed on 03.04.17.
 */
public class HandshakeRequest extends ToxMessage {

    public HandshakeRequest(LocalPeer client, RemotePeer server, HandshakePayload payload) {
        super(makeContent(client, server, payload));
    }

    private static byte[] makeContent(LocalPeer client, RemotePeer server, HandshakePayload payload){
        byte[] one_time_nonce=generateNonce();
        byte[] header=addAll(client.getPublicKey().toBytes(), one_time_nonce);

        PeerBox box=new PeerBox(client, server);
        byte[] encrypted_payload=box.encryptMessage(payload, one_time_nonce);

        byte[] full_message=addAll(header, encrypted_payload);
        return full_message;
    }
}

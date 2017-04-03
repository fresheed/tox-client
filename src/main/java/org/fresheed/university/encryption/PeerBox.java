package org.fresheed.university.encryption;

import org.abstractj.kalium.crypto.Box;
import org.fresheed.university.messages.HandshakePayload;
import org.fresheed.university.messages.ToxMessage;
import org.fresheed.university.protocol.LocalPeer;
import org.fresheed.university.protocol.RemotePeer;

/**
 * Created by fresheed on 03.04.17.
 */
public class PeerBox {
    private final Box box;
    public PeerBox(LocalPeer client, RemotePeer server) {
        box=new Box(server.getPublicKey(), client.getPrivateKey());
    }

    public byte[] encryptMessage(ToxMessage message, byte[] nonce) {
        byte[] clean_data=message.getContent();
        byte[] encrypted=box.encrypt(nonce, clean_data);
        return encrypted;
    }
}

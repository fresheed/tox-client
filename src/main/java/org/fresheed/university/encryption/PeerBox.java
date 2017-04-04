package org.fresheed.university.encryption;

import org.abstractj.kalium.NaCl;
import org.abstractj.kalium.crypto.Box;
import org.abstractj.kalium.crypto.SecretBox;
import org.fresheed.university.messages.*;
import org.fresheed.university.messages.requests.ToxRequest;
import org.fresheed.university.messages.responses.PingResponse;
import org.fresheed.university.messages.responses.ToxResponse;
import org.fresheed.university.protocol.LocalPeer;
import org.fresheed.university.protocol.RemotePeer;
import org.fresheed.university.protocol.ToxRelayedConnection;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.abstractj.kalium.NaCl.sodium;

/**
 * Created by fresheed on 03.04.17.
 */
public class PeerBox {
    //private final Box box;
    private final SecretBox box;
    private final byte[] shared_key;
    public PeerBox(LocalPeer client, RemotePeer server) {
        //box=new Box(server.getPublicKey(), client.getPrivateKey());
        //shared_key=new byte[NaCl.Sodium.BEF];
        shared_key = new byte[NaCl.Sodium.CRYPTO_BOX_CURVE25519XSALSA20POLY1305_BEFORENMBYTES];
        sodium().crypto_box_curve25519xsalsa20poly1305_beforenm(
                shared_key, server.getPublicKey().toBytes(), client.getPrivateKey().toBytes());
        box=new SecretBox(shared_key);
    }

    public byte[] encryptMessage(ToxRequest message, byte[] nonce) {
        byte[] clean_data=message.getContent();
        byte[] encrypted=box.encrypt(nonce, clean_data);
        return encrypted;
    }

    public ToxResponse decryptMessage(byte[] encrypted, byte[] nonce) throws DecodingError{
        byte[] decrypted=box.decrypt(nonce, encrypted);
        return parseMessage(decrypted);
    }

    private ToxResponse parseMessage(byte[] decrypted) throws  DecodingError{
        int msg_type=decrypted[0];
        switch (msg_type){
            case PingResponse.MESSAGE_TYPE_ID: return new PingResponse(decrypted);
        }
        throw new IllegalArgumentException("Matching message not implemented");
    }
}

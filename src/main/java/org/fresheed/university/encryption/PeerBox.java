package org.fresheed.university.encryption;

import org.abstractj.kalium.NaCl;
import org.abstractj.kalium.crypto.SecretBox;
import org.fresheed.university.messages.*;
import org.fresheed.university.messages.requests.PingRequest;
import org.fresheed.university.messages.requests.RoutingRequest;
import org.fresheed.university.messages.requests.ToxOutgoingMessage;
import org.fresheed.university.messages.responses.*;
import org.fresheed.university.protocol.LocalPeer;
import org.fresheed.university.protocol.RemotePeer;

import static org.abstractj.kalium.NaCl.sodium;

/**
 * Created by fresheed on 03.04.17.
 */
public class PeerBox {
    //private final Box box;
    private final SecretBox box;
    private final byte[] shared_key;
    public PeerBox(LocalPeer client, RemotePeer server) {
        shared_key = new byte[NaCl.Sodium.CRYPTO_BOX_CURVE25519XSALSA20POLY1305_BEFORENMBYTES];
        sodium().crypto_box_curve25519xsalsa20poly1305_beforenm(
                shared_key, server.getPublicKey().toBytes(), client.getPrivateKey().toBytes());
        box=new SecretBox(shared_key);
    }

    public byte[] encryptMessage(ToxOutgoingMessage message, byte[] nonce) {
        byte[] clean_data=message.getContent();
        byte[] encrypted=box.encrypt(nonce, clean_data);
        return encrypted;
    }

    public ToxIncomingMessage decryptMessage(byte[] encrypted, byte[] nonce) throws DecodingError{
        byte[] decrypted=box.decrypt(nonce, encrypted);
        return parseMessage(decrypted);
    }

    private ToxIncomingMessage parseMessage(byte[] decrypted) throws  DecodingError{
        int msg_type=decrypted[0];
        switch (msg_type){
            case PingRequest.TYPE_PING_REQUEST: return new PingRequest(decrypted);
            case PingResponse.TYPE_PING_RESPONSE: return new PingResponse(decrypted);
            case RoutingResponse.TYPE_ROUTING_RESPONSE: return new RoutingResponse(decrypted);
            case ConnectNotification.TYPE_CONNECT_NOTIFICATION: return new ConnectNotification(decrypted);
            case DisconnectNotification.TYPE_DISCONNECT_NOTIFICATION: return new DisconnectNotification(decrypted);
            case OOBRecv.MESSAGE_TYPE_OOBRECV: return new OOBRecv(decrypted);
        }
        throw new IllegalArgumentException("Matching message not implemented");
    }
}

package org.fresheed.university.protocol;

import org.abstractj.kalium.keys.KeyPair;
import org.abstractj.kalium.keys.PrivateKey;
import org.abstractj.kalium.keys.PublicKey;
import org.fresheed.university.encryption.PeerBox;
import org.fresheed.university.messages.HandshakePayload;
import org.fresheed.university.messages.HandshakeRequest;
import org.fresheed.university.transfer.DataChannel;
import org.fresheed.university.transfer.DataChannelError;
import static org.fresheed.university.encryption.EncryptionUtils.generateNonce;

/**
 * Created by fresheed on 03.04.17.
 */
public class ToxRelayedConnection {

    public static ToxRelayedConnection connect(LocalPeer client, RemotePeer server) throws ConnectionError{
        DataChannel channel;
        try {
            channel=server.getDataChannel();
        } catch (DataChannelError dataChannelError) {
            throw new ConnectionError("Cannot retrieve content channel for remote peer");
        }
        LocalPeer local=new LocalPeer() {
            private final KeyPair keys=new KeyPair();
            @Override
            public PublicKey getPublicKey() {
                return keys.getPublicKey();
            }

            @Override
            public PrivateKey getPrivateKey() {
                return keys.getPrivateKey();
            }
        };
        byte[] initial_nonce=generateNonce();
        HandshakePayload payload=new HandshakePayload(local, initial_nonce);
        HandshakeRequest request=new HandshakeRequest(client, server, payload);
        try {
            channel.send(request.getContent());
            byte[] server_response=channel.receive(96);
            System.out.println("Received response");
            return new ToxRelayedConnection();
        } catch (DataChannelError dataChannelError) {
            throw new ConnectionError("Cannot perform handshake");
        }
    }
}

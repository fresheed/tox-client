package org.fresheed.university.protocol;

import org.abstractj.kalium.keys.KeyPair;
import org.abstractj.kalium.keys.PrivateKey;
import org.abstractj.kalium.keys.PublicKey;
import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.encryption.PeerBox;
import org.fresheed.university.messages.*;
import org.fresheed.university.messages.datatypes.Uint16;
import org.fresheed.university.messages.requests.HandshakeRequest;
import org.fresheed.university.messages.requests.ToxRequest;
import org.fresheed.university.messages.responses.HandshakeResponse;
import org.fresheed.university.messages.responses.ToxIncomingMessage;
import org.fresheed.university.transfer.DataChannel;
import org.fresheed.university.transfer.DataChannelError;

import java.math.BigInteger;

import static org.fresheed.university.encryption.EncryptionUtils.generateNonce;

/**
 * Created by fresheed on 03.04.17.
 */
public class ToxRelayedConnection {

    private final DataChannel channel;
    private final BigInteger base_incoming_nonce, base_outgoing_nonce;
    private final PeerBox box;
    private long cur_incoming_num=0, cur_outgoing_num=0;


    public static ToxRelayedConnection connect(LocalPeer client, RemotePeer server) throws ConnectionError{
        DataChannel channel;
        try {
            channel=server.getDataChannel();
        } catch (DataChannelError dataChannelError) {
            throw new ConnectionError("Cannot retrieve content channel for remote peer");
        }
        LocalPeer interim_client=generateLocalProxy();
        byte[] nonce_for_send=generateNonce();
        HandshakePayload payload=new HandshakePayload(interim_client, nonce_for_send);
        HandshakeRequest request=new HandshakeRequest(client, server, payload);
        try {
            channel.send(request.getContent());
            byte[] server_response=channel.receive(96);
            HandshakeResponse response=new HandshakeResponse(server_response, client, server);
            PublicKey server_interim_key=response.getPayload().getKey();
            byte[] nonce_for_receive=response.getPayload().getNonce();
            RemotePeer interim_server=createProxyAtChannel(server_interim_key, channel);

            ToxRelayedConnection conn=new ToxRelayedConnection(interim_client, interim_server,
                    nonce_for_send, nonce_for_receive);

            return conn;

        } catch (DataChannelError dataChannelError) {
            throw new ConnectionError("Cannot perform handshake");
        }
    }

    private static LocalPeer generateLocalProxy(){
        return new LocalPeer(){
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
    }

    private static RemotePeer createProxyAtChannel(PublicKey pseudonym, DataChannel channel){
        return new RemotePeer() {
                @Override
                public PublicKey getPublicKey() {
                    return pseudonym;
                }

                @Override
                public DataChannel getDataChannel() throws DataChannelError {
                    return channel;
                }
            };
    }

    public ToxRelayedConnection(LocalPeer local, RemotePeer remote,
                                byte[] nonce_outgoing, byte[] nonce_incoming){
        try {
            this.channel = remote.getDataChannel();
        } catch (DataChannelError err){
            throw new RuntimeException("Invalid scenario - cannot retrieve prepared data channel");
        }
        base_incoming_nonce=new BigInteger(nonce_incoming);
        base_outgoing_nonce=new BigInteger(nonce_outgoing);
        box=new PeerBox(local, remote);
    }

    public void send(ToxRequest request) throws ConnectionError{
        byte[] clean=request.getContent();
        byte[] encrypted=box.encryptMessage(request, nextOutgoingNonce());
        Uint16 len=new Uint16(encrypted.length);
        byte[] full_message=ArrayUtils.addAll(len.getBytes(), encrypted);
        try {
            channel.send(full_message);
        } catch (DataChannelError err) {
            throw new ConnectionError("Cannot send message due to internal channel error", err);
        }
    }

    public ToxIncomingMessage receive() throws ConnectionError {
        try {
            final int header_size=2;
            int encrypted_size=(int)new Uint16(channel.receive(header_size)).getValue();
            byte[] encrypted_content=channel.receive(encrypted_size);
            ToxIncomingMessage decrypted_message=box.decryptMessage(encrypted_content, nextIncomingNonce());
            return decrypted_message;
        } catch (DataChannelError dce){
            throw new ConnectionError("Cannot receive message due to internal channel error", dce);
        } catch (DecodingError de) {
            throw new ConnectionError("Cannot parse response", de);
        }
    }

    public void close(){
        try {
            channel.close();
        } catch (DataChannelError dataChannelError) {
            dataChannelError.printStackTrace();
        }
    }

    synchronized private byte[] nextIncomingNonce(){
        BigInteger cur_step=BigInteger.valueOf(cur_incoming_num);
        BigInteger next_nonce=base_incoming_nonce.add(cur_step);
        cur_incoming_num++;
        return next_nonce.toByteArray();
    }

    synchronized private byte[] nextOutgoingNonce(){
        BigInteger cur_step=BigInteger.valueOf(cur_outgoing_num);
        BigInteger next_nonce=base_outgoing_nonce.add(cur_step);
        cur_outgoing_num++;
        return next_nonce.toByteArray();
    }
}


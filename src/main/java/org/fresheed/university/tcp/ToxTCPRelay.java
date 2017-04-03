package org.fresheed.university.tcp;

import org.abstractj.kalium.keys.PublicKey;
import org.fresheed.university.protocol.RemotePeer;
import org.fresheed.university.tcp.TCPDataChannel;
import org.fresheed.university.transfer.DataChannelError;
import org.fresheed.university.transfer.DataChannel;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by fresheed on 02.04.17.
 */
public class ToxTCPRelay implements RemotePeer{

    private final String host;
    private final int port;
    private final PublicKey pub_key;

    public ToxTCPRelay(String host, int port, String pub_key_repr){
        byte[] pub_key_value= DatatypeConverter.parseHexBinary(pub_key_repr);
        pub_key=new PublicKey(pub_key_value);
        this.host=host;
        this.port=port;
    }

    @Override
    public PublicKey getPublicKey() {
        return pub_key;
    }

    public DataChannel getDataChannel() throws DataChannelError {
        try {
            Socket socket = new Socket(host, port);
            DataChannel channel=new TCPDataChannel(socket);
            return channel;
        } catch (IOException e) {
            throw new DataChannelError("Cannot establish connection to server");
        }

    }
}

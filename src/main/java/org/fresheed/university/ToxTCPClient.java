package org.fresheed.university;

import org.abstractj.kalium.crypto.Box;
import org.abstractj.kalium.crypto.Random;
import org.abstractj.kalium.keys.KeyPair;
import org.abstractj.kalium.keys.PrivateKey;
import org.abstractj.kalium.keys.PublicKey;
import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.protocol.LocalPeer;
import org.fresheed.university.tcp.ToxTCPRelay;
import org.fresheed.university.transfer.DataChannelError;
import org.fresheed.university.transfer.DataChannel;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by fresheed on 02.04.17.
 */
public class ToxTCPClient implements LocalPeer {

    private final PrivateKey priv_key;
    private final PublicKey pub_key;

    public ToxTCPClient(String priv_key_repr){
        byte[] priv_key_value=DatatypeConverter.parseHexBinary(priv_key_repr);
        KeyPair keys=new KeyPair(priv_key_value);
        priv_key=keys.getPrivateKey();
        pub_key=keys.getPublicKey();
    }

    @Override
    public PrivateKey getPrivateKey(){
        return priv_key;
    }

    @Override
    public PublicKey getPublicKey(){
        return pub_key;
    }


}

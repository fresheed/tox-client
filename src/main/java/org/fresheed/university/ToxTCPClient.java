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
import java.lang.reflect.Array;

/**
 * Created by fresheed on 02.04.17.
 */
public class ToxTCPClient implements LocalPeer {
    // temp's:
    // pubkey F15C97EB766FFFDE6FA675F0112E45E5EFB52BB941AC84340586B42B8C961968
    // nospam C4AE8135
    // checksum C1C7

    private final PrivateKey priv_key;
    private final PublicKey pub_key;
    private static final byte[] nospam=new byte[]{1,2,3,4};


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

    public String getToxId(){
        byte[] checksum=new byte[]{0,0};
        byte[] key_nospam=ArrayUtils.addAll(pub_key.toBytes(), nospam);
        for (int i=0; i<key_nospam.length; i+=2){
            checksum[0]^=key_nospam[i];
            checksum[1]^=key_nospam[i+1];
        }
        return DatatypeConverter.printHexBinary(key_nospam)+DatatypeConverter.printHexBinary(checksum);
    }

}

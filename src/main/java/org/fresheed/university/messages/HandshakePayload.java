package org.fresheed.university.messages;

import org.apache.commons.lang3.ArrayUtils;
import org.fresheed.university.protocol.LocalPeer;
import org.fresheed.university.protocol.RemotePeer;

/**
 * Created by fresheed on 03.04.17.
 */
public class HandshakePayload extends ToxMessage{

    public HandshakePayload(LocalPeer local, byte[] initial_nonce){
        super(ArrayUtils.addAll(local.getPublicKey().toBytes(), initial_nonce));
    }

}

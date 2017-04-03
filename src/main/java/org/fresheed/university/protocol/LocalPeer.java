package org.fresheed.university.protocol;

import org.abstractj.kalium.keys.PrivateKey;
import org.abstractj.kalium.keys.PublicKey;

/**
 * Created by fresheed on 03.04.17.
 */
public interface LocalPeer {
    PublicKey getPublicKey();
    PrivateKey getPrivateKey();
}

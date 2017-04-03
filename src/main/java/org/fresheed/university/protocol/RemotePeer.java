package org.fresheed.university.protocol;

import org.abstractj.kalium.keys.PublicKey;
import org.fresheed.university.transfer.DataChannelError;
import org.fresheed.university.transfer.DataChannel;

/**
 * Created by fresheed on 03.04.17.
 */
public interface RemotePeer {
    PublicKey getPublicKey();
    DataChannel getDataChannel() throws DataChannelError;

}

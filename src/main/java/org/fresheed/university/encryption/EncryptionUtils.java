package org.fresheed.university.encryption;

import org.abstractj.kalium.crypto.Random;

/**
 * Created by fresheed on 03.04.17.
 */
public class EncryptionUtils {

    public static final int NONCE_SIZE=24;

    public static byte[] generateNonce(){
        return new Random().randomBytes(NONCE_SIZE);
    }

}

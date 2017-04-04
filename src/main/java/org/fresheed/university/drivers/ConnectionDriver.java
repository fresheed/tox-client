package org.fresheed.university.drivers;

import org.fresheed.university.protocol.ConnectionError;

/**
 * Created by fresheed on 04.04.17.
 */
public interface ConnectionDriver {
    void startProcessing() throws ConnectionError;
    void waitForCompletion();
}

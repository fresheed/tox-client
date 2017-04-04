package org.fresheed.university.messages.responses;

import org.fresheed.university.drivers.ResponseVisitor;

/**
 * Created by fresheed on 04.04.17.
 */
public interface ToxIncomingMessage {
    void accept(ResponseVisitor visitor);
}

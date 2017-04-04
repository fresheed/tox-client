package org.fresheed.university.drivers;

import org.fresheed.university.messages.requests.PingRequest;
import org.fresheed.university.messages.responses.PingResponse;

/**
 * Created by fresheed on 04.04.17.
 */
public interface ResponseVisitor {
    void visitPingRequest(PingRequest request);
    void visitPingResponse(PingResponse response);
}

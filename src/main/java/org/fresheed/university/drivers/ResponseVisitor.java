package org.fresheed.university.drivers;

import org.fresheed.university.messages.requests.PingRequest;
import org.fresheed.university.messages.requests.RoutingRequest;
import org.fresheed.university.messages.responses.*;

/**
 * Created by fresheed on 04.04.17.
 */
public interface ResponseVisitor {
    void visitPingRequest(PingRequest request);
    void visitPingResponse(PingResponse response);
    void visitRoutingResponse(RoutingResponse response);
    void visitConnectNotification(ConnectNotification notification);
    void visitOOBRecv(OOBRecv message);
    void visitDisconnectNotification(DisconnectNotification notification);
}

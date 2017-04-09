package org.fresheed.university.drivers;

import org.abstractj.kalium.keys.PublicKey;
import org.fresheed.university.messages.requests.OOBSend;
import org.fresheed.university.messages.requests.OnlineDataPacket;
import org.fresheed.university.messages.requests.PingRequest;
import org.fresheed.university.messages.requests.RoutingRequest;
import org.fresheed.university.messages.responses.*;
import org.fresheed.university.protocol.ConnectionError;
import org.fresheed.university.protocol.ToxRelayedConnection;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by fresheed on 04.04.17.
 */
public class SimpleDriver implements ResponseVisitor, ConnectionDriver {
    private final ToxRelayedConnection connection;
    private Thread current_receiver=null;

    public SimpleDriver(ToxRelayedConnection connection){
        this.connection=connection;
    }

    @Override
    public void startProcessing() throws ConnectionError {
        activateConnection();
        current_receiver=new Thread(recv_processing);
        current_receiver.start();
    }

    @Override
    public void waitForCompletion(){
        try {
            current_receiver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void connect(String pubkey_repr){
        PublicKey target=new PublicKey(DatatypeConverter.parseHexBinary(pubkey_repr));
        RoutingRequest request=new RoutingRequest(target);
        try {
            connection.send(request);
        } catch (ConnectionError connectionError) {
            System.out.println("Unable to send routing request: "+connectionError);
        }
    }

    public void trySendOnline(int connection_id){
        OnlineDataPacket packet=new OnlineDataPacket(connection_id);
        try {
            connection.send(packet);
        } catch (ConnectionError connectionError) {
            System.out.println("Unable to send online packet: "+connectionError);
        }
    }

    public void oob(String pubkey_repr){
        PublicKey target=new PublicKey(DatatypeConverter.parseHexBinary(pubkey_repr));
        OOBSend request=new OOBSend(target, "hello peer".getBytes(Charset.forName("UTF-8")));
        try {
            connection.send(request);
        } catch (ConnectionError connectionError) {
            System.out.println("Unable to send routing request: "+connectionError);
        }
    }


    public void activateConnection() throws ConnectionError{
        connection.send(new PingRequest(0x55));
        PingResponse response=(PingResponse)connection.receive();
        if (response.getPingId()!=0x55){
            throw new ConnectionError("Unable to establish connection");
        }
    }

    private final Runnable recv_processing=new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Waiting for message...");
                    ToxIncomingMessage response = connection.receive();
                    response.accept(SimpleDriver.this);
                } catch (ConnectionError connectionError) {
                    System.out.println("Connection closed - stopping processing");
                    break;
                }
            }
        }
    };

    @Override
    public void visitPingRequest(PingRequest request) {
        try {
            PingResponse response=new PingResponse(request.getPingId());
            connection.send(response);
            System.out.println("Sent response to ping N "+response.getPingId());
        } catch (ConnectionError connectionError) {
            System.out.println("Connection closed - stopping processing");
        }
    }

    @Override
    public void visitPingResponse(PingResponse response) {
        System.out.println("Ping response: "+response.getPingId());
    }

    @Override
    public void visitRoutingResponse(RoutingResponse response) {
        String target=response.getTargetPeer().toString();
        int connection_id=response.getConnectionId();
        System.out.println(String.format("Connection id for %s is %d: ", target, connection_id));
    }

    @Override
    public void visitConnectNotification(ConnectNotification notification) {
        System.out.println(String.format("Connection %d is ready", notification.getConnectionId()));
    }

    @Override
    public void visitOOBRecv(OOBRecv oobRecv) {
        System.out.println("received: "+new String(oobRecv.getData(), StandardCharsets.UTF_8));
    }
}

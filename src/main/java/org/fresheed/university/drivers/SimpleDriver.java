package org.fresheed.university.drivers;

import org.fresheed.university.messages.requests.PingRequest;
import org.fresheed.university.messages.responses.PingResponse;
import org.fresheed.university.messages.responses.ToxIncomingMessage;
import org.fresheed.university.protocol.ConnectionError;
import org.fresheed.university.protocol.ToxRelayedConnection;

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

    public void activateConnection() throws ConnectionError{
        connection.send(new PingRequest(0x55));
        PingResponse response=(PingResponse)connection.receive();
        if (response.getPingId()!=0x55){
            throw new ConnectionError("Unable to establish connection");
        }
    }

//    private Runnable send_processing=new Runnable() {
//        @Override
//        public void run() {
//            while(true){
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    };

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
    public void waitForCompletion(){
        try {
            current_receiver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

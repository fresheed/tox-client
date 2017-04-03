package org.fresheed.university.tcp;


import org.fresheed.university.transfer.DataChannelError;
import org.fresheed.university.transfer.DataChannel;

import java.io.*;
import java.net.Socket;

/**
 * Created by fresheed on 18.02.17.
 */
public class TCPDataChannel implements DataChannel {
    private static final int MAX_BUFFER_SIZE=5000;

    private final Socket socket;
    private final InputStream input;
    private final OutputStream output;


    public TCPDataChannel(Socket socket) {
        this.socket = socket;
        try {
            this.input=socket.getInputStream();
            this.output=socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException("Cannot instantiate TCP Data channel", e);
        }
    }

    @Override
    public byte[] receive(int requested_size) throws DataChannelError {
        byte[] buffer=new byte[requested_size];
        int current_index=0;
        int remaining_read=requested_size;
        while (remaining_read > 0){
            try {
                int read_now=input.read(buffer, current_index, remaining_read);
                if (read_now < 0){
                    throw new RuntimeException("Error handling not implemented");
                } else if (read_now == 0){
                    System.out.println("Zero read");
                } else {
                    current_index+=read_now;
                    remaining_read-=read_now;
                }
            } catch (IOException e) {
                // assume only reason to fail is when socket was closed
                throw new DataChannelError("Tried to read from closed socket", e);
            }
        }
        System.out.println("Successfully read "+requested_size);
        return buffer;
    }

    @Override
    public void send(byte[] data) throws DataChannelError {
        try {
            output.write(data);
        } catch (IOException e) {
            // assume only reason to fail is when socket was closed
            throw new DataChannelError("Tried to write to closed socket", e);
        }
    }

    @Override
    public void close() throws DataChannelError {
        try {
            socket.close();
        } catch (IOException e) {
            throw new DataChannelError("Error occured on closing client socket", e);
        }
    }

    @Override
    public boolean isActive(){
        return !socket.isClosed();
    }

}

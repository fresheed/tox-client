package org.fresheed.university;

import org.fresheed.university.protocol.ConnectionError;
import org.fresheed.university.protocol.ToxRelayedConnection;
import org.fresheed.university.tcp.ToxTCPRelay;

import java.io.*;
import java.util.Properties;

/**
 * Created by fresheed on 18.02.17.
 */
public class ControlConsole {
    final static String PROPERTIES_PATH="tox.ini";

    public static void main(String[] args) {
        Properties props=getToxProperties();

        String client_priv_key=props.getProperty("CLIENT_PRIV_KEY");
        ToxTCPClient client=new ToxTCPClient(client_priv_key);

        String server_pub_key=props.getProperty("SERVER_PUB_KEY");
        String server_host=props.getProperty("SERVER_HOST");
        int server_port=Integer.parseInt(props.getProperty("SERVER_PORT"));
        ToxTCPRelay relay=new ToxTCPRelay(server_host, server_port, server_pub_key);

        try {
            ToxRelayedConnection connection=ToxRelayedConnection.connect(client, relay);
        } catch (ConnectionError connectionError) {
            System.err.println("Cannot establish connection");
            connectionError.printStackTrace();
        }
    }

    // TODO: add exception to getToxProperties()
    // TODO: make all fields private

    private static Properties getToxProperties() {
        try (InputStream input = new FileInputStream(PROPERTIES_PATH)){
            Properties prop = new Properties();
             prop.load(input);
             return prop;
        } catch (FileNotFoundException e){
            System.err.println("CANNOT PROCESS FURTHER");
        } catch (IOException e){
            System.err.println("CANNOT PROCESS FURTHER");
        }
        return null;
    }

}


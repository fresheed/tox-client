package org.fresheed.university;

import org.fresheed.university.drivers.ConnectionDriver;
import org.fresheed.university.drivers.SimpleDriver;
import org.fresheed.university.protocol.ConnectionError;
import org.fresheed.university.protocol.ToxRelayedConnection;
import org.fresheed.university.tcp.ToxTCPRelay;

import java.io.*;
import java.util.Properties;

/**
 * Created by fresheed on 18.02.17.
 */
public class ControlConsole {
    final static String DEFAULT_PROPERTIES_PATH ="tox.ini";

    // refactor exception login in relay data channel retrieve
    // test backward uint conversion
    // check if chosen solution with visitor is canonical

    public static void main(String[] args) {
        String properties_path= DEFAULT_PROPERTIES_PATH;
        if (args.length>0){
            properties_path=args[0];
        }
        Properties props= null;
        try {
            props = getToxProperties(properties_path);
        } catch (IOException e) {
            System.out.println("Cannot read properties");
            System.exit(1);
        }


        String client_priv_key=props.getProperty("CLIENT_PRIV_KEY");
        ToxTCPClient client=new ToxTCPClient(client_priv_key);

        System.out.println("Using public key: "+client.getPublicKey().toString());
        System.out.println("Tox ID: "+client.getToxId());
        System.out.println("Temp's public key: F15C97EB766FFFDE6FA675F0112E45E5EFB52BB941AC84340586B42B8C961968");

        String server_pub_key=props.getProperty("SERVER_PUB_KEY");
        String server_host=props.getProperty("SERVER_HOST");
        int server_port=Integer.parseInt(props.getProperty("SERVER_PORT"));
        ToxTCPRelay relay=new ToxTCPRelay(server_host, server_port, server_pub_key);

        try {
            ToxRelayedConnection conn=ToxRelayedConnection.connect(client, relay);

            SimpleDriver driver=new SimpleDriver(conn);
            driver.startProcessing();
            controlDriverFromConsole(driver);

            conn.close();
            driver.waitForCompletion();
            System.out.println("Client stopped");
        } catch (ConnectionError connectionError) {
            System.err.println("Cannot establish connection");
            connectionError.printStackTrace();
        }
    }

    private static void controlDriverFromConsole(SimpleDriver driver){
        BufferedReader console=new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try {
                System.out.print(">>> ");
                String input=console.readLine();
                if ("quit".equals(input)){
                    System.out.println("Shutting connection down");
                    break;
                } else if (input.startsWith("connect ")){
                    String pubkey_repr=input.replace("connect ", "");
                    driver.connect(pubkey_repr);
                } else if (input.startsWith("try ")){
                    int conn_id=Integer.parseInt(input.replace("try ", ""));
                    driver.trySendOnline(conn_id);
                } else if (input.startsWith("oob ")){
                    String pubkey_repr=input.replace("oob ", "");
                    driver.oob(pubkey_repr);
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }


    private static Properties getToxProperties(String path) throws IOException {
        InputStream input = new FileInputStream(path);
        Properties prop = new Properties();
        prop.load(input);
        return prop;
    }

}


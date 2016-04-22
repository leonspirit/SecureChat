package kij_chat_client;

import java.io.IOException;
import java.net.Socket;
import static javafx.application.Platform.exit;

/**
 * original
 * ->http://www.dreamincode.net/forums/topic/262304-simple-client-and-server-chat-program/
 *
 * @author santen-suru
 */
public class Main {

    private final static int PORT_37 = 3237;
    private final static int PORT_40 = 4047;
    private final static String HOST = "localhost";//SET A CONSTANT VARIABLE HOST

    public static void main(String[] args) throws IOException {
        try {

            Socket s37 = new Socket(HOST, PORT_37);//CONNECT TO THE SERVER
            Socket s40 = new Socket(HOST, PORT_40);
            
            System.out.println("You connected to " + HOST);//IF CONNECTED THEN PRINT IT OUT

            Client client = new Client(s37, s40);//START NEW CLIENT OBJECT

            Thread t = new Thread(client);//INITIATE NEW THREAD
            t.start();//START THREAD

        } catch (Exception noServer)//IF DIDNT CONNECT PRINT THAT THEY DIDNT
        {
            System.out.println("The server might not be up at thits time.");
            System.out.println("Please try again later.");
        }
    }
}

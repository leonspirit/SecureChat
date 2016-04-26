package kij_chat_authority_server;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * original
 * ->http://www.dreamincode.net/forums/topic/262304-simple-client-and-server-chat-program/
 *
 * @author santen-suru
 */
public class Main {

    // Tid-User list

    public static volatile ArrayList<Pair<Socket, String>> _loginlist = new ArrayList<>();
    public static final User user = new User();
    public static final ArrayList<Pair<String, String>> _userlist = user.getUserList();
    private static String public_key_user;
    private static String private_key_user;
    //public static final Group group = new Group();
    //public static final ArrayList<Pair<String,String>> _grouplist = group.getGroupList();
     public static void generatersa()
    {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(512,random);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            byte[] privateKeyBytes = privateKey.getEncoded();
            //System.out.println(privateKeyBytes);
            byte[] publicKeyBytes = publicKey.getEncoded();
            //System.out.println(publicKeyBytes);
            /*StringBuffer retString = new StringBuffer();
            for (int i = 0; i < publicKeyBytes.length; ++i) {
                retString.append(Integer.toHexString(0x0100 + (publicKeyBytes[i] & 0x00FF)).substring(1));
            }
            //System.out.println("public"+retString);
            */
            public_key_user= Base64.encode(publicKeyBytes);
            Keys.setPubUserKey(public_key_user);
            /*StringBuffer retString1 = new StringBuffer();
            for (int i = 0; i < privateKeyBytes.length; ++i) {
                retString1.append(Integer.toHexString(0x0100 + (privateKeyBytes[i] & 0x00FF)).substring(1));
            }
            //System.out.println("private"+retString1);
            */
            private_key_user= Base64.encode(privateKeyBytes);
            Keys.setPrivUserKey(private_key_user);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) throws IOException {
        generatersa();
        try {
            final int PORT = 4047;//SET NEW CONSTANT VARIABLE: PORT
            ServerSocket server = new ServerSocket(PORT); //SET PORT NUMBER
            System.out.println("Waiting for clients...");//AT THE START PRINT THIS

            while (true)//WHILE THE PROGRAM IS RUNNING
            {
                Socket s = server.accept();//ACCEPT SOCKETS(CLIENTS) TRYING TO CONNECT

                System.out.println("Client connected from " + s.getLocalAddress().getHostName());	//	TELL THEM THAT THE CLIENT CONNECTED

                Client chat = new Client(s, _loginlist, _userlist);//CREATE A NEW CLIENT OBJECT
                Thread t = new Thread(chat);//MAKE A NEW THREAD
                t.start();//START THE THREAD
            }
        } catch (Exception e) {
            System.out.println("An error occured.");//IF AN ERROR OCCURED THEN PRINT IT
            e.printStackTrace();
        }
    }
}

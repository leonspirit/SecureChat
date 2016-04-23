package kij_chat_server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * original
 * ->http://www.dreamincode.net/forums/topic/262304-simple-client-and-server-chat-program/
 *
 * @author billy&freddy
 */
public class Client implements Runnable {

    private Socket socket;//SOCKET INSTANCE VARIABLE
    private String username;
    private boolean login = false;
    private static String public_key;
    private static String private_key;
    private ArrayList<Pair<Socket, String>> _loginlist;
    private ArrayList<Pair<String, String>> _userlist;
    private static List<Group> _grouplist = new ArrayList<Group>();

    public Client(Socket s, ArrayList<Pair<Socket, String>> _loginlist, ArrayList<Pair<String, String>> _userlist) {
        socket = s;//INSTANTIATE THE SOCKET)
        this._loginlist = _loginlist;
        this._userlist = _userlist;
    }

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
            StringBuffer retString = new StringBuffer();
            for (int i = 0; i < publicKeyBytes.length; ++i) {
                retString.append(Integer.toHexString(0x0100 + (publicKeyBytes[i] & 0x00FF)).substring(1));
            }
            //System.out.println("public"+retString);
            public_key= retString.toString();
            //Keys.setPubUserKey(public_key_user);
            StringBuffer retString1 = new StringBuffer();
            for (int i = 0; i < privateKeyBytes.length; ++i) {
                retString1.append(Integer.toHexString(0x0100 + (privateKeyBytes[i] & 0x00FF)).substring(1));
            }
            //System.out.println("private"+retString1);
            private_key= retString1.toString();
            //Keys.setPrivUserKey(private_key_user);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() //(IMPLEMENTED FROM THE RUNNABLE INTERFACE)
    {
        try //HAVE TO HAVE THIS FOR THE in AND out VARIABLES
        {
            generatersa();
            Scanner in = new Scanner(socket.getInputStream());//GET THE SOCKETS INPUT STREAM (THE STREAM THAT YOU WILL GET WHAT THEY TYPE FROM)
            PrintWriter out = new PrintWriter(socket.getOutputStream());//GET THE SOCKETS OUTPUT STREAM (THE STREAM YOU WILL SEND INFORMATION TO THEM FROM)

            while (true)//WHILE THE PROGRAM IS RUNNING
            {
                if (in.hasNext()) {
                    String input = in.nextLine();//IF THERE IS INPUT THEN MAKE A NEW VARIABLE input AND READ WHAT THEY TYPED
//					System.out.println("Client Said: " + input);//PRINT IT OUT TO THE SCREEN
//					out.println("You Said: " + input);//RESEND IT TO THE CLIENT
//					out.flush();//FLUSH THE STREAM

                   //terima public key
                   if (input.split(" ")[0].toLowerCase().equals("pu") == true) {
                       String PUkey = input.split(" ")[1];
                       String DigSig = PUkey;//nanti di hash teros di enkrispsi, sementara gini dulu
                       Pair <String,String> Certificate = new Pair(PUkey, DigSig);
                       out.println("C " + Certificate.getFirst()+" "+Certificate.getSecond());
                       out.flush();
                       out.println("PS "+public_key);
                       out.flush();
                       
                   }
                   
                   
                   
                }
            }
        } catch (Exception e) {
            e.printStackTrace();//MOST LIKELY THERE WONT BE AN ERROR BUT ITS GOOD TO CATCH
        }
    }

}

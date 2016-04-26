package kij_chat_authority_server;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
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
    private static String public_key_user;
    private static String private_key_user;
    private ArrayList<Pair<Socket, String>> _loginlist;
    private ArrayList<Pair<String, String>> _userlist;
    private static List<Group> _grouplist = new ArrayList<Group>();

    public Client(Socket s, ArrayList<Pair<Socket, String>> _loginlist, ArrayList<Pair<String, String>> _userlist) {
        socket = s;//INSTANTIATE THE SOCKET)
        this._loginlist = _loginlist;
        this._userlist = _userlist;
    }

  
    
    @Override
    public void run() //(IMPLEMENTED FROM THE RUNNABLE INTERFACE)
    {
        try //HAVE TO HAVE THIS FOR THE in AND out VARIABLES
        {
            
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
                       String tempDigSig = Hashing.getshahasing(PUkey);//nanti di hash teros di enkrispsi, sementara gini dulu
                       //System.out.println(tempDigSig);
                       String DigSig=EncryptandDecrypt.getEncryptedDatawithPrivateKey(tempDigSig, Keys.getPrivUserKey());
                       Pair <String,String> Certificate = new Pair(PUkey, DigSig);
                       out.println("PS "+Keys.getPubUserKey());
                       out.flush();
                       out.println("C " + Certificate.getFirst()+" "+Certificate.getSecond());
                       out.flush();
                       
                   }
                   
                   
                   
                }
            }
        } catch (Exception e) {
            e.printStackTrace();//MOST LIKELY THERE WONT BE AN ERROR BUT ITS GOOD TO CATCH
        }
    }

}

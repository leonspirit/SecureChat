package kij_chat_server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * original
 * ->http://www.dreamincode.net/forums/topic/262304-simple-client-and-server-chat-program/
 *
 * @author santen-suru
 */
public class Client implements Runnable {

    private Socket socket;//SOCKET INSTANCE VARIABLE
    private String username;
    private boolean login = false;

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
                   if (input.split(" ")[0].toLowerCase().equals("PU") == true) {
                       String PUkey = input.split(" ")[1];
                       String DigSig = PUkey;//nanti di hash teros di enkrispsi, sementara gini dulu
                       Pair <String,String> Certificate = new Pair(PUkey, DigSig);
                       out.println("PS" + Certificate);
                       out.flush();
                   }
                   
                   
                   
                }
            }
        } catch (Exception e) {
            e.printStackTrace();//MOST LIKELY THERE WONT BE AN ERROR BUT ITS GOOD TO CATCH
        }
    }

}

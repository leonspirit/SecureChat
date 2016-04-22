package kij_chat_client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.util.Pair;

/**
 * original
 * ->http://www.dreamincode.net/forums/topic/262304-simple-client-and-server-chat-program/
 *
 * @author santen-suru
 */
public class Client implements Runnable {

    private Socket socket_37, socket_40;//MAKE SOCKET INSTANCE VARIABLE
    Keys key = new Keys();
    
    // use arraylist -> arraylist dapat diparsing as reference
    volatile ArrayList<String> log = new ArrayList<>();
    volatile ArrayList<Group> groupList = new ArrayList<Group>();
    volatile ArrayList<String> userList = new ArrayList<String>();

    public Client(Socket s37, Socket s40) {
        socket_37 = s37;//INSTANTIATE THE INSTANCE VARIABLE
        socket_40 = s40;
        log.add(String.valueOf("false"));
    }

    @Override
    public void run()//INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
    {
        //generate key disini
        //Keys.set
        
        try {
            Scanner chat = new Scanner(System.in);//GET THE INPUT FROM THE CMD
            
            Scanner in37 = new Scanner(socket_37.getInputStream());//GET THE CLIENTS INPUT STREAM (USED TO READ DATA SENT FROM THE SERVER)
            PrintWriter out37 = new PrintWriter(socket_37.getOutputStream());//GET THE CLIENTS OUTPUT STREAM (USED TO SEND DATA TO THE SERVER)

            Scanner in40 = new Scanner(socket_40.getInputStream());
            PrintWriter out40 = new PrintWriter(socket_40.getOutputStream());
//			while (true)//WHILE THE PROGRAM IS RUNNING
//			{						
//				String input = chat.nextLine();	//SET NEW VARIABLE input TO THE VALUE OF WHAT THE CLIENT TYPED IN
//				out.println(input);//SEND IT TO THE SERVER
//				out.flush();//FLUSH THE STREAM
//				
//				if(in.hasNext())//IF THE SERVER SENT US SOMETHING
//					System.out.println(in.nextLine());//PRINT IT OUT
//			}
          
            //minta sertifikat disini
            
            
            Read_37 reader_37 = new Read_37(in37, out37, log, groupList, userList, key);
            Thread tr_37 = new Thread(reader_37);
            tr_37.start();
            
            Read_40 reader_40 = new Read_40(in40, out40, log, groupList, userList, key);
            Thread tr_40 = new Thread(reader_40);
            tr_40.start();
            
            Write_37 writer_37 = new Write_37(chat, out37, log, groupList, userList, key);
            Thread tw_37 = new Thread(writer_37);
            tw_37.start();
            
            Write_40 writer_40 = new Write_40(chat, out40, log, groupList, userList, key);
            Thread tw_40 = new Thread(writer_40);
            tw_40.start();
            
            
            while (tr_37.isAlive() == true) {
                if (tr_37.isAlive() == false && tw_37.isAlive() == false) {
                    socket_37.close();
                }
            }
            while (tr_40.isAlive() == true) {
                if (tr_40.isAlive() == false && tw_40.isAlive() == false) {
                    socket_40.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Write_40 implements Runnable {

    private Scanner chat;
    private PrintWriter out;
    private boolean init;
    boolean keepGoing = true;
    ArrayList<String> log;
    ArrayList<Certificate> cert;
    private String public_key_user;
    StringBuffer commandto_40;
    
    
    public Write_40(Scanner chat, PrintWriter out, ArrayList<String> log, ArrayList<Certificate> cert, String public_key_user,StringBuffer commandto_40) {
        this.chat = chat;
        this.out = out;
        this.log = log;
        this.cert=cert;
        this.commandto_40=commandto_40;
        this.init=false;
        this.public_key_user = public_key_user;
        
        
    }

    @Override
    public void run()//INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
    {
        try {
            while (keepGoing)//WHILE THE PROGRAM IS RUNNING
            {
                
                String input = null ;	//SET NEW VARIABLE input TO THE VALUE OF WHAT THE CLIENT TYPED IN
                if(!init)
                {
                    input="PU Public "+this.public_key_user;
                    init=true;
                    out.println(input);//SEND IT TO THE SERVER
                    out.flush();//FLUSH THE STREAM
                }
                else if(init)
                {
                    //pilihan 1
                    //commandto_40.wait();
                    /*if(!commandto_40.equals(""))
                    {
                        input=commandto_40.toString();
                        commandto_40.replace(0, commandto_40.length()-1, "");
                        
                        out.println(input);//SEND IT TO THE SERVER
                        out.flush();//FLUSH THE STREAM
                    }*/
                    //pilihan 2
                    input=chat.nextLine();
                    out.println(input);//SEND IT TO THE SERVER
                    out.flush();//FLUSH THE STREAM
                }
               
                //System.out.println(input);
                

                if (input.contains("logout")) {
                    if (log.contains("true")) {
                        keepGoing = false;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
        }
    }

}

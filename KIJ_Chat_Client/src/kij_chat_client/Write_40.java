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
    private PrintWriter out;
    boolean keepGoing = true;
    ArrayList<String> log;
    
    private String public_key_user;
    //StringBuffer commandto_40;
    
    
    public Write_40(PrintWriter out, ArrayList<String> log) {
        this.out = out;
        this.log = log;
        this.public_key_user = Keys.getPubUserKey();
        
        
    }

    @Override
    public void run()//INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
    {
        try {
            while (keepGoing)//WHILE THE PROGRAM IS RUNNING
            {
                //kirim public key user
                String input = null ;	
                input="PU "+this.public_key_user;
                keepGoing=false;
                //System.out.println(input);
                out.println(input);//SEND IT TO THE SERVER
                out.flush();//FLUSH THE STREAM
                log.clear();log.add("true");
            }
        } catch (Exception e) {
            e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
        }
    }

}

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

public class Write_37 implements Runnable {

    private Scanner chat;
    private PrintWriter out;
    boolean keepGoing = true;
    ArrayList<String> log;
    ArrayList<Group> groupList;
    ArrayList<String> userList;
    Keys key;

    public Write_37(Scanner chat, PrintWriter out, ArrayList<String> log, ArrayList<Group> groupList, ArrayList<String> userList, Keys key) {
        this.chat = chat;
        this.out = out;
        this.log = log;
        this.groupList = groupList;
        this.userList = userList;
        this.key = key;
        out.println("PU " + key.getPubUserKey());
        out.flush();
    }

    @Override
    public void run()//INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
    {
        try {
            while (keepGoing)//WHILE THE PROGRAM IS RUNNING
            {
                String input = chat.nextLine();	//SET NEW VARIABLE input TO THE VALUE OF WHAT THE CLIENT TYPED IN
                out.println(input);//SEND IT TO THE SERVER
                out.flush();//FLUSH THE STREAM

                String vals[] = input.split(" ");
                if(vals[0].toLowerCase().equals("login")){
                    
                }
                else if(vals[0].toLowerCase().equals("logout")){
                    
                }
                else if(vals[0].toLowerCase().equals("pm")){
                    
                }
                else if(vals[0].toLowerCase().equals("cg")){
                    
                }
                else if(vals[0].toLowerCase().equals("jg")){
                    
                }
                else if(vals[0].toLowerCase().equals("gm")){
                    
                }
                else if(vals[0].toLowerCase().equals("bm")){
                    
                }
                /*
                if (input.contains("logout")) {
                    if (log.contains("true")) {
                        keepGoing = false;
                    }
                }
                */
            }
        } catch (Exception e) {
            e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
        }
    }

}

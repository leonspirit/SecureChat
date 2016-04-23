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
    
    private void try_pm(String user, String msg, String cm, String gn){
        String messageOut;
        if(cm.equals("pm")){
            messageOut = "PM" + " " + "USER" +  " " + user +  " " + msg;
            out.println(messageOut);
        }
        else if(cm.equals("gm")){
            messageOut = "PM" + " " + "GROUP" + " " + gn + " " + user +  " " + msg;
            out.println(messageOut);
        }
        else if(cm.equals("bm")){
            messageOut = "PM" + " " + "BROADCAST" +  " " + user + " " + msg;
            out.println(messageOut);
        }
    }

    @Override
    public void run()//INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
    {
        try {
            while (keepGoing)//WHILE THE PROGRAM IS RUNNING
            {
                String input = chat.nextLine();	//SET NEW VARIABLE input TO THE VALUE OF WHAT THE CLIENT TYPED IN
                
                String vals[] = input.split(" ");
                if(vals[0].toLowerCase().equals("login")){
                    System.out.println(input);
                    out.println(input);
                    log.clear();
                    log.add(vals[1]);
                }
                else if(vals[0].toLowerCase().equals("logout")){
                    out.println(input);
                    if(log.contains("true")){
                        keepGoing = false;
                    }
                }
                else if(vals[0].toLowerCase().equals("pm")){
                    String messageOut = "";
                    for (int j = 2; j < vals.length; j++) {
                        messageOut += vals[j] + " ";
                    }
                    try_pm(vals[1], messageOut, vals[0] , null);
                }
                else if(vals[0].toLowerCase().equals("cg")){
                    out.println(input);
                }
                else if(vals[0].toLowerCase().equals("jg")){
                    out.println(input);
                }
                else if(vals[0].toLowerCase().equals("gm")){
                    String messageOut = "";
                    for (int j = 2; j < vals.length; j++) {
                        messageOut += vals[j] + " ";
                    }
                    
                    for(Group g : groupList){
                        if(g.getName().equals(vals[1])){
                            for(String s : g.getGroupList()){
                                try_pm(s, messageOut, vals[0] , g.getName());
                            }
                        }
                    }
                }
                else if(vals[0].toLowerCase().equals("bm")){
                    String messageOut = "";
                    for (int j = 2; j < vals.length; j++) {
                        messageOut += vals[j] + " ";
                    }
                    
                    for (String u : userList){
                        try_pm(u, messageOut, vals[0] , null);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
        }
    }

}

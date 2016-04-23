/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_client;

/*import java.net.Socket;*/
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.util.Pair;

public class Read_37 implements Runnable {

    private Scanner in;//MAKE SOCKET INSTANCE VARIABLE
    private PrintWriter out;
    String input;
    boolean keepGoing = true;
    ArrayList<String> log;
    ArrayList<Group> groupList;
    ArrayList<String> userList;
    Keys key;

    public Read_37(Scanner in, PrintWriter out, ArrayList<String> log, ArrayList<Group> groupList, ArrayList<String> userList, Keys key) {
        this.in = in;
        this.out = out;
        this.log = log;
        this.groupList = groupList;
        this.userList = userList;
        this.key = key;
    }

    @Override
    public void run()//INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
    {
        try {
            while (keepGoing)//WHILE THE PROGRAM IS RUNNING
            {
                if (this.in.hasNext()) {
                    //IF THE SERVER SENT US SOMETHING
                    input = this.in.nextLine();
                    
                    String vals[] = input.split(" ");
                    if(vals[0].toLowerCase().equals("mg")){
                        
                        boolean created = false;
                        Group now = null;
                        for (Group g : groupList){
                            if(g.getName().equals(vals[1])){
                                created = true;
                                now = g;
                            }
                        }
                        
                        if(created == false)now = new Group(vals[1]);
                        now.updateGroup(vals[2]);
                  
                    }
                    else if(vals[0].toLowerCase().equals("u")){
                        userList.add(vals[1]);
                    }
                    else if(vals[0].toLowerCase().equals("rc")){
                        String username = vals[1];
                        out.println("GC " + vals[1] + " " + log.get(0) + " " + key.getUserCertificate());
                        out.flush();
                    }
                    else if(vals[0].toLowerCase().equals("gc")){
                        key.addUserCertificate(new Pair(vals[2],vals[3]));
                    }
                    else if(vals[0].toLowerCase().equals("success")){
                        System.out.println(input);
                        if(vals[1].toLowerCase().equals("logout")){
                            keepGoing = false;
                        }
                        else if (vals[1].toLowerCase().equals("login")){
                            log.add("true");
                        }
                    }
                    else if(vals[0].toLowerCase().equals("fail")){
                        System.out.println(input);
                        if(vals[1].toLowerCase().equals("login")){
                            log.clear();
                        }
                    }
                    else{
                        System.out.println(input);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
        }
    }
}

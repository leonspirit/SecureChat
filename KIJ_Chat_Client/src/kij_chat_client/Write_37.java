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
        out.flush();
    }
    //khusus login,logout,cg,jg
    private String ubah_to_chipertext(String[] vals)
    {
        StringBuilder input_login=new StringBuilder();
        for(int x=0;x<vals.length;x++)
        {
            if(x<2)
            {
                input_login.append(vals[x]+" ");
            }
            else
            {
                input_login.append(Hashing.getshahasing(vals[x]));
            }
        }
        String data_yang_dikirim= EncryptandDecrypt.getEncryptedDatawithPrivateKey(input_login.toString(), Keys.getPrivUserKey());
        return data_yang_dikirim;
    }
    //khusus untuk rc
    private String ubah_to_chipertextrc(String[] vals)
    {
        StringBuilder input_login=new StringBuilder();
        for(int x=0;x<vals.length;x++)
        {
            input_login.append(vals[x]+" ");   
        }
        input_login.append(log.get(0));
        String data_yang_dikirim= EncryptandDecrypt.getEncryptedDatawithPrivateKey(input_login.toString(), Keys.getPrivUserKey());
        return data_yang_dikirim;
    }
    @Override
    public void run()//INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
    {
        try {
            while (keepGoing)//WHILE THE PROGRAM IS RUNNING
            {
                String input = chat.nextLine();	//SET NEW VARIABLE input TO THE VALUE OF WHAT THE CLIENT TYPED IN
                //System.out.println(input);
                String vals[] = input.split(" ");
                if(vals[0].toLowerCase().equals("login")){
                    
                    out.println(ubah_to_chipertext(vals));
                    out.flush();
                    log.clear();
                    log.add(vals[1]);
                }
                else if(vals[0].toLowerCase().equals("rc"))
                {
                    out.println(ubah_to_chipertextrc(vals));
                    out.flush();
                }
                else if(vals[0].toLowerCase().equals("logout")){
                    out.println(ubah_to_chipertext(vals));
                    out.flush();
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
                    out.println(ubah_to_chipertext(vals));
                    out.flush();
                }
                else if(vals[0].toLowerCase().equals("jg")){
                    out.println(ubah_to_chipertext(vals));
                    out.flush();
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
                    for (int j = 1; j < vals.length; j++) {
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

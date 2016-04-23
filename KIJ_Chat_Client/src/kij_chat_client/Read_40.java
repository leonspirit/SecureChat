/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_client;

/*import java.net.Socket;*/
import java.util.ArrayList;
import java.util.Scanner;

public class Read_40 implements Runnable {

    private Scanner in;//MAKE SOCKET INSTANCE VARIABLE
    String input;
    boolean keepGoing = true;
    ArrayList<String> log;
    ArrayList<Certificate> cert;
    Pair<String,String> our_cert;
    private boolean init;
    StringBuffer public_key_ca;
    
    public Read_40(Scanner in, ArrayList<String> log, ArrayList<Certificate> cert, Pair<String,String> our_cert,StringBuffer public_key_ca) {
        this.in = in;
        this.log = log;
        this.cert =cert;
        this.our_cert = our_cert;
        this.init=false;
        this.public_key_ca=public_key_ca;
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
                    if(!init)
                    {
                        if(input.split(" ")[0].equals("C"))
                        {
                            String a=input.split(" ")[1];
                            String b= input.split(" ")[2];
                            our_cert= new Pair(a,b);//g eruh bener atau salah;
                            
                        }
                        input= this.in.nextLine();
                        if(input.split(" ")[0].equals("PS"))
                        {
                            public_key_ca.append(input.split(" ")[1]);
                        }
                        init=true;
                    }
                    else
                    {
                        if(input.split(" ")[0].equals("GC"))
                        {
                            Pair<String,String> data= new Pair(input.split(" ")[3],input.split(" ")[4]);
                            Certificate c= new Certificate(input.split(" ")[2]);
                            c.addpublickey(data);
                            cert.add(c);
                        }
                    }
                    System.out.println(input);//PRINT IT OUT
                    if (input.split(" ")[0].toLowerCase().equals("success")) {
                        if (input.split(" ")[1].toLowerCase().equals("logout")) {
                            keepGoing = false;
                        } else if (input.split(" ")[1].toLowerCase().equals("login")) {
                            log.clear();
                            log.add("true");
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
        }
    }
}

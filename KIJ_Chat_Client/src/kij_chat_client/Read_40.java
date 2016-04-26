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
    
    
    public Read_40(Scanner in, ArrayList<String> log) {
        this.in = in;
        this.log = log;
        
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
                    if(input.split(" ")[0].toLowerCase().equals("ps"))
                    {
                        System.out.println(input);
                        Keys.setPubServerKey(input.split(" ")[1]);
                    }
                    
                    if(this.in.hasNext())
                    {
                        
                        input = this.in.nextLine();
                        if(input.split(" ")[0].toLowerCase().equals("c"))
                        {
                            //System.out.println(input);
                            String a=input.split(" ")[1];
                            System.out.println(a);
                            String b= input.split(" ")[2];
                            System.out.println(b);
                            String temp_data=EncryptandDecrypt.getDecryptedDatawithPublicKey(b, Keys.getPubServerKey());
                            System.out.println(temp_data);
                            Pair<String,String>data = new Pair(a,b);//g eruh bener atau salah;
                            Keys.setUserCertificate(data);
                            //System.out.println("certif"+Keys.getUserCertificate().getFirst().toString()+" "+Keys.getUserCertificate().getSecond().toString());
                        }
                        
                    }
                    
                    //PRINT IT OUT
                    keepGoing = false;
                    log.clear();
                    log.add("true");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
        }
    }
}

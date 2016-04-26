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
                if (this.in.hasNext()) {
                    //IF THE SERVER SENT US SOMETHING
                    input = this.in.nextLine();
                    //System.out.println(input);
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
                        groupList.add(now);
                    }
                    else if(vals[0].toLowerCase().equals("pm")){
                        
                        System.out.println(vals[1] +" " + EncryptandDecrypt.getDecryptedDatawithPrivateKey(vals[2], key.getPrivUserKey()));
                    }
                    else if (vals[0].toLowerCase().equals("gm"))
                    {
                    //    System.out.println(input);
                        System.out.println(vals[1]+" "+vals[2]+" "+vals[3] +" "+ EncryptandDecrypt.getDecryptedDatawithPrivateKey(vals[4], key.getPrivUserKey()));
                    }
                    
                    else if (vals[0].toLowerCase().equals("bm"))
                    {
                    //    System.out.println(input);
                        System.out.println(vals[1]+" "+vals[2]+" "+vals[3] +" "+ EncryptandDecrypt.getDecryptedDatawithPrivateKey(vals[4], key.getPrivUserKey()));
                    }
                    else if(vals[0].toLowerCase().equals("u")){
                        userList.add(vals[1]);
                        System.out.println(input);
                        String kirim_rc="RC "+vals[1]+" ";
                        System.out.println(kirim_rc);
                        out.println(ubah_to_chipertextrc(kirim_rc.split(" ")));
                        out.flush();
                    }
                    else if(vals[0].toLowerCase().equals("rc")){
                        String username = vals[1];
                        out.println("GC " + vals[2] + " " + log.get(0) + " " + Keys.getUserCertificate().getFirst()+" "+Keys.getUserCertificate().getSecond());
                        out.flush();
                    }
                    else if(vals[0].toLowerCase().equals("gc")){
                        String hashingpub=Hashing.getshahasing(vals[3]);
                        //System.out.println(vals[4]);
                        //System.out.println(Keys.getPubServerKey());
                        String balik=EncryptandDecrypt.getDecryptedDatawithPublicKey(vals[4], Keys.getPubServerKey());
                        //System.out.println("balik nama" + balik);
                        //System.out.println("hasing"+hashingpub);
                        if(hashingpub.equals(balik))
                        {
                            Pair<String,String> data= new Pair(vals[2],vals[3]);
                            //System.out.println("pair"+data.getFirst()+" "+data.getSecond());
                            Keys.addUserCertificate(data);
                        }
                        else
                        {
                            System.out.println("Failed RC");
                        }
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

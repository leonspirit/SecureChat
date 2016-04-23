package kij_chat_server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * original
 * ->http://www.dreamincode.net/forums/topic/262304-simple-client-and-server-chat-program/
 *
 * @author santen-suru
 */
public class Client implements Runnable {

    private Socket socket;//SOCKET INSTANCE VARIABLE
    private String username;
    private boolean login = false;

    private ArrayList<Pair<Socket, String>> _loginlist;
    private ArrayList<Pair<String, String>> _userlist;
    private static List<Group> _grouplist = new ArrayList<Group>();

    public Client(Socket s, ArrayList<Pair<Socket, String>> _loginlist, ArrayList<Pair<String, String>> _userlist) {
        socket = s;//INSTANTIATE THE SOCKET)
        this._loginlist = _loginlist;
        this._userlist = _userlist;
    }

    private boolean private_msg(String input) {
        boolean exist = false;
        String[] vals = input.split(" ");

        for (Pair<Socket, String> cur : _loginlist) {
            if (cur.getSecond().equals(vals[1])) {
                PrintWriter outDest = null;
                try {
                    outDest = new PrintWriter(cur.getFirst().getOutputStream());
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }

                String messageOut = "";
                for (int j = 3; j < vals.length; j++) {
                    messageOut += vals[j] + " ";
                }
                System.out.println(this.username + " to " + vals[1] + " : " + messageOut);
                outDest.println(this.username + ": " + messageOut);
                outDest.flush();
                exist = true;
            }
        }

        if (exist == false) {
            System.out.println("pm to " + vals[1] + " by " + this.username + " failed.");
            return false;
        }
        return true;
    }

    private boolean group_msg(String input, String user, String groupname) {
        boolean exist = false;
        String[] vals = input.split(" ");

        for (Pair<Socket, String> cur : _loginlist) {
            if (cur.getSecond().equals(user)) {
                PrintWriter outDest = null;
                try {
                    outDest = new PrintWriter(cur.getFirst().getOutputStream());
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println(this.username + " <" + groupname + "> to " + user + " : " + input);
                outDest.println(this.username + " <" + groupname + "> : " + input);
                outDest.flush();
                exist = true;
            }
        }

        if (exist == false) {
            System.out.println("gm to " + user + " by " + this.username + " failed.");
            return false;
        }
        return true;
    }

    @Override
    public void run() //(IMPLEMENTED FROM THE RUNNABLE INTERFACE)
    {
        try //HAVE TO HAVE THIS FOR THE in AND out VARIABLES
        {
            Scanner in = new Scanner(socket.getInputStream());//GET THE SOCKETS INPUT STREAM (THE STREAM THAT YOU WILL GET WHAT THEY TYPE FROM)
            PrintWriter out = new PrintWriter(socket.getOutputStream());//GET THE SOCKETS OUTPUT STREAM (THE STREAM YOU WILL SEND INFORMATION TO THEM FROM)

            while (true)//WHILE THE PROGRAM IS RUNNING
            {
                if (in.hasNext()) {
                    String input = in.nextLine();//IF THERE IS INPUT THEN MAKE A NEW VARIABLE input AND READ WHAT THEY TYPED
//					System.out.println("Client Said: " + input);//PRINT IT OUT TO THE SCREEN
//					out.println("You Said: " + input);//RESEND IT TO THE CLIENT
//					out.flush();//FLUSH THE STREAM
                    //System.out.println(input);
                    // param LOGIN <userName> <pass>
                    if (input.split(" ")[0].toLowerCase().equals("login") == true) {
                        String[] vals = input.split(" ");
                        if (this._userlist.contains(new Pair(vals[1], vals[2])) == true) {
                            if (this.login == false) {
                                this._loginlist.add(new Pair(this.socket, vals[1]));
                                this.username = vals[1];
                                this.login = true;
                                System.out.println("Users count: " + this._loginlist.size());
                                out.println("SUCCESS login");
                                out.flush();
                            } else {
                                out.println("FAIL login");
                                out.flush();
                            }
                        } else {
                            out.println("FAIL login");
                            out.flush();
                        }
                    }

                    // param LOGOUT
                    if (input.split(" ")[0].toLowerCase().equals("logout") == true) {
                        String[] vals = input.split(" ");

                        if (this._loginlist.contains(new Pair(this.socket, this.username)) == true) {
                            
                            for(Group g : _grouplist){
                                g.delUser(this.username);
                            }
                            
                            this._loginlist.remove(new Pair(this.socket, this.username));
                            System.out.println(this._loginlist.size());
                            out.println("SUCCESS logout");
                            out.flush();
                            this.socket.close();
                            break;
                        } else {
                            out.println("FAIL logout");
                            out.flush();
                        }
                    }

                    // param PM USER <userName dst> <message>
                    if (input.split(" ")[0].toLowerCase().equals("pm") 
                            && input.split(" ")[1].toLowerCase().equals("user")) {

                        boolean succ = private_msg(input);
                        if (!succ) {
                            out.println("FAIL pm");
                            out.flush();
                        }
                    }

                    // param CG <groupName>
                    if (input.split(" ")[0].toLowerCase().equals("cg") == true) {
                        String[] vals = input.split(" ");

                        boolean exist = false;

                        for (Group selGroup : _grouplist) {
                            if (selGroup.getName().equals(vals[1])) {
                                exist = true;
                            }
                        }

                        if (exist == false) {
                            Group newGroup = new Group(vals[1]);
                            newGroup.updateGroup(this.username);
                            _grouplist.add(newGroup);
                            int total = _grouplist.size();
                            System.out.println("total group: " + total);
                            System.out.println("cg " + vals[1] + " by " + this.username + " successed.");
                            out.println("SUCCESS cg");
                            out.flush();
                        } else {
                            System.out.println("cg " + vals[1] + " by " + this.username + " failed.");
                            out.println("FAIL cg");
                            out.flush();
                        }
                    }

                    //param JG <groupName>
                    if (input.split(" ")[0].toLowerCase().equals("jg") == true) {
                        String[] vals = input.split(" ");

                        boolean exist = false;

                        for (Group selGroup : _grouplist) {
                            if (selGroup.getName().equals(vals[1])) {
                                exist = true;
                                selGroup.updateGroup(this.username);
                            }
                        }

                        if (exist == true) {
                            System.out.println("jg " + vals[1] + " by " + this.username + " successed.");
                            out.println("SUCCESS jg");
                            out.flush();
                        } else {
                            System.out.println("jg " + vals[1] + " by " + this.username + " failed.");
                            out.println("FAIL jg");
                            out.flush();
                        }
                    }

                    //param PM GROUP <groupName> <userName dest> <message>
                    if (input.split(" ")[0].toLowerCase().equals("pm")
                            && input.split(" ")[1].toLowerCase().equals("group") ) {

                        boolean exist = false;
                        Group sending = null;
                        String[] vals = input.split(" ");

                        for (Group g : _grouplist) {
                            if (g.getName().equals(vals[2])) {
                                exist = true;
                                sending = g;
                            }
                        }

                        if (!exist) {
                            System.out.println("gm " + vals[2] + " by " + this.username + " failed.");
                            out.println("FAIL gm");
                            out.flush();
                        }                
                        else {
                            boolean found = sending.checkUser(this.username);
                            if(!found){
                                System.out.println("gm " + vals[2] + " by " + this.username + " failed.");
                                out.println("FAIL gm");
                                out.flush();
                            }
                            else{
                                
                                String messageOut = " ";
                                for (int j = 4; j < vals.length; j++) {
                                    messageOut += vals[j] + " ";
                                }

                                boolean succ = group_msg(messageOut, vals[3], vals[2]);
                                if (!succ) {
                                    out.println("FAIL gm to " + vals[3]);
                                    out.flush();
                                }
                            }
                            
                        }
                    }
                    
                    // param PM BROADCAST <userName dest> <message>
                    if (input.split(" ")[0].toLowerCase().equals("pm")
                            && input.split("")[1].toLowerCase().equals("broadcast") ) {
  
                        String[] vals = input.split(" ");
                        String messageOut = " ";
                        for (int j = 3; j < vals.length; j++) {
                            messageOut += vals[j] + " ";
                        }
                        
                        boolean succ = group_msg(messageOut, vals[2], "BROADCAST");
                        if (!succ) {
                            out.println("FAIL broadcast to " + vals[2]);
                            out.flush();
                        }               
                    }
                    
                    if(input.split(" ")[0].toLowerCase().equals("pu")){
                        
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();//MOST LIKELY THERE WONT BE AN ERROR BUT ITS GOOD TO CATCH
        }
    }

}

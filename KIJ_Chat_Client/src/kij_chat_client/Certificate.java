/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_client;

import java.util.ArrayList;


/**
 *
 * @author freddy
 */
public class Certificate {
    private Pair<String,String> public_key_user_lain;
    String public_key_ca;
    private String user_name;
    Certificate(String name) {
        this.user_name=name;
    }
    public void addpublickey(Pair<String,String> userlain)
    {        
        //untuk ngecheck antara hash dengan datanya aq tidak tau gmn caranya sudah.
        this.public_key_user_lain=userlain;
    }
    public void setpublickeyca(String public_key_ca)
    {
        this.public_key_ca=public_key_ca;
    }
    public String getpublickey()
    {
        return this.public_key_user_lain.getFirst();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_authority_server;

import java.util.ArrayList;

/**
 *
 * @author santen-suru
 */
public class User {
    // User-Password list
    private ArrayList<Pair<String,String>> _userlist = new ArrayList<>();
    
    User() {
        _userlist.add(new Pair("Admin", "Admin"));
        _userlist.add(new Pair("Andi", "Andi"));
        _userlist.add(new Pair("Budi", "Budi"));
        _userlist.add(new Pair("Rudi", "Rudi"));
        _userlist.add(new Pair("Luci", "Luci"));
        _userlist.add(new Pair("ari", "a"));
        _userlist.add(new Pair("budi", "b"));
        _userlist.add(new Pair("cindy", "c"));
    }
    
    public ArrayList<Pair<String,String>> getUserList() {
        return _userlist;
    }
}

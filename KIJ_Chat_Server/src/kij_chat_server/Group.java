/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_server;

import java.util.ArrayList;

/**
 *
 * @author santen-suru
 */
public class Group {
    // Group-User list
    //private ArrayList<Pair<String,String>> _grouplist = new ArrayList<>();
    private String groupName;
    private ArrayList<String> groupMember = new ArrayList<String>();
    
    public String getName(){
        return groupName;
    }
    
    Group(String name) {
        this.groupName = name;
    }
    
    public ArrayList<String> getGroupList() {
        return groupMember;
    }
    
    public void updateGroup(String memberName) {
        groupMember.add(memberName);
    }
}

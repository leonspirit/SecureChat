/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_client;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Leonspirit
 */
public class Keys {
 
    private String pubUserKey;
    private String privUserKey;
    private String pubServerKey;
    private String userCertificate;
    private ArrayList<Pair<String,String>> certificates;

    public String getPubUserKey() {
        return pubUserKey;
    }

    public void setPubUserKey(String pubUserKey) {
        this.pubUserKey = pubUserKey;
    }

    public String getPrivUserKey() {
        return privUserKey;
    }

    public void setPrivUserKey(String privUserKey) {
        this.privUserKey = privUserKey;
    }

    public String getPubServerKey() {
        return pubServerKey;
    }

    public void setPubServerKey(String pubServerKey) {
        this.pubServerKey = pubServerKey;
    }

    public String getUserCertificate() {
        return userCertificate;
    }

    public void addUserCertificate(Pair<String,String>cert){
        certificates.add(cert);
    }
    
    public ArrayList<Pair<String,String>> getCertificates() {
        return certificates;
    }

    public void setCertificates(ArrayList<Pair<String,String>> certificates) {
        this.certificates = certificates;
    }
    
    
}

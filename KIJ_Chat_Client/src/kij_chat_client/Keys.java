/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_client;

import java.util.ArrayList;


/**
 *
 * @author Leonspirit
 */
public class Keys {
 
    private static String pubUserKey;
    private static String privUserKey;

    
    private static String pubServerKey;
    private static Pair<String,String> userCertificate;
    private ArrayList<Pair<String,String>> certificates;

    public static String getPubUserKey() {
        return pubUserKey;
    }
    public static Pair<String, String> getUserCertificate() {
        return userCertificate;
    }

    public static void setUserCertificate(Pair<String, String> userCertificate) {
        Keys.userCertificate = userCertificate;
    }
    public static void setPubUserKey(String pubUserKey) {
        Keys.pubUserKey = pubUserKey;
    }

    public static String getPrivUserKey() {
        return privUserKey;
    }

    public static void setPrivUserKey(String privUserKey) {
        Keys.privUserKey = privUserKey;
    }

    public static String getPubServerKey() {
        return pubServerKey;
    }

    public static void setPubServerKey(String pubServerKey) {
        Keys.pubServerKey = pubServerKey;
    }

    /*public static Pair<String,String> getUserCertificate() {
        return userCertificate;
    }*/

    public void addUserCertificate(Pair< String , String> cert ){
        certificates.add(cert);
    }
    
    public ArrayList<Pair<String,String>> getCertificates() {
        return certificates;
    }

    public void setCertificates(ArrayList<Pair<String,String>> certificates) {
        this.certificates = certificates;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_client;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author freddy
 */
public class EncryptandDecrypt {
    public static List<String> splitEqually(String text, int size) {
    // Give the list the right capacity to start with. You could use an array
    // instead if you wanted.
    List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

    for (int start = 0; start < text.length(); start += size) {
        ret.add(text.substring(start, Math.min(text.length(), start + size)));
    }
    return ret;
    }
    public static String encrypt(String message,String publicKey)
    {
        String hasil=null;
        try {
            byte[] publicKeyBytes=Base64.decode(publicKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey publicf = kf.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            
            Cipher c=Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, publicf);
            byte[] rawencrypt=c.doFinal(message.getBytes());
            hasil=Base64.encode(rawencrypt);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasil;
    }
    public static String encrypt1(String message,String privateKey)
    {
        String hasil=null;
        try {
            byte[] privateKeyBytes=Base64.decode(privateKey);
            KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
            PrivateKey privatef = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
            
            Cipher c=Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, privatef);
            byte[] rawencrypt=c.doFinal(message.getBytes());
            hasil=Base64.encode(rawencrypt);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasil;
    }
    public static String decrypt(String message,String privateKey)
    {
        String hasil=null;
        
        try {
            byte[] privateKeyBytes=Base64.decode(privateKey);
            KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
            PrivateKey privatef = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
            Cipher c= Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, privatef);
            byte[] rawdecrypt=c.doFinal(Base64.decode(message));
            hasil=new String(rawdecrypt);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasil;
    }
    public static String decrypt1(String message,String publicKey)
    {
        String hasil=null;
        
        try {
            byte[] publicKeyBytes=Base64.decode(publicKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey publicf = kf.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            Cipher c= Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, publicf);
            byte[] rawdecrypt=c.doFinal(Base64.decode(message));
            hasil=new String(rawdecrypt);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasil;
    }
    public static String getEncryptedDatawithPublicKey(String message,String publicKey)
    {
        List<String> chunk_data=splitEqually(message,50);
        StringBuilder encrypted_message= new StringBuilder();
        for(int x=0;x<chunk_data.size();x++)
        {
            String res_chunk_data=chunk_data.get(x);
            //System.out.println(res_chunk_data);
            String encrypteddata=encrypt(res_chunk_data,publicKey);
            encrypted_message.append(encrypteddata);
            //System.out.println(encrypteddata);
        }
        return encrypted_message.toString();
    }
    public static String getEncryptedDatawithPrivateKey(String message,String privateKey)
    {
        List<String> chunk_data=splitEqually(message,50);
        StringBuilder encrypted_message= new StringBuilder();
        for(int x=0;x<chunk_data.size();x++)
        {
            String res_chunk_data=chunk_data.get(x);
            //System.out.println(res_chunk_data);
            String encrypteddata=encrypt1(res_chunk_data,privateKey);
            encrypted_message.append(encrypteddata);
            //System.out.println(encrypteddata);
        }
        return encrypted_message.toString();
    }
    public static String getDecryptedDatawithPrivateKey(String message,String privateKey)
    {
        String data_to_decrypt= message;
        String[] data_ready_to_decrypt=data_to_decrypt.split("==");
        StringBuilder decrypted_message=new StringBuilder();
        for(int x=0;x<data_ready_to_decrypt.length;x++)
        {
            StringBuilder temp= new StringBuilder(data_ready_to_decrypt[x]);
            temp.append("==");
            String decrypteddata=decrypt(temp.toString(),privateKey);
            //System.out.println(decrypteddata);
            decrypted_message.append(decrypteddata);
        }
        return decrypted_message.toString();
    }
    public static String getDecryptedDatawithPublicKey(String message,String publicKey)
    {
        String data_to_decrypt= message;
        String[] data_ready_to_decrypt=data_to_decrypt.split("==");
        StringBuilder decrypted_message=new StringBuilder();
        for(int x=0;x<data_ready_to_decrypt.length;x++)
        {
            StringBuilder temp= new StringBuilder(data_ready_to_decrypt[x]);
            temp.append("==");
            String decrypteddata=decrypt1(temp.toString(),publicKey);
            //System.out.println(decrypteddata);
            decrypted_message.append(decrypteddata);
        }
        return decrypted_message.toString();
    }
}

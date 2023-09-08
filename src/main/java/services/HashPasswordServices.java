/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HANGOCHAN
 */
public class HashPasswordServices {
    public static String HashPassword(String password){
       try {
           MessageDigest messageDigest = MessageDigest.getInstance("MD5");
           messageDigest.update(password.getBytes());
           byte[] resultArray = messageDigest.digest();
           StringBuilder stringBuilder = new StringBuilder();
           for (byte b : resultArray) {
               stringBuilder.append(String.format("%02x", b));
           }
           return stringBuilder.toString();
       } catch (NoSuchAlgorithmException ex) {
           Logger.getLogger(LoginServices.class.getName()).log(Level.SEVERE, null, ex);
       }
       return "";
   }
}

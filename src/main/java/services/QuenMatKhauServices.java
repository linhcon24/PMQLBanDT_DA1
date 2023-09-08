/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.NhanVien;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import repositories.NhanVienRepositories;
import utilities.EmailSender;

/**
 *
 * @author hodangquan
 */
public class QuenMatKhauServices {
    private static String makhaumoi;

    public static String getMakhaumoi() {
        return makhaumoi;
    }

    public static void setMakhaumoi(String makhaumoi) {
        QuenMatKhauServices.makhaumoi = makhaumoi;
    }

    public NhanVienRepositories getNvr() {
        return nvr;
    }

    public void setNvr(NhanVienRepositories nvr) {
        this.nvr = nvr;
    }
    
     NhanVienRepositories nvr ;
    public QuenMatKhauServices(){
        nvr = new NhanVienRepositories();
    }
    public boolean checkTonTai(String mail , String ma){
        NhanVien nv = nvr.fill(ma);
        if(nv == null){
            return false;
        }
        if(!nv.getEmail().equals(mail)){
            return false;
        }
        return true;
    }
    public String taoPassmoi(){
        Random random = new Random();
        int number ;
        String result = "";
        for(int i = 0 ; i < 9 ; i++){
        number = random.nextInt(9);
        result += number;
    }
        String matKhau = result;
        return matKhau;
    }
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
    public void capNhatPass(String username){
        String passMoi = taoPassmoi();
         nvr.doiMatKhau(username, HashPassword(passMoi));
         setMakhaumoi(passMoi);
    }
    public void guiMail(String Username , String email){
         try {
             DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
             ZonedDateTime now = ZonedDateTime.now();
             String time = dtf.format(now);
             
             NhanVien nv = nvr.fill(Username);
             String title = "Change Password Successfully at " +time;
             String content = "Xin chào "+nv.getTenNV()+" ! . <br>"
                     + "Bạn vừa yêu cầu cập nhật lại mật khẩu cho tài khoản "+Username+". <br>"
                     + "Mật khẩu mới của bạn là:  " +getMakhaumoi() ;
             EmailSender emailSender = new EmailSender();
             emailSender.guiMail(email, title, content);
         } catch (MessagingException ex) {
             Logger.getLogger(QuenMatKhauServices.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }
}

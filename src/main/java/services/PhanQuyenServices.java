/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author HANGOCHAN
 */
public class PhanQuyenServices {
     private static String HoTen ;
    private static String ma ;
    private static Integer vaiTro ;

    public PhanQuyenServices() {
    }

    public static String getHoTen() {
        return HoTen;
    }

    public static void setHoTen(String HoTen) {
        PhanQuyenServices.HoTen = HoTen;
    }

    public static String getMa() {
        return ma;
    }

    public static void setMa(String ma) {
        PhanQuyenServices.ma = ma;
    }

    public static Integer getVaiTro() {
        return vaiTro;
    }

    public static void setVaiTro(Integer vaiTro) {
        PhanQuyenServices.vaiTro = vaiTro;
    }
    
}

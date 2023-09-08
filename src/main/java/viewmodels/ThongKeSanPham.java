/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodels;

/**
 *
 * @author HANGOCHAN
 */
public class ThongKeSanPham {
    private String maSP ;
    private String tenSP ;
    private Long soLuongBan ;

    public ThongKeSanPham() {
    }

    public ThongKeSanPham(String maSP, String tenSP, Long soLuongBan) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuongBan = soLuongBan;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Long getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(Long soLuongBan) {
        this.soLuongBan = soLuongBan;
    }

    
    
    
}

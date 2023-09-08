/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodels;

/**
 *
 * @author HANGOCHAN
 */
public class ThongKeSoLuongTheoDanhMuc {
    private String maDM ;
    private String tenDM ;
    private Long soLuong ;

    public ThongKeSoLuongTheoDanhMuc() {
    }

    public ThongKeSoLuongTheoDanhMuc(String maDM, String tenDM, Long soLuong) {
        this.maDM = maDM;
        this.tenDM = tenDM;
        this.soLuong = soLuong;
    }

    public String getMaDM() {
        return maDM;
    }

    public void setMaDM(String maDM) {
        this.maDM = maDM;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    public Long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }
    
    
}

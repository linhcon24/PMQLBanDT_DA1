/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodels;

import java.math.BigDecimal;

/**
 *
 * @author HANGOCHAN
 */
public class ThongKeDoanhThuTheoDanhMuc {
    private String maDM ;
    private String tenDM ;
    private BigDecimal tongTien ;
    private BigDecimal giamGia ;

    public ThongKeDoanhThuTheoDanhMuc() {
    }

    public ThongKeDoanhThuTheoDanhMuc(String maDM, String tenDM, BigDecimal tongTien, BigDecimal giamGia) {
        this.maDM = maDM;
        this.tenDM = tenDM;
        this.tongTien = tongTien;
        this.giamGia = giamGia;
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

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public BigDecimal getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(BigDecimal giamGia) {
        this.giamGia = giamGia;
    }

   
    
    
}

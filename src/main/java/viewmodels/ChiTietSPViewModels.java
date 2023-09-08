/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodels;

import domainmodels.SanPham;
import java.math.BigDecimal;

/**
 *
 * @author HANGOCHAN
 */
public class ChiTietSPViewModels {
    private String tenSP ;
    private String nsx ;
    private String mauSac ;
    private Integer boNho ;
    private Integer tonKho ;
    private BigDecimal giaNhap ;
    private BigDecimal giaBan ;
    private String anh ;
    private Integer trangThai ;

    public ChiTietSPViewModels(String tenSP, String nsx, String mauSac, Integer boNho, Integer tonKho, BigDecimal giaNhap, BigDecimal giaBan, String anh, Integer trangThai) {
        this.tenSP = tenSP;
        this.nsx = nsx;
        this.mauSac = mauSac;
        this.boNho = boNho;
        this.tonKho = tonKho;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.anh = anh;
        this.trangThai = trangThai;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getNsx() {
        return nsx;
    }

    public void setNsx(String nsx) {
        this.nsx = nsx;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public Integer getBoNho() {
        return boNho;
    }

    public void setBoNho(Integer boNho) {
        this.boNho = boNho;
    }

    public Integer getTonKho() {
        return tonKho;
    }

    public void setTonKho(Integer tonKho) {
        this.tonKho = tonKho;
    }

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

   
    

    
    
    
    
}

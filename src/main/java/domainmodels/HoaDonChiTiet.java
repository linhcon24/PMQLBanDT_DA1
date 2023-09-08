/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodels;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author HANGOCHAN
 */
@Entity
@Table(name = "HoaDonChiTiet")
public class HoaDonChiTiet implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name = "MaHD", nullable = false)
    private HoaDon maHD ;
    @Id
    @ManyToOne
    @JoinColumn(name = "MaImei", nullable = false)
    private ChiTietSP maImei ;
    @Column(name = "SoLuong")
    private Integer soLuong ;
    @Column (name = "DonGia")
    private BigDecimal donGia ;
     @Column(name = "GiamGia")
    private BigDecimal giamGia ;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(HoaDon maHD, ChiTietSP maImei, Integer soLuong, BigDecimal donGia, BigDecimal giamGia) {
        this.maHD = maHD;
        this.maImei = maImei;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.giamGia = giamGia;
    }

     
    public HoaDon getMaHD() {
        return maHD;
    }

    public void setMaHD(HoaDon maHD) {
        this.maHD = maHD;
    }

    public ChiTietSP getMaImei() {
        return maImei;
    }

    public void setMaImei(ChiTietSP maImei) {
        this.maImei = maImei;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public BigDecimal getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(BigDecimal giamGia) {
        this.giamGia = giamGia;
    }
    
    
    
    
    
    
}

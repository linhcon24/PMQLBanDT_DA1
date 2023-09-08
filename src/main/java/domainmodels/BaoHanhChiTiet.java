/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodels;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "BaoHanhChiTiet")
public class BaoHanhChiTiet implements Serializable{
    @Id
    @ManyToOne 
    @JoinColumn(name = "MaImei", nullable = false)
    private ChiTietSP maImei ;
    @Id
    @ManyToOne
    @JoinColumn(name = "MaBH", nullable = false)
    private BaoHanh maBH ;
    @Column(name = "NgayBatDau")
    private Date ngayBatDau ;
    @Column(name = "NgayKetThuc")
    private Date ngayKetThuc ;
    @Column(name = "GhiChu")
    private String ghiChu ;

    public ChiTietSP getMaImei() {
        return maImei;
    }

    public void setMaImei(ChiTietSP maImei) {
        this.maImei = maImei;
    }

    public BaoHanh getMaBH() {
        return maBH;
    }

    public void setMaBH(BaoHanh maBH) {
        this.maBH = maBH;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    
    
    
    
}

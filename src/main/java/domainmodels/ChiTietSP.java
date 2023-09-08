/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodels;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author HANGOCHAN
 */
@Entity
@Table(name = "ChiTietSP")
public class ChiTietSP implements Serializable{
    @Id
    @Column (name = "MaImei", nullable = false)
    private String maImei ;
    @Column(name = "GiaNhap")
    private BigDecimal giaNhap ;
    @Column(name = "GiaBan")
    private BigDecimal giaBan ;
    @Column(name = "MoTa")
    private String moTa ;
    @Column(name = "ThoiGianBaoHanh")
    private Integer thoiGianBH ;
    @Column(name = "Anh")
    private String anh ;
    @Column(name = "NgayTao")
    private Date ngayTao ;
    @Column(name = "NgaySua")
    private Date ngaySua ;
    @Column(name = "TrangThai")
    private Integer trangThai ;
    
    @ManyToOne
    @JoinColumn(name = "MaSP", nullable = false)
    private SanPham sanPham;
    
    
    @OneToMany(mappedBy = "maImei", fetch = FetchType.LAZY)
    private List<HoaDonChiTiet> hoaDonChiTiets;
    @OneToMany(mappedBy = "maImei", fetch = FetchType.LAZY)
    private List<BaoHanhChiTiet> baoHanhChiTiets;

    public String getMaImei() {
        return maImei;
    }

    public void setMaImei(String maImei) {
        this.maImei = maImei;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getThoiGianBH() {
        return thoiGianBH;
    }

    public void setThoiGianBH(Integer thoiGianBH) {
        this.thoiGianBH = thoiGianBH;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgaySua() {
        return ngaySua;
    }

    public void setNgaySua(Date ngaySua) {
        this.ngaySua = ngaySua;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public List<HoaDonChiTiet> getHoaDonChiTiets() {
        return hoaDonChiTiets;
    }

    public void setHoaDonChiTiets(List<HoaDonChiTiet> hoaDonChiTiets) {
        this.hoaDonChiTiets = hoaDonChiTiets;
    }

    public List<BaoHanhChiTiet> getBaoHanhChiTiets() {
        return baoHanhChiTiets;
    }

    public void setBaoHanhChiTiets(List<BaoHanhChiTiet> baoHanhChiTiets) {
        this.baoHanhChiTiets = baoHanhChiTiets;
    }

    
    
    
    
}

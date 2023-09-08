/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainmodels;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author HANGOCHAN
 */
@Entity
@Table(name = "KhuyenMai")
public class KhuyenMai implements Serializable{
    @Id
    @Column(name = "MaKM", nullable = false)
    private String maKM ;
    @Column(name = "TenKM")
    private String tenKM ;
    @Column(name = "ChietKhau")
    private Integer chietKhau ;
    @Column(name = "NgayBatDau")
    private Date ngayBatDau ;
    @Column(name = "NgayKetThuc")
    private Date ngayKetThuc ;
    @Column(name = "NgayTao")
    private Date ngayTao ;
    @Column(name = "NgaySua")
    private Date ngaySua ;
    @Column(name = "TrangThai")
    private Integer trangThai ;
    
    @OneToMany(mappedBy = "khuyenMai", fetch = FetchType.LAZY)
    private List<HoaDon> hoaDons;

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public Integer getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(Integer chietKhau) {
        this.chietKhau = chietKhau;
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

    public List<HoaDon> getHoaDons() {
        return hoaDons;
    }

    public void setHoaDons(List<HoaDon> hoaDons) {
        this.hoaDons = hoaDons;
    }
    
    
    
    
}

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
@Table(name = "KhachHang")
public class KhachHang implements Serializable{
    @Id
    @Column(name = "MaKH")
    private String maKH ;
    @Column(name = "TenKH")
    private String tenKH ;
    @Column(name = "NgaySinh")
    private Date ngaySinh ;
    @Column(name = "SoDienThoai")
    private String sdt ;
    @Column(name = "DiaChi")
    private String diaChi ;
    @Column(name = "NgayTao")
    private Date ngayTao ;
    @Column(name = "NgaySua")
    private Date ngaySua ;
    @Column(name = "TrangThai")
    private Integer trangThai ;
    
    @OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
    private List<HoaDon> hoaDons;
    @OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
    private List<BaoHanh> baoHanhs;

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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

    public List<BaoHanh> getBaoHanhs() {
        return baoHanhs;
    }

    public void setBaoHanhs(List<BaoHanh> baoHanhs) {
        this.baoHanhs = baoHanhs;
    }
    
    
    
}

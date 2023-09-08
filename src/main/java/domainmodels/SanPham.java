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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author HANGOCHAN
 */
@Entity
@Table (name = "SanPham")
public class SanPham implements Serializable{
    @Id
    @Column (name = "MaSP" , nullable = false)
    private String maSP ;
    @Column (name = "TenSP")
    private String tenSP ;
    @Column(name = "Camera")
    private String camera ;
    @Column(name = "HeDieuHanh")
    private String heDieuHanh ;
    @Column(name = "Ram")
    private Integer ram ;
    @Column(name = "Cpu")
    private String cpu ;
    @Column(name = "ManHinh")
    private String manHinh ;
    @Column(name = "DungLuongPin")
    private Integer pin ;
    @Column(name = "XuatXu")
    private String xuatxu ;
    @Column (name = "NgayTao")
    private Date ngayTao ;
    @Column (name = "NgaySua")
    private Date ngaySua ;
    @Column (name = "TrangThai")
    private Integer trangThai ;
    @ManyToOne
    @JoinColumn (name = "MaNSX" , nullable = false)
    private NSX nsx ;
    @ManyToOne
    @JoinColumn (name = "MaDM" , nullable = false)
    private DanhMuc danhmuc ;
     @ManyToOne
    @JoinColumn(name = "MaMauSac" , nullable = false)
    private MauSac mausac ;
    @ManyToOne
    @JoinColumn(name = "MaBNT", nullable = false)
    private BoNhoTrong bonhotrong ;
    @OneToMany(mappedBy = "sanPham" , fetch = FetchType.LAZY)
    List<ChiTietSP> chiTietSPs;

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

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getHeDieuHanh() {
        return heDieuHanh;
    }

    public void setHeDieuHanh(String heDieuHanh) {
        this.heDieuHanh = heDieuHanh;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getManHinh() {
        return manHinh;
    }

    public void setManHinh(String manHinh) {
        this.manHinh = manHinh;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getXuatxu() {
        return xuatxu;
    }

    public void setXuatxu(String xuatxu) {
        this.xuatxu = xuatxu;
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

    public NSX getNsx() {
        return nsx;
    }

    public void setNsx(NSX nsx) {
        this.nsx = nsx;
    }

    public DanhMuc getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(DanhMuc danhmuc) {
        this.danhmuc = danhmuc;
    }

    public MauSac getMausac() {
        return mausac;
    }

    public void setMausac(MauSac mausac) {
        this.mausac = mausac;
    }

    public BoNhoTrong getBonhotrong() {
        return bonhotrong;
    }

    public void setBonhotrong(BoNhoTrong bonhotrong) {
        this.bonhotrong = bonhotrong;
    }

    public List<ChiTietSP> getChiTietSPs() {
        return chiTietSPs;
    }

    public void setChiTietSPs(List<ChiTietSP> chiTietSPs) {
        this.chiTietSPs = chiTietSPs;
    }
    

    

   

   
    
    
    
    
    
}

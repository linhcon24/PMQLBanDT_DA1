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
@Table(name = "BaoHanh")
public class BaoHanh implements Serializable{
    @Id
    @Column(name = "MaBH", nullable = false)
    private String maBH ;
    @Column(name = "NgayTao")
    private Date ngayTao ;
    @Column(name = "NgaySua")
    private Date ngaySua ;
    @Column(name = "TrangThai")
    private Integer trangThai ;

    
    @ManyToOne
    @JoinColumn(name = "MaKH" , nullable = false)
    private KhachHang khachHang;
    @OneToMany(mappedBy = "maBH", fetch = FetchType.LAZY)
    private List<BaoHanhChiTiet> baoHanhChiTiets;

    public String getMaBH() {
        return maBH;
    }

    public void setMaBH(String maBH) {
        this.maBH = maBH;
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

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public List<BaoHanhChiTiet> getBaoHanhChiTiets() {
        return baoHanhChiTiets;
    }

    public void setBaoHanhChiTiets(List<BaoHanhChiTiet> baoHanhChiTiets) {
        this.baoHanhChiTiets = baoHanhChiTiets;
    }

   
    
    
}

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author HANGOCHAN
 */
@Entity
@Table (name = "BoNhoTrong")
public class BoNhoTrong implements Serializable{
    @Id
    @Column( name = "MaBNT", nullable = false)
    private String maBNT ;
    @Column (name = "DungLuong")
    private Integer dungLuong ;
    @Column (name = "NgayTao")
    private Date ngayTao ;
    @Column (name = "NgaySua")
    private Date ngaySua ;
    @Column (name = "TrangThai")
    private Integer trangThai ;
    
    @OneToMany(mappedBy = "bonhotrong" , fetch = FetchType.LAZY)
    List<SanPham> sanPhams;

    public String getMaBNT() {
        return maBNT;
    }

    public void setMaBNT(String maBNT) {
        this.maBNT = maBNT;
    }

    public Integer getDungLuong() {
        return dungLuong;
    }

    public void setDungLuong(Integer dungLuong) {
        this.dungLuong = dungLuong;
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

    public List<SanPham> getSanPhams() {
        return sanPhams;
    }

    public void setSanPhams(List<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }

    
    
    
    
}

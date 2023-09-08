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
@Table (name = "NSX")
public class NSX implements Serializable{
    @Id
    @Column(name = "MaNSX" , nullable = false)
    private String maNSX ;
    @Column (name = "TenNSX")
    private String tenNSX ;
    @Column (name = "NgayTao")
    private Date ngayTao ;
    @Column (name = "NgaySua")
    private Date ngaySua ;
    @Column (name = "TrangThai")
    private Integer trangThai ;
    @OneToMany (mappedBy = "nsx" , fetch = FetchType.LAZY)
    private List<SanPham> sanphams ;

    public String getMaNSX() {
        return maNSX;
    }

    public void setMaNSX(String maNSX) {
        this.maNSX = maNSX;
    }

    public String getTenNSX() {
        return tenNSX;
    }

    public void setTenNSX(String tenNSX) {
        this.tenNSX = tenNSX;
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

    public List<SanPham> getSanphams() {
        return sanphams;
    }

    public void setSanphams(List<SanPham> sanphams) {
        this.sanphams = sanphams;
    }
    
    
    
    
}

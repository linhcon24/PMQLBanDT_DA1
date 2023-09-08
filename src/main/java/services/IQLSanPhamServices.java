/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.SanPham;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IQLSanPhamServices {
    public List<SanPham> getALL();
    public boolean add(SanPham sanPham);
    public boolean update(SanPham sanPham);
    public boolean delete(SanPham sanPham);
    public SanPham seachbyMa(String ma);
    public SanPham layMa();
    public List<SanPham> getAllbyTrangThai(Integer trangThai);
    public List<SanPham> phanTrang(Integer limitPage, Integer page);
    public List<SanPham> loc(String danhMuc , String mauSac , Integer boNho,Integer litmitNumber , Integer number);
    public List<SanPham> locSanPham(String danhMuc , String mauSac , Integer boNho);
    public List<SanPham> timKiembyTrangThai(String ten);
    public List<SanPham> timKiemPhanTrang(String ten , Integer limitPage, Integer page);
    
    
    
}

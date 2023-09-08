/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.SanPham;
import java.util.Date;
import java.util.List;
import repositories.QLSanPhamRepositories;


/**
 *
 * @author Admin
 */
public class QLSanPhamServices implements IQLSanPhamServices{
    QLSanPhamRepositories spr;
    
    public QLSanPhamServices(){
        spr = new QLSanPhamRepositories();
    }

    @Override
    public List<SanPham> getALL() {
        return spr.getAll();
    }

    @Override
    public boolean add(SanPham sanPham) {
        return spr.add(sanPham);
    }

    @Override
    public boolean update(SanPham sanPham) {
        return spr.update(sanPham);
    }

    @Override
    public boolean delete(SanPham sanPham) {
        return spr.delete(sanPham);
    }

    @Override
    public SanPham seachbyMa(String ma) {
        return spr.seachbyMa(ma);
    }

    @Override
    public SanPham layMa() {
        return spr.layMa();
    }

    @Override
    public List<SanPham> getAllbyTrangThai(Integer trangThai) {
        return spr.getAllbyTrangThai(trangThai);
    }

    @Override
    public List<SanPham> phanTrang(Integer limitPage, Integer page) {
        return spr.phanTrang(limitPage, page);
    }

    @Override
    public List<SanPham> loc(String danhMuc, String mauSac, Integer boNho,Integer litmitNumber , Integer number) {
        return spr.loc(danhMuc, mauSac, boNho, litmitNumber ,  number);
    }

    @Override
    public List<SanPham> locSanPham(String danhMuc, String mauSac, Integer boNho) {
        return spr.locSanPham(danhMuc, mauSac, boNho);
    }

    @Override
    public List<SanPham> timKiembyTrangThai(String ten) {
        return spr.timKiembyTrangThai(ten);
    }

    @Override
    public List<SanPham> timKiemPhanTrang(String ten, Integer limitPage, Integer page) {
        return spr.timKiemPhanTrang(ten, limitPage, page);
    }



   
}

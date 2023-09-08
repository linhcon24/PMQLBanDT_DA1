/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.NhanVien;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import repositories.NhanVienRepositories;

/**
 *
 * @author HANGOCHAN
 */
public class NhanVienServices implements INhanVienServices{
    NhanVienRepositories nvr ;
    
    public NhanVienServices(){
        nvr = new NhanVienRepositories();
    }

    @Override
    public List<NhanVien> getALL() {
        return  nvr.getALL();
    }

    @Override
    public boolean add(NhanVien nhanVien) {
        return nvr.add(nhanVien);
    }

    @Override
    public boolean update(NhanVien nhanVien) {
        return nvr.update(nhanVien);
    }

    @Override
    public boolean delete(String ma) {
         return nvr.delete(ma);
    }

    @Override
    public NhanVien seachbyMa(String ma) {
        return nvr.seachbyMa(ma);
    }

    @Override
    public NhanVien fill(String maNV) {
        return nvr.fill(maNV);
    }

    @Override
    public List<NhanVien> getALLbyVaiTro(Integer vaiTro) {
        return nvr.getALLbyVaiTro(vaiTro);
    }

    @Override
    public List<NhanVien> phanTrang(Integer limitPage, Integer page) {
       return nvr.phanTrang(limitPage, page);
    }

    @Override
    public List<NhanVien> getALLbyTrangThai(Integer trangThai) {
        return nvr.getALLbyTrangThai(trangThai);
    }

    @Override
    public List<NhanVien> timKiembyTrangThai(String ten) {
        return nvr.timKiembyTrangThai(ten);
    }

    @Override
    public List<NhanVien> timKiemPhanTrang(String ten, Integer limitPage, Integer page) {
        return nvr.timKiemPhanTrang(ten, limitPage, page);
    }

    @Override
    public List<NhanVien> locPhanTrang(Integer vaitro, Integer limitPage, Integer page) {
        return nvr.locPhanTrang(vaitro, limitPage, page);
    }
    
}

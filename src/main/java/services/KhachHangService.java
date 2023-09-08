/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.KhachHang;
import java.util.Date;
import java.util.List;
import repositories.KhachHangRepositores;
import viewmodels.ThongKeKhachHang;

/**
 *
 * @author Tungt
 */
public class KhachHangService implements IKhachHangService{
    KhachHangRepositores khr;
    
    public KhachHangService(){
        khr = new KhachHangRepositores();
    }

    @Override
    public List<KhachHang> getALL() {
        return khr.getALL();
    }

    @Override
    public boolean add(KhachHang khachHang) {
        return khr.add(khachHang);
    }

    @Override
    public boolean update(KhachHang khachHang) {
        return khr.update(khachHang);
    }

    @Override
    public boolean delete(String ma) {
        return khr.delete(ma);
    }

    @Override
    public KhachHang seachbyMa(String ma) {
        return khr.seachbyMa(ma);
    }

    @Override
    public KhachHang fill(String maKH) {
        return khr.fill(maKH);
    }

    @Override
    public KhachHang layMa() {
        return khr.layMa();
    }


    @Override
    public List<ThongKeKhachHang> thongKeTheoNgay(Date date) {
        return khr.thongKeTheoNgay(date);
    }

    @Override
    public List<ThongKeKhachHang> thongKeTheoThang(Integer thang, Integer nam) {
        return khr.thongKeTheoThang(thang, nam);
    }

    @Override
    public List<ThongKeKhachHang> thongKeTheoNam(Integer nam) {
        return khr.thongKeTheoNam(nam);
    }

    @Override
    public List<KhachHang> phanTrang(Integer limitPage, Integer page) {
        return khr.phanTrang(limitPage, page);
    }

    @Override
    public List<KhachHang> getALLbyTrangThai(Integer trangThai) {
        return khr.getALLbyTrangThai(trangThai);
    }

    @Override
    public List<KhachHang> timKiembyTrangThai(String ten) {
        return khr.timKiembyTrangThai(ten);
    }

    @Override
    public List<KhachHang> timKiemPhanTrang(String ten, Integer limitPage, Integer page) {
        return khr.timKiemPhanTrang(ten, limitPage, page);
    }
    
     

    
    
}

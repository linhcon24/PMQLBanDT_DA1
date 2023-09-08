/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.HoaDonChiTiet;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import repositories.HoaDonChiTietRepositories;
import viewmodels.ThongKeDoanhThu;
import viewmodels.ThongKeDoanhThuTheoDanhMuc;
import viewmodels.ThongKeSanPham;
import viewmodels.ThongKeSoLuongTheoDanhMuc;

/**
 *
 * @author HANGOCHAN
 */
public class HoaDonChiTietServices implements IHoaDonChiTietServies{
    HoaDonChiTietRepositories hdctr ;
    
    public HoaDonChiTietServices(){
        hdctr = new HoaDonChiTietRepositories();
    }

    @Override
    public List<HoaDonChiTiet> getALL(String ma) {
        return hdctr.getALL(ma);
    }

    @Override
    public boolean add(HoaDonChiTiet m) {
        return hdctr.add(m);
    }

    @Override
    public boolean update(HoaDonChiTiet hoaDon) {
        return hdctr.update(hoaDon);
    }

    @Override
    public void delete(String imei, String mahd) {
        hdctr.delete(imei, mahd);
    }

    @Override
    public HoaDonChiTiet seachbyMaImei(String ma) {
        return hdctr.seachbyMaImei(ma);
    }

    @Override
    public void updateImei(String ma, String maImei, String maImeiMoi) {
        hdctr.updateImei(ma, maImei, maImeiMoi);
    }

    @Override
    public List<ThongKeSanPham> thongKeTheoNgay(Date date) {
        return hdctr.thongKeTheoNgay(date);
    }

    @Override
    public List<ThongKeSanPham> thongKeSoLuongDaBanTheoNgay(Date date) {
        return hdctr.thongKeSoLuongDaBanTheoNgay(date);
    }

    @Override
    public List<ThongKeDoanhThu> thongKeDoanhThuTheoNgay(Date date) {
        return hdctr.thongKeDoanhThuTheoNgay(date);
    }

    @Override
    public void updateGiamGia(String ma, String maImei, BigDecimal giamGia) {
         hdctr.updateGiamGia(ma, maImei, giamGia);
    }

    @Override
    public List<ThongKeSanPham> thongKeTheoThang(Integer thang, Integer nam) {
        return hdctr.thongKeTheoThang(thang, nam);
    }

    @Override
    public List<ThongKeSanPham> thongKeSoLuongDaBanTheoThang(Integer thang, Integer nam) {
        return hdctr.thongKeSoLuongDaBanTheoThang(thang, nam);
    }

    @Override
    public List<ThongKeDoanhThu> thongKeDoanhThuTheoThang(Integer thang, Integer nam) {
        return hdctr.thongKeDoanhThuTheoThang(thang, nam);
    }

    @Override
    public List<ThongKeSanPham> thongKeTheoNam(Integer nam) {
        return hdctr.thongKeTheoNam(nam);
    }

    @Override
    public List<ThongKeSanPham> thongKeSoLuongDaBanTheoNam(Integer nam) {
        return hdctr.thongKeSoLuongDaBanTheoNam(nam);
    }

    @Override
    public List<ThongKeDoanhThu> thongKeDoanhThuTheoNam(Integer nam) {
        return hdctr.thongKeDoanhThuTheoNam(nam);
    }

    @Override
    public List<ThongKeDoanhThuTheoDanhMuc> thongKeDoanhThuDanhMucTheoNgay(Date date) {
        return hdctr.thongKeDoanhThuDanhMucTheoNgay(date);
    }

    @Override
    public List<ThongKeDoanhThuTheoDanhMuc> thongKeDoanhThuDanhMucTheoThang(Integer thang, Integer nam) {
        return hdctr.thongKeDoanhThuDanhMucTheoThang(thang, nam);
    }

    @Override
    public List<ThongKeDoanhThuTheoDanhMuc> thongKeDoanhThuDanhMucTheoNam(Integer nam) {
        return hdctr.thongKeDoanhThuDanhMucTheoNam(nam);
    }

    @Override
    public List<ThongKeSoLuongTheoDanhMuc> thongKeSoLuongDanhMucTheoNgay(Date date) {
        return hdctr.thongKeSoLuongDanhMucTheoNgay(date);
    }

    @Override
    public List<ThongKeSoLuongTheoDanhMuc> thongKeSoLuongDanhMucTheoThang(Integer thang, Integer nam) {
        return hdctr.thongKeSoLuongDanhMucTheoThang(thang,nam);
    }

    @Override
    public List<ThongKeSoLuongTheoDanhMuc> thongKeSoLuongDanhMucTheoNam(Integer nam) {
        return hdctr.thongKeSoLuongDanhMucTheoNam(nam);
    }

   
}

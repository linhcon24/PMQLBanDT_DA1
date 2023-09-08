/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import domainmodels.HoaDonChiTiet;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import viewmodels.ThongKeDoanhThu;
import viewmodels.ThongKeDoanhThuTheoDanhMuc;
import viewmodels.ThongKeSanPham;
import viewmodels.ThongKeSoLuongTheoDanhMuc;

/**
 *
 * @author HANGOCHAN
 */
public interface IHoaDonChiTietServies {
    public List<HoaDonChiTiet> getALL(String ma);
    public boolean add(HoaDonChiTiet m);
    public boolean update(HoaDonChiTiet hoaDon);
    public void delete(String imei ,String mahd);
    public HoaDonChiTiet seachbyMaImei(String ma);
    public void updateImei(String ma , String maImei , String maImeiMoi);
    public List<ThongKeSanPham> thongKeTheoNgay(Date date);
    public List<ThongKeSanPham> thongKeSoLuongDaBanTheoNgay(Date date);
    public List<ThongKeDoanhThu> thongKeDoanhThuTheoNgay(Date date);
    public void updateGiamGia(String ma , String maImei , BigDecimal giamGia);
    public List<ThongKeSanPham> thongKeTheoThang(Integer thang , Integer nam);
    public List<ThongKeSanPham> thongKeSoLuongDaBanTheoThang(Integer thang , Integer nam);
    public List<ThongKeDoanhThu> thongKeDoanhThuTheoThang(Integer thang , Integer nam);
    public List<ThongKeSanPham> thongKeTheoNam(Integer nam);
    public List<ThongKeSanPham> thongKeSoLuongDaBanTheoNam(Integer nam);
    public List<ThongKeDoanhThu> thongKeDoanhThuTheoNam(Integer nam);
    public List<ThongKeDoanhThuTheoDanhMuc> thongKeDoanhThuDanhMucTheoNgay(Date date);
    public List<ThongKeDoanhThuTheoDanhMuc> thongKeDoanhThuDanhMucTheoThang(Integer thang , Integer nam);
    public List<ThongKeDoanhThuTheoDanhMuc> thongKeDoanhThuDanhMucTheoNam(Integer nam);
    public List<ThongKeSoLuongTheoDanhMuc> thongKeSoLuongDanhMucTheoNgay(Date date);
    public List<ThongKeSoLuongTheoDanhMuc> thongKeSoLuongDanhMucTheoThang(Integer thang , Integer nam);
    public List<ThongKeSoLuongTheoDanhMuc> thongKeSoLuongDanhMucTheoNam(Integer nam);
}

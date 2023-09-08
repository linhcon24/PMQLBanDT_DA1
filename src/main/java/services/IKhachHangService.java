/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import domainmodels.KhachHang;
import java.util.Date;
import java.util.List;
import viewmodels.ThongKeKhachHang;

/**
 *
 * @author Tungt
 */
public interface IKhachHangService {
    public List<KhachHang> getALL();
    public boolean add(KhachHang khachHang);
    public boolean update(KhachHang khachHang);
    public boolean delete(String ma);
    public KhachHang seachbyMa(String ma);
    public KhachHang fill(String maKH);
    public KhachHang layMa();
    public List<ThongKeKhachHang> thongKeTheoNgay(Date date);
    public List<ThongKeKhachHang> thongKeTheoThang(Integer thang , Integer nam);
    public List<ThongKeKhachHang> thongKeTheoNam(Integer nam);
    public List<KhachHang> phanTrang(Integer limitPage ,Integer page);
    public List<KhachHang> getALLbyTrangThai(Integer trangThai);
    public List<KhachHang> timKiembyTrangThai(String ten);
    public List<KhachHang> timKiemPhanTrang(String ten , Integer limitPage, Integer page);
}

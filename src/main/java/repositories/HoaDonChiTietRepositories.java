/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainmodels.HoaDonChiTiet;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilities.HibernateConfig;
import viewmodels.ThongKeDoanhThu;
import viewmodels.ThongKeDoanhThuTheoDanhMuc;
import viewmodels.ThongKeSanPham;
import viewmodels.ThongKeSoLuongTheoDanhMuc;

/**
 *
 * @author HANGOCHAN
 */
public class HoaDonChiTietRepositories {
    public List<HoaDonChiTiet> getALL(String maHD){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDonChiTiet where MaHD = :ma");
        q.setParameter("ma", maHD);
        List<HoaDonChiTiet> list = q.getResultList();
        return list;
    }
    public boolean add(HoaDonChiTiet m){
        
        Transaction transaction = null ;
        try (Session s = HibernateConfig.getFACTORY().openSession()){
            transaction = s.beginTransaction();
            s.save(m);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }
    public boolean update(HoaDonChiTiet hoaDon){
        Transaction transaction = null;
        try(Session s = HibernateConfig.getFACTORY().openSession()){
            transaction= s.beginTransaction();
            s.update(hoaDon);
            transaction.commit();
            return true;
        }catch(Exception e){
            transaction.rollback();
            return false;
        }
    }
    public void delete(String imei ,String mahd){
        Session session = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery("DELETE FROM HoaDonChiTiet where MaHD = :ma and MaImei = :imei");
        q.setParameter("ma", mahd);
        q.setParameter("imei", imei);
        int index = q.executeUpdate();
        transaction.commit();
        session.close();
        
    }
    public HoaDonChiTiet seachbyMa(String ma){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDonChiTiet where MaHD = :ma");
        q.setParameter("ma", ma);
        List<HoaDonChiTiet> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
    public HoaDonChiTiet seachbyMaImei(String ma){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDonChiTiet where MaImei = :ma");
        q.setParameter("ma", ma);
        List<HoaDonChiTiet> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
    public void updateImei(String ma , String maImei , String maImeiMoi){
        Session session = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery("UPDATE HoaDonChiTiet SET MaImei = :maImeiMoi where MaImei = :maImei and MaHD = : maHD");
        q.setParameter("maImeiMoi", maImeiMoi);
        q.setParameter("maImei", maImei);
        q.setParameter("maHD", ma);
        int index = q.executeUpdate();
        transaction.commit();
        session.close();
    }
    public void updateGiamGia(String ma , String maImei , BigDecimal giamGia){
        Session session = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery("UPDATE HoaDonChiTiet SET giamGia = :giamGia where MaImei = :maImei and MaHD = : maHD");
        q.setParameter("giamGia", giamGia);
        q.setParameter("maImei", maImei);
        q.setParameter("maHD", ma);
        int index = q.executeUpdate();
        transaction.commit();
        session.close();
    }
    public List<ThongKeSanPham> thongKeTheoNgay(Date date){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeSanPham(e.maImei.sanPham.maSP as maSP,e.maImei.sanPham.tenSP as tenSP,COUNT(e.maImei) as soLuongBan) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and ngayMua = :ngay "    
                + "Group by e.maImei.sanPham.maSP , e.maImei.sanPham.tenSP "
                + "Order by COUNT(e.maImei) DESC");
        query.setParameter("ngay", date);
        query.setMaxResults(5);
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeSanPham> list = query.getResultList();
        return list;
    }
    
    public List<ThongKeSanPham> thongKeSoLuongDaBanTheoNgay(Date date){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeSanPham(e.maImei.sanPham.maSP as maSP,e.maImei.sanPham.tenSP as tenSP,COUNT(e.maImei) as soLuongBan) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and ngayMua = :ngay "    
                + "Group by e.maImei.sanPham.maSP , e.maImei.sanPham.tenSP ");
        query.setParameter("ngay", date);
       
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeSanPham> list = query.getResultList();
        return list;
    }
    public List<ThongKeDoanhThu> thongKeDoanhThuTheoNgay(Date date){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeDoanhThu(e.maImei.sanPham.maSP ,e.maImei.sanPham.tenSP ,SUM(e.donGia),SUM(e.giamGia)) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and ngayMua = :ngay "    
                + "Group by e.maImei.sanPham.maSP , e.maImei.sanPham.tenSP");
        query.setParameter("ngay", date);
       
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeDoanhThu> list = query.getResultList();
        return list;
    }
    
    public List<ThongKeSanPham> thongKeTheoThang(Integer thang , Integer nam){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeSanPham(e.maImei.sanPham.maSP as maSP,e.maImei.sanPham.tenSP as tenSP,COUNT(e.maImei) as soLuongBan) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and MONTH(ngayMua) = :ngay and YEAR(ngayMua) = :nam "    
                + "Group by e.maImei.sanPham.maSP , e.maImei.sanPham.tenSP "
                + "Order by COUNT(e.maImei) DESC");
        query.setParameter("ngay", thang);
        query.setParameter("nam", nam);
        query.setMaxResults(5);
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeSanPham> list = query.getResultList();
        return list;
    }
    
    public List<ThongKeSanPham> thongKeSoLuongDaBanTheoThang(Integer thang , Integer nam){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeSanPham(e.maImei.sanPham.maSP as maSP,e.maImei.sanPham.tenSP as tenSP,COUNT(e.maImei) as soLuongBan) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and MONTH(ngayMua) = :ngay and YEAR(ngayMua) = :nam "    
                + "Group by e.maImei.sanPham.maSP , e.maImei.sanPham.tenSP ");
        query.setParameter("ngay", thang);
        query.setParameter("nam", nam);
       
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeSanPham> list = query.getResultList();
        return list;
    }
    public List<ThongKeDoanhThu> thongKeDoanhThuTheoThang(Integer thang , Integer nam){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeDoanhThu(e.maImei.sanPham.maSP ,e.maImei.sanPham.tenSP ,SUM(e.donGia),SUM(e.giamGia)) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and MONTH(ngayMua) = :ngay and YEAR(ngayMua) = :nam "    
                + "Group by e.maImei.sanPham.maSP , e.maImei.sanPham.tenSP");
        query.setParameter("ngay", thang);
        query.setParameter("nam", nam);
       
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeDoanhThu> list = query.getResultList();
        return list;
    }
    
    public List<ThongKeSanPham> thongKeTheoNam(Integer nam){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeSanPham(e.maImei.sanPham.maSP as maSP,e.maImei.sanPham.tenSP as tenSP,COUNT(e.maImei) as soLuongBan) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and YEAR(ngayMua) = :nam "    
                + "Group by e.maImei.sanPham.maSP , e.maImei.sanPham.tenSP "
                + "Order by COUNT(e.maImei) DESC");
        query.setParameter("nam", nam);
        query.setMaxResults(5);
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeSanPham> list = query.getResultList();
        return list;
    }
    
    public List<ThongKeSanPham> thongKeSoLuongDaBanTheoNam(Integer nam){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeSanPham(e.maImei.sanPham.maSP as maSP,e.maImei.sanPham.tenSP as tenSP,COUNT(e.maImei) as soLuongBan) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and YEAR(ngayMua) = :nam "    
                + "Group by e.maImei.sanPham.maSP , e.maImei.sanPham.tenSP ");
        query.setParameter("nam", nam);
       
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeSanPham> list = query.getResultList();
        return list;
    }
    public List<ThongKeDoanhThu> thongKeDoanhThuTheoNam(Integer nam){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeDoanhThu(e.maImei.sanPham.maSP ,e.maImei.sanPham.tenSP ,SUM(e.donGia),SUM(e.giamGia)) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and YEAR(ngayMua) = :nam "    
                + "Group by e.maImei.sanPham.maSP , e.maImei.sanPham.tenSP");
        query.setParameter("nam", nam);
       
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeDoanhThu> list = query.getResultList();
        return list;
    }
    public List<ThongKeDoanhThuTheoDanhMuc> thongKeDoanhThuDanhMucTheoNgay(Date date){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeDoanhThuTheoDanhMuc(e.maImei.sanPham.danhmuc.maDanhMuc,e.maImei.sanPham.danhmuc.tenDanhMuc,SUM(e.donGia),SUM(e.giamGia)) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and ngayMua = :ngay "    
                + "Group by e.maImei.sanPham.danhmuc.maDanhMuc , e.maImei.sanPham.danhmuc.tenDanhMuc");
        query.setParameter("ngay", date);
       
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeDoanhThuTheoDanhMuc> list = query.getResultList();
        return list;
    }
    public List<ThongKeDoanhThuTheoDanhMuc> thongKeDoanhThuDanhMucTheoThang(Integer thang , Integer nam){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeDoanhThuTheoDanhMuc(e.maImei.sanPham.danhmuc.maDanhMuc,e.maImei.sanPham.danhmuc.tenDanhMuc,SUM(e.donGia),SUM(e.giamGia)) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and MONTH(ngayMua) = :ngay and YEAR(ngayMua) = :nam "    
                + "Group by e.maImei.sanPham.danhmuc.maDanhMuc , e.maImei.sanPham.danhmuc.tenDanhMuc");
         query.setParameter("ngay", thang);
        query.setParameter("nam", nam);
       
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeDoanhThuTheoDanhMuc> list = query.getResultList();
        return list;
    }
    public List<ThongKeDoanhThuTheoDanhMuc> thongKeDoanhThuDanhMucTheoNam(Integer nam){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeDoanhThuTheoDanhMuc(e.maImei.sanPham.danhmuc.maDanhMuc,e.maImei.sanPham.danhmuc.tenDanhMuc,SUM(e.donGia),SUM(e.giamGia)) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and YEAR(ngayMua) = :nam "    
                + "Group by e.maImei.sanPham.danhmuc.maDanhMuc , e.maImei.sanPham.danhmuc.tenDanhMuc");
        query.setParameter("nam", nam);
       
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeDoanhThuTheoDanhMuc> list = query.getResultList();
        return list;
    }
    public List<ThongKeSoLuongTheoDanhMuc> thongKeSoLuongDanhMucTheoNgay(Date date){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeSoLuongTheoDanhMuc(e.maImei.sanPham.danhmuc.maDanhMuc,e.maImei.sanPham.danhmuc.tenDanhMuc,COUNT(e.maImei)) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and ngayMua = :ngay "    
                + "Group by e.maImei.sanPham.danhmuc.maDanhMuc , e.maImei.sanPham.danhmuc.tenDanhMuc");
        query.setParameter("ngay", date);
       
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeSoLuongTheoDanhMuc> list = query.getResultList();
        return list;
    }
    public List<ThongKeSoLuongTheoDanhMuc> thongKeSoLuongDanhMucTheoThang(Integer thang , Integer nam){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeSoLuongTheoDanhMuc(e.maImei.sanPham.danhmuc.maDanhMuc,e.maImei.sanPham.danhmuc.tenDanhMuc,COUNT(e.maImei)) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and MONTH(ngayMua) = :ngay and YEAR(ngayMua) = :nam "    
                + "Group by e.maImei.sanPham.danhmuc.maDanhMuc , e.maImei.sanPham.danhmuc.tenDanhMuc");
        query.setParameter("ngay", thang);
        query.setParameter("nam", nam);
       
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeSoLuongTheoDanhMuc> list = query.getResultList();
        return list;
    }
    public List<ThongKeSoLuongTheoDanhMuc> thongKeSoLuongDanhMucTheoNam(Integer nam){
        Session s = HibernateConfig.getFACTORY().openSession();
       
        Query query = s.createQuery("Select new viewmodels.ThongKeSoLuongTheoDanhMuc(e.maImei.sanPham.danhmuc.maDanhMuc,e.maImei.sanPham.danhmuc.tenDanhMuc,COUNT(e.maImei)) FROM HoaDonChiTiet e "
                + "Where e.maHD.trangThai = 2 and YEAR(ngayMua) = :nam "    
                + "Group by e.maImei.sanPham.danhmuc.maDanhMuc , e.maImei.sanPham.danhmuc.tenDanhMuc");
        query.setParameter("nam", nam);
       
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeSoLuongTheoDanhMuc> list = query.getResultList();
        return list;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainmodels.KhachHang;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilities.HibernateConfig;
import viewmodels.ThongKeKhachHang;

/**
 *
 * @author Tungt
 */
public class KhachHangRepositores {

    public List<KhachHang> getALL() {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From KhachHang e where e.trangThai = 0 Order by e.ngayTao desc");
        List<KhachHang> list = q.getResultList();
        return list;
    }

    public boolean add(KhachHang khachHang) {
        Transaction transaction = null;
        try ( Session s = HibernateConfig.getFACTORY().openSession()) {
            transaction = s.beginTransaction();
            s.save(khachHang);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public boolean update(KhachHang khachHang) {
        Transaction transaction = null;
        try ( Session s = HibernateConfig.getFACTORY().openSession()) {
            transaction = s.beginTransaction();
            s.update(khachHang);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public boolean delete(String ma) {
        Transaction transaction = null;
        try ( Session s = HibernateConfig.getFACTORY().openSession()) {
            KhachHang kh = s.get(KhachHang.class, ma);
            kh.setTrangThai(1);
            transaction = s.beginTransaction();
            s.update(kh);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }

    }

    public KhachHang seachbyMa(String ma) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From KhachHang where MaKH = :ma");
        q.setParameter("ma", ma);
        List<KhachHang> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public KhachHang fill(String maKH) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From KhachHang where MaKH = :masp");
        q.setParameter("masp", maKH);
        List<KhachHang> list = q.getResultList();
        return list.get(0);
    }

    public KhachHang layMa() {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From KhachHang where MaKH in (Select 'KH'+ Cast(Max(Cast(SUBSTRING(MaKH,3,Len(MaKH) - 2) as int)) as string) from KhachHang)");
        List<KhachHang> list = q.getResultList();
        return list.get(0);
    }

    public List<KhachHang> phanTrang(Integer limitPage, Integer page) {
        Session s = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = s.beginTransaction();
        Query query = s.createQuery("From KhachHang e where TrangThai = 0 Order by e.ngayTao desc");
        query.setFirstResult((limitPage * page) - limitPage);
        query.setMaxResults(limitPage);
        transaction.commit();
        List<KhachHang> list = query.getResultList();
        s.close();
        return list;

    }

    public List<ThongKeKhachHang> thongKeTheoNgay(Date date) {
        Session s = HibernateConfig.getFACTORY().openSession();

        Query query = s.createQuery("Select new viewmodels.ThongKeKhachHang(e.khachHang.maKH , e.khachHang.tenKH , COUNT(e.maHD)) FROM HoaDon e "
                + "Where e.maHD.trangThai = 2 and ngayMua = :ngay "
                + "Group by e.khachHang.maKH , e.khachHang.tenKH "
                + "Order by COUNT(e.maHD) DESC");
        query.setParameter("ngay", date);
        query.setMaxResults(5);
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeKhachHang> list = query.getResultList();
        return list;
    }

    public List<ThongKeKhachHang> thongKeTheoThang(Integer thang, Integer nam) {
        Session s = HibernateConfig.getFACTORY().openSession();

        Query query = s.createQuery("Select new viewmodels.ThongKeKhachHang(e.khachHang.maKH , e.khachHang.tenKH , COUNT(e.maHD)) FROM HoaDon e "
                + "Where e.maHD.trangThai = 2 and MONTH(ngayMua) = :ngay and YEAR(ngayMua) = :nam "
                + "Group by e.khachHang.maKH , e.khachHang.tenKH "
                + "Order by COUNT(e.maHD) DESC");
        query.setParameter("ngay", thang);
        query.setParameter("nam", nam);
        query.setMaxResults(5);
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeKhachHang> list = query.getResultList();
        return list;
    }

    public List<ThongKeKhachHang> thongKeTheoNam(Integer nam) {
        Session s = HibernateConfig.getFACTORY().openSession();

        Query query = s.createQuery("Select new viewmodels.ThongKeKhachHang(e.khachHang.maKH , e.khachHang.tenKH , COUNT(e.maHD)) FROM HoaDon e "
                + "Where e.maHD.trangThai = 2 and YEAR(ngayMua) = :nam "
                + "Group by e.khachHang.maKH , e.khachHang.tenKH "
                + "Order by COUNT(e.maHD) DESC");
        query.setParameter("nam", nam);
        query.setMaxResults(5);
//        query.setFirstResult(0);
//        query.setMaxResults(10);
        List<ThongKeKhachHang> list = query.getResultList();
        return list;
    }
    
    public List<KhachHang> getALLbyTrangThai(Integer trangThai){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From KhachHang e where e.trangThai = :trangthai Order by e.ngayTao desc");
        q.setParameter("trangthai", trangThai);
        List<KhachHang> list = q.getResultList();
        return list;
    }
    public List<KhachHang> timKiembyTrangThai(String ten){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From KhachHang where TenKH like :ten and TrangThai = 0");
        q.setParameter("ten", "%"+ten+"%");
        List<KhachHang> list = q.getResultList();
        return list;
    }
    public List<KhachHang> timKiemPhanTrang(String ten , Integer limitPage, Integer page) {
        Session s = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = s.beginTransaction();
        Query query = s.createQuery("From KhachHang where TenKH like :ten and TrangThai = 0");
        query.setFirstResult((limitPage * page) - limitPage);
        query.setMaxResults(limitPage);
        query.setParameter("ten", "%"+ten+"%");
        transaction.commit();
        List<KhachHang> list = query.getResultList();
        s.close();
        return list;

    }

}

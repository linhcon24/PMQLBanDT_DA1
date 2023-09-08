/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainmodels.HoaDon;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilities.HibernateConfig;

/**
 *
 * @author HANGOCHAN
 */
public class HoaDonRepositories {

    public List<HoaDon> getALL() {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDon e Order by e.ngayTao desc");
        List<HoaDon> list = q.getResultList();
        return list;
    }

    public boolean add(HoaDon m) {

        Transaction transaction = null;
        try ( Session s = HibernateConfig.getFACTORY().openSession()) {
            transaction = s.beginTransaction();
            s.save(m);
            transaction.commit();
            return true;

        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }
//    public void themHD(String maHD , Date ngayMua , Date ngayTao , String maNV , String maKH ,Integer trangThai){
//        Session session = HibernateConfig.getFACTORY().openSession();
//        Transaction transaction = session.beginTransaction();
//        Query q = session.createQuery("Insert into ChiTietSP(MaHD,NgayMua,NgayTao,MaNV,MaKH,TrangThai) values (:mahd,:ngaymua,:ngaytao,:manv,:makh,:trangthai)");
//        q.setParameter("mahd", maHD);
//        q.setParameter("ngaymua", ngayMua);
//        q.setParameter("ngaytao", ngayTao);
//        q.setParameter("manv", maNV);
//        q.setParameter("makh", maKH);
//        q.setParameter("trangthai", trangThai);
//        int index = q.executeUpdate();
//        transaction.commit();
//        session.close();
//        
//    }

    public void suaHD(String maHD, String ghiChu, BigDecimal tongTien, BigDecimal giamGia, Integer trangThai, String maKM, String diachi) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery("UPDATE HoaDon SET DiaChiNhanHang = :diachi , GhiChu = :ghichu , TongTien = :tongtien , GiamGia = :giamgia , TrangThai = :trangThai , MaKM = :makm where MaHD = :mahd");
        q.setParameter("mahd", maHD);
        q.setParameter("ghichu", ghiChu);
        q.setParameter("tongtien", tongTien);
        q.setParameter("giamgia", giamGia);
        q.setParameter("trangThai", trangThai);
        q.setParameter("makm", maKM);
        q.setParameter("diachi", diachi);
        int index = q.executeUpdate();
        transaction.commit();
        session.close();

    }

    public void updateNgaySua(Date ngaySua, String maHD) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery("UPDATE HoaDon SET NgaySua = :ngaysua where MaHD = :mahd");
        q.setParameter("ngaysua", ngaySua);
        q.setParameter("mahd", maHD);
        int index = q.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void updateNgayTT(Date ngaymua, String maHD) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery("UPDATE HoaDon SET NgayMua = :ngaysua where MaHD = :mahd");
        q.setParameter("ngaysua", ngaymua);
        q.setParameter("mahd", maHD);
        int index = q.executeUpdate();
        transaction.commit();
        session.close();
    }

    public boolean update(HoaDon hoaDon) {
        Transaction transaction = null;
        try ( Session s = HibernateConfig.getFACTORY().openSession()) {
            transaction = s.beginTransaction();
            s.update(hoaDon);
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
            HoaDon nv = s.get(HoaDon.class, ma);
            nv.setTrangThai(1);
            transaction = s.beginTransaction();
            s.update(nv);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }

    }

    public HoaDon seachbyMa(String ma) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDon where MaHD = :ma");
        q.setParameter("ma", ma);
        List<HoaDon> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public HoaDon fill(String ma) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDon where MaHD = :masp");
        q.setParameter("masp", ma);
        List<HoaDon> list = q.getResultList();
        return list.get(0);
    }

    public HoaDon layMa() {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDon where MaHD in (Select 'HD'+ Cast(Max(Cast(SUBSTRING(MaHD,3,Len(MaHD) - 2) as int)) as string) from HoaDon)");
        List<HoaDon> list = q.getResultList();
        return list.get(0);
    }

    public List<HoaDon> countHoaDontheoNgay(Integer trangThai, Date ngay) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDon where TrangThai = :trangthai and NgayMua = :ngaymua");
        q.setParameter("trangthai", trangThai);
        q.setParameter("ngaymua", ngay);
        List<HoaDon> list = q.getResultList();
        return list;
    }

    public List<HoaDon> countHoaDonHuytheoNgay(Integer trangThai, Date ngay) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDon where TrangThai = :trangthai and NgaySua = :ngaymua");
        q.setParameter("trangthai", trangThai);
        q.setParameter("ngaymua", ngay);
        List<HoaDon> list = q.getResultList();
        return list;
    }

    public List<HoaDon> countHoaDon(Integer trangThai) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDon where TrangThai = :trangthai");
        q.setParameter("trangthai", trangThai);
        List<HoaDon> list = q.getResultList();
        return list;
    }

    public List<HoaDon> countHoaDontheoThang(Integer trangThai, Integer thang, Integer nam) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDon where TrangThai = :trangthai and MONTH(NgayMua) = :ngaymua and YEAR(NgayMua) = :nam");
        q.setParameter("trangthai", trangThai);
        q.setParameter("ngaymua", thang);
        q.setParameter("nam", nam);
        List<HoaDon> list = q.getResultList();
        return list;
    }

    public List<HoaDon> countHoaDonHuytheoThang(Integer trangThai, Integer thang, Integer nam) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDon where TrangThai = :trangthai and MONTH(NgaySua) = :ngaymua and YEAR(NgaySua) = :nam");
        q.setParameter("trangthai", trangThai);
        q.setParameter("ngaymua", thang);
        q.setParameter("nam", nam);
        List<HoaDon> list = q.getResultList();
        return list;
    }

    public List<HoaDon> countHoaDontheoNam(Integer trangThai, Integer nam) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDon where TrangThai = :trangthai and YEAR(NgayMua) = :nam");
        q.setParameter("trangthai", trangThai);

        q.setParameter("nam", nam);
        List<HoaDon> list = q.getResultList();
        return list;
    }

    public List<HoaDon> countHoaDonHuytheoNam(Integer trangThai, Integer nam) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDon where TrangThai = :trangthai and YEAR(NgaySua) = :nam");
        q.setParameter("trangthai", trangThai);
        q.setParameter("nam", nam);
        List<HoaDon> list = q.getResultList();
        return list;
    }

    public List<HoaDon> locTheoTrangThai(Integer trangThai) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From HoaDon where TrangThai = :trangthai");
        q.setParameter("trangthai", trangThai);
        List<HoaDon> list = q.getResultList();
        return list;
    }

}

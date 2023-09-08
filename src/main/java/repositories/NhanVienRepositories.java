/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainmodels.NhanVien;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilities.HibernateConfig;

/**
 *
 * @author HANGOCHAN
 */
public class NhanVienRepositories {
    
    
    public List<NhanVien> getALL(){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From NhanVien");
        List<NhanVien> list = q.getResultList();
        return list;
    }
    public boolean add(NhanVien nhanVien){
        Transaction transaction = null;
        try(Session s = HibernateConfig.getFACTORY().openSession()){
            transaction= s.beginTransaction();
            s.save(nhanVien);
            transaction.commit();
            return true;
        }catch(Exception e){
            transaction.rollback();
            return false;
        }
    }
    public boolean update(NhanVien nhanVien){
        Transaction transaction = null;
        try(Session s = HibernateConfig.getFACTORY().openSession()){
            transaction= s.beginTransaction();
            s.update(nhanVien);
            transaction.commit();
            return true;
        }catch(Exception e){
            transaction.rollback();
            return false;
        }
    }
    public boolean delete(String ma){
        Transaction transaction = null;
        try(Session s = HibernateConfig.getFACTORY().openSession()){
            NhanVien nv = s.get(NhanVien.class, ma);
            nv.setTrangThai(1);
            transaction= s.beginTransaction();
            s.update(nv);
            transaction.commit();
            return true;
        }catch(Exception e){
            transaction.rollback();
            return false;
        }
        
    }
    public NhanVien seachbyMa(String ma){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From NhanVien where MaNV = :ma");
        q.setParameter("ma", ma);
        List<NhanVien> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
    public NhanVien fill(String maNV){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From NhanVien where MaNV = :masp");
        q.setParameter("masp", maNV);
        List<NhanVien> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
    public void doiMatKhau(String maNV , String matKhau){
        Session session = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery("UPDATE NhanVien SET MatKhau = :matkhau where MaNV = :ma");
        q.setParameter("ma", maNV);
        q.setParameter("matkhau", matKhau);
        int index = q.executeUpdate();
        transaction.commit();
        session.close();
    }
    public List<NhanVien> getALLbyVaiTro(Integer vaiTro){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From NhanVien where VaiTro = :vaiTro");
        q.setParameter("vaiTro", vaiTro);
        List<NhanVien> list = q.getResultList();
        return list;
    }
    public List<NhanVien> phanTrang(Integer limitPage, Integer page) {
        Session s = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = s.beginTransaction();
        Query query = s.createQuery("From NhanVien e where e.trangThai = 0 Order by e.ngayTao desc");
        query.setFirstResult((limitPage * page) - limitPage);
        query.setMaxResults(limitPage);
        transaction.commit();
        List<NhanVien> list = query.getResultList();
        s.close();
        return list;

    }
    public List<NhanVien> getALLbyTrangThai(Integer trangThai){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From NhanVien e where e.trangThai = :trangthai Order by e.ngayTao desc");
        q.setParameter("trangthai", trangThai);
        List<NhanVien> list = q.getResultList();
        return list;
    }
    public List<NhanVien> timKiembyTrangThai(String ten){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From NhanVien where TenNV like :ten and TrangThai = 0");
        q.setParameter("ten", "%"+ten+"%");
        List<NhanVien> list = q.getResultList();
        return list;
    }
    public List<NhanVien> timKiemPhanTrang(String ten , Integer limitPage, Integer page) {
        Session s = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = s.beginTransaction();
        Query query = s.createQuery("From NhanVien where TenNV like :ten and TrangThai = 0");
        query.setFirstResult((limitPage * page) - limitPage);
        query.setMaxResults(limitPage);
        query.setParameter("ten", "%"+ten+"%");
        transaction.commit();
        List<NhanVien> list = query.getResultList();
        s.close();
        return list;

    }
     public List<NhanVien> locPhanTrang(Integer vaitro , Integer limitPage, Integer page) {
        Session s = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = s.beginTransaction();
        Query query = s.createQuery("From NhanVien where VaiTro = :vaiTro and TrangThai = 0");
        query.setFirstResult((limitPage * page) - limitPage);
        query.setMaxResults(limitPage);
        query.setParameter("vaiTro", vaitro);
        transaction.commit();
        List<NhanVien> list = query.getResultList();
        s.close();
        return list;

    }
}

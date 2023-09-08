/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainmodels.ChiTietSP;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilities.HibernateConfig;

/**
 *
 * @author HANGOCHAN
 */
public class ChiTietSPRepositories {
    public List<ChiTietSP> getAll(){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From ChiTietSP");
        List<ChiTietSP> list = q.getResultList();
        return list;
    }
    public boolean add(ChiTietSP m){
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
    public boolean update(ChiTietSP m){
        Transaction transaction = null ;
        try (Session s = HibernateConfig.getFACTORY().openSession()){
            transaction = s.beginTransaction();
            s.update(m);
            transaction.commit();
            return true;
            
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }
    public boolean delete(ChiTietSP m){
        Transaction transaction = null ;
        try (Session s = HibernateConfig.getFACTORY().openSession()){
            m.setTrangThai(1);
            transaction = s.beginTransaction();
            s.update(m);
            transaction.commit();
            return true;
            
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }
    public void xoaImei(String ma){
        Session session = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery("DELETE FROM ChiTietSP where MaSP = :masp");
        q.setParameter("masp", ma);
        int index = q.executeUpdate();
        transaction.commit();
        session.close();
    }
    public ChiTietSP seachbyMa(String ma){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From ChiTietSP where MaImei = :ma");
        q.setParameter("ma", ma);
        List<ChiTietSP> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
    public List<ChiTietSP> count(String maSP){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From ChiTietSP where MaSP = :masp and TrangThai = 0");
        q.setParameter("masp", maSP);
        List<ChiTietSP> list = q.getResultList();
        return list;
    }
    public ChiTietSP fill(String maSP){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From ChiTietSP where MaSP = :masp");
        q.setParameter("masp", maSP);
        List<ChiTietSP> list = q.getResultList();
        return list.get(0);
    }
    public List<ChiTietSP> getImeibyMaSP(String maSP){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From ChiTietSP where MaSP = :masp");
        q.setParameter("masp", maSP);
        List<ChiTietSP> list = q.getResultList();
        return list;
    }
    public ChiTietSP load(String maSP){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From ChiTietSP where MaSP = :masp and TrangThai = 0");
        q.setParameter("masp", maSP);
        List<ChiTietSP> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
    
    
//    public void updateImei(String ma ){
//        Session session = HibernateConfig.getFACTORY().openSession();
//        Transaction transaction = session.beginTransaction();
//        Query q = session.createQuery("UPDATE ChiTietSP SET TrangThai = 1 where MaImei = :masp");
//        q.setParameter("masp", ma);
//        int index = q.executeUpdate();
//        transaction.commit();
//        session.close();
//    }
    public void updateImeiTrangThai(String ma , Integer trangThai){
        Session session = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery("UPDATE ChiTietSP SET TrangThai = :trangthai where MaImei = :masp");
        q.setParameter("masp", ma);
        q.setParameter("trangthai", trangThai);
        int index = q.executeUpdate();
        transaction.commit();
        session.close();
    }
    public List<ChiTietSP> getImei(String imei){
        Session session = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery("FROM ChiTietSP where MaImei like :maImei and TrangThai = 0");
        q.setParameter("maImei", "%"+imei+"%");
        List<ChiTietSP> chiTietSPs = q.getResultList();
        return chiTietSPs;
    }
    
    
}

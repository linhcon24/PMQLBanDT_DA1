/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainmodels.BaoHanhChiTiet;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilities.HibernateConfig;

/**
 *
 * @author HANGOCHAN
 */
public class QLBaoHanhChiTietRepositories {
    public List<BaoHanhChiTiet> getAll(String ma){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From BaoHanhChiTiet where MaBH = :ma");
        q.setParameter("ma", ma);
        List<BaoHanhChiTiet> list = q.getResultList();
        return list;
    }
    public boolean add(BaoHanhChiTiet m){
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
    public boolean update(BaoHanhChiTiet m){
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
    public boolean delete(BaoHanhChiTiet m){
        Transaction transaction = null ;
        try (Session s = HibernateConfig.getFACTORY().openSession()){
            transaction = s.beginTransaction();
            s.delete(m);
            transaction.commit();
            return true;
            
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }
    public BaoHanhChiTiet seachbyMa(String ma){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From BaoHanhChiTiet where MaImei = :ma");
        q.setParameter("ma", ma);
        List<BaoHanhChiTiet> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
    public void xoaBaoHanh(String ma){
        Session session = HibernateConfig.getFACTORY().openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery("DELETE FROM BaoHanhChiTiet where MaImei = :ma");
        q.setParameter("ma", ma);
        int index = q.executeUpdate();
        transaction.commit();
        session.close();
    }
}

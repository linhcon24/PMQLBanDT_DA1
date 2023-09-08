/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainmodels.NSX;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilities.HibernateConfig;

/**
 *
 * @author hodangquan
 */
public class NSXRepositories {
    public List<NSX> getAll(){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From NSX e where e.trangThai = 0 Order by e.ngayTao desc");
        List<NSX> list = q.getResultList();
        return list;
    }
    public boolean add(NSX nsx){
        Transaction transaction = null ;
        try (Session s = HibernateConfig.getFACTORY().openSession()){
            transaction = s.beginTransaction();
            s.save(nsx);
            transaction.commit();
            return true;
            
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }
    public boolean update(NSX nsx){
        Transaction transaction = null ;
        try (Session s = HibernateConfig.getFACTORY().openSession()){
            transaction = s.beginTransaction();
            s.update(nsx);
            transaction.commit();
            return true;
            
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }
    public boolean delete(NSX nsx){
        Transaction transaction = null ;
        try (Session s = HibernateConfig.getFACTORY().openSession()){
            nsx.setTrangThai(1);
            transaction = s.beginTransaction();
            s.update(nsx);
            transaction.commit();
            return true;
            
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }
    public NSX seachbyMa(String ma){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From NSX where MaNSX = :ma");
        q.setParameter("ma", ma);
        List<NSX> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
}

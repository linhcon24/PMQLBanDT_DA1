/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainmodels.BaoHanh;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilities.HibernateConfig;

/**
 *
 * @author HANGOCHAN
 */
public class QLBaoHanhRepositories {
    public List<BaoHanh> getAll(){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From BaoHanh");
        List<BaoHanh> list = q.getResultList();
        return list;
    }
    public boolean add(BaoHanh m){
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
    public boolean update(BaoHanh m){
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
    public boolean delete(BaoHanh m){
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
    public BaoHanh seachbyMa(String ma){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From BaoHanh where MaBH = :ma");
        q.setParameter("ma", ma);
        List<BaoHanh> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
    public BaoHanh layMa() {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From BaoHanh where MaBH in (Select 'BH'+ Cast(Max(Cast(SUBSTRING(MaBH,3,Len(MaBH) - 2) as int)) as string) from BaoHanh)");
        List<BaoHanh> list = q.getResultList();
        return list.get(0);
    }
}

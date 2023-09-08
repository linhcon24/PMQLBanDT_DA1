/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainmodels.BoNhoTrong;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilities.HibernateConfig;

/**
 *
 * @author Tungt
 */
public class QLBoNhoTrongRepositories {
    public List<BoNhoTrong> getAll(){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From BoNhoTrong e where e.trangThai = 0 Order by e.ngayTao desc");
        List<BoNhoTrong> list = q.getResultList();
        return list;
    }
    public boolean add(BoNhoTrong m){
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
    public boolean update(BoNhoTrong m){
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
    public boolean delete(BoNhoTrong m){
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
    public BoNhoTrong seachbyMa(String ma){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From BoNhoTrong where MaBNT = :ma");
        q.setParameter("ma", ma);
        List<BoNhoTrong> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
    public List<BoNhoTrong> timKiembyTrangThai(Integer dungLuong){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From BoNhoTrong where DungLuong = :ten and TrangThai = 0");
        q.setParameter("ten",+dungLuong);
        List<BoNhoTrong> list = q.getResultList();
        return list;
    }
    public BoNhoTrong layMa() {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From BoNhoTrong where MaBNT in (Select 'BN'+ Cast(Max(Cast(SUBSTRING(MaBNT,3,Len(MaBNT) - 2) as int)) as string) from BoNhoTrong)");
        List<BoNhoTrong> list = q.getResultList();
        return list.get(0);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainmodels.MauSac;
import domainmodels.NSX;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilities.HibernateConfig;

/**
 *
 * @author trungbui
 */
public class QLMauSacRepositories {
    public List<MauSac> getAll(){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From MauSac e where e.trangThai = 0 Order by e.ngayTao desc");
        List<MauSac> list = q.getResultList();
        return list;
    }
    public boolean add(MauSac m){
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
    public boolean update(MauSac m){
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
    public boolean delete(MauSac m){
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
    public MauSac seachbyMa(String ma){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From MauSac where MaMauSac = :ma");
        q.setParameter("ma", ma);
        List<MauSac> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
      public List<MauSac> timKiembyTrangThai(String ten){
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From MauSac where TenMauSac like :ten and TrangThai = 0");
        q.setParameter("ten", "%"+ten+"%");
        List<MauSac> list = q.getResultList();
        return list;
    }
    
    public MauSac layMa() {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From MauSac where MaMauSac in (Select 'MS'+ Cast(Max(Cast(SUBSTRING(MaMauSac,3,Len(MaMauSac) - 2) as int)) as string) from MauSac)");
        List<MauSac> list = q.getResultList();
        return list.get(0);
    }
}

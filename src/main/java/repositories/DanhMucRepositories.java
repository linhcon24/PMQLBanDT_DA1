/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainmodels.DanhMuc;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilities.HibernateConfig;

/**
 *
 * @author adm
 */
public class DanhMucRepositories {

    public List<DanhMuc> getAll() {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From DanhMuc e where e.trangThai = 0 Order by e.ngayTao desc");
        List<DanhMuc> list = q.getResultList();
        return list;
    }

    public boolean add(DanhMuc d) {
        Transaction transaction = null;
        try ( Session s = HibernateConfig.getFACTORY().openSession()) {
            transaction = s.beginTransaction();
            s.save(d);
            transaction.commit();
            return true;

        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public boolean update(DanhMuc d) {
        Transaction transaction = null;
        try ( Session s = HibernateConfig.getFACTORY().openSession()) {
            transaction = s.beginTransaction();
            s.update(d);
            transaction.commit();
            return true;

        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public boolean delete(DanhMuc d) {
        Transaction transaction = null;
        try ( Session s = HibernateConfig.getFACTORY().openSession()) {
            d.setTrangThai(1);
            transaction = s.beginTransaction();
            s.update(d);
            transaction.commit();
            return true;

        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public DanhMuc seachbyMa(String ma) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From DanhMuc where MaDM = :ma");
        q.setParameter("ma", ma);
        List<DanhMuc> list = q.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public List<DanhMuc> timKiembyTrangThai(String ten) {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From DanhMuc where TenDM like :ten and TrangThai = 0");
        q.setParameter("ten", "%"+ten+"%");
        List<DanhMuc> list = q.getResultList();
        return list;
    }

    public DanhMuc layMa() {
        Session session = HibernateConfig.getFACTORY().openSession();
        Query q = session.createQuery("From DanhMuc where MaDM in (Select 'DM'+ Cast(Max(Cast(SUBSTRING(MaDM,3,Len(MaDM) - 2) as int)) as string) from DanhMuc)");
        List<DanhMuc> list = q.getResultList();
        return list.get(0);
    }
}

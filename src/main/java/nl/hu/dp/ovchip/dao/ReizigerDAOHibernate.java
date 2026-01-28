package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {

    @Override
    public boolean save(Reiziger r) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(r);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(reiziger);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        Transaction tx = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(reiziger);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Reiziger.class, id);
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(LocalDate geboortedatum) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
        return session.createQuery(
                "FROM Reiziger WHERE geboortedatum = :gb", Reiziger.class)
                .setParameter("gb", geboortedatum)
                .list();
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Reiziger", Reiziger.class).list();
        }
    }
}

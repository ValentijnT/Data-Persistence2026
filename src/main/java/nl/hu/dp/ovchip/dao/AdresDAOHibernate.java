package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domain.Adres;
import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AdresDAOHibernate implements AdresDAO {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public boolean save(Adres adres) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(adres);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("fout bij save adres: " + adres);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(adres);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("fout bij update adres: " + adres);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(adres);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("fout bij delete adres: " + adres);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger r) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM Adres a WHERE a.reiziger = :r", Adres.class)
                    .setParameter("r", r)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Adres", Adres.class).list();
        }
    }
}
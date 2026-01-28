package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {

    private final SessionFactory sessionFactory;

    public ReizigerDAOHibernate() {
        this.sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Reiziger.class)
                .buildSessionFactory();
    }

    @Override
    public Boolean save(Reiziger reiziger) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(reiziger);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("fout bij save reiziger: " + reiziger);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean update(Reiziger reiziger) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(reiziger);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("fout bij update reiziger: " + reiziger);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean delete(Reiziger reiziger) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(reiziger);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("fout bij delete reiziger: " + reiziger);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Reiziger.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Reiziger r WHERE r.geboortedatum = :date", Reiziger.class)
                    .setParameter("date", date)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Reiziger", Reiziger.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        if (sessionFactory != null) sessionFactory.close();
    }
}
package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domain.Adres;
import nl.hu.dp.ovchip.domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class AdresDAOHibernate implements AdresDAO {

    private final SessionFactory sessionFactory;

    public AdresDAOHibernate() {
        this.sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Adres.class)
                .buildSessionFactory();
    }

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
        return null;
    }

    @Override
    public List<Adres> findAll() {
        return List.of();
    }

//    @Override
//    public List<Reiziger> findByGbdatum(LocalDate date) {
//        try (Session session = sessionFactory.openSession()) {
//            return session.createQuery("FROM Reiziger r WHERE r.geboortedatum = :date", Reiziger.class)
//                    .setParameter("date", date)
//                    .list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    @Override
//    public List<Reiziger> findAll() {
//        try (Session session = sessionFactory.openSession()) {
//            return session.createQuery("FROM Reiziger", Reiziger.class).list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public void close() {
//        if (sessionFactory != null) sessionFactory.close();
//    }
}
package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domain.OVChipkaart;
import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public boolean save(Reiziger reiziger) {
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
    public boolean update(Reiziger reiziger) {
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
    public boolean delete(Reiziger reiziger) {
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
            Reiziger reiziger = session.get(Reiziger.class, id);

            if(reiziger != null){
                reiziger.getOVchipkaarten().size();
                for (OVChipkaart ov : reiziger.getOVchipkaarten()) {
                    ov.getProducten().size();
                }
            }
            return reiziger;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            List<Reiziger> reizigers = session.createQuery("FROM Reiziger r WHERE r.geboortedatum = :date", Reiziger.class)
                    .setParameter("date", date)
                    .list();

            for (Reiziger reiziger : reizigers) {
                reiziger.getOVchipkaarten().size();
            }
            return reizigers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<Reiziger> reizigers = session.createQuery("FROM Reiziger", Reiziger.class).list();
            for (Reiziger reiziger : reizigers) {
                reiziger.getOVchipkaarten().size();
            }
            return reizigers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        if (sessionFactory != null) sessionFactory.close();
    }
}
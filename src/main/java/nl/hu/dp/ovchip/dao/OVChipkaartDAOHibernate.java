package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domain.Adres;
import nl.hu.dp.ovchip.domain.OVChipkaart;
import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public boolean save(OVChipkaart ov) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(ov);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("fout bij save ov: " + ov);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ov) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(ov);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("fout bij update ov: " + ov);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ov) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(ov);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("fout bij delete ov: " + ov);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger r) {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM OVChipkaart WHERE reiziger = :r", OVChipkaart.class)
                    .setParameter("r", r)
                    .getResultList();
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public OVChipkaart findById(int id) {
        return null;
    }

    @Override
    public List<OVChipkaart> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM OVChipkaart", OVChipkaart.class).list();
        }
    }
}

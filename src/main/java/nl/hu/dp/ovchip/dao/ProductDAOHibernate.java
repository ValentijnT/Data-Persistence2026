package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domain.OVChipkaart;
import nl.hu.dp.ovchip.domain.Product;
import nl.hu.dp.ovchip.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public boolean save(Product product) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(product);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(product);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        Transaction tx = null;

        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Product managed = (Product) session.merge(product);

            for(OVChipkaart ov : new ArrayList<>(managed.getOvchipkaarten())) {
                ov.removeProduct(managed);
                session.saveOrUpdate(ov);
            }
            session.delete(managed);

            tx.commit();
            return true;
        } catch (Exception e){
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT p FROM Product p JOIN p.ovchipkaarten ov WHERE ov.kaart_nummer = :kaartNummer",
                    Product.class
            ).setParameter("kaartNummer", ovChipkaart.getKaartNummer())
                    .getResultList();
        }
    }

    @Override
    public List<Product> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Product", Product.class).getResultList();
        }
    }
}

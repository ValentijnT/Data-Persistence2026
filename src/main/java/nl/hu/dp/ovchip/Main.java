package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.dao.*;
import nl.hu.dp.ovchip.domain.*;
import nl.hu.dp.ovchip.util.HibernateUtil;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        HibernateUtil.getSessionFactory();

        //Test P4H Hibernate
        System.out.println("\n\nTest P4H:");
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate();
        AdresDAOHibernate adao = new AdresDAOHibernate();
        OVChipkaartDAOHibernate ovdao = new OVChipkaartDAOHibernate();
        ProductDAOHibernate pdao = new ProductDAOHibernate();


        testP5H(rdao, adao, ovdao, pdao);

        HibernateUtil.shutdown();
    }

    private static void testP5H(ReizigerDAOHibernate rdao, AdresDAOHibernate adao, OVChipkaartDAOHibernate ovdao, ProductDAOHibernate pdao) {
        System.out.println("\n--- Nieuwe reiziger opslaan ---");

        Reiziger r = new Reiziger(999, "T", "van", "Test", LocalDate.of(2000, 1, 1));
        Adres a = new Adres(999, "1234AB", "10", "Teststraat", "Zeist", r);
        r.setAdres(a);

        OVChipkaart k1 = new OVChipkaart(90001, LocalDate.now().plusYears(1), 1, 20.0, r);
        OVChipkaart k2 = new OVChipkaart(90002, LocalDate.now().plusYears(2), 2, 50.0, r);

        r.addOVChipkaart(k1);
        r.addOVChipkaart(k2);

        Product product3 = pdao.findById(3);

        Product p1 = new Product(70001, "TestProduct1", "Beschrijving 1", 10.0);
        Product p2 = new Product(70002, "TestProduct2", "Beschrijving 2", 20.0);

        k1.addProduct(p1);
        k1.addProduct(p2);
        k2.addProduct(p1);
        k2.addProduct(product3);

        pdao.save(p1);
        pdao.save(p2);

        rdao.save(r);

        System.out.println("Reiziger opgeslagen:\n" + r);

        System.out.println("\n--- Reiziger ophalen ---");
        Reiziger rFetched = rdao.findById(999);
        System.out.println(rFetched);

        System.out.println("\n--- OVChipkaart 90001 ophalen ---");
        OVChipkaart kaart = ovdao.findById(90001);
        System.out.println(kaart);

        System.out.println("\n--- Reiziger updaten ---");
        rFetched.setAchternaam("NieuweNaam");
        rdao.update(rFetched);
        System.out.println("Geüpdatet:\n" + rdao.findById(999));

        pdao.delete(p1);
        pdao.delete(p2);

        System.out.println(ovdao.findById(90002));

        System.out.println("\n--- Reiziger verwijderen ---");
        rdao.delete(rFetched);
        System.out.println("Bestaat reiziger nog? " + rdao.findById(999));

        System.out.println("Als null dan is OVchipkaart met reiziger verwijderd: ");
        System.out.println(ovdao.findById(90002));

        System.out.println("Als Product 3 nog bestaat, dan is hij niet mee verwijderd met de reiziger: ");
        System.out.println(pdao.findById(3));

        System.out.println("\n--- Alle producten ---");
        for (Product p : pdao.findAll()) {
            System.out.println(p);
        }

        System.out.println("\n=== EINDE TEST P5H ===");
    }

}

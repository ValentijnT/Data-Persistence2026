package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.dao.*;
import nl.hu.dp.ovchip.domain.Adres;
import nl.hu.dp.ovchip.domain.OVChipkaart;
import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.util.HibernateUtil;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        HibernateUtil.getSessionFactory();

        //Test P4H Hibernate
        System.out.println("\n\nTest P4H:");
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate();
        AdresDAOHibernate adao = new AdresDAOHibernate();
        OVChipkaartDAOHibernate ovdao = new OVChipkaartDAOHibernate();

        testP4H(rdao, adao, ovdao);

        HibernateUtil.shutdown();
    }

    private static void testP4H(ReizigerDAOHibernate rdao, AdresDAOHibernate adao, OVChipkaartDAOHibernate ovdao) {

        Reiziger reiziger = new Reiziger(6, "V", null, "Tollenaar", LocalDate.of(2003, 9, 26));
        Adres adres = new Adres(6, "3704AZ", "8", "schipsloot", "zeist", reiziger);

        OVChipkaart k1 = new OVChipkaart(12345, LocalDate.of(2027, 1, 1), 2, 25.00, reiziger);
        OVChipkaart k2 = new OVChipkaart(67890, LocalDate.of(2028, 1, 1), 1, 50.00, reiziger);

        reiziger.setAdres(adres);
        reiziger.addOVChipkaart(k1);
        reiziger.addOVChipkaart(k2);


        rdao.save(reiziger);

        System.out.println("opgeslagen reiziger: ");
        System.out.println(rdao.findById(6));

        //update
        System.out.println("\nupdate reiziger met ovchipkaarten: ");
        reiziger.setAchternaam("toltje");
        k1.setSaldo(99.99);
        k2.setKlasse(2);

        rdao.update(reiziger);

        System.out.println("na update reiziger: ");
        System.out.println(rdao.findById(6));

        //findByReiziger
        System.out.println("\nfindByReiziger");
        ovdao.findByReiziger(rdao.findById(2)).forEach(System.out::println);

        //findByGbdatum
        System.out.println("\nfindByGbdatum");
        for (Reiziger r : rdao.findByGbdatum(LocalDate.of(2003, 9, 26))){
            System.out.println(r);
        }

        //rdao findAll
        System.out.println("\nfindAll rdao");
        for(Reiziger r : rdao.findAll()){
            System.out.println(r);
        }

        //delete
        System.out.println("\ndelete reiziger en ovchipkaarten: ");

        rdao.delete(reiziger);

        System.out.println("na delete reiziger (moet null aangeven): ");
        System.out.println(rdao.findById(6));

        //findAll ovchipkaarten
        System.out.println("\nfindAll() van ovchipkaarten: ");
        ovdao.findAll().forEach(System.out::println);
    }
}
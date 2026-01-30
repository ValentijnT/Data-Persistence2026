package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.dao.*;
import nl.hu.dp.ovchip.domain.Adres;
import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.util.HibernateUtil;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        HibernateUtil.getSessionFactory();

        //Test P2H Hibernate
        System.out.println("\n\nTest P3H:");
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate();
        AdresDAOHibernate adao = new AdresDAOHibernate();
        testP3H(rdao, adao);

        HibernateUtil.shutdown();
    }

    private static void testP3H(ReizigerDAOHibernate rdao, AdresDAOHibernate adao) {
        //save
        System.out.println("Test save adres + Reiziger: \n");
        Reiziger valentijn = new Reiziger(67, "V", null, "Tollenaar", LocalDate.of(2003, 9, 26));
        Adres adres = new Adres(67, "3704AZ", "8", "heidelberglaan", "Utrecht", valentijn);

        adres.setReiziger(valentijn);
        valentijn.setAdres(adres);

        rdao.save(valentijn);

        System.out.println("\nNieuwe reiziger in database: ");
        System.out.println(rdao.findById(67));

        //update
        System.out.println("\nNieuwe reiziger en adres updaten in database: ");
        adres.setHuisnummer("10B");
        valentijn.setAchternaam("toltje");
        rdao.update(valentijn);

        System.out.println("\nReiziger na update in de database: ");
        System.out.println(rdao.findById(67));

        //findByReiziger
        System.out.println("\nfindByReiziger geeft: ");
        System.out.println(adao.findByReiziger(valentijn));

        //delete
        System.out.println("\nNieuwe reiziger in database verwijderen: ");

        rdao.delete(valentijn);
        System.out.println("Valentijn uit db verwijderd: " + rdao.findById(67));

        System.out.println("\nAlle adress: ");
        for (Adres a : adao.findAll()) {
            System.out.println(a);
        }

        System.out.println("\nAlle reizigers en adressen: ");
        for (Reiziger r : rdao.findAll()) {
            System.out.println(r);
        }


    }
}
package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.dao.ReizigerDAO;
import nl.hu.dp.ovchip.dao.ReizigerDAOHibernate;
import nl.hu.dp.ovchip.dao.ReizigerDAOPsql;
import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.util.DatabaseConnection;
import nl.hu.dp.ovchip.util.HibernateUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        HibernateUtil.getSessionFactory();

        //Test P2H Hibernate
        System.out.println("\n\nTest P2H:");
        ReizigerDAO hibernateDao = new ReizigerDAOHibernate();
        testReizigerDAO(hibernateDao);

        HibernateUtil.shutdown();
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        LocalDate gbdatum = LocalDate.of(1981, 3,14);
        Reiziger sietske = new Reiziger(77, "S", null, "Boers", gbdatum);
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.


        System.out.println("reiziger voor update achternaam: \n" + rdao.findById(77));
        //update bestaande reiziger
        sietske.setAchternaam("Zusters");
        rdao.update(sietske);
        System.out.println("reiziger na update achternaam: \n" + rdao.findById(77));

        //findByGbdatum testen
        System.out.println("\nReizigers die op 1981-03-14 geboren zijn:");
        List<Reiziger> reizigersByGbdatum = rdao.findByGbdatum(gbdatum);
        for (Reiziger r : reizigersByGbdatum) {
            System.out.println(r);
        }

        //delete Sietske testen
        rdao.delete(sietske);
        System.out.println("\nZoeken naar sietske nadat ze is verwijderd:");
        System.out.println(rdao.findById(77)); //geeft null.
    }
}
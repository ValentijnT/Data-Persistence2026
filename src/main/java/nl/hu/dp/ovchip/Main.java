package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.dao.*;
import nl.hu.dp.ovchip.domain.*;
import nl.hu.dp.ovchip.util.DatabaseConnection;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        try (Connection conn = DatabaseConnection.getConnection()) {

            // DAO’s aanmaken
            ReizigerDAOPsql rdao = new ReizigerDAOPsql(conn);
            AdresDAOPsql adao = new AdresDAOPsql(conn);
            OVChipkaartDAOPsql ovdao = new OVChipkaartDAOPsql(conn);
            ProductDAOPsql pdao = new ProductDAOPsql(conn);

            // DAO’s koppelen
            rdao.setAdresDAO(adao);
            rdao.setOvChipkaartDAO(ovdao);
            adao.setReizigerDAO(rdao);
            ovdao.setReizigerDAO(rdao);
            ovdao.setProductDAO(pdao);
            pdao.setOVChipkaartDAO(ovdao);

            System.out.println("\nP5Test starten:");

            //alle reizigers ophalen
            System.out.println("\n--- Alle reizigers ---");
            for (Reiziger r : rdao.findAll()) {
                System.out.println(r);
            }

            // Nieuwe reiziger + adres + kaarten + producten opslaan
            System.out.println("\n--- Nieuwe reiziger opslaan met alles erop en eraan ---");

            Reiziger r = new Reiziger(999, "T", "van", "Test", LocalDate.of(2000, 1, 1));
            Adres a = new Adres(999, "1234AB", "Teststraat", "10", "Zeist", r);
            r.setAdres(a);

            OVChipkaart k1 = new OVChipkaart(90001, LocalDate.now().plusYears(1), 1, 20.0, r);
            OVChipkaart k2 = new OVChipkaart(90002, LocalDate.now().plusYears(2), 2, 50.0, r);

            Product p1 = new Product(70001, "TestProduct1", "Beschrijving 1", 10.0);
            Product p2 = new Product(70002, "TestProduct2", "Beschrijving 2", 20.0);

            pdao.save(p1);
            pdao.save(p2);

            k1.addProduct(p1);
            k1.addProduct(p2);
            k2.addProduct(p1);

            r.addOVChipkaart(k1);
            r.addOVChipkaart(k2);

            rdao.save(r);
            System.out.println("Reiziger opgeslagen: " + r);

            // Ophalen van reiziger 999
            System.out.println("\n--- Reiziger 999 ophalen ---");
            Reiziger ophalen = rdao.findById(999);
            System.out.println(ophalen);

            // Updaten
            System.out.println("\n--- Reiziger updaten ---");
            ophalen.setAchternaam("NieuweNaam");
            rdao.update(ophalen);
            System.out.println("Geüpdatet: " + rdao.findById(999));

            // OVChipkaart ophalen
            System.out.println("\n--- OVChipkaart 90001 ophalen ---");
            ovdao.findById(90001);

            // Verwijderen
            System.out.println("\n--- Reiziger verwijderen ---");
            rdao.delete(ophalen);
            System.out.println("Bestaat reiziger nog? " + rdao.findById(999));

            pdao.delete(p1);
            pdao.delete(p2);

            // alle producten ophalen
            System.out.println("\n--- alle producten ---");
            for (Product p : pdao.findAll()) {
                System.out.println(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

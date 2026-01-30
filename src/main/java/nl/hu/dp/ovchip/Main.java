package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.dao.*;
import nl.hu.dp.ovchip.domain.Adres;
import nl.hu.dp.ovchip.domain.OVChipkaart;
import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        try(Connection myConn = DatabaseConnection.getConnection()){
            AdresDAOPsql adao = new AdresDAOPsql(myConn);
            OVChipkaartDAOPsql ovdao = new OVChipkaartDAOPsql(myConn);
            ReizigerDAOPsql rdao = new ReizigerDAOPsql(myConn);

            rdao.setAdresDAO(adao);
            rdao.setOvChipkaartDAO(ovdao);

            //Test P4
            System.out.println("\n\nTest 4:");

            testP4(rdao, ovdao);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void testP4(ReizigerDAO rdao, OVChipkaartDAO ovdao) {
        //save
        System.out.println("\nSave reiziger met ovchipkaarten: ");
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
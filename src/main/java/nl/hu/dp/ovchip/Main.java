package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.dao.*;
import nl.hu.dp.ovchip.domain.Adres;
import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        AdresDAOHibernate adao = new AdresDAOHibernate();
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate();

        testP3H(rdao, adao);
    }

    private static void testP3H(ReizigerDAO rdao, AdresDAO adao) throws SQLException {
        System.out.println("P3H: \n");

        System.out.println("Test save adres + Reiziger: \n");
        Reiziger valentijn = new Reiziger(67, "V", null, "Tollenaar", LocalDate.of(2003, 9, 26));
        Adres adres = new Adres(67, "3704AZ", "8", "heidelberglaan", "Utrecht", valentijn);

        adres.setReiziger(valentijn);
        valentijn.setAdres(adres);

        rdao.save(valentijn);

        System.out.println("Nieuwe reiziger in database: ");
        System.out.println(rdao.findById(67));
    }
}
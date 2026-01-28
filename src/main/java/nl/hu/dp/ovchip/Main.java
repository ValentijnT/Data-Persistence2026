package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.dao.*;
import nl.hu.dp.ovchip.domain.Adres;
import nl.hu.dp.ovchip.domain.Reiziger;
import nl.hu.dp.ovchip.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AdresDAOHibernate adao = new AdresDAOHibernate();
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate();

        testP3H(rdao, adao);
    }

    private static void testP3H(ReizigerDAO rdao, AdresDAO adao) throws SQLException {

    }
}
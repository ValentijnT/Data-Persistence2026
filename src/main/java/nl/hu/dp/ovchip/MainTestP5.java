package nl.hu.dp.ovchip;

import nl.hu.dp.ovchip.dao.AdresDAOPsql;
import nl.hu.dp.ovchip.dao.OVChipkaartDAOPsql;
import nl.hu.dp.ovchip.dao.ProductDAOPsql;
import nl.hu.dp.ovchip.dao.ReizigerDAOPsql;
import nl.hu.dp.ovchip.domain.OVChipkaart;
import nl.hu.dp.ovchip.domain.Product;
import nl.hu.dp.ovchip.domain.Reiziger;

import java.sql.*;
import java.time.LocalDate;

import java.util.List;

public class MainTestP5 {

    private static Connection connection;
    private static String databaseName = "ovchip"; // Hier moet de naam van de database komen.
    private static String userName = "postgres"; // Hier moet de user name ingevuld worden.
    private static String password = "geheim"; // Hier moet het wachtwoord ingevuld worden.

    private static ReizigerDAOPsql rdao;
    private static AdresDAOPsql adao;
    private static OVChipkaartDAOPsql ovdao ;
    private static ProductDAOPsql pdao;

    public static void main(String[] args) {

        try {
            System.out.println("Start test connectie met database.");
            System.out.println("Start ophalen alle reizigers als test voor connectie.");

            testConnection();

            // Alle DAO-implementatie-objecten worden aangemaakt.
            rdao = new ReizigerDAOPsql(connection);
            adao = new AdresDAOPsql(connection);
            ovdao = new OVChipkaartDAOPsql(connection);
            pdao = new ProductDAOPsql(connection);

            // Alle DAO-implementatie-objecten worden aan elkaar geassocieerd.
            rdao.setAdresDAO(adao);
            rdao.setOvChipkaartDAO(ovdao);
            adao.setReizigerDAO(rdao);
            ovdao.setReizigerDAO(rdao);
            ovdao.setProductDAO(pdao);
            pdao.setOVChipkaartDAO(ovdao);

            System.out.println("De connectie met de database werkt goed!");
            System.out.println("Alle reizigers worden als test goed opgehaald.");

        } catch (Exception e) {
            System.out.println("Er is iets verkeerd gegaan met de connectie: " + e.getMessage());
        }

        System.out.println("Einde test connectie met database.");

        System.out.println();

        try {
            System.out.println("Start test 1 Product wordt opgehaald uit de database met behulp van een OVChipkaart.");

            if (testNeemProductMetBehulpVanOVChipkaartUitDatabase()) {
                System.out.println("Test 1 Product wordt opgehaald uit de database is wel gelukt.");
            } else {
                System.out.println("Test 1 Product wordt opgehaald uit de database is NIET gelukt.");
            }


        } catch (Exception e) {
            System.out.println("Er is iets verkeerd gegaan met de connectie bij het halen van 1 Product: " + e.getMessage());
        }

        System.out.println("Einde test 1 OVChipkaart-object wordt opgehaald uit de database.");

        System.out.println();

        try {
            System.out.println("Start test alle Producten worden opgehaald uit de database.");

            if (testHaalAlleProducten()) {
                System.out.println("Test 1 alle Producten worden opgehaald uit de database is wel gelukt.");
            } else {
                System.out.println("Test 1 alle Producten worden opgehaald uit de database is NIET gelukt.");
            }

        } catch (Exception e) {
            System.out.println("Er is iets verkeerd gegaan met de connectie bij het ophalen van alle Producten: " + e.getMessage());
        }

        System.out.println("Einde test alle Producten worden opgehaald uit de database.");

        System.out.println();

        try {
            System.out.println("Start test 1 Product wordt opgeslagen in de database.");

            testSla1ProductOpInDatabase();

            System.out.println("Test 1 Product wordt opgeslagen in de database is gelukt.");

        } catch (Exception e) {
            System.out.println("Er is iets verkeerd gegaan met de connectie bij het opslaan van 1 Product: " + e.getMessage());
        }

        System.out.println("Einde test Product wordt opgeslagen in de database.");

        System.out.println();

        try {
            System.out.println("Start test 1 Product wordt geupdatet in de database.");

            testUpdate1ProductInDatabase();

            System.out.println("Test 1 Product wordt geupdatet in de database is gelukt.");

        } catch (Exception e) {
            System.out.println("Er is iets verkeerd gegaan met de connectie bij het updaten van 1 Product: " + e.getMessage());
        }

        System.out.println("Einde test 1 Product wordt geupdatet in de database.");

        System.out.println();

        try {
            System.out.println("Start test 1 Product wordt verwijderd in de database.");

            testVerwijder1ProductUitDatabase();

            System.out.println("Test 1 Product wordt verwijderd in de database is gelukt.");

        } catch (Exception e) {
            System.out.println("Er is iets verkeerd gegaan met de connectie bij het verwijderen van 1 Product: " + e.getMessage());
        } /*catch (NullPointerException e) {
            System.out.println("Er is iets verkeerd gegaan met de connectie bij het verwijderen van 1 Product: " + e.getMessage());
        }*/

        System.out.println("Einde test 1 Product wordt verwijderd in de database.");

        System.out.println();

        try {
            System.out.println("Start test 1 Reiziger-object met meer OVChipkaarten en Producten wordt opgehaald uit de database.");

            if (testNeem1ReizigerMetMeerOVChipkaartenEnProductenUitDatabase()) {
                System.out.println("Test 1 Reiziger-object met meer OVChipkaarten en Producten wordt opgehaald is wel gelukt.");
            } else {
                System.out.println("Test 1 Reiziger-object met meer OVChipkaarten en Producten wordt opgehaald is NIET gelukt.");
            }

        } catch (Exception e) {
            System.out.println("Er is iets verkeerd gegaan met de connectie bij het ophalen van 1 Reiziger-object met meer OVChipkaarten: " + e.getMessage());
        } /*catch (NullPointerException e) {
            System.out.println("Er is iets verkeerd gegaan met de connectie bij het opslaan van 1 Reiziger-object met meer OVChipkaarten: " + e.getMessage());
        }*/

        System.out.println("Einde test 1 Reiziger-object met meer OVChipkaarten wordt opgehaald uit de database.");

        System.out.println();

        try {
            System.out.println("Start test 1 Reiziger-object met meer OVChipkaarten en meer Producten wordt opgeslagen in de database.");

            testSla1ReizigerMetMeerOVChipkaartenEnMeerProductenOpInDatabase();;

            System.out.println("Test 1 Reiziger-object met meer OVChipkaarten en meer Producten wordt opgeslagen in de database is gelukt.");

        } catch (Exception e) {
            System.out.println("Er is iets verkeerd gegaan met de connectie bij het opslaan van 1 Reiziger-object met meer OVChipkaarten en meer Producten: " + e.getMessage());
        }

        System.out.println("Einde test 1 Reiziger-object met meer OVChipkaarten en meer Producten wordt opgeslagen in de database.");

        System.out.println();

        try {
            System.out.println("Start test 1 Reiziger-object met meer OVChipkaarten wordt geupdatet in de database.");

            testUpdate1ReizigerMetMeerOVChipkaartenInDatabase();

            System.out.println("Test 1 Reiziger-object met meer OVChipkaarten wordt geupdatet in de database is gelukt.");

        } catch (Exception e) {
            System.out.println("Er is iets verkeerd gegaan met de connectie bij het updaten van 1 Reiziger-object met meer OVChipkaarten: " + e.getMessage());
        }

        System.out.println("Einde test 1 Reiziger-object met meer OVChipkaarten wordt geupdatet in de database.");

        System.out.println();

        try {
            System.out.println("Start test 1 Reiziger-object met meer OVChipkaarten wordt verwijderd in de database.");

            testVerwijderReizigerMetMeerOVChipkaartUitDatabase();

            System.out.println("Test 1 Reiziger-object met meer OVChipkaarten wordt verwijderd in de database is gelukt.");

        } catch (Exception e) {
            System.out.println("Er is iets verkeerd gegaan met de connectie bij het verwijderen van 1 Reiziger-object met meer OVChipkaarten: " + e.getMessage());
        }

        System.out.println("Einde test 1 Reiziger-object met meer OVChipkaarten wordt verwijderd in de database.");

        System.out.println();
    }

    /**
     * Connectie tussen Java en de database met JDBC
     * <p>
     * Deze methode test de connectie door middel van een SELECT op alle reizigers.
     *
     * @throws SQLException
     */
    private static void testConnection() throws SQLException {
        System.out.println("\n---------- Test JDBC --------------------");

        Connection conn = getConnection();

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM reiziger;");
        while (rs.next()) {
            int id = rs.getInt("reiziger_id");
            String voorl = rs.getString("voorletters");
            String tv = rs.getString("tussenvoegsel");
            String anaam = rs.getString("achternaam");
            Date geboortedatum = rs.getDate("geboortedatum");

            String naam = voorl + ". " + (tv != null ? tv + " " : "") + anaam;
            System.out.println("#" + id + ": " + naam + " (" + geboortedatum.toString() + ")");
        }
        rs.close();
        st.close();
    }

    private static boolean testNeemProductMetBehulpVanOVChipkaartUitDatabase() throws SQLException {
        // We maken een tempReiziger met ID = 4 aan, omdat deze 1 OVChipkaart bezit.
        Reiziger tempReiziger = new Reiziger(4, null, null, null, null);
        List<OVChipkaart> ovChipkaarten = ovdao.findByReiziger(tempReiziger);
        OVChipkaart ovChipkaart = ovChipkaarten.get(0);
        // Vervolgens wordt deze OVChipkaart gebruikt om een Product op te halen.
        List<Product> producten = pdao.findByOVChipkaart(ovChipkaart);
        Product product = producten.get(0);
        if (product.getProduct_nummer() == 6) { // Dit is het nummer van de OVChipkaart.
            // Het ophalen gaat goed.
            return true;
        } else {
            // Het ophalen gaat niet goed.
            return false;
        }
    }

    private static boolean testHaalAlleProducten() throws SQLException {
        List<Product> producten = pdao.findAll();

        if (producten.size() == 7) {
            // Alle Producten worden opgehaald.
            return true;
        } else {
            return false;
        }
    }

    private static void testSla1ProductOpInDatabase() throws SQLException {
        Product product = new Product(10, "Testproduct", "Productinvoer voor test opslaan en updaten", 100);
        pdao.save(product);
        Product product2 = new Product(11, "Testproduct 2", "Productinvoer voor test opslaan en verwijderen", 100);
        pdao.save(product2);
    }

    private static void testUpdate1ProductInDatabase() throws SQLException {
        Product product = new Product(10, "Testproduct", "Productinvoer voor test", 100);
        product.setPrijs(10);
        product.setNaam("Testproduct na update");

        pdao.update(product);


    }

    private static void testVerwijder1ProductUitDatabase() throws SQLException {
        Product product = new Product(11, "Testproduct 2", "Productinvoer voor test opslaan en verwijderen", 100);
        pdao.delete(product);
    }

    private static boolean testNeem1ReizigerMetMeerOVChipkaartenEnProductenUitDatabase() throws SQLException {
        Reiziger reiziger = rdao.findById(5);
        if (reiziger.getOVchipkaarten().size() > 1) {
            // Meerdere OVChipkaarten kunnen opgehaald worden.
            for (int i = 0; i < reiziger.getOVchipkaarten().size(); i++) {
                if (reiziger.getOVchipkaarten().get(i).getKaartNummer() == 90537) {
                    if (reiziger.getOVchipkaarten().get(i).getProducten().size() == 2) {
                        return true; // Hiermee is bewezen dat 2 producten worden opgehaald.
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private static void testSla1ReizigerMetMeerOVChipkaartenEnMeerProductenOpInDatabase() throws SQLException {
        Reiziger eigenaar = new Reiziger(7, "P", null, "Franken",
                null);
        // 2 OVChipkaarten worden gemaakt.
        OVChipkaart ovChipkaart1 = new OVChipkaart(90000, LocalDate.of(2023, 5, 4), 1, 10, eigenaar);
        OVChipkaart ovChipkaart2 = new OVChipkaart(100000, LocalDate.of(2023, 4, 7), 2, 100, eigenaar);

        eigenaar.addOVChipkaart(ovChipkaart1);
        eigenaar.addOVChipkaart(ovChipkaart2);
        ovChipkaart1.addProduct(new Product(11, "Appel", "Rode lekkere appels", 5));
        ovChipkaart1.addProduct(new Product(12, "Peer", "Groene lekkere peren", 6));
        ovChipkaart2.addProduct(new Product(13, "Mandarijn", "Goede mandarijn", 4));
        ovChipkaart2.addProduct(new Product(13, "Sinaasappel", "Grote sinaasappel", 7));

        rdao.save(eigenaar);

    }

    private static void testUpdate1ReizigerMetMeerOVChipkaartenInDatabase() throws SQLException {
        Reiziger reiziger = rdao.findById(5); // Deze reiziger heeft 2 OVChipkaarten.
        // Reiziger en de 2 OVChipkaarten worden geupdatet.
        reiziger.setAchternaam("Pietersen");
        reiziger.getOVchipkaarten().get(0).setSaldo(100);
        reiziger.getOVchipkaarten().get(1).setSaldo(200);
        rdao.update(reiziger);
    }

    private static void testVerwijderReizigerMetMeerOVChipkaartUitDatabase() throws SQLException {
        // Hier wordt Reiziger met ID = 5 genomen, omdat deze Reiziger 2 OVChipkaarten heeft.
        // Deze 2 OVChipkaarten moeten beide verwijderd worden.
        Reiziger reiziger = rdao.findById(5);
        rdao.delete(reiziger);
    }


    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            String url = "jdbc:postgresql://localhost/" +
                    databaseName +
                    "?user=" + userName +
                    "&password=" + password; // &ssl=true";
            connection = DriverManager.getConnection(url);
        }

        return connection;
    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}

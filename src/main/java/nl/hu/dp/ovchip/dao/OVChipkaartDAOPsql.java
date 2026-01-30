package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domain.OVChipkaart;
import nl.hu.dp.ovchip.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{
    private Connection conn;
    private ReizigerDAO reizigerDAO;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public void setReizigerDAO(ReizigerDAO reizigerDAO) {this.reizigerDAO = reizigerDAO;}

    @Override
    public boolean save(OVChipkaart ov) {
        String sql = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, ov.getKaartNummer());
            pst.setDate(2, java.sql.Date.valueOf(ov.getGeldigTot()));
            pst.setInt(3, ov.getKlasse());
            pst.setDouble(4, ov.getSaldo());
            pst.setInt(5, ov.getReiziger().getReiziger_id());

            return pst.executeUpdate() > 0;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        String sql = "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?";
        try(PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setDate(1, java.sql.Date.valueOf(ovChipkaart.getGeldigTot()));
            pst.setInt(2, ovChipkaart.getKlasse());
            pst.setDouble(3, ovChipkaart.getSaldo());
            pst.setInt(4, ovChipkaart.getReiziger().getReiziger_id());
            pst.setInt(5, ovChipkaart.getKaartNummer());

            return pst.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        String sql = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
        try(PreparedStatement pst = conn.prepareStatement(sql)){
            pst .setInt(1, ovChipkaart.getKaartNummer());

            return pst.executeUpdate() > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger r) {
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();

        String sql = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
        try(PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setInt(1, r.getReiziger_id());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                OVChipkaart ovChipkaart = new OVChipkaart(
                        rs.getInt("kaart_nummer"),
                        rs.getDate("geldig_tot").toLocalDate(),
                        rs.getInt("klasse"),
                        rs.getDouble("saldo"),
                        r
                );
                ovChipkaarten.add(ovChipkaart);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return  ovChipkaarten;
    }

    @Override
    public List<OVChipkaart> findAll() {
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        String sql = "SELECT * FROM ov_chipkaart";

        try(PreparedStatement pst = conn.prepareStatement(sql)){
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                OVChipkaart ov = new OVChipkaart(
                        rs.getInt("kaart_nummer"),
                        rs.getDate("geldig_tot").toLocalDate(),
                        rs.getInt("klasse"),
                        rs.getDouble("saldo"),
                        null
                );
                ovChipkaarten.add(ov);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ovChipkaarten;
    }
}

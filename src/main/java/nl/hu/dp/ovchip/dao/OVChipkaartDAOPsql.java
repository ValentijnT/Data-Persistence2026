package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domain.OVChipkaart;
import nl.hu.dp.ovchip.domain.Product;
import nl.hu.dp.ovchip.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{
    private Connection conn;
    private ReizigerDAO rdao;
    private ProductDAOPsql pdao;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public void setReizigerDAO(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    public void setProductDAO(ProductDAOPsql pdao) {
        this.pdao = pdao;
    }

    @Override
    public boolean save(OVChipkaart ov) {
        String sql = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)";
        String linkInsert = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?, ?)";
        try (
                PreparedStatement pst = conn.prepareStatement(sql);
                PreparedStatement pstLinkInsert = conn.prepareStatement(linkInsert)
        ) {
            pst.setInt(1, ov.getKaartNummer());
            pst.setDate(2, java.sql.Date.valueOf(ov.getGeldigTot()));
            pst.setInt(3, ov.getKlasse());
            pst.setDouble(4, ov.getSaldo());
            pst.setInt(5, ov.getReiziger().getReiziger_id());
            pst.executeUpdate();

            //save link
            for(Product p : ov.getProducten()){
                pstLinkInsert.setInt(1, ov.getKaartNummer());
                pstLinkInsert.setInt(2, p.getProduct_nummer());
                pstLinkInsert.executeUpdate();
            }

            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        String sql = "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?";
        String linkDelete = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?";
        String linkInsert = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?, ?)";
        try(
                PreparedStatement pst = conn.prepareStatement(sql);
                PreparedStatement pstLinkDelete = conn.prepareStatement(linkDelete);
                PreparedStatement pstLinkInsert = conn.prepareStatement(linkInsert)
        ){
            pst.setDate(1, java.sql.Date.valueOf(ovChipkaart.getGeldigTot()));
            pst.setInt(2, ovChipkaart.getKlasse());
            pst.setDouble(3, ovChipkaart.getSaldo());
            pst.setInt(4, ovChipkaart.getReiziger().getReiziger_id());
            pst.setInt(5, ovChipkaart.getKaartNummer());
            pst.executeUpdate();

            //Delete link
            pstLinkDelete.setInt(1, ovChipkaart.getKaartNummer());
            pstLinkDelete.executeUpdate();

            for (Product p : ovChipkaart.getProducten()) {
                pstLinkInsert.setInt(1, ovChipkaart.getKaartNummer());
                pstLinkInsert.setInt(2, p.getProduct_nummer());
                pstLinkInsert.executeUpdate();
            }

            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        String linkDelete = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?";
        String sql = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
        try(
                PreparedStatement pstLinkDelete = conn.prepareStatement(linkDelete);
                PreparedStatement pst = conn.prepareStatement(sql)
        ){
            //delete links
            pstLinkDelete.setInt(1, ovChipkaart.getKaartNummer());
            pstLinkDelete.executeUpdate();

            //delete ovChipkaart
            pst .setInt(1, ovChipkaart.getKaartNummer());
            pst.executeUpdate();

            return true;
        }catch (SQLException e){
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
                OVChipkaart ov = new OVChipkaart(
                        rs.getInt("kaart_nummer"),
                        rs.getDate("geldig_tot").toLocalDate(),
                        rs.getInt("klasse"),
                        rs.getDouble("saldo"),
                        r
                );
                if(pdao != null) {
                    ov.setProducten(pdao.findByOVChipkaart(ov));
                }
                ovChipkaarten.add(ov);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  ovChipkaarten;
    }

    @Override
    public OVChipkaart findById(int id) {
        String sql = "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?";
        try(PreparedStatement pst = conn.prepareStatement(sql)){
            pst.setInt(1, id);

            ResultSet rs =  pst.executeQuery();

            if(rs.next()) {
                OVChipkaart ov = new OVChipkaart(
                        rs.getInt("kaart_nummer"),
                        rs.getDate("geldig_tot").toLocalDate(),
                        rs.getInt("klasse"),
                        rs.getDouble("saldo"),
                        null
                );

                if(pdao != null){
                    ov.setProducten(pdao.findByOVChipkaart(ov));
                }

                return ov;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
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

                if (pdao != null) {
                    ov.setProducten(pdao.findByOVChipkaart(ov));
                }

                ovChipkaarten.add(ov);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ovChipkaarten;
    }
}

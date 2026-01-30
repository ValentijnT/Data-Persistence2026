package nl.hu.dp.ovchip.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {

    @Id
    @Column(name = "kaart_nummer")
    private int kaart_nummer;

    @Column(name = "geldig_tot")
    private LocalDate geldig_tot;

    @Column(name = "klasse")
    private int klasse;

    @Column(name = "saldo")
    private double saldo;

    @ManyToOne
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;

    @ManyToMany
    @JoinTable(name = "ov_chipkaart_product",
                joinColumns = @JoinColumn(name = "kaart_nummer"),
                inverseJoinColumns = @JoinColumn(name = "product_nummer")
    )
    private List<Product> producten = new ArrayList<>();

    public OVChipkaart() {}

    public OVChipkaart(int kaart_nummer, LocalDate geldig_tot, int klasse, double saldo, Reiziger reiziger) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }


    public int getKaartNummer() { return kaart_nummer; }
    public LocalDate getGeldigTot() { return geldig_tot; }
    public int getKlasse() { return klasse; }
    public double getSaldo() { return saldo; }
    public Reiziger getReiziger() { return reiziger; }
    public List<Product> getProducten() { return producten; }

    public void setKaartNummer(int kaartNummer) { this.kaart_nummer = kaartNummer; }
    public void setGeldigTot(LocalDate geldigTot) { this.geldig_tot = geldigTot; }
    public void setKlasse(int klasse) { this.klasse = klasse; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public void setReiziger(Reiziger reiziger) { this.reiziger = reiziger; }
    public void setProducten(List<Product> producten) { this.producten = producten; }

    public void addProduct(Product product) {
        if(!producten.contains(product)){
            producten.add(product);
        }
        if (!product.getOvchipkaarten().contains(this)) {
            product.getOvchipkaarten().add(this);
        }
    }

    public void removeProduct(Product product) {
        producten.remove(product);
        product.removeOVChipkaart(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("•OV_Chipkaart {#").append(kaart_nummer)
                .append(", geldig tot: ").append(geldig_tot)
                .append(", klasse: ").append(klasse)
                .append(", saldo: ").append(saldo)
                .append("}");

        if (producten != null && !producten.isEmpty()) {
            sb.append("\n  Producten: ");
            for (Product p : producten) {
                sb.append("\n   -");
                sb.append(p);
            }
        } else {
            sb.append("\n Producten: Geen");
        }

        return sb.toString();
    }
}

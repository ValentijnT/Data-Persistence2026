package nl.hu.dp.ovchip.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reiziger")
public class Reiziger {
    
    @Id
    @Column(name = "reiziger_id")
    private int reiziger_id;

    @Column(name = "voorletters")
    private String voorletters;

    @Column(name = "tussenvoegsel")
    private String tussenvoegsel;

    @Column(name = "achternaam")
    private String achternaam;

    @Column(name = "geboortedatum")
    private LocalDate geboortedatum;

    @OneToOne(mappedBy = "reiziger", cascade = CascadeType.ALL)
    private Adres adres;

    private List<OVChipkaart> ov_chipkaarten = new ArrayList<>();
    
    public Reiziger() {}

    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, LocalDate geboortedatum) {
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getReiziger_id() { return reiziger_id; }
    public String getVoorletters() { return voorletters; }
    public String getTussenvoegsel() { return tussenvoegsel; }
    public String getAchternaam() { return achternaam; }
    public LocalDate getGeboortedatum() { return geboortedatum; }
    public Adres getAdres() { return adres; }
    public List<OVChipkaart> getOV_chipkaarten() {return ov_chipkaarten;}

    public void setReiziger_id(int reiziger_id) { this.reiziger_id = reiziger_id; }
    public void setVoorletters(String voorletters) { this.voorletters = voorletters; }
    public void setTussenvoegsel(String tussenvoegsel) { this.tussenvoegsel = tussenvoegsel; }
    public void setAchternaam(String achternaam) { this.achternaam = achternaam; }
    public void setGeboortedatum(LocalDate geboortedatum) { this.geboortedatum = geboortedatum; }
    public void setAdres(Adres adres) { this.adres = adres; }

    public void addOVChipkaart(OVChipkaart ov_chipkaart) {
        ov_chipkaarten.add(ov_chipkaart);
        ov_chipkaart.setReiziger(this);
    }

    public void removeOVChipkaart(OVChipkaart ov_chipkaart) {
        ov_chipkaarten.remove(ov_chipkaart);
        ov_chipkaart.setReiziger(null);
    }

    public String getNaam(){
        String tv = (tussenvoegsel != null) ? tussenvoegsel + " " : "";
        return voorletters + ". " + tv + achternaam;
    }

    public String toString() {
        String adresString = (adres != null) ? ", Adres: " + adres : "";

        String ovString = "";
        if(ov_chipkaarten != null && !ov_chipkaarten.isEmpty()) {
            ovString = "OVChipkaarten: \n";
            for (OVChipkaart ov : ov_chipkaarten) {
                ovString += ov.toString() + ".\n";
            }
            ovString = ovString.substring(0, ovString.length() - 2) + ".";
        } else {
            ovString = ", OVChipkaarten: []";
        }

        return  "Reiziger {#" + reiziger_id + ": " + getNaam() + " (" + geboortedatum + ")" + adresString + "}\n" + ovString;
    }

}

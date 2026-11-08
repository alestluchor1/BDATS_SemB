package javafx.bdats_sema_tluchor.obyvatele;

public class Obec {

    private String PSC;
    private String nazev;
    private int pocetMuz;
    private int pocetZen;

    public Obec(String PSC, String nazev, int pocetMuz, int pocetZen) {
        this.PSC = PSC;
        this.nazev = nazev;
        this.pocetMuz = pocetMuz;
        this.pocetZen = pocetZen;
    }

    // Gettery
    public String getPSC() {
        return PSC;
    }

    public String getNazev() {
        return nazev;
    }

    public int getPocetMuz() {
        return pocetMuz;
    }

    public int getPocetZen() {
        return pocetZen;
    }

    public int getCelkemObyvatel() {
        return pocetMuz + pocetZen;
    }

    @Override
    public String toString() {
        return "Obec" + "{PSC=" + PSC
                + ", Název=" + nazev
                + ", Počet mužů=" + pocetMuz
                + ", Počet žen=" + pocetZen
                + ", Celkem obyvatel=" + getCelkemObyvatel()
                + ", "
                + "}";
    }

}

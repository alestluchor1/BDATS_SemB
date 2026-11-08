package javafx.bdats_sema_tluchor.obyvatele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import javafx.bdats_sema_tluchor.abstrdoublelist.AbstrDoubleList;
import javafx.bdats_sema_tluchor.abstrdoublelist.AbstrDoubleListException;
import static javafx.bdats_sema_tluchor.obyvatele.enumPozice.NASLEDNIK;
import static javafx.bdats_sema_tluchor.obyvatele.enumPozice.POSLEDNI;
import static javafx.bdats_sema_tluchor.obyvatele.enumPozice.PREDCHUDCE;
import static javafx.bdats_sema_tluchor.obyvatele.enumPozice.PRVNI;

public class Obyvatele {

    private final AbstrDoubleList<Obec>[] kraje;

    public Obyvatele() {
        kraje = new AbstrDoubleList[enumKraj.values().length];
        for (int i = 0; i < kraje.length; i++) {
            kraje[i] = new AbstrDoubleList<>();
        }
    }

    private float getPrumerKraje(AbstrDoubleList<Obec> obce) {
        Iterator<Obec> it = obce.iterator();
        int pocetLidi = 0;
        while (it.hasNext()) {
            Obec obec = it.next();
            pocetLidi += obec.getCelkemObyvatel();
        }
        if (obce.size() == 0) {
            return 0;
        }
        return (float) pocetLidi / obce.size();
    }

    public AbstrDoubleList<Obec> zobrazObceNadPrumer(enumKraj kraj) {
        AbstrDoubleList<Obec> obce = ziskatObcePodleKraje(kraj);

        Iterator<Obec> it = obce.iterator();
        float prumer = getPrumerKraje(obce);
        AbstrDoubleList<Obec> obceNadPrumer = new AbstrDoubleList<>();
        while (it.hasNext()) {
            Obec obec = it.next();
            if (obec.getCelkemObyvatel() > prumer) {
                obceNadPrumer.vlozPrvni(obec);
            }
        }
        return obceNadPrumer;
    }

    public int importData(String soubor) {
        int pocetZaznamu = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(soubor))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 7) {
                    try {
                        int indexKraje = Integer.parseInt(parts[0]) - 1;
                        String PSC = parts[2];
                        String nazev = parts[3];
                        int pocetMuz = Integer.parseInt(parts[4]);
                        int pocetZen = Integer.parseInt(parts[5]);

                        Obec obec = new Obec(PSC, nazev, pocetMuz, pocetZen);

                        if (indexKraje >= 0 && indexKraje < kraje.length) {
                            kraje[indexKraje].vlozPosledni(obec);
                            pocetZaznamu++;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Chyba při převodu čísla: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
        }
        return pocetZaznamu;
    }

    public AbstrDoubleList<Obec> zobrazObce(enumKraj kraj) {
        return kraje[kraj.ordinal()];
    }

    public void vlozObec(Obec obec, enumPozice pozice, enumKraj kraj) throws AbstrDoubleListException {

        AbstrDoubleList<Obec> abstrDoubleList = kraje[kraj.ordinal()];

        switch (pozice) {
            case PRVNI ->
                abstrDoubleList.vlozPrvni(obec);
            case POSLEDNI ->
                abstrDoubleList.vlozPosledni(obec);
            case NASLEDNIK ->
                abstrDoubleList.vlozNaslednika(obec);
            case PREDCHUDCE ->
                abstrDoubleList.vlozPredchudce(obec);
            default ->
                throw new AbstrDoubleListException("Neplatná pozice.");
        }
    }

    public float zjistiPrumer(enumKraj kraj) {
        int celkemObyvatel = 0;
        int pocetObci = 0;

        if (kraj == null) {
            // Pokud je kraj null, počítáme průměr pro všechny kraje
            for (enumKraj k : enumKraj.values()) {
                AbstrDoubleList<Obec> obce = zobrazObce(k);
                Iterator<Obec> it = obce.iterator();
                while (it.hasNext()) {
                    celkemObyvatel += it.next().getCelkemObyvatel();
                }
                pocetObci += obce.size();
            }

            if (pocetObci > 0) {
                return (float) celkemObyvatel / pocetObci;
            } else {
                return 0;
            }
        } else {
            // Výpočet průměru pouze pro zadaný kraj
            return getPrumerKraje(zobrazObce(kraj));
        }
    }

    public void zrus(enumKraj kraj) {
        if (kraj == null) {

            for (int i = 0; i < kraje.length; i++) {
                kraje[i] = new AbstrDoubleList<>();
            }

        }
    }

    public Obec zpristupniObec(enumPozice pozice, enumKraj kraj) throws AbstrDoubleListException {
        int indexKraje = kraj.ordinal();
        if (indexKraje < 0 || indexKraje >= kraje.length) {
            return null;
        }
        AbstrDoubleList<Obec> obceList = kraje[indexKraje];

        switch (pozice) {
            case PRVNI -> {
                return obceList.zpristupniPrvni();
            }
            case POSLEDNI -> {
                return obceList.zpristupniPosledni();
            }
            case PREDCHUDCE -> {
                return obceList.zpristupniPredchudce();
            }
            case NASLEDNIK -> {
                return obceList.zpristupniNaslednika();
            }
            default -> {
                System.out.println("Neznámá pozice: " + pozice);
                return null;
            }
        }
    }

    public AbstrDoubleList<Obec> ziskatObcePodleKraje(enumKraj kraj) {

        AbstrDoubleList<Obec> seznamObci = new AbstrDoubleList<>();

        if (kraj != null) {
            // Přidejte obce pro konkrétní kraj
            AbstrDoubleList<Obec> obce = kraje[kraj.ordinal()];
            Iterator<Obec> iterator = obce.iterator();
            while (iterator.hasNext()) {
                seznamObci.vlozPosledni(iterator.next());
            }

        }

        return seznamObci;
    }

    public Obec odeberObec(enumPozice pozice, enumKraj kraj) throws AbstrDoubleListException {
        AbstrDoubleList<Obec> abstrDoubleList = zobrazObce(kraj);
        Obec odebrano = null;
        switch (pozice) {
            case PRVNI ->
                odebrano = abstrDoubleList.odeberPrvni();
            case AKTUALNI ->
                odebrano = abstrDoubleList.odeberAktualni();
            case NASLEDNIK ->
                odebrano = abstrDoubleList.odeberNaslednika();
            case PREDCHUDCE ->
                odebrano = abstrDoubleList.odeberPredchudce();
            case POSLEDNI ->
                odebrano = abstrDoubleList.odeberPosledni();
        }
        return odebrano;
    }
}

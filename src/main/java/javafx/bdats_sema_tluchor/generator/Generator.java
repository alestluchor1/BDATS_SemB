package javafx.bdats_sema_tluchor.generator;

import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import java.util.Random;
import javafx.bdats_sema_tluchor.obyvatele.Obec;
import javafx.bdats_sema_tluchor.obyvatele.enumKraj;

public class Generator {

    private final Random random = new Random();

    // Obce a jejich PSČ
    private static final String[] OBCE = {
        "Praha", "Ceske Budejovice", "Brno", "Karlovy Vary", "Jihlava", "Hradec Kralove",
        "Liberec", "Ostrava", "Olomouc", "Pardubice", "Plzen", "Melnik", "Usti nad Labem", "Zlin"
    };

    private static final String[] PSC = {
        "11538", "37421", "60200", "36001", "58601", "50003", "46001", "70200", "77900", "53002", "30100", "27601", "40001", "76001"
    };

    public Obec generateData(ListView<Obec> listView, Spinner<Integer> spPocetGenerovani, enumKraj vybranyKraj) {

        String obec = OBCE[random.nextInt(OBCE.length)];
        String psc = PSC[random.nextInt(PSC.length)];

        int pocetMuzu = random.nextInt(10000) + 5000;
        int pocetZen = random.nextInt(10000) + 5000;

        Obec novaObec = new Obec(psc, obec, pocetMuzu, pocetZen);

        listView.getItems().add(novaObec);

        return novaObec;
    }

}

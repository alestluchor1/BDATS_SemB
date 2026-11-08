package javafx.bdats_sema_tluchor.GUI;

import javafx.application.Application;
import javafx.bdats_sema_tluchor.obyvatele.AgendaKraj;
import javafx.bdats_sema_tluchor.obyvatele.Obec;
import javafx.bdats_sema_tluchor.obyvatele.enumKraj;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.bdats_sema_tluchor.generator.Generator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ProgAgendaKraj extends Application {

    private static final int SIRKA_OKNO = 800;
    private static final int VYSKA_OKNO = 500;
    private final Generator generator = new Generator();

    // Binární strom pro správu obcí
    private AgendaKraj<String, Obec> agendaKraj;

    // Tlačítka
    private static final Button btnNajdi = new Button("Najdi");
    private static final Button btnVloz = new Button("Vlož");
    private static final Button btnOdeber = new Button("Odeber");
    private static final Button btnNactiZKraje = new Button("Načti z (kraje)");
    private static final Button btnDoHloubky = new Button("Iterace do hloubky");
    private static final Button btnDoSirky = new Button("Iterace do šířky");
    private static final Button btnGenerovat = new Button("Generuj");
    private static final Button btnUloz = new Button("Ulož");
    private static final Button btnVybuduj = new Button("Vybuduj");
    private static final Button btnZrus = new Button("Zruš");
    private static final Button btnNacti = new Button("Načti");

    //Spinner
    private final Spinner<Integer> spPocetGenerovani = new Spinner<>(1, 100, 1);

    //ObservableList
    private final ObservableList<Obec> olObce = FXCollections.observableArrayList();

    // ListView pro zobrazení obcí
    private final ListView<Obec> listViewObce = new ListView<>();

    // TextField pro zadání nové obce
    private final TextField txtNovaObec = new TextField();

    // ComboBox pro výběr kraje
    private final ComboBox<enumKraj> comboKraj = new ComboBox<>();

    @Override
    public void start(Stage stage) {

        agendaKraj = new AgendaKraj<>();

        listViewObce.setItems(olObce);

        // Hlavní nadpis aplikace
        Label titleLabel = new Label("Správa obcí");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setAlignment(Pos.CENTER);

        // Nastavení ComboBox pro výběr kraje
        comboKraj.getItems().setAll(enumKraj.values());
        comboKraj.setPromptText("Vyberte kraj");

        // Nastavení TextField
        txtNovaObec.setPromptText("Zadejte název obce...");

        // Akce pro načítání obcí z CSV souboru
        btnNacti.setOnAction(e -> {
            try {
                importData(stage);
            } catch (Exception ex) {
                Logger.getLogger(ProgAgendaKraj.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Akce pro vložení nové obce
        btnVloz.setOnAction(e -> {
            vlozObec();
        });

        // Akce pro vyhledání obce
        btnNajdi.setOnAction(e -> {
            najdiObec();
        });

        // Akce pro smazání obce podle klíče
        btnOdeber.setOnAction(e -> {
            odeberObec();
        });

        // Akce pro iteraci do hloubky
        btnDoHloubky.setOnAction(e -> {
            iterujDoHloubky();
        });

        // Akce pro iteraci do šířky
        btnDoSirky.setOnAction(e -> {
            iterujDoSirky();
        });

        // Akce pro generování obcí
        btnGenerovat.setOnAction(e -> {
            generujObec();
        });

        // Akce pro uložení obcí do csv souboru
        btnUloz.setOnAction(e -> {
            ulozDoSouboru(listViewObce);
        });

        // Akce pro tlačítko "Vybuduj"
        btnVybuduj.setOnAction(e -> {
            vybudujStrom();
        });

        // Akce pro smazání (zrušení) stromu
        btnZrus.setOnAction(e -> {
            zrusStrom();
        });

        // Akce pro načítání obcí z csv souboru "kraje" 
        btnNactiZKraje.setOnAction(e -> {
            try {
                importZSouboruKraje(stage);
            } catch (Exception ex) {
                Logger.getLogger(ProgAgendaKraj.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Rozložení ovládacích prvků
        HBox hboxControls = new HBox(10, comboKraj, btnNacti, btnNajdi, btnDoHloubky, btnDoSirky, btnGenerovat, spPocetGenerovani);
        hboxControls.setAlignment(Pos.CENTER);

        HBox hboxControls2 = new HBox(10, btnVybuduj, btnUloz, btnVloz, btnOdeber, btnZrus, btnNactiZKraje);
        hboxControls2.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(15, titleLabel, listViewObce, hboxControls, hboxControls2);
        vbox.setPadding(new Insets(15));
        vbox.setAlignment(Pos.TOP_CENTER);

        // Nastavení scény
        Scene scene = new Scene(vbox, SIRKA_OKNO, VYSKA_OKNO);
        vbox.setStyle("-fx-background-color: lightblue;");
        stage.setScene(scene);
        stage.setTitle("Správa obcí (Tlučhoř)");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    // Metoda pro načítání obcí z CSV souboru "kraje"
    private void importZSouboruKraje(Stage stage) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV soubory", "*.csv"));
        File soubor = fileChooser.showOpenDialog(stage);

        if (soubor != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(soubor))) {
                String line;
                ObservableList<Obec> noveObce = FXCollections.observableArrayList();

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length >= 7) {
                        try {
                            String nazev = parts[3].trim();
                            String PSC = parts[2].trim();
                            int pocetMuz = Integer.parseInt(parts[4].trim());
                            int pocetZen = Integer.parseInt(parts[5].trim());
                            int kodKraje = Integer.parseInt(parts[0].trim()) - 1;

                            if (kodKraje >= 0 && kodKraje < enumKraj.values().length) {

                                // Vytvoření nové obce a přidání do binárního stromu
                                Obec obec = new Obec(PSC, nazev, pocetMuz, pocetZen);
                                agendaKraj.vloz(nazev, obec);
                                noveObce.add(obec);
                            }
                        } catch (NumberFormatException e) {
                            Logger.getLogger(ProgAgendaKraj.class.getName()).log(Level.WARNING, "Chyba při převodu čísla: " + e.getMessage(), e);
                        }
                    }
                }

                if (!noveObce.isEmpty()) {
                    olObce.addAll(noveObce);
                }
            } catch (IOException e) {
                showAlert("Chyba", "Chyba při čtení souboru: " + e.getMessage());
            }
        }
    }
    
    // Metoda pro import dat z csv souboru v určitém formátu
    private void importData(Stage stage) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV soubory", "*.csv"));
        File soubor = fileChooser.showOpenDialog(stage);

        if (soubor != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(soubor))) {
                String line;
                while ((line = br.readLine()) != null) {

                    if (line.startsWith("Obec{") && line.endsWith(", }")) {
                        line = line.substring(5, line.length() - 3);

                        String[] detailsParts = line.split(", ");

                        String psc = detailsParts[0].split("=")[1].trim();
                        String nazev = detailsParts[1].split("=")[1].trim();
                        int pocetMuz = Integer.parseInt(detailsParts[2].split("=")[1].trim());
                        int pocetZen = Integer.parseInt(detailsParts[3].split("=")[1].trim());

                        Obec novaObec = new Obec(psc, nazev, pocetMuz, pocetZen);

                        agendaKraj.vloz(nazev, novaObec);

                        olObce.add(novaObec);
                    }
                }
            } catch (IOException e) {
                showAlert("Chyba", "Chyba " + e.getMessage());
            } catch (IllegalArgumentException e) {
                showAlert("Chyba", "Neplatný formát dat ve vstupním souboru.");
            }
        }
    }

    // Metoda pro vložení nové obce
    private void vlozObec() {

        enumKraj vybranyKraj = comboKraj.getValue();
        if (vybranyKraj == null) {
            // Pokud není vybrán kraj, zobrazí se varování
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vyberte kraj.");
            alert.show();
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Vložení nové obce");
        dialog.setHeaderText("Zadejte informace o nové obci (PSC;název;počet mužů;počet žen):");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            String[] parts = input.split(";");
            if (parts.length == 4) {
                try {
                    // Získání jednotlivých hodnot pro novou obec
                    String PSC = parts[0].trim();
                    String nazev = parts[1].trim();
                    int pocetMuz = Integer.parseInt(parts[2].trim());
                    int pocetZen = Integer.parseInt(parts[3].trim());

                    Obec novaObec = new Obec(PSC, nazev, pocetMuz, pocetZen);

                    agendaKraj.vloz(nazev, novaObec);

                    olObce.add(novaObec);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Obec byla úspěšně vložena.");
                    alert.show();
                } catch (NumberFormatException e) {

                    Alert alert = new Alert(Alert.AlertType.ERROR, "Chyba při převodu čísel: " + e.getMessage());
                    alert.show();
                } catch (Exception ex) {

                    showAlert("Chyba", "Nastala chyba při vložení obce.");
                    Logger.getLogger(ProgAgendaKraj.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

                Alert alert = new Alert(Alert.AlertType.WARNING, "Špatný formát. Zadejte PSC;název;počet mužů;počet žen.");
                alert.show();
            }
        });
    }

    // Metoda pro vyhledání obce
    private void najdiObec() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Vyhledání obce");
        dialog.setHeaderText("Zadejte název obce, kterou chcete najít:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            String nazevObce = input.trim();

            if (nazevObce.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Název obce nemůže být prázdný.");
                alert.show();
                return;
            }

            try {
                Obec foundObec = agendaKraj.najdi(nazevObce);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Nalezená obec");
                alert.setHeaderText("Obec byla nalezena:");
                alert.setContentText(foundObec.toString());
                alert.show();
            } catch (Exception ex) {

                showAlert("Chyba", "Obec s názvem '" + nazevObce + "' nebyla nalezena.");
            }
        });
    }

    // Metoda pro odstranění obce
    private void odeberObec() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Odstranění obce");
        dialog.setHeaderText("Zadejte název obce k odstranění:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nazev -> {
            try {

                Obec foundObec = agendaKraj.odeber(nazev);

                if (foundObec != null) {

                    olObce.removeIf(obec -> obec.getNazev().equals(nazev));

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Obec byla úspěšně odstraněna.");
                    alert.show();
                } else {

                    showAlert("Chyba", "Obec s tímto názvem neexistuje.");
                }
            } catch (Exception ex) {

                Logger.getLogger(ProgAgendaKraj.class.getName()).log(Level.SEVERE, null, ex);
                showAlert("Chyba", "Došlo k chybě při odstraňování obce.");
            }
        });
    }

    // Metoda pro iteraci do hloubky (IN-ORDER)
    private void iterujDoHloubky() {

        Iterator<Obec> iterator = agendaKraj.vytvorIteratorDoHloubky();

        listViewObce.getItems().clear();

        while (iterator.hasNext()) {
            Obec obec = iterator.next();
            listViewObce.getItems().add(obec);
        }
    }

// Metoda pro iteraci do šířky (Breadth-First Search)
    private void iterujDoSirky() {

        Iterator<Obec> iterator = agendaKraj.vytvorIteratorDoSirky();

        listViewObce.getItems().clear();

        while (iterator.hasNext()) {
            Obec obec = iterator.next();
            listViewObce.getItems().add(obec);
        }
    }

    private void generujObec() {

        Integer pocetGenerovani = spPocetGenerovani.getValue();

        if (pocetGenerovani == null || pocetGenerovani <= 0) {
            showAlert("Varování", "Zadejte platný počet obcí pro generování.");
            return;
        }

        try {

            enumKraj vybranyKraj = comboKraj.getValue();
            if (vybranyKraj == null) {
                showAlert("Varování", "Vyberte kraj před generováním obcí.");
                return;
            }

            // Generování obcí
            for (int i = 0; i < pocetGenerovani; i++) {

                Obec novaObec = generator.generateData(listViewObce, spPocetGenerovani, vybranyKraj);  // Předáme všechny potřebné parametry

                agendaKraj.vloz(novaObec.getNazev(), novaObec);
            }

            showAlert("Úspěch", "Obce byly úspěšně vygenerovány a přidány.");

        } catch (Exception e) {

            Logger.getLogger(ProgAgendaKraj.class.getName()).log(Level.SEVERE, null, e);
            showAlert("Chyba", "Došlo k chybě při generování obce: " + e.getMessage());
        }
    }

    // Metoda pro uložení obcí do csv souboru
    private void ulozDoSouboru(ListView<Obec> listView) {

        if (listView.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Žádné položky k uložení.");
            alert.show();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Uložit CSV soubor");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV soubory", "*.csv"));

        // Otevře dialog pro uložení souboru
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            // podmínka, jestli má soubor příponu .csv
            if (!file.getName().toLowerCase().endsWith(".csv")) {
                file = new File(file.getAbsolutePath() + ".csv");
            }
            try (FileWriter writer = new FileWriter(file)) {
                for (Obec item : listView.getItems()) {
                    writer.write(item + "\n");
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Soubor byl úspěšně uložen na: " + file.getAbsolutePath());
                alert.show();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Chyba při ukládání souboru: " + e.getMessage());
                alert.show();

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Uložení souboru bylo zrušeno.");
            alert.show();
        }
    }

    // Metoda pro vyvážení stromu
    private void vybudujStrom() {

        try {
            agendaKraj.vybuduj();
            showAlert("Úspěch", "Strom byl úspěšně vyvážen.");

            listViewObce.getItems().clear();
            Iterator<Obec> iterator = agendaKraj.vytvorIteratorDoHloubky();

            while (iterator.hasNext()) {
                Obec obec = iterator.next();
                olObce.add(obec);
            }
        } catch (Exception ex) {
            showAlert("Chyba", "Chyba při vyvážení stromu: " + ex.getMessage());
        }
    }

    // Smazání celého stromu
    private void zrusStrom() {
        agendaKraj.zrus();
        listViewObce.getItems().clear();
    }

    // Metoda pro zobrazení varovného okna
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

package javafx.bdats_sema_tluchor.obyvatele;

import java.io.IOException;
import javafx.bdats_sema_tluchor.abstrdoublelist.AbstrDoubleList;
import javafx.bdats_sema_tluchor.abstrdoublelist.AbstrDoubleListException;

/**
 *
 * @author atluc
 */
public interface IObyvatele extends Iterable {

    /**
     * Importuje data ze zadaného souboru a naplní seznamy obcí.
     *
     * @param soubor Cesta k datovému souboru.
     * @return Počet úspěšně importovaných záznamů.
     * @throws IOException Pokud dojde k chybě při čtení souboru.
     */
    int importData(String soubor) throws IOException;

    /**
     * Vypočítá průměrný počet obyvatel obcí v daném seznamu.
     *
     * @param obce Seznam obcí, pro které se má vypočítat průměr.
     * @return Průměrný počet obyvatel jako float.
     */
    float getPrumerKraje(AbstrDoubleList<Obec> obce);

    /**
     * Získá obce v zadaném kraji, které mají populaci nad průměrem.
     *
     * @param kraj Kraj, pro který se mají získat obce.
     * @return Seznam obcí s populací nad průměrem.
     */
    AbstrDoubleList<Obec> zobrazObceNadPrumer(enumKraj kraj);

    /**
     * Získá všechny obce v zadaném kraji.
     *
     * @param kraj Kraj, pro který se mají získat obce.
     * @return Seznam všech obcí v daném kraji.
     */
    AbstrDoubleList<Obec> zobrazObce(enumKraj kraj);

    /**
     * Vloží obec na zadanou pozici do zadaného kraje.
     *
     * @param obec Obec, kterou je třeba vložit.
     * @param pozice Pozice, na které se má obec vložit.
     * @param kraj Kraj, do kterého se má obec vložit.
     * @throws AbstrDoubleListException Pokud je pozice neplatná.
     */
    void vlozObec(Obec obec, enumPozice pozice, enumKraj kraj) throws AbstrDoubleListException;

    /**
     * Vypočítá průměrný počet obyvatel pro zadaný kraj. Pokud není zadaný kraj,
     * vypočítá průměr pro všechny kraje.
     *
     * @param kraj Kraj, pro který se má vypočítat průměr (nebo null pro
     * všechny).
     * @return Průměrný počet obyvatel jako float.
     */
    float zjistiPrumer(enumKraj kraj);

    /**
     * Smaže všechny obce ve zvoleném kraji, nebo ve všech krajích, pokud není
     * žádný zvolen.
     *
     * @param kraj Kraj, ze kterého se mají smazat obce (nebo null pro všechny).
     */
    void zrus(enumKraj kraj);

    /**
     * Přistupuje k obci na základě její pozice v daném kraji.
     *
     * @param pozice Pozice obce, ke které se má přistoupit.
     * @param kraj Kraj, ze kterého se má obec přistupovat.
     * @return Obec na zadané pozici, nebo null, pokud nebyla nalezena.
     * @throws AbstrDoubleListException Pokud je pozice neplatná.
     */
    Obec zpristupniObec(enumPozice pozice, enumKraj kraj) throws AbstrDoubleListException;

    /**
     * Získá obce na základě zadaného kraje. Pokud je kraj null, získá obce ze
     * všech krajů.
     *
     * @param kraj Kraj, pro který se mají získat obce (nebo null pro všechny).
     * @return Seznam obcí pro zvolený kraj nebo pro všechny kraje.
     */
    AbstrDoubleList<Obec> ziskatObcePodleKraje(enumKraj kraj);

    /**
     * Odebere obec z vybrané pozice v daném kraji.
     *
     * @param pozice Pozice obce, kterou je třeba odebrat.
     * @param kraj Kraj, ze kterého se má obec odebrat.
     * @return Odebraná obec, nebo null, pokud žádná obec nebyla nalezena.
     * @throws AbstrDoubleListException Pokud je pozice neplatná.
     */
    Obec odeberObec(enumPozice pozice, enumKraj kraj) throws AbstrDoubleListException;
}

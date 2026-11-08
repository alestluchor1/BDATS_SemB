package javafx.bdats_sema_tluchor.obyvatele;

import java.util.Iterator;

/**
 * Rozhraní pro správu agendy obcí.
 * @param <V> Typ hodnoty, která bude obsahovat informace o obci.
 */
public interface IAgendaKraj<V> {

    /**
     * Najde obec podle klíče.
     * @param key Klíč obce (např. název obce).
     * @return Vráti hodnotu obce (např. její detaily).
     * @throws Exception Pokud obec nebyla nalezena nebo klíč je neplatný.
     */
    V najdi(String key) throws Exception;

    /**
     * Vloží novou obec do seznamu.
     * @param key Klíč obce (např. název obce).
     * @param value Hodnota obce (např. detaily obce).
     * @throws Exception Pokud vložení selže (např. nevalidní klíč).
     */
    void vloz(String key, V value) throws Exception;

    /**
     * Odebere obec ze seznamu podle klíče.
     * @param key Klíč obce, která má být odstraněna (např. název obce).
     * @return Vráti hodnotu odstraněné obce.
     * @throws Exception Pokud obec nebyla nalezena nebo klíč je neplatný.
     */
    V odeber(String key) throws Exception;

    /**
     * Vybuduje vyvážený binární vyhledávací strom (BVS) na základě klíčů.
     * Tento algoritmus vezme všechny prvky a reorganizuje je do vyváženého stromu.
     * @throws Exception Pokud vybudování selže.
     */
    void vybuduj() throws Exception;

    /**
     * Vytvoří iterátor pro procházení tabulky obcí.
     * @param typ Určuje typ procházení (do hloubky nebo do šířky).
     * @return Iterátor pro tabulku obcí.
     */
    Iterator<V> vytvorIterátor(eTypProhl typ);

    /**
     * Generuje jednotlivé obce podle zadaných parametrů (např. název, popis, počet obyvatel atd.).
     * @param params Parametry pro generování obce (např. název, počet obyvatel).
     * @return Vytvořená obec.
     * @throws Exception Pokud generování obce selže.
     */
    V generujObec(Object... params) throws Exception;

    /**
     * Výčet pro určení typu procházení.
     */
    public enum eTypProhl {
        DO_SIRKY,  // Pro šířkové procházení
        DO_HLOUBKY // Pro hloubkové procházení
    }
}

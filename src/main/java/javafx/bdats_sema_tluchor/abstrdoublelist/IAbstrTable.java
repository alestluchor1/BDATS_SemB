package javafx.bdats_sema_tluchor.abstrdoublelist;

import java.util.Iterator;

/**
 * Rozhraní pro operace s tabulkou, která mapuje klíče na hodnoty.
 *
 * @param <K> Typ klíče (musí implementovat Comparable), název obce
 * @param <V> Typ hodnoty
 */
public interface IAbstrTable<K extends Comparable<K>, V> {

    /**
     * Vyprázdní tabulku.
     */
    void zrus();

    /**
     * Zjistí, zda je tabulka prázdná.
     *
     * @return true pokud je tabulka prázdná, jinak false.
     */
    boolean jePrazdny();

    /**
     * Vrátí velikost tabulky (počet uložených položek).
     *
     * @return Počet položek v tabulce.
     */
    int velikost();

    /**
     * Najde hodnotu podle klíče.
     *
     * @param key Klíč, podle kterého hledáme.
     * @return Hodnotu odpovídající klíči.
     * @throws Exception Pokud klíč není nalezen nebo je null.
     */
    V najdi(K key) throws Exception;

    /**
     * Vloží nový pár klíč-hodnota do tabulky.
     *
     * @param key Klíč.
     * @param value Hodnota.
     * @throws Exception Pokud dojde k chybě při vkládání (např. klíč je null).
     */
    void vloz(K key, V value) throws Exception;

    /**
     * Odstraní prvek podle klíče.
     *
     * @param key Klíč prvku, který má být odstraněn.
     * @return Hodnota odstraněného prvku.
     * @throws Exception Pokud klíč není nalezen nebo je null.
     */
    V odeber(K key) throws Exception;

    /**
     * Vytvoří iterátor podle zvoleného typu procházení.
     *
     * @param typ Typ procházení (např. do šířky nebo do hloubky).
     * @return Iterátor pro procházení hodnot v tabulce.
     */
    Iterator<V> vytvorIterator(eTypProhl typ);

    /**
     * Možné typy procházení tabulky.
     */
    enum eTypProhl {
        DO_HLOUBKY,
        DO_SIRKY
    }
}

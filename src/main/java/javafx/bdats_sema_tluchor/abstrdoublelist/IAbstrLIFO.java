package javafx.bdats_sema_tluchor.abstrdoublelist;

import java.util.Iterator;

/**
 * Rozhraní pro operace s datovou strukturou LIFO (zásobník).
 *
 * @param <T> Typ prvku, který bude zásobník uchovávat.
 */
public interface IAbstrLIFO<T> {

    /**
     * Zruší obsah zásobníku a vymaže všechny prvky.
     */
    void zrus();

    /**
     * Zjistí, zda je zásobník prázdný.
     *
     * @return true, pokud je zásobník prázdný, jinak false.
     */
    boolean jePrazdny();

    /**
     * Vloží prvek na vrchol zásobníku.
     *
     * @param data Data, která mají být vložena do zásobníku.
     */
    void vloz(T data);

    /**
     * Odebere a vrátí prvek z vrcholu zásobníku.
     *
     * @return Odebraný prvek z vrcholu zásobníku.
     * @throws Exception Pokud dojde k chybě při odebírání prvku (např. zásobník je prázdný).
     */
    T odeber() throws Exception;

    /**
     * Vrací iterátor, který umožňuje procházet prvky zásobníku.
     *
     * @return Iterátor pro procházení prvků zásobníku.
     */
    Iterator<T> iterator();

    /**
     * Získá velikost zásobníku.
     *
     * @return Počet prvků v zásobníku.
     */
    int size();
}

package javafx.bdats_sema_tluchor.abstrdoublelist;

import java.util.Iterator;

/**
 * Rozhraní pro operace s FIFO  datovou strukturou (frontou).
 *
 * @param <T> Typ prvku, který bude fronta uchovávat.
 */
public interface IAbstrFIFO<T> {

    /**
     * Zruší obsah fronty a odstraní všechny prvky.
     */
    void zrus();

    /**
     * Zjistí, zda je fronta prázdná.
     *
     * @return true, pokud je fronta prázdná, jinak false.
     */
    boolean jePrazdny();

    /**
     * Vloží data do fronty.
     *
     * @param data Data, která mají být vložena do fronty.
     */
    void vloz(T data);

    /**
     * Odebere a vrátí první prvek z fronty.
     *
     * @return Odebraný prvek z fronty.
     * @throws Exception Pokud dojde k chybě při odebírání prvku (např. pokud je fronta prázdná).
     */
    T odeber() throws Exception;

    /**
     * Vrací iterátor pro procházení prvků fronty.
     *
     * @return Iterátor pro prvky fronty.
     */
    Iterator<T> iterator();

    /**
     * Vrací velikost fronty.
     *
     * @return Počet prvků ve frontě.
     */
    int size();
}

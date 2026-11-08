
package javafx.bdats_sema_tluchor.abstrdoublelist;

import java.util.Iterator;

/**
 *
 * @author atluc
 * @param <T>
 */
public interface IAbstrDoubleList<T> extends Iterable {
    
    /**
     * Zrušení celého seznamu.
     */
    void zrus();

    /**
     * Test naplněnosti seznamu.
     *
     * @return true, pokud je seznam prázdný, jinak false.
     */
    boolean jePrazdny();

    /**
     * Vložení prvku do seznamu na první místo.
     *
     * @param data prvek k vložení
     * @throws NullPointerException pokud je data null
     */
    void vlozPrvni(T data);

    /**
     * Vložení prvku do seznamu na poslední místo.
     *
     * @param data prvek k vložení
     * @throws NullPointerException pokud je data null
     */
    void vlozPosledni(T data);

    /**
     * Vložení prvku do seznamu jakožto následníka aktuálního prvku.
     *
     * @param data prvek k vložení
     * @throws AbstrDoubleListException pokud je seznam prázdný nebo aktuální prvek je null
     * @throws NullPointerException pokud je data null
     */
    void vlozNaslednika(T data) throws AbstrDoubleListException;

    /**
     * Vložení prvku do seznamu jakožto předchůdce aktuálního prvku.
     *
     * @param data prvek k vložení
     * @throws AbstrDoubleListException pokud je seznam prázdný nebo aktuální prvek je null
     * @throws NullPointerException pokud je data null
     */
    void vlozPredchudce(T data) throws AbstrDoubleListException;

    // Operace typu zpřístupni, přenastavují pozici aktuálního prvku.

    /**
     * Zpřístupnění aktuálního prvku seznamu.
     *
     * @return aktuální prvek
     * @throws AbstrDoubleListException pokud je seznam prázdný nebo aktuální prvek je null
     */
    T zpristupniAktualni() throws AbstrDoubleListException;

    /**
     * Zpřístupnění prvního prvku seznamu.
     *
     * @return první prvek
     * @throws AbstrDoubleListException pokud je seznam prázdný
     */
    T zpristupniPrvni() throws AbstrDoubleListException;

    /**
     * Zpřístupnění posledního prvku seznamu.
     *
     * @return poslední prvek
     * @throws AbstrDoubleListException pokud je seznam prázdný
     */
    T zpristupniPosledni() throws AbstrDoubleListException;

    /**
     * Zpřístupnění následníka aktuálního prvku.
     *
     * @return následník aktuálního prvku
     * @throws AbstrDoubleListException pokud je seznam prázdný nebo aktuální prvek je null
     */
    T zpristupniNaslednika() throws AbstrDoubleListException;

    /**
     * Zpřístupnění předchůdce aktuálního prvku.
     *
     * @return předchůdce aktuálního prvku
     * @throws AbstrDoubleListException pokud je seznam prázdný nebo aktuální prvek je null
     */
    T zpristupniPredchudce() throws AbstrDoubleListException;

    /**
     * Odebrání (vyjmutí) aktuálního prvku ze seznamu.
     * Po této operaci je aktuální prvek nastaven na první prvek.
     *
     * @return odebraný prvek
     * @throws AbstrDoubleListException pokud je seznam prázdný nebo aktuální prvek je null
     */
    T odeberAktualni() throws AbstrDoubleListException;

    /**
     * Odebrání prvního prvku ze seznamu.
     *
     * @return odebraný první prvek
     * @throws AbstrDoubleListException pokud je seznam prázdný
     */
    T odeberPrvni() throws AbstrDoubleListException;

    /**
     * Odebrání posledního prvku ze seznamu.
     *
     * @return odebraný poslední prvek
     * @throws AbstrDoubleListException pokud je seznam prázdný
     */
    T odeberPosledni() throws AbstrDoubleListException;

    /**
     * Odebrání následníka aktuálního prvku ze seznamu.
     *
     * @return odebraný následník
     * @throws AbstrDoubleListException pokud je seznam prázdný nebo aktuální prvek je null
     */
    T odeberNaslednika() throws AbstrDoubleListException;

    /**
     * Odebrání předchůdce aktuálního prvku ze seznamu.
     *
     * @return odebraný předchůdce
     * @throws AbstrDoubleListException pokud je seznam prázdný nebo aktuální prvek je null
     */
    T odeberPredchudce() throws AbstrDoubleListException;

    /**
     * Vytvoří iterátor (dle rozhraní Iterable).
     *
     * @return iterátor pro prvky seznamu
     */
    @Override
    Iterator<T> iterator();
}
package javafx.bdats_sema_tluchor.abstrdoublelist;

import java.util.Iterator;

/**
 *
 * @author atluc
 * @param <T>
 */
// FIFO
public class AbstrFIFO<T> implements IAbstrFIFO<T> {

    // Atributy
    AbstrDoubleList<T> list;

    // Konstruktor
    public AbstrFIFO() {

        list = new AbstrDoubleList<>();
    }

    // Metody
    @Override
    public void zrus() {
        list.zrus();
    }

    @Override
    public boolean jePrazdny() {
        return list.jePrazdny();
    }

    @Override
    public void vloz(T data) {
        list.vlozPosledni(data);
    }

    @Override
    public T odeber() throws Exception {

        try {
            return list.odeberPrvni();
        } catch (AbstrDoubleListException ex) {
            throw new Exception("Chyba při odebrání prvku", ex);
        }

    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public int size() {

        return list.velikostSeznamu;

    }

}

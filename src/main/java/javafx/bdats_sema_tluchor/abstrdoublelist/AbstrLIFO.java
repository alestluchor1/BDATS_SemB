package javafx.bdats_sema_tluchor.abstrdoublelist;

import java.util.Iterator;

/**
 *
 * @author atluc
 * @param <T>
 */
//LIFO
public class AbstrLIFO<T> implements IAbstrLIFO<T> {

    // Atributy
    private final AbstrDoubleList<T> list;

    // Konstruktor
    public AbstrLIFO() {
        this.list = new AbstrDoubleList<>();
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
        list.vlozPrvni(data);
      
    }

    // Odebere a vrátí data ze zásobníku
    @Override
    public T odeber() throws Exception {
        try {
            return list.odeberPrvni();

        } catch (AbstrDoubleListException e) {
            throw new Exception("Chyba při odebírání ze zásobníku!", e);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return this.list.iterator();
    }

    // Velikost zásobníku
    @Override
    public int size() {
        return list.velikostSeznamu;

    }

}

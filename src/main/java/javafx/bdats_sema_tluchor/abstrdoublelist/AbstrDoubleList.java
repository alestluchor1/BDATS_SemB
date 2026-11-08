package javafx.bdats_sema_tluchor.abstrdoublelist;

import java.util.Iterator;

/**
 *
 * @author atluc
 * @param <T>
 *
 */
public class AbstrDoubleList<T> implements IAbstrDoubleList<T>, Iterable {

    private static class Prvek<T> {

        private final T data;
        private Prvek<T> naslednik;
        private Prvek<T> predchudce;

        public Prvek(T data) {
            this.data = data;
            this.naslednik = null;
            this.predchudce = null;
        }

    }

    private Prvek<T> prvni;
    private Prvek<T> aktualni;
    private Prvek<T> posledni;
    int velikostSeznamu;

    public AbstrDoubleList() {
        this.prvni = null;
        this.aktualni = null;
        this.posledni = null;
        this.velikostSeznamu = 0;
    }

    @Override
    public void zrus() {
        prvni = null;
        aktualni = null;
        posledni = null;
        velikostSeznamu = 0;
    }

    @Override
    public boolean jePrazdny() {
        return velikostSeznamu == 0 || prvni == null;
    }

    @Override
    public void vlozPrvni(T data) {

        if (data == null) {
            throw new NullPointerException("Vložený prvek na první pozici je null!");
        }

        Prvek<T> novyPrvek = new Prvek<>(data);

        if (jePrazdny()) {

            prvni = novyPrvek;
            posledni = novyPrvek;
            aktualni = novyPrvek;

            prvni.naslednik = prvni;
            prvni.predchudce = prvni;

        } else {

            novyPrvek.predchudce = posledni;
            novyPrvek.naslednik = prvni;
            prvni.predchudce = novyPrvek;
            posledni.naslednik = novyPrvek;
            prvni = novyPrvek;

        }
        velikostSeznamu++;
    }

    @Override
    public void vlozPosledni(T data) {

        if (data == null) {
            throw new NullPointerException("Data jsou null");
        }

        Prvek<T> novyPosledni = new Prvek<>(data);

        if (jePrazdny()) {

            prvni = novyPosledni;
            posledni = novyPosledni;
            prvni.predchudce = novyPosledni;
            prvni.naslednik = novyPosledni;

        } else {

            novyPosledni.predchudce = posledni;
            novyPosledni.naslednik = prvni;
            posledni.naslednik = novyPosledni;
            prvni.predchudce = novyPosledni;

            posledni = novyPosledni;
        }
        velikostSeznamu++;
    }

    @Override
    public void vlozNaslednika(T data) throws AbstrDoubleListException {

        Prvek<T> novyNaslednik = new Prvek<>(data);

        if (jePrazdny()) {
            throw new AbstrDoubleListException("Seznam je prázdný!");
        }

        if (data == null) {
            throw new NullPointerException("Data jsou null");
        }

        if (aktualni == null) {
            throw new AbstrDoubleListException("Aktuální prvek je null, Nemůže být vložen nový prvek (Následník)");
        }

        novyNaslednik.predchudce = aktualni;
        novyNaslednik.naslednik = aktualni.naslednik;

        if (aktualni.naslednik != null) {
            aktualni.naslednik.predchudce = novyNaslednik;
        }

        if (aktualni == posledni) {
            posledni = novyNaslednik;
        }

        aktualni.naslednik = novyNaslednik;

        velikostSeznamu++;
    }

    @Override
    public void vlozPredchudce(T data) throws AbstrDoubleListException {

        Prvek<T> novyPredchudce = new Prvek<>(data);

        if (data == null) {
            throw new AbstrDoubleListException("Data jsou null!");
        }

        if (jePrazdny()) {
            throw new AbstrDoubleListException("Seznam je prázdný!");
        }

        if (aktualni == null) {
            throw new AbstrDoubleListException("Není nastaven aktuální prvek");
        }

        novyPredchudce.naslednik = aktualni;
        novyPredchudce.predchudce = aktualni.predchudce;

        if (aktualni.predchudce != null) {
            aktualni.predchudce.naslednik = novyPredchudce;
        }

        aktualni.predchudce = novyPredchudce;

        velikostSeznamu++;
    }

    @Override
    public T zpristupniAktualni() throws AbstrDoubleListException {

        if (jePrazdny() || aktualni == null) {
            throw new AbstrDoubleListException("Seznam je prázdný nebo aktuální prvek je null! Aktuální prvek není nastaven!");
        }
        return aktualni.data;

    }

    @Override
    public T zpristupniPrvni() throws AbstrDoubleListException {

        if (jePrazdny()) {
            throw new AbstrDoubleListException("Seznam je prázdný !");
        }
        aktualni = prvni;
        return aktualni.data;

    }

    @Override
    public T zpristupniPosledni() throws AbstrDoubleListException {

        if (jePrazdny()) {
            throw new AbstrDoubleListException("Seznam je prázdný !");
        }
        aktualni = posledni;
        return aktualni.data;

    }

    @Override
    public T zpristupniNaslednika() throws AbstrDoubleListException {

        if (jePrazdny()) {
            throw new AbstrDoubleListException("Seznam je prázdný!");
        }

        if (aktualni == null) {
            throw new AbstrDoubleListException("Aktuální prvek je null !");
        }

        aktualni = aktualni.naslednik;
        return aktualni.data;
    }

    @Override
    public T zpristupniPredchudce() throws AbstrDoubleListException {

        if (jePrazdny()) {
            throw new AbstrDoubleListException("Seznam je prádný!");
        }

        if (aktualni == null) {
            throw new AbstrDoubleListException("Aktuální prvek není nastaven!");
        }

        aktualni = aktualni.predchudce;
        return aktualni.data;
    }

    @Override
    public T odeberAktualni() throws AbstrDoubleListException {

        T odebranyAktualniPrvek = aktualni.data;

        if (jePrazdny()) {
            throw new AbstrDoubleListException("Seznam je prázdný");
        }

        if (aktualni == null) {
            throw new AbstrDoubleListException("Aktuální prvek je null!");
        }

        if (velikostSeznamu == 1) {
            return odeberPrvni();
        }

        if (aktualni == posledni) {
            return odeberPosledni();
        }

        if (aktualni.predchudce != null) {
            aktualni.predchudce.naslednik = aktualni.naslednik;
        }

        if (aktualni.naslednik != null) {
            aktualni.naslednik.predchudce = aktualni.predchudce;
        }

        aktualni = prvni;
        velikostSeznamu--;
        return odebranyAktualniPrvek;
    }

    @Override
    public T odeberPrvni() throws AbstrDoubleListException {

        T odebranyPrvniPrvek = prvni.data;

        if (jePrazdny()) {
            throw new AbstrDoubleListException("Seznam je prázdný");
        }

        if (velikostSeznamu == 1) {

            prvni = null;
        } else {

            Prvek<T> novyPrvni = prvni.naslednik;
            novyPrvni.predchudce = prvni.predchudce;

            prvni.predchudce.naslednik = novyPrvni;
            prvni = novyPrvni;
        }

        velikostSeznamu--;
        return odebranyPrvniPrvek;
    }

    @Override
    public T odeberPosledni() throws AbstrDoubleListException {

        if (jePrazdny()) {
            throw new AbstrDoubleListException("Seznam je prázdný");
        }

        T odebranyPosledniPrvek = posledni.data;

        if (velikostSeznamu == 1) {
            prvni = null;
            posledni = null;
        } else {

            Prvek<T> novyPosledni = posledni.predchudce;
            novyPosledni.naslednik = prvni;
            prvni.predchudce = novyPosledni;

            posledni = novyPosledni;
        }

        if (aktualni == posledni) {
            aktualni = null;
        }

        velikostSeznamu--;
        return odebranyPosledniPrvek;
    }

    @Override
    public T odeberNaslednika() throws AbstrDoubleListException {
        if (jePrazdny()) {
            throw new AbstrDoubleListException("Seznam je prázdný");
        }
        if (aktualni == null) {
            throw new AbstrDoubleListException("Aktuální prvek je null!");
        }

        if (aktualni.naslednik == prvni) {
            return odeberPrvni();
        }

        Prvek<T> odebranyNaslednik = aktualni.naslednik;
        aktualni.naslednik = odebranyNaslednik.naslednik;

        if (odebranyNaslednik.naslednik == posledni) {
            posledni = aktualni;
        }

        velikostSeznamu--;
        return odebranyNaslednik.data;
    }

    @Override
    public T odeberPredchudce() throws AbstrDoubleListException {

        if (aktualni == null) {
            throw new AbstrDoubleListException("Aktuální prvek je null!");
        }

        if (jePrazdny()) {
            throw new AbstrDoubleListException("Seznam je prázdný");
        }

        if (aktualni == prvni) {
            return odeberPosledni();
        }

        Prvek<T> odebranyPredchudce = aktualni.predchudce;
        aktualni.predchudce = odebranyPredchudce.predchudce;

        if (odebranyPredchudce == prvni) {
            prvni = aktualni;
        }

        velikostSeznamu--;
        return odebranyPredchudce.data;

    }

    public int size() {
        return velikostSeznamu;
    }

    @Override
    public Iterator<T> iterator() {

        return new Iterator<>() {

            private Prvek<T> PrvekIterator = prvni;

            @Override
            public boolean hasNext() {
                return PrvekIterator != null;
            }

            @Override
            public T next() {

                T dataPrvekIterator = PrvekIterator.data;

                if (PrvekIterator.naslednik == prvni) {

                    PrvekIterator = null;
                } else {

                    PrvekIterator = PrvekIterator.naslednik;
                }
                return dataPrvekIterator;
            }
        };
    }

}

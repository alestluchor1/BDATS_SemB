package javafx.bdats_sema_tluchor.abstrdoublelist;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author atluc
 * @param <K>
 * @param <V>
 */
public class AbstrTable<K extends Comparable<K>, V> implements IAbstrTable<K, V> {

    // Atributy
    private Prvek root; // Kořen stromu
    private int pocetPrvku; //počet prvků v binárním stromu

    public AbstrTable() {
        this.root = null;
        this.pocetPrvku = 0;
    }

    /////////////////////
    private class Prvek {

        // Atributy
        private K key;
        private V value;
        private Prvek right;
        private Prvek left;

        // Konstruktor
        public Prvek(K key, V value, Prvek left, Prvek right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        // 2. Konstruktor, vloží prvek bez potomků
        public Prvek(K key, V value) {
            this(key, value, null, null);
        }
    }

    @Override
    public void zrus() {

        pocetPrvku = 0;
        root = null;
    }

    @Override
    public boolean jePrazdny() {

        return (root == null || pocetPrvku == 0);

    }

    @Override
    public int velikost() {

        return pocetPrvku;
    }

    @Override
    public V najdi(K key) throws Exception {

        if (key == null) {
            throw Error.KEY_IS_NULL;
        }
        if (jePrazdny()) {
            throw Error.EMPTY;
        }

        Prvek prvek = najitPrvek(key, root);
        if (prvek == null) {
            throw Error.NO_KEY_VALUE;
        }

        return prvek.value;
    }

    // Pomocná metoda k najdi
    private Prvek najitPrvek(K key, Prvek prvek) {
        while (prvek != null) {
            if (key.equals(prvek.key)) {
                return prvek; // Klíč nalezen
            }

            int porovnani = key.compareTo(prvek.key);
            if (porovnani <= 0) {
                prvek = prvek.left; // Pokračuj vlevo
            }
            if (porovnani > 0) {
                prvek = prvek.right; // Pokračuj vpravo
            }
        }
        return null;
    }

    @Override
    public void vloz(K key, V value) throws Exception {

        if (key == null) {
            throw Error.KEY_IS_NULL;
        }

        if (value == null) {
            throw Error.NO_KEY_VALUE;
        }

        Prvek novyPrvek = new Prvek(key, value);

        if (jePrazdny()) {
            root = novyPrvek;
        } else {
            pridatPrvek(root, novyPrvek);
        }
        pocetPrvku++;
    }

    // Pomocná metoda pro přidání uzlu
    private void pridatPrvek(Prvek parent, Prvek potomek) {
        int porovnani = potomek.key.compareTo(parent.key); // Hodnota porovnání

        if (porovnani <= 0) { // Pokud je klíč menší nebo rovný, přidáme do levého podstromu
            if (parent.left == null) {
                parent.left = potomek;
            } else {
                pridatPrvek(parent.left, potomek);
            }
        } else { // Pokud je klíč větší, přidáme do pravého podstromu
            if (parent.right == null) {
                parent.right = potomek;
            } else {
                pridatPrvek(parent.right, potomek);
            }
        }

        // pocetPrvku++;
    }

    @Override
    public V odeber(K key) throws Exception {

        if (key == null) {
            throw Error.KEY_IS_NULL;
        }
        if (jePrazdny()) {
            throw Error.EMPTY;
        }

        Prvek odebranyPrvek = odebratPrvek(root, key);
        if (odebranyPrvek == null) {
            throw Error.NO_KEY_VALUE;
        }

        pocetPrvku--;
        return odebranyPrvek.value; 
    }

// Pomocná metoda pro odstranění uzlu
    private Prvek odebratPrvek(Prvek prvek, K key) {
        if (prvek == null) {
            return null;
        }

        int porovnani = key.compareTo(prvek.key);
        if (porovnani < 0) {
            prvek.left = odebratPrvek(prvek.left, key);
        } else if (porovnani > 0) {
            prvek.right = odebratPrvek(prvek.right, key);
        } else {
            // Uzel nalezen
            if (prvek.left == null) {
                return prvek.right; // Náhrada pravým podstromem
            } else if (prvek.right == null) {
                return prvek.left; // Náhrada levým podstromem
            } else {
                // Pokud má oba potomky
                Prvek minPrvek = najdiMinPrvek(prvek.right); // Najdi nejmenší uzel v pravém podstromu
                prvek.key = minPrvek.key;
                prvek.value = minPrvek.value;
                prvek.right = odebratPrvek(prvek.right, minPrvek.key);
            }
        }
        return prvek;
    }

// Pomocná metoda pro nalezení minimálního prvku v pravem podstromu
    private Prvek najdiMinPrvek(Prvek prvek) {
        while (prvek.left != null) {
            prvek = prvek.left;
        }
        return prvek;
    }

    //Iterator
    @Override
    public Iterator<V> vytvorIterator(eTypProhl typ) {

        if (typ == eTypProhl.DO_SIRKY) {
            return new doSirkyIterator(root);
        }

        if (typ == eTypProhl.DO_HLOUBKY) {
            return new doHloubkyIterator(root);
        }
        return null;
    }

    // Iterátor do hloubky, IN-ORDER (levý podstrom → kořen → pravý podstrom)
    private class doHloubkyIterator implements Iterator<V> {

        private final AbstrLIFO<Prvek> zasobnikLifo = new AbstrLIFO<>();  // Zásobník pro uchování uzlů
        private Prvek aktualniUzel = root;  // Začínáme u kořene stromu

        // Konstruktor
        private doHloubkyIterator(Prvek root) {
            this.aktualniUzel = root;
        }

        @Override
        public boolean hasNext() {

            return aktualniUzel != null || !zasobnikLifo.jePrazdny();
        }

        @Override
        public V next() {
            try {
                // Pokud už nejsou žádné další uzly, vyvoláme výjimku
                if (!hasNext()) {
                    throw new NoSuchElementException("Žádný další prvek v iterátoru!");
                }

                // Procházíme levé podstromy uzlů, dokud nenarazíme na nejvzdálenější levý uzel
                while (aktualniUzel != null) {
                    zasobnikLifo.vloz(aktualniUzel);  // Uložíme aktuální uzel do zásobníku
                    aktualniUzel = aktualniUzel.left;  // Posuneme se na levého potomka
                }

                // Vytáhneme uzel z vrchu zásobníku 
                Prvek dalsiUzel = zasobnikLifo.odeber();
                aktualniUzel = dalsiUzel.right;  // Po zpracování uzlu se přesuneme na jeho pravého potomka

                return dalsiUzel.value;
            } catch (Exception ex) {
                Logger.getLogger(AbstrTable.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    // Iterátor do šířky, Level-Order
    private class doSirkyIterator implements Iterator<V> {

        // Atributy
        private AbstrFIFO<Prvek> zasobnikFifo = new AbstrFIFO<>();

        // Konstruktor
        private doSirkyIterator(Prvek root) {

            zasobnikFifo.vloz(root);

        }

        @Override
        public boolean hasNext() {

            return !zasobnikFifo.jePrazdny();
        }

        @Override
        public V next() throws NoSuchElementException {

            if (!hasNext()) {
                throw new NoSuchElementException("Žádný další prvek v iterátoru!");
            }

            try {
                Prvek prvek = zasobnikFifo.odeber();
                if (prvek != null) {
                    // Přidáme levý a pravý uzel do fronty
                    if (prvek.left != null) {
                        zasobnikFifo.vloz(prvek.left);
                    }
                    if (prvek.right != null) {
                        zasobnikFifo.vloz(prvek.right);
                    }
                }
                return prvek.value; // Vrací hodnotu uzlu

            } catch (Exception e) {
                throw new NoSuchElementException("Chyba při průchodu stromem: " + e.getMessage());
            }
        }
    }

    public static class Error extends Exception {

        // Konstanty
        private static final Error NO_KEY_VALUE = new Error("Klíč nemá přiřazenou hodnotu!");
        private static final Error EMPTY = new Error("Tabulka je již prázdná!");
        private static final Error KEY_IS_NULL = new Error("Klíč je null!");

        // Konstruktor
        public Error(String message, Throwable cause) {
            super(message, cause);
        }

        public Error(String message) {
            super(message);
        }
    }
}

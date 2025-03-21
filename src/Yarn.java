import java.util.ArrayList;
import java.util.List;

/** A Yarn class a gombafonal reprezentációja. */
public class Yarn {

    private List<Tecton> tectons;
    private Mushroom mushroom;

    /**
     * A Yarn class konstruktora.
     * @param mushroom A gomba, amihez a gombafonal tartozik.
     */
    public Yarn(Mushroom mushroom) {
        this.tectons = new ArrayList<>();
        this.mushroom = mushroom;
    }

    /**
     * Ellenőrzi, hogy a gombafonal kapcsolódik-e gombatesthez. Implementáció: ellenőrzi, hogy a
     * gombatest Tectonja szerepel-e a gombafonalhoz tartozó Tectonok listájában.
     *
     * @return Kapcsolódik-e gombatesthez?
     */
    public boolean isConnected() {
        if (mushroom == null) {
            return false; // Ha nincs gomba, akkor nem connected
        }

        for (Tecton tecton : tectons) {
            if (tecton.getMushroom() == mushroom) {
                return true; // Ha a gomba Tectonja benne van a listában, akkor connected
            }
        }

        return false; // Ha a gomba Tectonja nincs benne a listában, akkor nem connected
    }

    /**
     * kettéhasítja a gombafonalat. Ha a Tecton nem az első vagy az utolsó,
     * létrehoz két új Yarn-t és törli önmagát.
     *
     * @param tecton A Tecton, ahol a gombafonalat ketté kell hasítani.
     */
    public void split(Tecton tecton) {
        if (!tectons.contains(tecton)) {
            System.out.println("Tecton is not part of this Yarn.");
            return;
        }

        int index = tectons.indexOf(tecton);
        if (index == 0 || index == tectons.size() - 1) {
            System.out.println("Splitting yarn on an edge Tecton, removing");
            tecton.removeYarn(this);
            tectons.remove(tecton);
            return;
        }

        // 1. Létrehozunk két új Yarn objektumot
        Yarn newYarn1 = new Yarn(mushroom);
        Yarn newYarn2 = new Yarn(mushroom);

        // 2. Az első Yarn-hez hozzáadjuk a Tectonokat a vágási pontig
        for (int i = 0; i < index; i++) {
            Tecton t = tectons.get(i);
            newYarn1.addTecton(t);
            t.removeYarn(this);
        }

        // 3. A második Yarn-hez hozzáadjuk a Tectonokat a vágási pont után
        for (int i = index + 1; i < tectons.size(); i++) {
            Tecton t = tectons.get(i);
            newYarn2.addTecton(t);
            t.removeYarn(this);
        }

        // 4. Eltávolítjuk az összes Tectont a jelenlegi Yarn-ból
        for (Tecton t : new ArrayList<>(tectons)) {
            t.removeYarn(this);
            tectons.remove(t);
        }

        System.out.println("Yarn split into two.");
    }

    /**
     * Hozzáad egy Tectont a gombafonalhoz.
     *
     * @param t A hozzáadandó Tecton.
     */
    public void addTecton(Tecton t) {
        if (!tectons.contains(t)) {
            this.tectons.add(t);
            if (!t.getYarns().contains(this)) {
                t.growYarn(this);
            }
            System.out.println("Adding tecton to yarn: " + t);
        }
    }

    /**
     * Eltávolít egy tectont a gombafonaltól.
     *
     * @param t Az eltávolítandó tecton.
     */
    public void removeTecton(Tecton t) {
        this.tectons.remove(t);
        if (t.getYarns().contains(this)) {
            t.removeYarn(this);
        }
        System.out.println("Removing tecton from yarn: " + t);
    }

    /**
     * Visszaadja a gombafonalhoz tartozó Tectonok listáját.
     *
     * @return A Tectonok listája.
     */
    public List<Tecton> getTectons() {
        return tectons;
    }

    /**
     * Visszaadja a gombafonalhoz tartozó gombát
     * @return A {@link Mushroom} típusú gomba
     */
    public Mushroom getMushroom() {
        return mushroom;
    }
}

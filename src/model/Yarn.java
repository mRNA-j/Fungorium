package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** A Yarn class a gombafonal reprezentációja. */
public class Yarn {

    protected List<Tecton> tectons;
    protected Mushroom mushroom;
    protected MushroomPicker player;
    private String id;
    protected String name = "yarn";

    public String getId() {
        return id;
    }

    public MushroomPicker getPlayer() {
        return player;
    }

    public String getName(){return name;}

    /**
     * A Yarn class konstruktora.
     */
    public Yarn() {
        this.tectons = new ArrayList<>();
        id = "Yarn" + new Random().nextInt();
        name="normal";
    }

    /**
     * A Yarn class konstruktora.
     */
    public Yarn(List<Tecton> tectons) {
        this.tectons = tectons;
        id = "" + new Random().nextInt();
    }

    /**
     * A Yarn class konstruktora.
     * @param mushroom A gomba, amihez a gombafonal tartozik.
     */
    public Yarn(Mushroom mushroom, MushroomPicker picker,String id) {
        this.tectons = new ArrayList<>();
        tectons.add(mushroom.getTecton());
        tectons.get(0).growYarn(this);
        this.mushroom = mushroom;
        this.id = id;
        this.player = picker;
    }

    /**
     * Ellenőrzi, hogy a gombafonal kapcsolódik-e gombatesthez. Implementáció: ellenőrzi, hogy a
     * gombatest Tectonja szerepel-e a gombafonalhoz tartozó Tectonok listájában.
     *
     * @return Kapcsolódik-e gombatesthez?
     */
    public boolean isConnected() {

        for (Tecton tecton : tectons) {
            if (tecton.getMushroom() == mushroom && mushroom != null) {
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
            tectons.remove(tecton);
            tecton.getYarns().remove(this);  // Direct removal without calling removeYarn
            return;
        }

        // 1. Create two new Yarn objects
        Yarn newYarn1 = new Yarn();
        Yarn newYarn2 = new Yarn();

        int mushroomIndex = 0;
        for (int i = 0; i < tectons.size(); i++) {
            if(tectons.get(i).getMushroom() == mushroom)
                mushroomIndex = i;
        }

        if(mushroomIndex < index) {
            newYarn1.mushroom = mushroom;
        } else {
            newYarn2.mushroom = mushroom;
        }

        // 2. Add Tectons to the first yarn up to the cutting point
        for (int i = 0; i < index; i++) {
            Tecton t = tectons.get(i);
            t.growYarn(newYarn1);
        }

        // 3. Add Tectons to the second yarn after the cutting point
        for (int i = index + 1; i < tectons.size(); i++) {
            Tecton t = tectons.get(i);
            t.growYarn(newYarn2);
        }

        // 4. Finally, remove this yarn from all tectons' yarn lists
        for (Tecton t : new ArrayList<>(tectons)) {
            t.getYarns().remove(this);  // Direct removal
        }

        tectons.clear();  // Clear the list of tectons in this yarn
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

    /**
     * Visszaadja a gombafonalhoz tartozó gombát
     */
    public void setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }

    /**
     * Mivel az alap fonálnak nincs speciális hatása, nem történik semmi
     */
    public void runEffect() {
        return;
    }
}

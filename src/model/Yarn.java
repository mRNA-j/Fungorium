package model;

import view.YarnView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** A Yarn class a gombafonal reprezentációja. */
public class Yarn implements Serializable {
    private YarnView yarnView;
    protected List<Tecton> tectons;
    protected Mushroom mushroom;
    protected MushroomPicker player;
    private String id; //tesztelésnél használt azonosító
    protected String name = "normal";

    /**
     * A Yarn class konstruktora.
     */
    public Yarn() {
        yarnView = new YarnView(this);
        this.tectons = new ArrayList<>();
        id = "Yarn" + new Random().nextInt();
        name="normal";
    }

    /**
     * Alapértelmezett konstruktor.
     * @param mushroom A gomba, amihez a gombafonal tartozik.
     * @param picker A gombász akinek  fonala
     */
    public Yarn(Mushroom mushroom, MushroomPicker picker) {
        yarnView = new YarnView(this);
        this.tectons = new ArrayList<>();
        tectons.add(mushroom.getTecton());
        this.player = picker;
        picker.addYarn(this);
        mushroom.getTecton().addYarn(this);
    }

    /**
     * Tesztelésnél használt konstruktor.
     * @param mushroom A gomba, amihez a gombafonal tartozik.
     * @param picker A gombász akinek  fonala
     * @param id a teszteléshez hazsnált azonosító
     */
    public Yarn(Mushroom mushroom, MushroomPicker picker,String id) {
        yarnView = new YarnView(this);
        this.tectons = new ArrayList<>();
        tectons.add(mushroom.getTecton());
        this.mushroom = mushroom;
        this.id = id;
        this.player = picker;
        picker.addYarn(this);
        mushroom.getTecton().addYarn(this);
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public MushroomPicker getPlayer() {
        return player;
    }

    public void setPlayer(MushroomPicker e) {
        player = e;
    }

    public String getName(){return name;}



    public YarnView getYarnView() {
        return yarnView;
    }

    public void setYarnView(YarnView yarnView) {
        this.yarnView = yarnView;
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
    public void split(Tecton tecton, Tecton iranyAmerreSzakad) {
        if (!tectons.contains(tecton)) {
            return;
        }

        int index = tectons.indexOf(tecton);

        //Nem vegpont a tecton
        int mushroomIndex = 0;
        for (int i = 0; i < tectons.size(); i++) {
            if(tectons.get(i).getMushroom() == mushroom)
                mushroomIndex = i;
        }

        Yarn newYarn1 = new Yarn();
        Yarn newYarn2 = new Yarn();

        newYarn1.setId(getId() + "_1");
        newYarn2.setId(getId()  + "_2");

        newYarn1.setPlayer(getPlayer());
        newYarn2.setPlayer(getPlayer());

        //Keressuk meg melyik iranyba vagunk
        int iranyIndex = tectons.indexOf(iranyAmerreSzakad);

        if(iranyIndex < index) {
            // Tektonokat hozzáadjuk a első fonalhoz
            for (int i = 0; i < index; i++) {
                Tecton t = tectons.get(i);
                t.growYarn(newYarn1);
            }

            // Tektonokat hozzáadjuk a második fonalhoz
            for (int i = index; i < tectons.size(); i++) {
                Tecton t = tectons.get(i);
                t.growYarn(newYarn2);
            }
        } else {
            // Tektonokat hozzáadjuk a első fonalhoz
            for (int i = 0; i <= index; i++) {
                Tecton t = tectons.get(i);
                t.growYarn(newYarn1);
            }

            // Tektonokat hozzáadjuk a második fonalhoz
            for (int i = index+1; i < tectons.size(); i++) {
                Tecton t = tectons.get(i);
                t.growYarn(newYarn2);
            }
        }

        if(mushroomIndex < index) {
            newYarn1.mushroom = mushroom;
        } else {
            newYarn2.mushroom = mushroom;
        }

        // Eltávolítjuk a yarn-t az összes tektonból ami tartalmazta
        for (Tecton t : new ArrayList<>(tectons)) {
            t.getYarns().remove(this);  // Direct removal
        }

        //Tekton listáját kitöröljük
        tectons.clear();
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
     * Az alapértelmezett fonálnak nincs speciális hatása, nem történik semmi
     */
    public void runEffect() {
    }

    @Override
    public String toString(){
        return id;
    }
}

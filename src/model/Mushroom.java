package model;

import view.MushroomView;

import java.io.Serializable;

/**
 * A gomba entitást reprezentáló osztály.
 * Kezeli a gomba korát, spóráit és a tectonhoz való kapcsolódását.
 */
public class Mushroom implements Serializable {
    MushroomView mushroomView;



    private MushroomPicker owner;
    private String id; // Hozzáadott ID mező
    private int numberOfDispersions;
    private int newSporeGrowth;
    private int age;
    private boolean hasSpore;
    private Tecton tecton;
    private Spore currentSpore; // Hozzáadott mező a jelenlegi spóra tárolására

    /**
     * Létrehoz egy új gomba objektumot a megadott tectonon.
     * @param tecton A tecton, amin a gomba elhelyezkedik.
     */
    public Mushroom(Tecton tecton, MushroomPicker picker) {
        mushroomView = new MushroomView(this);
        this.tecton = tecton;
        tecton.addMushroom(this);
        owner = picker;
        age = 0;
        numberOfDispersions = 0;
        newSporeGrowth = 5;
        hasSpore = false;
        currentSpore = null;
    }



    public Mushroom(Tecton tecton,MushroomPicker picker , String testID) {
        mushroomView = new MushroomView(this);
        owner = picker;
        this.tecton = tecton;
        tecton.addMushroom(this);
        age = 0;
        numberOfDispersions = 0;
        newSporeGrowth = 5;
        hasSpore = false;
        currentSpore = null;
        this.id = testID;
    }

    public MushroomPicker getOwner() {
        return owner;
    }

    public void setOwner(MushroomPicker owner) {
        this.owner = owner;
    }

    public MushroomView getMushroomView() {
        return mushroomView;
    }

    public int getNewSporeGrowth() {
        return newSporeGrowth;
    }

    public void setNewSporeGrowth(int newSporeGrowth) {
        if(newSporeGrowth < 0) {
            return;
        }
        if(newSporeGrowth == 0) {
            setHasSpore(true);
        }

        this.newSporeGrowth = newSporeGrowth;
    }

    /**
     * Visszaadja a gomba azonosítóját.
     * @return A gomba azonosítója.
     */
    public String getId() {
        return id;
    }

    /**
     * Beállítja a gomba azonosítóját.
     * @param id Az új azonosító.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Visszaadja a gomba életkorát.
     * @return A gomba életkora.
     */
    public int getAge() {
        return age;
    }

    /**
     * Visszaadja, hogy a gombának van-e spórája.
     * @return Igaz, ha a gombának van spórája, egyébként hamis.
     */
    public boolean getHasSpore() {
        return hasSpore;
    }

    /**
     * Beállítja a gomba spóra állapotát.
     * @param hasSpore Igaz, ha a gombának van spórája, egyébként hamis.
     */
    public void setHasSpore(boolean hasSpore) {
        this.hasSpore = hasSpore;
    }

    /**
     * Visszaadja a gombában lévő jelenlegi spórát.
     * @return A jelenlegi spóra, vagy null ha nincs.
     */
    public Spore getCurrentSpore() {
        return currentSpore;
    }

    /**
     * Spórát növe szt a gombában.
     * @param spore A növesztendő spóra.
     */
    public void growSpore(Spore spore) {
        if (!hasSpore) {
            this.currentSpore = spore;
            this.hasSpore = true;
        }
    }

    /**
     * Spóra kilövését kezeli.
     * A gombában lévő spórát hozzáadja a megadott tectonhoz.
     * @param tecton A tecton, amire a spórát kilövi.
     */
    public void disperseSpore(Tecton tecton) {
        //growSpore(new AcceleratorSpore(tecton, "Spore1"));
        currentSpore = new AcceleratorSpore("Spore1");
        //System.out.println("BELEPTEM");
        if (hasSpore && currentSpore != null) {
            tecton.addSpore(currentSpore);
            numberOfDispersions++;
            hasSpore = false;
            currentSpore = null;
            restartSporeGrowth();
            // System.out.println("VANESPORA: " + hasSpore);
        }
    }

    /**
     * Újraindítja a spóra növekedési idejét.
     * Az új spóra növekedéséig hátralévő időt 5-re állítja.
     */
    public void restartSporeGrowth() {
        newSporeGrowth = 5;
        setHasSpore(false);
    }



    /**
     * Visszaadja a tectont, amelyen a gomba található.
     * @return A tecton, amin a gomba elhelyezkedik.
     */
    public Tecton getTecton() {
        return tecton;
    }
}

package model;

import view.MushroomView;

import java.io.Serializable;

/**
 * A gomba entitást reprezentáló osztály.
 * Kezeli a gomba korát, spóráit és a tectonhoz való kapcsolódását.
 */
public class Mushroom implements Serializable {
    private final MushroomView mushroomView;
    private MushroomPicker owner;
    private String id; // Hozzáadott ID mező
    private int numberOfDispersions;
    private int newSporeGrowth;
    private int age;
    private boolean hasSpore;
    private final Tecton tecton;
    private Spore currentSpore; // Hozzáadott mező a jelenlegi spóra tárolására

    /**
     * Létrehoz egy új gomba objektumot a megadott tectonon.
     * @param tecton A tecton, amin a gomba elhelyezkedik.
     * @param picker A gombász akihez a gomba tartozik
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
        id = picker.getId();
    }

    /**
     * Létrehoz egy új gomba objektumot a megadott tectonon.
     * @param tecton A tecton, amin a gomba elhelyezkedik.
     * @param picker A gombász akihez a gomba tartozik
     * @param testID a teszteléshez használt azonosító
     */
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

    /**
     * Visszadja a gomba owner-ét.
     * @return MushroomPicker A gomba tulajdonosa
     * */
    public MushroomPicker getOwner() {
        return owner;
    }

    /**
     * Beállítja a gomba owner-ét.
     * @param owner a gombász akihez tartozni fog.
     */
    public void setOwner(MushroomPicker owner) {
        this.owner = owner;
    }

    /**
     * Visszaadja a nézetet
     * @return MushroomView
     */
    public MushroomView getMushroomView() {
        return mushroomView;
    }

    /**
     * Visszaadja, hogy hány körig tart a spórát kinöveszteni
     * @return int
     */
    public int getNewSporeGrowth() {
        return newSporeGrowth;
    }

    /**
     * Beállítja a sporatermelés időtartamát a paraméterként kapott értékre.
     * @param newSporeGrowth az érték amire bállítja a spóranövekedés idejét-
     * */
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
    /*public Spore getCurrentSpore() {
        return currentSpore;
    }*/

    /**
     * Spóra kilövését kezeli.
     * A gombában lévő spórát hozzáadja a megadott tectonhoz.
     * @param tecton A tecton, amire a spórát kilövi.
     */
    public void disperseSpore(Tecton tecton, String type, String id) {
        switch(type) {
            case "Accelerator":
                currentSpore = new AcceleratorSpore("MEG KELL CSINAÉLNI");
                break;
            case "Decelerator":
                currentSpore = new DeceleratorSpore("MEG KELL CSINA");
                break;
            case "Cut Preventing":
                currentSpore = new CutPreventingSpore("MEG KELL CSINA");
                break;
            case "Insect Duplicating":
                currentSpore = new InsectDuplicatingSpore("MEG KELL");
                break;
            case "Paralyzing":
                currentSpore = new ParalyzingSpore("MEG KELL");
                break;
            default:
                currentSpore = new AcceleratorSpore("MEG KELL");
                break;
        }

        //Akkor lehet csak kilőni, ha nincs
        if (hasSpore && currentSpore != null && numberOfDispersions<5) {
            tecton.addSpore(currentSpore);
            numberOfDispersions++;
            hasSpore = false;
            currentSpore = null;
            restartSporeGrowth();
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

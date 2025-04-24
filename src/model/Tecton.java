package model;

import view.TectonView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** A Tecton class a játéktér egy területi egységének reprezentációja. */
public class Tecton implements Serializable {
    private TectonView tectonView;

    private String id;
    private int yarnLimit;
    private boolean mushroomPrevent;
    private boolean isKeepAlive;
    private Mushroom mushroom;
    private List<Insect> insects;
    private List<Yarn> yarns;
    private List<Spore> spores;
    private List<Tecton> neighbours;
    private boolean yarnAbsorption = false;

    public TectonView getTectonView() {
        return tectonView;
    }
    public void setTectonView(TectonView tectonView) {
        this.tectonView = tectonView;
    }

    public boolean getMushroomPrevent(){
        return mushroomPrevent;
    }

    public boolean getYarnAbsorption(){ return yarnAbsorption; }

    public boolean getIsKeepAlive(){ return isKeepAlive; }

    public void setYarnAbsorption(boolean yarnAbsorption){
        this.yarnAbsorption = yarnAbsorption;
    }

    public String getType() {
        if (isKeepAlive)        return "KeepAlive";
        if (mushroomPrevent)    return "MushroomPrevent";
        if (yarnAbsorption)     return "YarnAbsorbing";
        return yarnLimit > 1 ? "MultipleYarn"
                : "normal";
    }

    /**
     * A Tecton class konstruktora.
     *
     * @param yarnLimit A maximális gombafonal szám, ami a Tectonon lehet.
     * @param mushroomPrevent true, ha a Tectonon nem lehet gomba.
     * @param isKeepAlive true, ha a tekton életbentartó.
     */
    public Tecton(String id, int yarnLimit, boolean mushroomPrevent, boolean isKeepAlive) {
        tectonView = new TectonView(this);

        this.id = id;
        this.yarnLimit = yarnLimit;
        this.mushroomPrevent = mushroomPrevent;
        this.isKeepAlive = isKeepAlive;
        this.mushroom = null;
        this.insects = new ArrayList<>();
        this.yarns = new ArrayList<>();
        this.spores = new ArrayList<>();
        this.neighbours = new ArrayList<>(); // Inicializáljuk a listát
    }
    public void addYarn(Yarn y){
        //System.out.println(y.getId());
        yarns.add(y);
    }
    /**
     * Hozzáad egy gombát a Tectonhoz.
     *
     * @param mushroom A hozzáadandó gomba.
     */
    public void addMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }

    /**
     * Eltávolít egy gombát a Tectonról.
     *
     * @param mushroom Az eltávolítandó gomba.
     */
    public void removeMushroom(Mushroom mushroom) {
        this.mushroom = null;
        //System.out.println("Mushroom removed: " + mushroom);
    }

    /**
     * Hozzáad egy rovart a Tectonhoz.
     *
     * @param insect A hozzáadandó rovar.
     */
    public void addInsect(Insect insect) {
        this.insects.add(insect);
        insect.setCurrentPlace(this);
    }

    /**
     * Eltávolít egy rovart a Tectonról.
     *
     * @param insect Az eltávolítandó rovar.
     */
    public void removeInsect(Insect insect) {
        this.insects.remove(insect);
        //System.out.println("Insect removed: " + insect);
    }

    /**
     * Ellenőrzi, hogy a paraméterként kapott Tecton szomszédos-e ezzel a Tectonnal.
     *
     * @param targetTecton A vizsgálandó Tecton.
     * @return true, ha a Tecton szomszédos, false egyébként.
     */
    public boolean isNeighbour(Tecton targetTecton) {
        return this.neighbours.contains(targetTecton); // Egyszerű ellenőrzés
    }

    /**
     * Hozzáad egy gombafonalat a Tectonhoz.
     *
     * @param yarn A hozzáadandó gombafonal.
     */
    public void growYarn(Yarn yarn) {
        if(yarns.contains(yarn)) {
            return;
        }
        if (yarn != null) {
            yarn.addTecton(this);
            yarns.add(yarn);
        }
    }

    /**
     * Eltávolít egy gombafonalat a Tectonról.
     *
     * @param yarn Az eltávolítandó gombafonal.
     */
    public void removeYarn(Yarn yarn, Tecton masik) {
        yarn.split(this, masik);
        yarns.remove(yarn);
        //System.out.println("Yarn removed: " + yarn);
    }

    /**
     * Hozzáad egy spórát a Tectonhoz.
     *
     * @param spore A hozzáadandó spóra.
     */
    public void addSpore(Spore spore) {
        this.spores.add(spore);
        //System.out.println("Spore added: " + spore);
    }

    /**
     * Eltávolít egy spórát a Tectonról.
     *
     * @param spore Az eltávolítandó spóra.
     */
    public void removeSpore(Spore spore) {
        this.spores.remove(spore);
        //System.out.println("Spore removed: " + spore);
    }

    /**
     * Visszaadja a Tectonhoz tartozó maximális gombafonal limitet.
     *
     * @return A gombafonal limit.
     */
    public int getYarnLimit() {
        return yarnLimit;
    }


    /**
     * Módosítja a Tectonhoz tartozó maximális gombafonal limitet.
     */
    public void setYarnLimit(int yl) {
        yarnLimit=yl;
    }

    /**
     * Visszaadja, hogy a Tectonon lehet-e gomba.
     *
     * @return true, ha a Tectonon nem lehet gomba, false egyébként.
     */
    public boolean isMushroomPrevent() {
        return mushroomPrevent;
    }

    /**
     * Visszaadja a Tectonon lévő gombafonalak listáját.
     *
     * @return A gombafonalak listája.
     */
    public List<Yarn> getYarns() {
        return yarns;
    }

    /**
     * Visszaadja a Tectonon lévő spórák listáját.
     *
     * @return A spórák listája.
     */
    public List<Spore> getSpores() {
        return spores;
    }

    /**
     * Visszaadja a Tectonon lévő gombát.
     *
     * @return A gomba.
     */
    public Mushroom getMushroom() {
        return mushroom;
    }

    /**
     * Visszaadja a Tectonon lévő bogarakat.
     *
     * @return A bogarak listája.
     */
    public List<Insect> getInsects() { return insects; }

    /**
     * Hozzáad egy szomszédos Tectont ehhez a Tectonhoz.
     *
     * @param tecton A hozzáadandó szomszédos Tecton.
     */
    public void addNeighbour(Tecton tecton) {
        if (!this.neighbours.contains(tecton)) {
            this.neighbours.add(tecton);
            tecton.addNeighbour(this); // A szomszédság kölcsönös
        }
    }

    /**
     * Eltávolít egy szomszédos Tectont erről a Tectonról.
     *
     * @param tecton Az eltávolítandó szomszédos Tecton.
     */
    public void removeNeighbour(Tecton tecton) {
        if (this.neighbours.contains(tecton)) {
            this.neighbours.remove(tecton);
            // Prevent recursive calls by checking if tecton still has this as a neighbor
            if (tecton.getNeighbours().contains(this)) {
                tecton.getNeighbours().remove(this); // Directly remove without recursion
            }
        }
    }

    /**
     * Visszaadja a Tecton szomszédos Tectonjainak listáját.
     *
     * @return A szomszédos Tectonok listája.
     */
    public List<Tecton> getNeighbours() {
        return neighbours;
    }

    public void printfNameAndId() {
        System.out.print("Tecton" + id);
    }

    /**
     * Futtatja a Tecton speciális hatását.
     * Implementáció: a Tecton típusától függően
     * eltérő hatást fejt ki a gombafonalra.
     *
     * @param yarn A gombafonal, amire a hatást kifejti.
     */
    public void runEffect(Yarn yarn) {}

    public String getId() {
        return id;
    }

    public boolean isConnectedWithYarn(Tecton other) {
        for (Yarn yarn : yarns) {
            if (yarn.getTectons().contains(other)) {
                return true;
            }
        }
        return false;
    }
}

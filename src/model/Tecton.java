package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** A Tecton class a játéktér egy területi egységének reprezentációja. */
public class Tecton extends BaseModel implements Serializable {
    private String id; //teszteléshez használt azonosító
    private final int yarnLimit;
    private final boolean mushroomPrevent;
    private final boolean isKeepAlive;
    private Mushroom mushroom;
    private List<Insect> insects;
    private List<Yarn> yarns;
    private List<Spore> spores;
    private List<Tecton> neighbours;
    private boolean yarnAbsorption = false;


    /**
     * Visszajelzést ad arról, hogy az tectonon lehet-e gombát növeszteni.
     *
     * @return true, ha gombát nem lehet nöeszteni, különben false.
     */
    public boolean getMushroomPrevent() {
        return mushroomPrevent;
    }

    /**
     * Visszajelzést ad arról, hogy a tecton képes-e a fonalak felszívására.
     *
     * @return true, ha képes felszíni a fonalakat, különben false.
     */
    public boolean getYarnAbsorption() {
        return yarnAbsorption;
    }

    /**
     * Visszaadja, hogy az tecton fonaléletben tartó-e.
     *
     * @return true ha igen, false ha nem.
     */
    public boolean getIsKeepAlive() {
        return isKeepAlive;
    }

    /**
     * Beállítja, hogy a tecton képes-e fonalak felszívására.
     *
     * @param yarnAbsorption true, ha képes, különben false.
     */
    public void setYarnAbsorption(boolean yarnAbsorption) {
        this.yarnAbsorption = yarnAbsorption;
    }


    //Tecton típusának nevét adja vissza, CSAK tesztelésnál hazsnáljuk.
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
    public Tecton( int yarnLimit, boolean mushroomPrevent, boolean isKeepAlive) {
        this.yarnLimit = yarnLimit;
        this.mushroomPrevent = mushroomPrevent;
        this.isKeepAlive = isKeepAlive;
        this.mushroom = null;
        this.insects = new ArrayList<>();
        this.yarns = new ArrayList<>();
        this.spores = new ArrayList<>();
        this.neighbours = new ArrayList<>(); // Inicializáljuk a listát
    }

    /**
     * A Tecton class konstruktora.
     *
     * @param id teszteléshez használt azonosító
     * @param yarnLimit A maximális gombafonal szám, ami a Tectonon lehet.
     * @param mushroomPrevent true, ha a Tectonon nem lehet gomba.
     * @param isKeepAlive true, ha a tekton életbentartó.
     */
    public Tecton(String id, int yarnLimit, boolean mushroomPrevent, boolean isKeepAlive) {
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
        yarns.add(y);
        y.addTecton(this);
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
            if(!yarns.contains(yarn)) {
                yarns.add(yarn);
            }
        }
        notifyObservers();
    }

    /**
     * Eltávolít egy gombafonalat a Tectonról.
     *
     * @param yarn Az eltávolítandó gombafonal.
     */
    public void removeYarn(Yarn yarn, Tecton masik) {
        yarn.split(this, masik);
        yarns.remove(yarn);
    }

    /**
     * Hozzáad egy spórát a Tectonhoz.
     *
     * @param spore A hozzáadandó spóra.
     */
    public void addSpore(Spore spore) {
        this.spores.add(spore);
    }

    /**
     * Eltávolít egy spórát a Tectonról.
     *
     * @param spore Az eltávolítandó spóra.
     */
    public void removeSpore(Spore spore) {
        this.spores.remove(spore);
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
     * Visszaadja a Tectonon lévő rovarokat.
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

    /**
     * Futtatja a Tecton speciális hatását.
     * Implementáció: a Tecton típusától függően
     * eltérő hatást fejt ki a gombafonalra.
     *
     * @param yarn A gombafonal, amire a hatást kifejti.
     */
    public void runEffect(Yarn yarn) {
        //Nincs különleges effectje, ezért üres
    }

    /**
     * Visszaadja az tecton egyedi azonosítóját.
     *
     * @return Az azonosító.
     */
    public String getId() {
        return id;
    }

    /**
     * Megállapítja, hogy ez a  Tecton össze van-e kötve egy másik tectonnal fonálon kereztül.
     *
     * @param other A másik Tecton, amelyhez való kapcsolódást vizsgáljuk.
     * @return true, ha üssze vannak kötve, különben false.
     */
    public boolean isConnectedWithYarn(Tecton other) {
        for (Yarn yarn : yarns) {
            if (yarn.getTectons().contains(other)) {
                return true;
            }
        }
        return false;
    }

    public List<Tecton> tectonsConnectedWithYarn(){
        List<Tecton> yarnNeighbours = new ArrayList<>();
        for(Tecton t :neighbours){
            if(isConnectedWithYarn(t)){
                yarnNeighbours.add(t);
            }
        }
        return yarnNeighbours;
    }

    public List<Tecton> tectonsConnectedByTheYarn(Yarn y){
        List<Tecton> tectons = new ArrayList<>();
        for(Tecton t: neighbours){
            if(t.yarns.contains(y)&&!tectons.contains(t)){
                tectons.add(t);
            }
        }
        return tectons;
    }

    @Override
    public String toString(){
        return id;
    }
}

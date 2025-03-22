import java.util.ArrayList;
import java.util.List;

/** A Tecton class a játéktér egy területi egységének reprezentációja. */
public class Tecton {

    private int id;
    private int yarnLimit;
    private boolean mushroomPrevent;
    private Mushroom mushroom;
    private List<Insect> insects;
    private List<Yarn> yarns;
    private List<Spore> spores;
    //private Tecton currentPlace;
    private List<Tecton> neighbours; // Új attribútum: szomszédok listája

    public boolean getMushroomPrevent(){
        return mushroomPrevent;
    }



    /**
     * A Tecton class konstruktora.
     *
     * @param yarnLimit A maximális gombafonal szám, ami a Tectonon lehet.
     * @param mushroomPrevent true, ha a Tectonon nem lehet gomba.
     */
    public Tecton(int id, int yarnLimit, boolean mushroomPrevent) {
        this.id = id;
        this.yarnLimit = yarnLimit;
        this.mushroomPrevent = mushroomPrevent;
        this.mushroom = null;
        this.insects = new ArrayList<>();
        this.yarns = new ArrayList<>();
        this.spores = new ArrayList<>();
        this.neighbours = new ArrayList<>(); // Inicializáljuk a listát
    }

    /**
     * Hozzáad egy gombát a Tectonhoz.
     *
     * @param mushroom A hozzáadandó gomba.
     */
    public void addMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
        System.out.println("Mushroom added: " + mushroom);
    }

    /**
     * Eltávolít egy gombát a Tectonról.
     *
     * @param mushroom Az eltávolítandó gomba.
     */
    public void removeMushroom(Mushroom mushroom) {
        this.mushroom = null;
        System.out.println("Mushroom removed: " + mushroom);
    }

    /**
     * Hozzáad egy rovart a Tectonhoz.
     *
     * @param insect A hozzáadandó rovar.
     */
    public void addInsect(Insect insect) {
        this.insects.add(insect);
        System.out.println(id + "Insect added: " + insect);
    }

    /**
     * Eltávolít egy rovart a Tectonról.
     *
     * @param insect Az eltávolítandó rovar.
     */
    public void removeInsect(Insect insect) {
        this.insects.remove(insect);
        System.out.println(id + "Insect removed: " + insect);
    }

    /**
     * Ellenőrzi, hogy a paraméterként kapott Tecton szomszédos-e ezzel a Tectonnal.
     *
     * @param targetTecton A vizsgálandó Tecton.
     * @return true, ha a Tecton szomszédos, false egyébként.
     */
    public boolean isNeighbour(Tecton targetTecton) {
        System.out.println("Checking if " + targetTecton + " is neighbour.");
        return this.neighbours.contains(targetTecton); // Egyszerű ellenőrzés
    }

    /**
     * Hozzáad egy gombafonalat a Tectonhoz.
     *
     * @param yarn A hozzáadandó gombafonal.
     */
    public void growYarn(Yarn yarn) {
        //yarnhoz hozzadjuk a tectont
        yarn.addTecton(this);
        yarns.add(yarn);
        System.out.println(id + " Yarn added: " + yarn);
    }

    /**
     * Eltávolít egy gombafonalat a Tectonról.
     *
     * @param yarn Az eltávolítandó gombafonal.
     */
    public void removeYarn(Yarn yarn) {
        yarn.split(this);
        yarns.remove(yarn);
        System.out.println("Yarn removed: " + yarn);
    }

    /**
     * Hozzáad egy spórát a Tectonhoz.
     *
     * @param spore A hozzáadandó spóra.
     */
    public void addSpore(Spore spore) {
        this.spores.add(spore);
        System.out.println("Spore added: " + spore);
    }

    /**
     * Eltávolít egy spórát a Tectonról.
     *
     * @param spore Az eltávolítandó spóra.
     */
    public void removeSpore(Spore spore) {
        this.spores.remove(spore);
        System.out.println("Spore removed: " + spore);
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
     *
     *
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

    /**
     * Futtatja a Tecton speciális hatását.
     * Implementáció: a Tecton típusától függően
     * eltérő hatást fejt ki a gombafonalra.
     *
     * @param yarn A gombafonal, amire a hatást kifejti.
     */
    public void runEffect(Yarn yarn) {}

    public int getId() {
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

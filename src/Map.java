import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** A Map class a játéktér reprezentációja. */
public class Map {
    private List<Tecton> tectons;
    private Random random; // Random szám generátor

    /** Konstruktor */
    public Map() {
        this.tectons = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Generálja a Tectonokat és beállítja a szomszédos Tectonokat.
     */
    public void generate() {
        // Példa implementáció: kézzel létrehozott gráf
        // Valós implementációban ezt valamilyen konfigurációból kell beolvasni!

        Tecton tecton1 = new Tecton(1, 1, false);
        Tecton tecton2 = new Tecton(2, 1, false);
        Tecton tecton3 = new Tecton(3, 2, true);
        Tecton tecton4 = new Tecton(4, 1, false);

        tectons.add(tecton1);
        tectons.add(tecton2);
        tectons.add(tecton3);
        tectons.add(tecton4);

        tecton1.addNeighbour(tecton2);
        tecton2.addNeighbour(tecton1);
        tecton2.addNeighbour(tecton3);
        tecton3.addNeighbour(tecton2);
        tecton3.addNeighbour(tecton4);
        tecton4.addNeighbour(tecton3);

        System.out.println("Map generated with " + tectons.size() + " tectons.");
    }

    /**
     * kettéhasítja a kiválasztott Tectont. Implementáció: Létre kell hozni két új Tectont, és a régit
     * el kell távolítani. Az új Tectonoknak be kell állítani a szomszédait.
     *
     * @param tecton A kettéhasítandó Tecton.
     */
    public void splitting(Tecton tecton) {
        if (!tectons.contains(tecton)) {
            System.out.println("Tecton not found on the map.");
            return;
        }

        if(tecton.getMushroom() != null) {
            tecton.removeMushroom(tecton.getMushroom());
        }

        // A régi Tectonon lévő gombafonalakat el kell vágni
        for (Yarn yarn : new ArrayList<>(tecton.getYarns())) {
            tecton.removeYarn(yarn);
        }

        // Létrehozunk két új Tectont
        Tecton newTecton1 = new Tecton(this.random.nextInt(), tecton.getYarnLimit(), tecton.isMushroomPrevent());
        Tecton newTecton2 = new Tecton(this.random.nextInt(), tecton.getYarnLimit(), tecton.isMushroomPrevent());

        // Beállítjuk az új Tectonok szomszédságát (a régi Tecton szomszédai + egymás)
        List<Tecton> originalNeighbours = new ArrayList<>(tecton.getNeighbours());
        for (Tecton neighbour : originalNeighbours) {
            neighbour.removeNeighbour(tecton);
            neighbour.addNeighbour(newTecton1);
            neighbour.addNeighbour(newTecton2);

            newTecton1.addNeighbour(neighbour);
            newTecton2.addNeighbour(neighbour);
        }

        // Egymás szomszédai is
        newTecton1.addNeighbour(newTecton2);
        newTecton2.addNeighbour(newTecton1);

    /*    // A régi Tectonon lévő gombákat, rovarokat, spórákat átrakjuk az új Tectonokra
            Mushroom mushroom = tecton.getMushroom();
            tecton.removeMushroom(mushroom);

            // Eldönthetjük, hogy melyikre kerüljön (random)
            if (random.nextBoolean()) {
                newTecton1.addMushroom(mushroom);
            } else {
                newTecton2.addMushroom(mushroom);
            }


        for (Insect insect : new ArrayList<>(tecton.getInsects())) {
            tecton.removeInsect(insect);

            // Eldönthetjük, hogy melyikre kerüljön (random)
            if (random.nextBoolean()) {
                newTecton1.addInsect(insect);
            } else {
                newTecton2.addInsect(insect);
            }
        }

        for (Spore spore : new ArrayList<>(tecton.getSpores())) {
            tecton.removeSpore(spore);
            // Eldönthetjük, hogy melyikre kerüljön (random)
            if (random.nextBoolean()) {
                newTecton1.addSpore(spore);
            } else {
                newTecton2.addSpore(spore);
            }
        }*/

        // A régi Tectont eltávolítjuk, az újakat hozzáadjuk a tectons listához
        tectons.remove(tecton);
        tectons.add(newTecton1);
        tectons.add(newTecton2);

        System.out.println(
                "Splitting selected tecton: "
                        + tecton
                        + " into "
                        + newTecton1
                        + " and "
                        + newTecton2);
    }

    /**
     * Frissíti a pálya állapotát (pl. felszívja a gombafonalakat a YarnAbsorbantTecton-okról).
     * Implementáció: Implementáció: végigmegy a Tectonokon, és frissíti az állapotukat.
     */
    public void refresh() {
        for (Tecton tecton : tectons) {
            // Példa: végigmegyünk a gombafonalakon, és futtatjuk a Tecton hatását rájuk
            for (Yarn yarn : new ArrayList<>(tecton.getYarns())) {
                tecton.runEffect(yarn);
            }
        }
        System.out.println("Map refreshed.");
    }

    /**
     * Visszaadja a pályán lévő Tectonok listáját.
     *
     * @return A {@link List} típusú, {@link Tecton} elemeket tartalmazó Tectonok listája.
     */
    public List<Tecton> getTectons() {
        return tectons;
    }
}

import model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** A Map class a játéktér reprezentációja. */
public class Map implements Serializable {
    private List<Tecton> tectons;
    private Random random; // Random szám generátor

    /** Konstruktor */
    public Map() {
        this.tectons = new ArrayList<>();
        this.random = new Random();
    }

    //new
    public void addTecton(Tecton t){
        tectons.add(t);
    }
    /**
     * Generálja a Tectonokat és beállítja a szomszédos Tectonokat.
     */
    public void generate() {
        // Példa implementáció: kézzel létrehozott gráf
        // Valós implementációban ezt valamilyen konfigurációból kell beolvasni!

        Tecton tecton1 = new Tecton("t1", 1, false, false);
        Tecton tecton2 = new Tecton("t2", 1, false, false);
        Tecton tecton3 = new Tecton("t3", 2, true, false);
        Tecton tecton4 = new Tecton("t4", 1, false, false);
        Tecton tecton5 = new YarnAbsorbantTecton( "t5",1, false, false);
        Tecton tecton6=new Tecton("t6",2,false, false);

        tectons.add(tecton1);
        tectons.add(tecton2);
        tectons.add(tecton3);
        tectons.add(tecton4);
        tectons.add(tecton5);
        tectons.add(tecton6);

        tecton1.addNeighbour(tecton2);
        tecton2.addNeighbour(tecton1);
        tecton2.addNeighbour(tecton3);
        tecton3.addNeighbour(tecton2);
        tecton3.addNeighbour(tecton4);
        tecton4.addNeighbour(tecton3);
        tecton5.addNeighbour(tecton4);
        tecton4.addNeighbour(tecton5);
        tecton6.addNeighbour(tecton5);
        tecton5.addNeighbour(tecton6);

        System.out.println("Map generated with " + tectons.size() + " tectons.");
    }

    /**
     * kettéhasítja a kiválasztott Tectont. Implementáció: Létre kell hozni két új Tectont, és a régit
     * el kell távolítani. Az új Tectonoknak be kell állítani a szomszédait.
     *
     * @param tecton A kettéhasítandó Model.Tecton.
     */
    public void splitting(Tecton tecton) {
        if (!tectons.contains(tecton)) {
            return;
        }

        if(tecton.getMushroom() != null) {
            tecton.removeMushroom(tecton.getMushroom());
        }

        for (Spore spore : new ArrayList<>(tecton.getSpores())) {
            tecton.removeSpore(spore);
        }

        Random random = new Random();
        List<Insect> templist = tecton.getInsects();

        //Fonalakat eltavolitjuk
        for (Yarn yarn : new ArrayList<>(tecton.getYarns())) {
            yarn.getTectons().remove(tecton);
            tecton.getYarns().remove(yarn);
        }

        // Létrehozunk két új Tectont
        Tecton newTecton1 = new Tecton(tecton.getId()+ "_1", tecton.getYarnLimit(), tecton.isMushroomPrevent(), tecton.getIsKeepAlive());
        Tecton newTecton2 = new Tecton(tecton.getId()+ "_2", tecton.getYarnLimit(), tecton.isMushroomPrevent(), tecton.getIsKeepAlive());

        // A rovarokat átrakjuk random tektonra
        for(Insect insect : templist) {

            // Eldönthetjük, hogy melyikre kerüljön (random)
            if (random.nextBoolean()) {
                newTecton1.addInsect(insect);
            } else {
                newTecton2.addInsect(insect);
            }
        }

        for(int i=0;i<templist.size();i++) {
            tecton.removeInsect(tecton.getInsects().get(i));
        }

        // Beállítjuk az új Tectonok szomszédságát (a régi Model.Tecton szomszédai + egymás)
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

        // A régi Tectont eltávolítjuk, az újakat hozzáadjuk a tectons listához
        tectons.remove(tecton);
        tectons.add(newTecton1);
        tectons.add(newTecton2);
    }

    /**
     * CSAK TESZTELÉSNÉL VAN HASZNÁLVA, mivel a rovar is egy random tektonra kerülne,
     * de mivel nincs randomozáió így biztosan az újonnan léttrejött tektonok közül az elsőre kerül
     * A teszt teljesen megegyezik a rendes randolmizást splittingel, csak a rovar áttevésben különbözik
     * kettéhasítja a kiválasztott Tectont. Implementáció: Létre kell hozni két új Tectont, és a régit
     * el kell távolítani. Az új Tectonoknak be kell állítani a szomszédait.
     *
     * @param tecton A kettéhasítandó Model.Tecton.
     */

    public void notRandomizedSplitting(Tecton tecton) {
        if (!tectons.contains(tecton)) {
            return;
        }

        if(tecton.getMushroom() != null) {
            tecton.removeMushroom(tecton.getMushroom());
        }

        for (Spore spore : new ArrayList<>(tecton.getSpores())) {
            tecton.removeSpore(spore);
        }

        Random random = new Random();
        List<Insect> templist = tecton.getInsects();
        for (Yarn yarn : new ArrayList<>(tecton.getYarns())) {
            tecton.removeYarn(yarn, tecton);
        }

        // Létrehozunk két új Tectont
        Tecton newTecton1 = new Tecton(tecton.getId()+ "_1", tecton.getYarnLimit(), tecton.isMushroomPrevent(), tecton.getIsKeepAlive());
        Tecton newTecton2 = new Tecton(tecton.getId()+ "_2", tecton.getYarnLimit(), tecton.isMushroomPrevent(), tecton.getIsKeepAlive());

        // A rovarokat átrakjuk random tektonra
        for(Insect insect : templist) {

            // Eldönthetjük, hogy melyikre kerüljön (random)
            newTecton1.addInsect(insect);
        }

        for(int i=0;i<templist.size();i++) {
            tecton.removeInsect(tecton.getInsects().get(i));
        }

        // Beállítjuk az új Tectonok szomszédságát (a régi Model.Tecton szomszédai + egymás)
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

        // A régi Tectont eltávolítjuk, az újakat hozzáadjuk a tectons listához
        tectons.remove(tecton);
        tectons.add(newTecton1);
        tectons.add(newTecton2);
    }

    /**
     * Frissíti a pálya állapotát (pl. felszívja a gombafonalakat a Model.YarnAbsorbantTecton-okról).
     * Implementáció: Implementáció: végigmegy a Tectonokon, és frissíti az állapotukat.
     */
    public void refresh() {
        for (Tecton tecton : tectons) {
            // Példa: végigmegyünk a gombafonalakon, és futtatjuk a Model.Tecton hatását rájuk
            for (Yarn yarn : new ArrayList<>(tecton.getYarns())) {
                yarn.runEffect();
                tecton.runEffect(yarn);
            }

            //Spóra növesztési idejét csökkenti
            if(tecton.getMushroom() != null) {
                tecton.getMushroom().setNewSporeGrowth(tecton.getMushroom().getNewSporeGrowth()-1);
            }

            //Rovarokról az effectet eltünteti
            for (Insect insect : tecton.getInsects()) {
                insect.resetEffect();
            }
        }

        //Fonalakat szívja fel
        noConnection();
    }

    /**
     * Visszaadja a pályán lévő Tectonok listáját.
     *
     * @return A {@link List} típusú, {@link Tecton} elemeket tartalmazó Tectonok listája.
     */
    public List<Tecton> getTectons() {
        return tectons;
    }

    public void setTectons(List<Tecton> ts) {
        tectons = ts;
    }

    public void noConnection() {
        for (Tecton tecton : tectons) {
            List<Yarn> yarnsToRemove = new ArrayList<>();
            List<Yarn> yarnsToKeepAlive = new ArrayList<>();

            for (Yarn yarn : tecton.getYarns()) {
                // Ellenőrizzük, hogy a fonal nincs-e csatlakoztatva gombához
                if (!yarnsToRemove.contains(yarn) && !yarnsToKeepAlive.contains(yarn)) {
                    if (!yarn.isConnected()) {
                        // Ellenőrizzük, hogy a fonal tektonjai között van-e életbentartó tekton
                        boolean hasKeepAliveTecton = false;
                        for (Tecton yarnTecton : yarn.getTectons()) {
                            if (yarnTecton.getIsKeepAlive()) {
                                hasKeepAliveTecton = true;
                                yarnsToKeepAlive.add(yarn);
                                break;
                            }
                        }

                        // Csak akkor távolítjuk el a fonalat, ha nincs életbentartó tekton
                        if (!hasKeepAliveTecton) {
                            yarnsToRemove.add(yarn);
                        }
                    }
                }
            }

            for (Yarn yarn : yarnsToRemove) {
                tecton.getYarns().remove(yarn);
            }
        }
    }
}

package controller;

import model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** A controller.Map class a játéktér reprezentációja. */
public class Map  implements Serializable {
    private List<Tecton> tectons;
    private final Random random; // Random szám generátor

    /** Konstruktor */
    public Map() {
        this.tectons = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Hozzáadja a paramáterként kapott tectont a pályához
     * @param t A hozzáadandó Model.Tecton.
     */
    public void addTecton(Tecton t){
        tectons.add(t);
    }
    /**
     * Generálja a Tectonokat és beállítja a szomszédos Tectonokat.
     * Tesztelásnál manuálisan van minden pálya feléptve, nem ezzel a függvénnyel
     */
    public void generate(ArrayList<MushroomPicker> mps, ArrayList<Entomologist> ents) {
        // Példa implementáció: kézzel létrehozott gráf
        // Valós implementációban ezt valamilyen konfigurációból kell beolvasni!

        Tecton t1 = new Tecton("t1", 2, false, false);
        Tecton t2 = new Tecton("t2", 2, false, false);
        Tecton t3 = new Tecton("t3", 1, false, false);
        Tecton t4 = new Tecton("t4", 2, false, false);
        Tecton t5 = new Tecton("t5", 1, false, false);

        Tecton t6 = new Tecton("t6", 2, false, false);
        Tecton t7 = new Tecton("t7", 1, false, false);
        Tecton t8 = new Tecton("t8", 1, true, false);
        Tecton t9 = new Tecton("t9", 1, false, true);
        Tecton t10 = new Tecton("t10", 2, false, false);

        t1.addNeighbour(t2);
        t1.addNeighbour(t5);
        t2.addNeighbour(t6);
        t6.addNeighbour(t7);
        t4.addNeighbour(t3);
        t3.addNeighbour(t8);
        t5.addNeighbour(t9);
        t8.addNeighbour(t10);
        t9.addNeighbour(t10);

        Spore one=new AcceleratorSpore("1");
        Spore two=new AcceleratorSpore("2");
        Spore three=new AcceleratorSpore("3");

        t2.addSpore(one);
        t2.addSpore(two);
        t2.addSpore(three);


        Insect i1 = new Insect(t1,ents.get(0), "i1");
        Insect i2 = new Insect(t3,ents.get(1), "i2");

        ents.get(0).addInsect(i1);
        ents.get(1).addInsect(i2);

        //ITT a hiba
        Mushroom m1 = new Mushroom(t1, mps.get(0), "m1");
        Yarn y1 = new Yarn(m1,mps.get(0), "y1");
        mps.get(0).actionGrowYarn(t1,t2, y1);


        Mushroom m2 = new Mushroom(t3, mps.get(1), "m2");
        Yarn y3 = new Yarn(m2,mps.get(1), "y2");


        t1.addSpore(new AcceleratorSpore("basespor1"));

        t2.addSpore(new CutPreventingSpore("basespor2"));
        t2.addSpore(new AcceleratorSpore("basespor3"));
        t2.addSpore(new AcceleratorSpore("basespor4"));


        addTecton(t1);
        addTecton(t2);
        addTecton(t3);
        addTecton(t4);
        addTecton(t5);
        addTecton(t6);
        addTecton(t7);
        addTecton(t8);
        addTecton(t9);
        addTecton(t10);

        System.out.println("generate" + mps.get(0).getName() + "  " + mps.get(0).getOwnedMushrooms().size());
        System.out.println("generate" + mps.get(1).getName() + "  " + mps.get(1).getOwnedMushrooms().size());
        System.out.println("generateYARN " + mps.get(0).getName() + "  " + mps.get(0).getOwnedYarns().size());

        for(int i=0; i<mps.get(0).getOwnedYarns().size();i++) {

            System.out.print(" " + mps.get(0).getOwnedYarns().get(i).getId());
        }

        System.out.println("generateYARN " + mps.get(1).getName() + "  " + mps.get(1).getOwnedYarns().size());
        for(int i=0; i<mps.get(1).getOwnedYarns().size();i++) {

            System.out.print(" " + mps.get(1).getOwnedYarns().get(i).getId());
        }
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

        //Random random = new Random();
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
     * de mivel nincs randomozáció így biztosan az újonnan léttrejött tektonok közül az elsőre kerül
     * A teszt teljesen megegyezik a rendes randomizált splittingel, csak a rovar áttevésben különbözik
     * kettéhasítja a kiválasztott Tectont. Implementáció: Létre kell hozni két új Tectont, és a régit
     * el kell távolítani. Az új Tectonoknak be kell állítani a szomszédait és minden más tulajdonságát.
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

    /**
     * A pályán a kör végén megkeresi azokat a fonalakat, amik nem csatlakoznak gombához vagy életbentartó tectonhoz.
     * Eltávolítja ezeket a fonalakat a pályáról.
     */
    public void noConnection() {
        for (Tecton tecton : tectons) {
            List<Yarn> yarnsToRemove = new ArrayList<>();
            List<Yarn> yarnsToKeepAlive = new ArrayList<>();

            for (Yarn yarn : tecton.getYarns()) {
                // Ellenőrizzük, hogy a fonal nincs-e csatlakoztatva gombához
                if (!yarnsToRemove.contains(yarn) && !yarnsToKeepAlive.contains(yarn) && !yarn.isConnected()) {
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

            for (Yarn yarn : yarnsToRemove) {
                tecton.getYarns().remove(yarn);
                if(yarn.getPlayer().getOwnedYarns().contains(yarn)){
                    yarn.getPlayer().getOwnedYarns().remove(yarn);
                }

            }
            tecton.notifyObservers();

        }
    }
}

package model;

import view.EntomologistView;

import java.io.Serializable;
import java.util.*;

/**
 * Az Model.Entomologist osztály a játékos egyik lehetséges szereplőjét reprezentálja,
 * aki egy rovart (Model.Insect) irányít a játék során.
 */
public class Entomologist extends Player implements Serializable {
    private List<Insect> insects; // a rovarász játékos rovarja

    /**
     * Konstruktor az Model.Entomologist példány létrehozásához.
     * @param name   A játékos neve.
     * @param insect A játékoshoz tartozó rovar
     */
    public Entomologist(String name, Insect insect){
        super(name, 0);
        EntomologistView entomologistView = new EntomologistView(this);
        super.setPlayerView(entomologistView);
        insects = new ArrayList<>();
        insects.add(insect);
    }

    /**
     * Konstruktor az Model.Entomologist példány létrehozásához.
     * @param name   A játékos neve.
     * @param insect A játékoshoz tartozó rovar.
     * @param testId A tesztelésnél hasznélt azonosító
     */
    public Entomologist(String name, Insect insect, String testId){
        super(name, 0, testId);
        EntomologistView entomologistView = new EntomologistView(this);
        super.setPlayerView(entomologistView);
        insects = new ArrayList<>();
        insects.add(insect);
    }

    /**
     * Konstruktor az Model.Entomologist példány létrehozásához.
     * @param name   A játékos neve.
     * @param testId A tesztelésnél hasznélt azonosító
     */
    public Entomologist(String name, String testId){

        super(name, 0, testId);
        EntomologistView entomologistView = new EntomologistView(this);
        super.setPlayerView(entomologistView);
        insects = new ArrayList<>();
        //hard coded
        Tecton t1 = new Tecton("t1", 2, false, false);
        Tecton t2 = new Tecton("t2", 2, false, false);
        Tecton t3 = new Tecton("t3", 2, false, false);
        Tecton t4 = new Tecton("t4", 2, false, false);
        Tecton t5 = new Tecton("t5", 2, false, false);
        t1.addNeighbour(t2);
        t1.addNeighbour(t5);
        t4.addNeighbour(t3);

        Insect i1 = new Insect(t1,this, "i1");
        Insect i2 = new Insect(t3,this, "i2");
        insects.add(i1);
        insects.add(i2);

        MushroomPicker mp = new MushroomPicker("pista", "mp1");
        Mushroom m1 = new Mushroom(t1, mp, "m1");
        Yarn y1 = new Yarn(m1,mp, "y1");
        t1.addYarn(y1);
        t2.addYarn(y1);
        Yarn y2 = new Yarn(m1,mp, "y2");
        t1.addYarn(y2);
        t5.addYarn(y2);
        Yarn y3 = new Yarn(m1,mp, "y3");
        t3.addYarn(y3);
        t4.addYarn(y3);


    }
    /**
     * Visszaadja az entomológushoz tartozó rovart.
     *
     * @return A rovarok (Model.Insect) listáját.
     */
    public List<Insect> getInsect(){
      return insects;
    }

    public void addInsect(Insect insect){
        insects.add(insect);
    }

    /**
     * Az akció várakozás, amely nem csinál semmit.
     */
    public void actionWait(Insect insect) {
        return;
    }

    /**
     * Megpróbálja a rovart egy másik tectonra mozgatni.
     *
     * @param targetTecton A cél tecton, ahová a rovar mozogni próbál.
     */
    public void actionMove(Tecton targetTecton, Insect insect) {
        if(insect.getParalized()){
            //System.out.println("A rovar bénítóspóra hatása alatt van, a mozgás nem lehetéges");
            return;
        }
        if(insect.getDecelerated()){
            //System.out.println("A rovar lassító spóra hatása alatt van, a mozgás nem lehetséges");
            return;
        }
        boolean sikeres = insect.move(targetTecton);
        if(!sikeres){
            //System.out.println("Mozgás végrehajtása sikertelen");
        }
    }

    /**
     * A rovar elfogyaszt egy adott spórát, és a játékos pontokat kap érte.
     *
     * @param spore A megevésre szánt spóra.
     */
    public void actionEatSpore(Spore spore, Insect insect) {
        if(insect.getParalized()){
            return;
        }
        insect.eatSpore(spore);

        this.addPoints(spore.getNutrition());
    }

    /**
     * Megpróbálja a rovart egy adott fonalat elvágni.
     *
     * @param yarn A vágni kívánt fonal.
     */
    public void actionCutYarn(Yarn yarn, Insect insect, Tecton amerreVagunk) {
        if(insect.getParalized()){
            //System.out.println("A rovar bénítóspóra hatása alatt van, a fonalvágás nem lehetéges");
            actionWait(insect);
            return;
        }
        if(insect.getCutPrevented()){
            //System.out.println("A rovar vágásgátló spóra hatása alatt van, a fonalvágás nem lehetéges");
            actionWait(insect);
            return;
        }
        insect.cutYarn(yarn, amerreVagunk);
    }
}
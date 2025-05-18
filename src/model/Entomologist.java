package model;



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

        insects = new ArrayList<>();

        //hard coded

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
        System.out.println("Insect added");
        insects.add(insect);
    }

    /**
     * Az akció várakozás, amely nem csinál semmit.
     */
    public void actionWait(Insect insect) {
        System.out.println("ENTOMOLOGIST: " + getName() + " várakozik a " + insect.getId() + " rovarral");
        return;
    }

    /**
     * Megpróbálja a rovart egy másik tectonra mozgatni.
     *
     * @param targetTecton A cél tecton, ahová a rovar mozogni próbál.
     */
    public void actionMove(Tecton targetTecton, Insect insect) {
        if(insect.getParalized()){
            System.out.println("ENTOMOLOGIST: " + getName() + " - A rovar bénítóspóra hatása alatt van, a mozgás nem lehetéges");
            return;
        }
        if(insect.getDecelerated()){
            System.out.println("ENTOMOLOGIST: " + getName() + " - A rovar lassító spóra hatása alatt van, a mozgás nem lehetséges");
            return;
        }
        System.out.println("ENTOMOLOGIST: " + getName() + " megpróbálja mozgatni a " + insect.getId() + " rovart a " + targetTecton + " tectonra");
        boolean sikeres = insect.move(targetTecton);
        if(!sikeres){
            System.out.println("ENTOMOLOGIST: " + getName() + " - Mozgás végrehajtása sikertelen");
        } else {
            System.out.println("ENTOMOLOGIST: " + getName() + " - Mozgás végrehajtása sikeres");   
        }
        notifyObservers();
    }

    /**
     * A rovar elfogyaszt egy adott spórát, és a játékos pontokat kap érte.
     *
     * @param spore A megevésre szánt spóra.
     */
    public void actionEatSpore(Spore spore, Insect insect) {
        if(insect.getParalized()){
            System.out.println("ENTOMOLOGIST: " + getName() + " - A rovar bénítóspóra hatása alatt van, a spóra evés nem lehetséges");
            return;
        }
        System.out.println("ENTOMOLOGIST: " + getName() + " megpróbálja megenni a " + spore + " spórát a " + insect.getId() + " rovarral");
        insect.eatSpore(spore);

        this.addPoints(spore.getNutrition());
        System.out.println("ENTOMOLOGIST: " + getName() + " " + spore.getNutrition() + " pontot szerzett a spóra elfogyasztásával");

        notifyObservers();
    }

    /**
     * Megpróbálja a rovart egy adott fonalat elvágni.
     *
     * @param yarn A vágni kívánt fonal.
     */
    public void actionCutYarn(Yarn yarn, Insect insect, Tecton amerreVagunk) {
        if(insect.getParalized()){
            System.out.println("ENTOMOLOGIST: " + getName() + " - A rovar bénítóspóra hatása alatt van, a fonalvágás nem lehetéges");
            actionWait(insect);
            return;
        }
        if(insect.getCutPrevented()){
            System.out.println("ENTOMOLOGIST: " + getName() + " - A rovar vágásgátló spóra hatása alatt van, a fonalvágás nem lehetséges");
            actionWait(insect);
            return;
        }
        System.out.println("ENTOMOLOGIST: " + getName() + " megpróbálja elvágni a " + yarn + " fonalat a " + insect.getId() + " rovarral a " + amerreVagunk + " tecton irányába");
        insect.cutYarn(yarn, amerreVagunk);

        notifyObservers();
    }
}

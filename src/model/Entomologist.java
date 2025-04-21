package model;

import java.util.*;

/**
 * Az Model.Entomologist osztály a játékos egyik lehetséges szereplőjét reprezentálja,
 * aki egy rovart (Model.Insect) irányít a játék során.
 */
public class Entomologist extends Player{
    private List<Insect> insects; // a rovarász játékos rovarja

    /**
     * Konstruktor az Model.Entomologist példány létrehozásához.
     *
     * @param name   A játékos neve.
     * @param insect A játékoshoz tartozó rovar.
     */
    public Entomologist(String name, Insect insect, String testId){
        super(name, 0, testId);
        insects = new ArrayList<>();
        insects.add(insect);
    }
    public Entomologist(String name, String testId){

        super(name, 0, testId);
        insects = new ArrayList<>();
    }
    /**
     * Visszaadja az entomológushoz tartozó rovart.
     *
     * @return A rovar (Model.Insect) objektum.
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
           // System.out.println("A rovar bénítóspóra hatása alatt van, az evés nem lehetéges");
            return;
        }
        insect.eatSpore(spore);

        //Pontadás a megevett spora alapjan
        //TODO - pontadas megcsinalasa
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
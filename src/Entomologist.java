import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Az Entomologist osztály a játékos egyik lehetséges szereplőjét reprezentálja,
 * aki egy rovart (Insect) irányít a játék során.
 */
public class Entomologist extends Player{
    private Insect insect; // a rovarász játékos rovarja

    /**
     * Konstruktor az Entomologist példány létrehozásához.
     *
     * @param name   A játékos neve.
     * @param insect A játékoshoz tartozó rovar.
     */
    public Entomologist(String name, Insect insect){
        super(name, 0);
        this.insect = insect;
    }

    /**
     * Visszaadja az entomológushoz tartozó rovart.
     *
     * @return A rovar (Insect) objektum.
     */
    public Insect getInsect(){
      return insect;
    }

    /**
     * Az akció várakozás, amely nem csinál semmit.
     */
    public void actionWait() {
        return;
    }

    /**
     * Az akció figyelés, amely nem csinál semmit.
     */
    public void actionWatch() {return;}

    /**
     * Megpróbálja a rovart egy másik tectonra mozgatni.
     *
     * @param targetTecton A cél tecton, ahová a rovar mozogni próbál.
     */
    public void actionMove(Tecton targetTecton) {
        Tecton currentTecton = insect.getCurrentPlace();
        if(insect.getParalized()){
            System.out.println("A rovar bénítóspóra hatása alatt van, a mozgás nem lehetéges");
            return;
        }
        if(insect.getDecelerated()){
            System.out.println("A rovar lassító spóra hatása alatt van, a mozgás nem lehetséges");
            return;
        }
        boolean sikeres = insect.move(targetTecton);
        if(!sikeres){
            System.out.println("Mozgás végrehajtása sikertelen");
        }
    }

    /**
     * A rovar elfogyaszt egy adott spórát, és a játékos pontokat kap érte.
     *
     * @param spore A megevésre szánt spóra.
     */
    public void actionEatSpore(Spore spore) {
        if(insect.getParalized()){
            System.out.println("A rovar bénítóspóra hatása alatt van, az evés nem lehetéges");
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
    public void actionCutYarn(Yarn yarn) {
        if(insect.getParalized()){
            System.out.println("A rovar bénítóspóra hatása alatt van, a fonalvágás nem lehetéges");
            actionWait();
            return;
        }
        if(insect.getCutPrevented()){
            System.out.println("A rovar vágásgátló spóra hatása alatt van, a fonalvágás nem lehetéges");
            actionWait();
            return;
        }
        insect.cutYarn(yarn);

    }
}
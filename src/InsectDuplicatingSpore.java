/**
 * A InsectDuplicatingSpore osztály a Spore osztályból származik, és a rovarduplikáló típusú spórát reprezentálja.
 * Ez a spóra képes létrehozni egy új rovart.
 * A spóra alapértelmezett tápanyag értéke 1, és a hatás neve "insectDuplicating".
 *
 * A spóra alkalmazza a rovarduplikáló hatást a rovarokra, ezzel létrehozva egy új rovart a spórát megevő rovar tuajdonosának
 * rovarjai közé a spórát megevő rovar tartózkodási helyén.
 */
public class InsectDuplicatingSpore extends Spore{

    /**
     * Alapértelmezett konstruktor, amely létrehozza a rovarduplikáló spórát.
     * Az alapértelmezett tápanyag érték 1, a hatás neve pedig "insectDuplicating".
     */
    public InsectDuplicatingSpore(){
        super(1, "insectDuplicating");
    }
    public InsectDuplicatingSpore(String testID){
        super(1, "insectDuplicating", testID);
    }

    @Override
    public void addEffect(Insect insect) {
        Insect newInsect = new Insect(insect.getCurrentPlace());
        insect.getOwner().addInsect(newInsect);
        insect.getCurrentPlace().addInsect(newInsect);
    }
}

package model;

import java.io.Serializable;

/**
 * A Model.InsectDuplicatingSpore osztály a Model.Spore osztályból származik, és a rovarduplikáló típusú spórát reprezentálja.
 * Ez a spóra képes létrehozni egy új rovart.
 * A spóra alapértelmezett tápanyag értéke 1, és a hatás neve "insectDuplicating".
 * A spóra alkalmazza a rovarduplikáló hatást a rovarokra, ezzel létrehozva egy új rovart a spórát megevő rovar tuajdonosának
 * rovarjai közé a spórát megevő rovar tartózkodási helyén.
 */
public class InsectDuplicatingSpore extends Spore implements Serializable {

    /**
     * Alapértelmezett konstruktor, amely létrehozza a rovarduplikáló spórát.
     * Az alapértelmezett tápanyag érték 1, a hatás neve pedig "insectDuplicating".
     */
    public InsectDuplicatingSpore(){
        super(1, "insectDuplicating");
    }

    /**
     * A tesztelésnél használt konstruktor, amely létrehozza a gyorsító spórát és beállítja a tesztelásnál használt azonosítót.
     * Teljesen egyezik az eredeti konstruktorral, az id beállítást leszámítva.
     * Az alapértelmezett tápanyag érték 1, a hatás neve pedig "insectDuplicating".
     *
     * @param testID a tesztelásnál használt azonosító
     */
    public InsectDuplicatingSpore(String testID){
        super(1, "insectDuplicating", testID);
    }

    /**
     * Léthoz mégy egy rovart, abból amelyik elfogyasztotta a spórát
     *
     * @param insect a rovar amelyik elfogyasztotta a spórát
     * */
    @Override
    public void addEffect(Insect insect) {
        Insect newInsect = new Insect(insect.getPlace(), insect.getOwner(), insect.getId()+"_dup");
        insect.getOwner().addInsect(newInsect);
    }
}

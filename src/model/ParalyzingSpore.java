package model;

import java.io.Serializable;

/**
 * A Model.ParalyzingSpore osztály a Model.Spore osztályból származik, és a bénító típusú spórát reprezentálja.
 * Ez a spóra képes megbénítani a rovarokat, megakadályozva azok bármilyen tevékenységét.
 * A spóra alapértelmezett tápanyag értéke 4, és a hatás neve "paralyzing".
 *
 * A spóra alkalmazza a bénító hatást a rovarokra.
 */
public class ParalyzingSpore extends Spore implements Serializable {
    /**
     * Alapértelmezett konstruktor, amely létrehozza a bénító spórát.
     * Az alapértelmezett tápanyag érték 4, a hatás neve pedig "paralyzing".
     */
    public ParalyzingSpore(){
        super(4, "paralyzing");
    }

    /**
     * A tesztelésnél használt konstruktor, amely létrehozza a bénító spórát és beállítja a tesztelésnél használt azonosítót.
     * Az alapértelmezett tápanyag érték 4, a hatás neve pedig "paralyzing".
     *
     * @param testID a tesztelésnél használt azonosító
     */
    public ParalyzingSpore(String testID){
        super(4, "paralyzing", testID);
    }
    /**
     * Alkalmazza a bénító hatást a megadott rovarra.
     * A metódus beállítja a rovar bénult állapotát.
     *
     * @param insect A rovar, amelyre a bénító hatást kell alkalmazni.
     */
    public void addEffect(Insect insect) {
        insect.setParalized(true);
        insect.setEffectDuration(2);
    }
}

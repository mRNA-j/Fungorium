package model;

import java.io.Serializable;

/**
* A Model.CutPreventingSpore osztály a Model.Spore osztályból származik, és a vágásgátló spórát reprezentálja.
* Ez a spóra képes megakadályozni a rovarokat a gombafoonalak elvágásában.
*
* A spóra alapértelmezett tápanyag értéke 2, és a hatás neve "cutpreventing".
*/
public class CutPreventingSpore extends Spore implements Serializable {
    /**
     * Alapértelmezett konstruktor, amely létrehozza a vágásgátló spórát.
     * Az alapértelmezett tápanyag érték 2, a hatás neve pedig "cutpreventing".
     */
    public CutPreventingSpore(){
        super(2, "cutpreventing");
    }

    /**
     * A tesztelésnél használt konstruktor, amely létrehozza a gyorsító spórát és beállítja a tesztelásnál használt azonosítót.
     * Teljesen egyezik az eredeti konstruktorral, az id beállítást leszámítva.
     * Az alapértelmezett tápanyag érték 1, a hatás neve pedig "cutpreventing".
     *
     * @param testId a tesztelásnál használt azonosító
     */
    public CutPreventingSpore(String testId){
        super(2, "cutpreventing", testId);
    }

    /**
     * Alkalmazza a vágásgátló hatást a megadott rovarra.
     * A metódus beállítja a rovar állapotát ennek megfelelően.
     *
     * @param insect A rovar, amelyen a vágásgátló hatás érvényesül.
     */
    public void addEffect(Insect insect) {
        insect.setCutPrevented(true);
        insect.setEffectDuration(2);
    }
}

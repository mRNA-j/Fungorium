package model;

/**
* A Model.CutPreventingSpore osztály a Model.Spore osztályból származik, és a vágásgátló spórát reprezentálja.
* Ez a spóra képes megakadályozni a rovarokat a gombafoonalak elvágásában.
*
* A spóra alapértelmezett tápanyag értéke 2, és a hatás neve "cutpreventing".
*/
public class CutPreventingSpore extends Spore {
    /**
     * Alapértelmezett konstruktor, amely létrehozza a vágásgátló spórát.
     * Az alapértelmezett tápanyag érték 2, a hatás neve pedig "cutpreventing".
     */
    public CutPreventingSpore(){
        super(2, "cutpreventing");
    }
    /**
     * Alkalmazza a vágásgátló hatást a megadott rovarra.
     * A metódus beállítja a rovar állapotát ennek megfelelően.
     *
     * @param insect A rovar, amelyen a vágásgátló hatás érvényesül.
     */
    public void addEffect(Insect insect) {
        insect.setCutPrevented(true);
    }
}
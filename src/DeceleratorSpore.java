/**
 * A DeceleratorSpore osztály a Spore osztályból származik, és a lassító típusú spórát reprezentálja.
 * Ez a spóra képes lelassítani a rovarokat.
 * A spóra alapértelmezett tápanyag értéke 3, és a hatás neve "decelerator".
 *
 * A spóra alkalmazza a lassító hatást a rovarokra, ezzel csökkentve azok mozgékonyságát.
 */
public class DeceleratorSpore extends Spore {
    /**
     * Alapértelmezett konstruktor, amely létrehozza a lassító spórát.
     * Az alapértelmezett tápanyag érték 3, a hatás neve pedig "decelerator".
     */
    public DeceleratorSpore(){
        super(3, "decelerator");
    }
    public DeceleratorSpore(String testID){
        super(3, "decelerator", testID);
    }
    /**
     * Alkalmazza a lassító hatást a megadott rovarra.
     * A metódus beállítja a rovar lassított állapotát.
     *
     * @param insect A rovar, amelyre a lassító hatást kell alkalmazni.
     */
    public void addEffect(Insect insect) {
        insect.setDecelerated(true);
    }
}
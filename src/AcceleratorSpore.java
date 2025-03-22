/**
 * Az AcceleratorSpore osztály a Spore osztályból származik, és a gyorsító típusú spórát reprezentálja.
 * Ez a spóra hatással van a rovarokra, és képes gyorsítani azok mozgását.
 * A spóra kétféle konstruktorral is létrehozható: egy alapértelmezett konstruktorral és egy paraméterezett konstruktorral.
 *
 * A konstruktorok beállítják a spóra tápanyagértékét és a megfelelő hatásnevet.
 * Az addEffect metódus alkalmazza a gyorsító hatást a rovaron.
 */
public class AcceleratorSpore extends Spore {
    /**
     * Alapértelmezett konstruktor, amely létrehozza a gyorsító spórát.
     * Az alapértelmezett tápanyag érték 1, a hatás neve pedig "accelerator".
     */
    public AcceleratorSpore(){
        super(1, "accelerator");
    }
    /**
     * Paraméterezett konstruktor, amely létrehozza a gyorsító spórát egy Tecton objektummal.
     * A tápanyag értéke 1, és a hatás neve "accelerating".
     *
     * @param tecton A Tecton objektum, amely szükséges a spóra inicializálásához.
     */
    public AcceleratorSpore(Tecton tecton) {
        super(1,"accelerator");
    }
    /**
     * Alkalmazza a gyorsító hatást a megadott rovarra.
     * A metódus beállítja a rovar gyorsított állapotát.
     *
     * @param insect A rovar, amelyre a gyorsító hatást alkalmazni kell.
     */
    public void addEffect(Insect insect) {
        insect.setAccelerated(true);
    }
}
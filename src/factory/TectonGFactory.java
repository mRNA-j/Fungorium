package factory;

import model.Tecton;
import view.TectonG;
import interfaces.ITectonGFactory;

/**
 * A TectonGFactory osztály felelős a TectonG objektumok létrehozásáért.
 * Implementálja az ITectonGFactory interfészt.
 */
public class TectonGFactory implements ITectonGFactory{

    /**
     * Létrehoz egy új TectonG objektumot a megadott paraméterekkel.
     * 
     * @param x A TectonG x koordinátája
     * @param y A TectonG y koordinátája
     * @param radius A TectonG sugara
     * @param id A TectonG azonosítója
     * @param t A Tecton modell objektum, amelyhez a TectonG tartozik
     * @return Az újonnan létrehozott TectonG objektum
     */
    @Override
    public TectonG onCreate(int x, int y, int radius, String id, Tecton t) {
        { return new TectonG(x, y, radius, id, t); }
    }
}

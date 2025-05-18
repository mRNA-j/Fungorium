package interfaces;

import model.Tecton;
import view.TectonG;

/**
 * Az ITectonGFactory interfész definiálja a TectonG objektumok
 * létrehozásáért felelős gyártó osztályok szerződését.
 * Ez az interfész biztosítja, hogy a különböző implementációk ugyanazt a
 * funkcionalitást nyújtsák a TectonG objektumok létrehozásához.
 */
public interface ITectonGFactory {
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
    TectonG onCreate(int x, int y, int radius, String id, Tecton t);
}

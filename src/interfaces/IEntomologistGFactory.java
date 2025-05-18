package interfaces;

import controller.Controller;
import model.Entomologist;
import view.EntomologistG;

/**
 * Az IEntomologistGFactory interfész definiálja az EntomologistG objektumok
 * létrehozásáért felelős gyártó osztályok szerződését.
 * Ez az interfész biztosítja, hogy a különböző implementációk ugyanazt a
 * funkcionalitást nyújtsák az EntomologistG objektumok létrehozásához.
 */
public interface IEntomologistGFactory {
    /**
     * Létrehoz egy új EntomologistG objektumot a megadott paraméterekkel.
     * 
     * @param entomologist A rovarász játékos modellje
     * @param nextPanelName A következő panel neve, amelyre váltani kell
     * @param controller A játék vezérlője
     * @return Az újonnan létrehozott EntomologistG objektum
     */
    EntomologistG onCreate(Entomologist entomologist, String nextPanelName, Controller controller);
}

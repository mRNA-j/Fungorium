package factory;

import controller.Controller;
import model.Entomologist;
import view.EntomologistG;
import interfaces.IEntomologistGFactory;

/**
 * Az EntomologistGFactory osztály felelős az EntomologistG objektumok létrehozásáért.
 * Implementálja az IEntomologistGFactory interfészt.
 */
public class EntomologistGFactory implements IEntomologistGFactory {
    
    /**
     * Létrehoz egy új EntomologistG objektumot a megadott paraméterekkel.
     * 
     * @param entomologist A rovarász játékos modellje
     * @param nextPanelName A következő panel neve, amelyre váltani kell
     * @param controller A játék vezérlője
     * @return Az újonnan létrehozott EntomologistG objektum
     */
    @Override
    public EntomologistG onCreate(Entomologist entomologist, String nextPanelName, Controller controller) {
        return new EntomologistG(entomologist, nextPanelName, controller);
    }
}

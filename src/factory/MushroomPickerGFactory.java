package factory;

import controller.Controller;
import model.MushroomPicker;
import view.MushroomPickerG;
import interfaces.IMushroomPickerGFactory;

/**
 * A MushroomPickerGFactory osztály felelős a MushroomPickerG objektumok létrehozásáért.
 * Implementálja az IMushroomPickerGFactory interfészt.
 */
public class MushroomPickerGFactory implements IMushroomPickerGFactory {
    
    /**
     * Létrehoz egy új MushroomPickerG objektumot a megadott paraméterekkel.
     * 
     * @param picker A gombaszedő játékos modellje
     * @param nextPanelName A következő panel neve, amelyre váltani kell
     * @param controller A játék vezérlője
     * @return Az újonnan létrehozott MushroomPickerG objektum
     */
    @Override
    public MushroomPickerG onCreate(MushroomPicker picker, String nextPanelName, Controller controller) {
        return new MushroomPickerG(picker, nextPanelName, controller);
    }
}

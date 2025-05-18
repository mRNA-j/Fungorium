package interfaces;

import controller.Controller;
import model.MushroomPicker;
import view.MushroomPickerG;

/**
 * Az IMushroomPickerGFactory interfész definiálja a MushroomPickerG objektumok
 * létrehozásáért felelős gyártó osztályok szerződését.
 * Ez az interfész biztosítja, hogy a különböző implementációk ugyanazt a
 * funkcionalitást nyújtsák a MushroomPickerG objektumok létrehozásához.
 */
public interface IMushroomPickerGFactory {
    /**
     * Létrehoz egy új MushroomPickerG objektumot a megadott paraméterekkel.
     * 
     * @param picker A gombaszedő játékos modellje
     * @param nextPanelName A következő panel neve, amelyre váltani kell
     * @param controller A játék vezérlője
     * @return Az újonnan létrehozott MushroomPickerG objektum
     */
    MushroomPickerG onCreate(MushroomPicker picker, String nextPanelName, Controller controller);
}

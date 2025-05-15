package interfaces;

import controller.Controller;
import model.MushroomPicker;
import view.MushroomPickerG;

public interface IMushroomPickerGFactory {
    MushroomPickerG onCreate(MushroomPicker picker, String nextPanelName, Controller controller);
}
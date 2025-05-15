package factory;

import controller.Controller;
import model.MushroomPicker;
import view.MushroomPickerG;
import interfaces.IMushroomPickerGFactory;

public class MushroomPickerGFactory implements IMushroomPickerGFactory {
    
    @Override
    public MushroomPickerG onCreate(MushroomPicker picker, String nextPanelName, Controller controller) {
        return new MushroomPickerG(picker, nextPanelName, controller);
    }
}
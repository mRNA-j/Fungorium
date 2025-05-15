package factory;

import model.MushroomPicker;
import view.MushroomPickerG;
import interfaces.IMushroomPickerGFactory;

public class MushroomPickerGFactory implements IMushroomPickerGFactory {
    
    @Override
    public MushroomPickerG onCreate(MushroomPicker picker, String nextPanelName) {
        return new MushroomPickerG(picker, nextPanelName);
    }
}
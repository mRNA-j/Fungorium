package view;

import controller.UpdateListener;

import javax.swing.*;
import model.MushroomPicker;
public class MushroomPickerG extends JComponent implements UpdateListener {
    private MushroomPicker mushroomPicker;
    JButton growYarnButton=new JButton("Grow Yarn");
    JButton growMushroomButton=new JButton("Grow Mushroom");
    JButton disperseButton=new JButton("Disperse Spore");
    JButton skipButton=new JButton("Skip");

   public MushroomPickerG(MushroomPicker mushroomPicker){}
    public void update(){}
}

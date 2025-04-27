package view;

import model.Insect;
import model.Mushroom;
import model.MushroomPicker;
import model.Yarn;

import javax.swing.text.PlainView;
import java.io.*;

public class MushroomPickerView extends PlayerView implements Serializable {
    private final MushroomPicker mp;

    /**
     * A MushroomPickerView konstruktora.
     * @param mp A Gombász Model
     */
    public MushroomPickerView(MushroomPicker mp) {this.mp = mp; }

    /**
     * A Model konzolra való kiírásáért felelős függvény.
     * - Player: [mushroomPicker.name]
     * - Type: MushroomPicker
     * - Points: [mushroomPicker.points]
     */
    @Override
    public void printObject(){
        System.out.println("Player: " + mp.getName());
        System.out.println("Type: MushroomPicker");
        System.out.println("Points: "+mp.getPoints());
        System.out.println();
    }
}

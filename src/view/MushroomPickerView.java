package view;

import model.Insect;
import model.Mushroom;
import model.MushroomPicker;
import model.Yarn;

import javax.swing.text.PlainView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MushroomPickerView extends PlayerView {
    private final MushroomPicker mp;

    public MushroomPickerView(MushroomPicker mp) {this.mp = mp; }

    @Override
    public void printObject(){
        System.out.println("Player: " + mp.getName());
        System.out.println("Type: MushroomPicker");
        System.out.println("Points: "+mp.getPoints());
        System.out.println();
    }
    @Override
    public void printToFile(File f){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, true))) {

            writer.write("Player: " + mp.getName());
            writer.newLine();

            writer.write("Type: MushroomPicker");
            writer.newLine();

            writer.write("Points: "+ mp.getPoints());
            writer.newLine();
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

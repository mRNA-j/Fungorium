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

    public MushroomPickerView(MushroomPicker mp) {  this.mp = mp; }

    @Override
    public void printObject(){
        System.out.println("Model.MushroomPicker: " + mp.getId());
        System.out.println("Name: " + mp.getName());
        System.out.println("Points: "+mp.getPoints());

        // Print insects owned by yarn
        StringBuilder mushroomsOwned = new StringBuilder("Mushrooms owned: ");
        for (Mushroom mushroom :  mp.getOwnedMushrooms()) {
            mushroomsOwned.append(mushroom.getId()).append(" ");
        }
        StringBuilder ownedYarns = new StringBuilder("Yarns owned: ");
        for (Yarn yarn :  mp.getOwnedYarns()) {
            ownedYarns.append(yarn.getId()).append(" ");
        }
        System.out.println(mushroomsOwned.toString().trim());
        System.out.println(ownedYarns.toString().trim());
        System.out.println();
    }
    @Override
    public void printToFile(File f){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, true))) {

            writer.write("Model.MushroomPicker: " + mp.getId());
            writer.newLine();

            writer.write("Name: " + mp.getName());
            writer.newLine();

            writer.write("Points: "+ mp.getPoints());
            writer.newLine();

            StringBuilder ownedMushrooms = new StringBuilder("Mushrooms owned: ");
            for (Mushroom mushroom :  mp.getOwnedMushrooms()) {
                ownedMushrooms.append(mushroom.getId()).append(" ");
            }
            writer.write(ownedMushrooms.toString().trim());
            writer.newLine();

            StringBuilder ownedYarns = new StringBuilder("Yarns owned: ");
            for (Yarn yarn :  mp.getOwnedYarns()) {
                ownedYarns.append(yarn.getId()).append(" ");
            }
            writer.write(ownedYarns.toString().trim());
            writer.newLine();
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

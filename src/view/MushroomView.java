package view;

import model.Mushroom;
import model.MushroomPicker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MushroomView implements View {
    private final Mushroom mushroom;
    private MushroomPicker owner;

    public MushroomView(Mushroom m) {
        mushroom = m;
    }


    @Override
    public void printObject() {
        System.out.println("Mushroom: " + mushroom.getId());
        System.out.println("HasSpore: " + mushroom.getHasSpore());
        System.out.println("Place: "    + mushroom.getTecton().getId());
        System.out.println("Owner: "    + (owner == null ? "none" : owner.getName()));
        System.out.println();
    }

    @Override
    public void printToFile(File f) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, true))) {

            writer.write("Mushroom: " + mushroom.getId());
            writer.newLine();

            writer.write("HasSpore: " + mushroom.getHasSpore());
            writer.newLine();

            writer.write("Place: "    + mushroom.getTecton().getId());
            writer.newLine();

            writer.write("Owner: " + (owner == null ? "none" : owner.getName()));
            writer.newLine();
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setOwner(MushroomPicker owner) {
        this.owner = owner;
    }
}

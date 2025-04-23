package view;

import model.Insect;
import model.Tecton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InsectView implements View{
    private final Insect insect;

    public InsectView(Insect i) {
        insect = i;
    }
    /*Insect: i1
Current Effect: none
Owner: Alice*/
    @Override
    public void printObject() {
        System.out.println("Insect: " + insect.getId());
        System.out.println("Current Effect: " +
                (insect.getCurrentEffect()==null ? "none" : insect.getCurrentEffect()));
        System.out.println("Owner: " + insect.getOwner().getName());
        System.out.println();
    }

    @Override
    public void printToFile(File f) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, true))) {

            writer.write("Insect: " + insect.getId());
            writer.newLine();

            writer.write("Current Effect: " + (insect.getCurrentEffect()==null ? "none" : insect.getCurrentEffect()));
            writer.newLine();

            writer.write("Owner: " + insect.getOwner().getName());
            writer.newLine();
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

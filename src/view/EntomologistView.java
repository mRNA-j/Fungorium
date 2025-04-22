package view;

import model.Entomologist;
import model.Player;
import model.Tecton;
import model.Insect;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EntomologistView extends PlayerView {
    private final Entomologist entomologist;

    EntomologistView(Entomologist entomologist) {
        this.entomologist = entomologist;
    }

    @Override
    public void printObject(){
        System.out.println("Model.Entomologist: " + entomologist.getId());
        System.out.println("Name: " + entomologist.getName());
        System.out.println("Points: "+entomologist.getPoints());

        // Print insects owned by yarn
        StringBuilder insectsOwned = new StringBuilder("Insects owned: ");
        for (Insect insect :  entomologist.getInsect()) {
            insectsOwned.append(insect.getId()).append(" ");
        }
        System.out.println(insectsOwned.toString().trim());
        System.out.println();
    }
    @Override
    public void printToFile(File f){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, true))) {

            writer.write("Model.Entomologist: " + entomologist.getId());
            writer.newLine();

            writer.write("Name: " + entomologist.getName());
            writer.newLine();

            writer.write("Points: "+entomologist.getPoints());
            writer.newLine();

            StringBuilder ownedInsects = new StringBuilder("Insects owned: ");
            for (Insect insect :  entomologist.getInsect()) {
                ownedInsects.append(insect.getId()).append(" ");
            }
            writer.write(ownedInsects.toString().trim());
            writer.newLine();
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

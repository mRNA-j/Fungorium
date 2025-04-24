package view;

import model.Entomologist;
import model.Player;
import model.Tecton;
import model.Insect;


import java.io.*;

public class EntomologistView extends PlayerView implements Serializable {
    private final Entomologist entomologist;

    public EntomologistView(Entomologist entomologist) {
        this.entomologist = entomologist;
    }

    @Override
    public void printObject(){
        System.out.println("Player: " + entomologist.getName());
        System.out.println("Type: Entomologist");
        System.out.println("Points: "+ entomologist.getPoints());
        System.out.println();
    }
    @Override
    public void printToFile(File f){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, true))) {

            writer.write("Player: " + entomologist.getName());
            writer.newLine();

            writer.write("Name: Entomologist");
            writer.newLine();

            writer.write("Points: "+entomologist.getPoints());
            writer.newLine();
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

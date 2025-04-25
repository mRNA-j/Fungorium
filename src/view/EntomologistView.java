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
}

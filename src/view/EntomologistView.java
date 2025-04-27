package view;

import model.Entomologist;
import model.Player;
import model.Tecton;
import model.Insect;


import java.io.*;

/**
 * A Model.Entomologist nézete.
 */
public class EntomologistView extends PlayerView implements Serializable {
    private final Entomologist entomologist;

    /**
     * Az EntomologistView konstruktora
     * @param entomologist Az Entomologist Model
     */
    public EntomologistView(Entomologist entomologist) {
        this.entomologist = entomologist;
    }

    /**
     * A Model konzolra való kiírásáért felelős függvény.
     * - Player: [entomologist.name]
     * - Type: Entomologist
     * - Points: [entomologist.points]
     */
    @Override
    public void printObject(){
        System.out.println("Player: " + entomologist.getName());
        System.out.println("Type: Entomologist");
        System.out.println("Points: "+ entomologist.getPoints());
        System.out.println();
    }
}

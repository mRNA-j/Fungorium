package view;

import model.Insect;
import model.Tecton;

import java.io.*;

public class InsectView implements View, Serializable {
    private final Insect insect;

    /**
     * Az InsectView konstruktora.
     * @param i A Bogár Model.
     */
    public InsectView(Insect i) {
        insect = i;
    }

    /**
     * A Model konzolra való kiírásáért felelős függvény.
     * - Insect: [insect.id]
     * - Current Effect: ["none"/insect.currentEffect]
     * - Owner: [insect.owner.name]
     */
    @Override
    public void printObject() {
        System.out.println("Insect: " + insect.getId());
        System.out.println("Current Effect: " +
                (insect.getCurrentEffect()==null ? "none" : insect.getCurrentEffect()));
        System.out.println("Owner: " + insect.getOwner().getName());
        System.out.println();
    }
}

package view;

import model.Mushroom;
import model.MushroomPicker;

import java.io.*;

public class MushroomView implements View, Serializable {
    private final Mushroom mushroom;

    /**
     * A MushroomView konstruktora
     * @param m A gomba Model.
     */
    public MushroomView(Mushroom m) {
        mushroom = m;
    }

    /**
     * A Model konzolra való kiírásáért felelős függvény.
     * - Mushroom: [mushroom.id]
     * - HasSpore: [mushroom.hasSpore]
     * - Place: [mushroom.tecton.id]
     * - Owner: ["none"/mushroom.owner.name]
     */
    @Override
    public void printObject() {
        System.out.println("Mushroom: " + mushroom.getId());
        System.out.println("HasSpore: " + mushroom.getHasSpore());
        System.out.println("Place: "    + mushroom.getTecton().getId());
        System.out.println("Owner: "    + (mushroom.getOwner() == null ? "none" : mushroom.getOwner().getName()));
        System.out.println();
    }

}

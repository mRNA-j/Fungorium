package view;

import model.Mushroom;
import model.MushroomPicker;

import java.io.*;

public class MushroomView implements View, Serializable {
    private final Mushroom mushroom;

    public MushroomView(Mushroom m) {
        mushroom = m;
    }


    @Override
    public void printObject() {
        System.out.println("Mushroom: " + mushroom.getId());
        System.out.println("HasSpore: " + mushroom.getHasSpore());
        System.out.println("Place: "    + mushroom.getTecton().getId());
        System.out.println("Owner: "    + (mushroom.getOwner() == null ? "none" : mushroom.getOwner().getName()));
        System.out.println();
    }

}

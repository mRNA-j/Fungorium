package view;

import model.Insect;
import model.Tecton;

import java.io.*;

public class InsectView implements View, Serializable {
    private final Insect insect;

    public InsectView(Insect i) {
        insect = i;
    }
    @Override
    public void printObject() {
        System.out.println("Insect: " + insect.getId());
        System.out.println("Current Effect: " +
                (insect.getCurrentEffect()==null ? "none" : insect.getCurrentEffect()));
        System.out.println("Owner: " + insect.getOwner().getName());
        System.out.println();
    }
}

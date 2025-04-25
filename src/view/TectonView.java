package view;

import model.*;

import java.io.*;
import java.util.stream.Collectors;

public class TectonView implements View, Serializable {
    private final Tecton tecton;

    public TectonView(Tecton tecton) {this.tecton = tecton; }
    @Override
    public void printObject() {
        System.out.println("Tecton: "   + tecton.getId());
        System.out.println("Type: "     + tecton.getType());

        /* ----------  MUSHROOM  ---------- */
        System.out.println("Mushroom: " + (tecton.getMushroom() != null
                ? tecton.getMushroom().getId()
                : "none"));

        /* ----------  INSECTS  ---------- */
        System.out.println("Insects: "  + (tecton.getInsects().isEmpty()
                ? "none"
                : tecton.getInsects()
                .stream()
                .map(Insect::getId)
                .collect(Collectors.joining(" "))));

        /* ----------  YARNS  ---------- */
        System.out.println("Yarns: "+ (tecton.getYarns().isEmpty()
                ? "none"
                : tecton.getYarns()
                .stream()
                .map(Yarn::getId)
                .collect(Collectors.joining(" "))));

        /* ----------  NEIGHBOURS  ---------- */
        System.out.println("Neighbours: "+ (tecton.getNeighbours().isEmpty()
                ? "none"
                : tecton.getNeighbours()
                .stream()
                .map(Tecton::getId)
                .collect(Collectors.joining(" "))));

        /* ----------  SPORES  ---------- */
        System.out.println("Spores: "   + (tecton.getSpores().isEmpty() || tecton.getSpores() == null
                ? "none"
                : tecton.getSpores()
                .stream()
                .map(Spore::getId)
                .collect(Collectors.joining(" "))));
        System.out.println();
    }
}

package view;

import model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class TectonView implements View {
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

    @Override
    public void printToFile(File f) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, true))) {

            writer.write("Tecton: " + tecton.getId());
            writer.newLine();

            writer.write("Type: " + tecton.getType());
            writer.newLine();

            writer.write("Mushroom: " + (tecton.getMushroom() != null
                    ? tecton.getMushroom().getId()
                    : "none"));
            writer.newLine();

            writer.write("Insects: "  + (tecton.getInsects().isEmpty()
                    ? "none"
                    : tecton.getInsects()
                    .stream()
                    .map(Insect::getId)
                    .collect(Collectors.joining(" "))));
            writer.newLine();

            writer.write("Yarns: "+ (tecton.getYarns().isEmpty()
                    ? "none"
                    : tecton.getYarns()
                    .stream()
                    .map(Yarn::getId)
                    .collect(Collectors.joining(" "))));
            writer.newLine();

            writer.write("Neighbours: "+ (tecton.getNeighbours().isEmpty()
                    ? "none"
                    : tecton.getNeighbours()
                    .stream()
                    .map(Tecton::getId)
                    .collect(Collectors.joining(" "))));
            writer.newLine();

            writer.write("Spores: "   + (tecton.getSpores().isEmpty() || tecton.getSpores() == null
                    ? "none"
                    : tecton.getSpores()
                    .stream()
                    .map(Spore::getId)
                    .collect(Collectors.joining(" "))));
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

package view;

import model.KillerYarn;
import model.Tecton;
import model.Yarn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class KillerYarnView implements View {
  private final KillerYarn killerYarn;

  public KillerYarnView(KillerYarn y) {
    killerYarn = y;
  }

  @Override
  public void printObject() {
    System.out.println("Model.Yarn: " + killerYarn.getId());
    System.out.println("Type: " + killerYarn.getName());

    // Print tectons connected by yarn
    StringBuilder tectonsInYarn = new StringBuilder("Tectons in yarn: ");
    for (Tecton tecton : killerYarn.getTectons()) {
      tectonsInYarn.append(tecton.getId()).append(" ");
    }
    System.out.println(tectonsInYarn.toString().trim());
    System.out.println();
  }

  @Override
  public void printToFile(File f) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, true))) {

      writer.write("Model.Yarn: " + killerYarn.getId());
      writer.newLine();

      writer.write("Type: " + killerYarn.getName());
      writer.newLine();

      StringBuilder tectonsInYarn = new StringBuilder("Tectons in yarn: ");
      for (Tecton tecton : killerYarn.getTectons()) {
        tectonsInYarn.append(tecton.getId()).append(" ");
      }
      writer.write(tectonsInYarn.toString().trim());
      writer.newLine();
      writer.newLine();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

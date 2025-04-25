package view;
import model.*;

import java.io.*;
import java.util.stream.Collectors;

public class YarnView implements View, Serializable {

  private final Yarn yarn;

  public YarnView(Yarn y) {
    yarn = y;
  }

  @Override
  public void printObject() {
    System.out.println("Yarn: " + yarn.getId());
    System.out.println("Type: " + yarn.getName());

    // Print tectons connected by yarn
    StringBuilder tectonsInYarn = new StringBuilder("Tectons in yarn: ");
    for (Tecton tecton : yarn.getTectons()) {
      tectonsInYarn.append(tecton.getId()).append(" ");
    }
    System.out.println(tectonsInYarn.toString().trim());
    System.out.println();
  }
}

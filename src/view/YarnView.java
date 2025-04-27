package view;
import model.*;

import java.io.*;
import java.util.stream.Collectors;

public class YarnView implements View, Serializable {

  private final Yarn yarn;

  /**
   * A YarnView konstruktora.
   * @param y A yarn Model.
   */
  public YarnView(Yarn y) {
    yarn = y;
  }

  /**
   * A Model konzolra való kiírásáért felelős függvény.
   * - Yarn: [yarn.id]
   * - Type: [yarn.type]
   * - Tectons in yarn: [tecton1.id] [tecton2.id] ...
   */
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

package view;

import model.Tecton;
import model.Yarn;

import java.io.File;

public class YarnView implements View{

  private final Yarn yarn;

  public YarnView(Yarn y) {
    yarn = y;
  }

  @Override
  public void printObject() {
    System.out.println("Model.Yarn: " + yarn.getId());
    System.out.println("Type: " + yarn.getName()); //TODO Implement type name - technically implementáltam, gyakorlatilag spagetti- Erna

    // Print tectons connected by yarn
    StringBuilder tectonsInYarn = new StringBuilder("Tectons in yarn: ");
    for (Tecton tecton : yarn.getTectons()) {
      tectonsInYarn.append(tecton.getId()).append(" ");
    }
    System.out.println(tectonsInYarn.toString().trim());
    System.out.println();
  }

  @Override
  public void printToFile(File f) {
    //TODO - fajl kiiras megvalositas
  }
}

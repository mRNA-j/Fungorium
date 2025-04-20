package model;

public class KillerYarn extends Yarn {
  private String id;

  public KillerYarn (Mushroom mushroom, MushroomPicker mp,String id) {
    super(mushroom, mp, id);
    name = "KillerYarn";
    this.id = id;
  }
  @Override
  public String getName() {          // ← this is what @Override belongs to
    return "kill";
  }
  @Override
  public String getId() {
    return id;
  }

  @Override
    public void runEffect() {
      for (Tecton tecton : tectons) {
        if(tecton.getInsects().isEmpty()) {
          return;
        }
        for (Insect insect : tecton.getInsects()) {
          if(insect.getParalized()) {
            //Eltávolítja a rovarász rovarjai közül
            insect.getOwner().getInsect().remove(insect);

            //Eltávolítja a tectonról
            tecton.removeInsect(insect);


            //Gombatest nő, ha az adott tektonon eddig nem volt
            if(!tecton.isMushroomPrevent() && tecton.getMushroom() == null) {
              new Mushroom(tecton, "sajt"); //TODO: idvel kezdeni kell valami vmi egyedi naming conventiont szuljunk neki
              this.getPlayer().addPoints(1);//sztem kell -Luca
            }
          }
        }
      }
    }
}

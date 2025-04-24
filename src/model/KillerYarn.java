package model;

import java.io.Serializable;

public class KillerYarn extends Yarn implements Serializable {
  private String id;

  public KillerYarn (Mushroom mushroom, MushroomPicker mp,String id) {
    super(mushroom, mp, id);
    //System.out.println(id);
    name = "KillerYarn";
    this.id = id;
  }
  @Override
  public String getName() {          // ← this is what @Override belongs to
    return name;
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
        for (int i=0; i<tecton.getInsects().size(); i++) {
          Insect insect = tecton.getInsects().get(i);
          if(insect.getParalized()) {
            //Eltávolítja a rovarász rovarjai közül
            insect.getOwner().getInsect().remove(insect);

            //Eltávolítja a tectonról
            tecton.removeInsect(insect);

            //Gombatest nő, ha az adott tektonon eddig nem volt
            if(!tecton.isMushroomPrevent() && tecton.getMushroom() == null) {
              Mushroom m = new Mushroom(tecton, player,"Mushroom_"+this.getId());
              //tecton.addMushroom(m);
              player.addMushroom(m);
              this.getPlayer().addPoints(3);
            }
          }
        }
      }
    }
}

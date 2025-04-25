package model;

import view.YarnView;

import java.io.Serializable;
import java.util.ArrayList;

public class KillerYarn extends Yarn implements Serializable {
  /**
   * Alapértelmezett konstruktor. Beállítja a name-et is.
   * @param mushroom A gomba, amihez a gombafonal tartozik.
   * @param mp A gombász akinek  fonala
   */
  public KillerYarn (Mushroom mushroom, MushroomPicker mp) {
    super(mushroom, mp);
    name = "KillerYarn";
  }

  /**
   * Tesztelésnél használt konstruktor. Beállítja a name-et is.
   * Megegyezik az eredeti kontruktorral, csak pluszba kap egy tesztelésnél használt id-t
   * @param mushroom A gomba, amihez a gombafonal tartozik.
   * @param mp A gombász akinek  fonala
   * @param id a teszteléshez hazsnált azonosító
   */
  public KillerYarn (Mushroom mushroom, MushroomPicker mp,String id) {
    super(mushroom, mp, id);
    name = "KillerYarn";
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

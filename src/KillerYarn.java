public class KillerYarn extends Yarn {

  public KillerYarn (Mushroom mushroom) {
    super(mushroom);
    name = "KillerYarn";
  }

  @Override
    public void runEffect() {
      for (Tecton tecton : tectons) {
        for (Insect insect : tecton.getInsects()) {
          if(insect.getParalized()) {
            //Eltávolítja a rovarász rovarjai közül
            insect.getOwner().getInsect().remove(insect);

            //Eltávolítja a tectonról
            tecton.removeInsect(insect);

            //Gombatest nő, ha az adott tektonon eddig nem volt
            if(!tecton.isMushroomPrevent() && tecton.getMushroom() == null) {
              new Mushroom(tecton);
            }
          }
        }
      }
    }
}

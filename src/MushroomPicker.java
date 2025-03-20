import java.util.List;

public class MushroomPicker extends Player {
    private List<Mushroom> ownedMushrooms;
    private List<Yarn> ownedYarns;

    public MushroomPicker(String name, Mushroom mushroom){
        super(name, 0);
        ownedMushrooms.add(mushroom);
    }

    public List<Mushroom> getOwnedMushrooms() {
        return ownedMushrooms;
    }
    public List<Yarn> getOwnedYarns() {
        return ownedYarns;
    }

    private static boolean IsTectonInRange(Tecton tecton){
        for(int i = 0; i<ownedYarns.size(); i++){
            if(ownedYarns.get(i).getTectons().contains(tecton)) {
                return true;
            }
        }
        return false;
    }

    private static boolean CanIConquerTecton(Tecton tecton){
        int limit = tecton.getYarnLimit();
        if(limit == 2){
            return true;
        }
        if(tecton.getYarns().length() == 0){
            return true;
        }
        if (limit == 1 && tecton.getYarns().getPlayer() == this){ //tipus lekerdezes??
            return true;
        }
        return false;
    }

    public void actionGrowMushroom(Tecton targetTecton) {
        if(!targetTecton.getMushroomPrevent()) {
            if (targetTecton.getMushroom().length() == 0) {
                if (targetTecton.getSpores().length() >= 3) {
                    if(isTectonInRange(targetTecton)) {
                        Mushroom newMushroom = new Mushroom(targetTecton);
                        targetTecton.addMushroom(newMushroom);
                        ownedMushrooms.add(newMushroom);
                        targetTecton.removeSpore(targetTecton.getSpore().remove(0));
                        targetTecton.removeSpore(targetTecton.getSpore().remove(0));
                        targetTecton.removeSpore(targetTecton.getSpore().remove(0));
                    }
                }
            }
        }
    }
    public void actionGrowYarn(Tecton targetTecton, Yarn selectedYarn, boolean firstTime) {
        if(selectedYarn.getTectons().get(0).isNeighbour(targetTecton) || selectedYarn.getTectons().get(1).isNeighbour(targetTecton)){
            if(CanIConquerTecton(targetTecton) && firstTime){
                if(targetTecton.getSpore().length() != 0){//vanspóra
                    targetTecton.removeSpore(targetTecton.getSpore().get(0));
                    actionGrowYarn(targetTecton, selectedYarn, false);
                }
                else{       //nincsspóra
                    targetTecton.growYarn(selectedYarn);    //what????
                }
            }
        }
    }
    public void actionSporeDispersion(Tecton targetTecton, Mushroom mushroom) {
    }


}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MushroomPicker extends Player {
    private List<Mushroom> ownedMushrooms;
    private static List<Yarn> ownedYarns;

    public MushroomPicker(String name, Mushroom mushroom){
        super(name, 0);
        ownedMushrooms.add(mushroom);
        ownedYarns = new ArrayList<Yarn>();
    }

    public List<Mushroom> getOwnedMushrooms() {
        return ownedMushrooms;
    }
    public List<Yarn> getOwnedYarns() {
        return ownedYarns;
    }

    private static boolean isTectonInRange(Tecton tecton){
        for(int i = 0; i< ownedYarns.size(); i++){
            if(ownedYarns.get(i).getTectons().contains(tecton)) {
                return true;
            }
        }
        return false;
    }

    private boolean canIConquerTecton(Tecton tecton){
        int limit = tecton.getYarnLimit();
        if(limit == 2){
            return true;
        }
        if(tecton.getYarns().size() == 0){
            return true;
        }
        if (limit == 1 && tecton.getYarns().get(0).getPlayer() == this){ //tipus lekerdezes??
            return true;
        }
        return false;
    }

    private List<Tecton> addNeighbours(Tecton tecton){
        return tecton.getNeighbours();
    }

    private List<Tecton> addsecondNeighbours(List<Tecton> tectons){
        List<Tecton> secondNeighbours = tectons;
        for(int i = 0; i< tectons.size(); i++){
            for(int j = 0; j < tectons.get(i).getNeighbours().size(); j++){
               if(!secondNeighbours.contains(tectons.get(i).getNeighbours().get(j))) {
                   secondNeighbours.add(tectons.get(i));
               }
            }

        }
        return  secondNeighbours;
    }

    public void actionGrowMushroom(Tecton targetTecton) {
        if(!targetTecton.getMushroomPrevent()) {
            if (targetTecton.getMushroom() != null) {
                if (targetTecton.getSpores().size() >= 3) {
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
        // yarn egyik oldala vagy másiki szomszedos e velunk
        if(selectedYarn.getTectons().get(0).isNeighbour(targetTecton) || selectedYarn.getTectons().get(1).isNeighbour(targetTecton)){
            //van-e rajta es lehet-e noveszteni
            if(canIConquerTecton(targetTecton) && firstTime){
                //van-e rajta spora
                if(targetTecton.getSpore().size() != 0){//vanspóra
                    targetTecton.removeSpore(targetTecton.getSpore().get(0));
                    actionGrowYarn(targetTecton, selectedYarn, false);
                }
                //nincsen spora
                else{       //nincsspóra
                    targetTecton.growYarn(selectedYarn);    //what????
                }
            }
        }
    }

    //hol kapom be a tectont? hol vizsgálom hogy melyik tektonra lőhet szerintem itt, visszacsatok
    public void actionSporeDispersion(Tecton targetTecton, Mushroom mushroom) {
        int age = mushroom.getAge();
        List<Tecton> neighbours = addNeighbours(targetTecton);

        if(mushroom.getHasSpore()){
            if(age > 10){
                List<Tecton> secondNeighbours = addsecondNeighbours(neighbours);
                neighbours = secondNeighbours;
            }
            //if (targetTecton.)
            mushroom.disperseSpore(targetTecton);
        }
    }


}
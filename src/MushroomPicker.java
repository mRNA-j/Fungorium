import java.util.List;

public class MushroomPicker extends Player {
    private List<Mushroom> ownedMushrooms;
    private Yarn[] ownedYarns;

    public MushroomPicker(String name, Mushroom mushroom){
        super(name, 0);
        ownedMushrooms.add(mushroom);
    }

    public void actionGrowMushroom(Tecton targetTecton) {}
    public void actionGrowYarn(Tecton targetTecton, Yarn selectedYarn) {}
    public void actionSporeDispersion(Tecton targetTecton, Mushroom mushroom) {
        // nem látom a Mushroom és Tecton cuccokat
    }
}
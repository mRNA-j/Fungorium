import java.util.Random;
import java.util.List;

/** A YarnAbsorbantTecton class egy speciális Tecton, ami felszívja a gombafonalakat. */
public class YarnAbsorbantTecton extends Tecton {

    /**
     * A YarnAbsorbantTecton class konstruktora.
     *
     * @param yarnLimit A maximális gombafonal szám, ami a Tectonon lehet.
     * @param mushroomPrevent A  jelzés, hogy a Tectonon nem lehet gomba.
     */
    public YarnAbsorbantTecton(int id,int yarnLimit, boolean mushroomPrevent) {
        super(id, yarnLimit, mushroomPrevent);
    }

    /**
     * Híváskor eltávolítja magáról a gombafonalat.
     *
     * @param yarn A felszívandó gombafonal.
     */
    @Override
    public void runEffect(Yarn yarn) {
        System.out.println("Running absorbant effect on yarn: " + yarn);

        int index = this.getId() - 2; // Ensure index is valid
        List<Tecton> tectons = yarn.getTectons();
        int size = tectons.size();

        // Validate index range
        if (index < 0) {
            index = 0;  // Prevent negative index
        }
        if (index >= size) {
            System.out.println("Index out of bounds, skipping effect.");
            return;
        }

        // Iterate safely using an iterator
        for (int i = size-1; i>index; i--) {
            tectons.get(i).removeYarn(yarn);
        }

        yarn.getTectons().subList(index, tectons.size()).clear();
    }
}
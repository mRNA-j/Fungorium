import java.util.Random;
import java.util.List;

/** A YarnAbsorbantTecton class egy speciális Tecton, ami felszívja a gombafonalakat. */
public class YarnAbsorbantTecton extends Tecton {



    private boolean yarnAbsorption = true;
    /**
     * A YarnAbsorbantTecton class konstruktora.
     *
     * @param yarnLimit A maximális gombafonal szám, ami a Tectonon lehet.
     * @param mushroomPrevent A  jelzés, hogy a Tectonon nem lehet gomba.
     */
    public YarnAbsorbantTecton(String id,int yarnLimit, boolean mushroomPrevent) {
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
        yarn.split(this);
        yarn.getTectons().remove(this);
        removeYarn(yarn);

    }

    public boolean getYarnAbsorption(){return yarnAbsorption;}
}
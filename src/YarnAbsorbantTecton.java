/** A YarnAbsorbantTecton class egy speciális Tecton, ami felszívja a gombafonalakat. */
public class YarnAbsorbantTecton extends Tecton {

    /**
     * A YarnAbsorbantTecton class konstruktora.
     *
     * @param yarnLimit A maximális gombafonal szám, ami a Tectonon lehet.
     * @param mushroomPrevent A  jelzés, hogy a Tectonon nem lehet gomba.
     */
    public YarnAbsorbantTecton(int yarnLimit, boolean mushroomPrevent) {
        super(yarnLimit, mushroomPrevent);
    }

    /**
     * Híváskor eltávolítja magáról a gombafonalat.
     *
     * @param yarn A felszívandó gombafonal.
     */
    @Override
    public void runEffect(Yarn yarn) {
        System.out.println("Running absorbant effect on yarn: " + yarn);
        yarn.getTectons().remove(this);
        removeYarn(yarn);
    }
}
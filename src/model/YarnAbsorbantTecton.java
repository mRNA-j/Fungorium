package model;

/** A Model.YarnAbsorbantTecton class egy speciális Model.Tecton, ami felszívja a gombafonalakat. */
public class YarnAbsorbantTecton extends Tecton {
    private boolean yarnAbsorption = true;
    /**
     * A Model.YarnAbsorbantTecton class konstruktora.
     *
     * @param yarnLimit A maximális gombafonal szám, ami a Tectonon lehet.
     * @param mushroomPrevent A  jelzés, hogy a Tectonon nem lehet gomba.
     */
    public YarnAbsorbantTecton(String id, int yarnLimit, boolean mushroomPrevent, boolean isKeepAlive) {
        super(id, yarnLimit, mushroomPrevent, isKeepAlive);
        super.setYarnAbsorption(true);
    }


    /**
     * Híváskor eltávolítja magáról a gombafonalat.
     *
     * @param yarn A felszívandó gombafonal.
     */
    @Override
    public void runEffect(Yarn yarn) {
        System.out.println("Running absorbant effect on yarn: " + yarn);
        removeYarn(yarn);
        yarn.getTectons().remove(this);
    }


    public boolean getYarnAbsorption(){return yarnAbsorption;}
}
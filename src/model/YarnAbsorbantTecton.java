package model;

import java.io.Serializable;

/** A Model.YarnAbsorbantTecton class egy speciális Model.Tecton, ami felszívja a gombafonalakat. */
public class YarnAbsorbantTecton extends Tecton implements Serializable {
    /**
     * A Model.YarnAbsorbantTecton class konstruktora.
     *
     * @param yarnLimit A maximális gombafonal szám, ami a Tectonon lehet.
     * @param mushroomPrevent A  jelzés, hogy a Tectonon nem lehet gomba.
     * @param isKeepAlive megadja hogy fonaléletbentartó-e
     */
    public YarnAbsorbantTecton( int yarnLimit, boolean mushroomPrevent, boolean isKeepAlive) {
        super(yarnLimit, mushroomPrevent, isKeepAlive);
        super.setYarnAbsorption(true);
    }


    /**
     * A Model.YarnAbsorbantTecton class konstruktora.
     *
     * @param id a teszteléshez használt kontruktor
     * @param yarnLimit A maximális gombafonal szám, ami a Tectonon lehet.
     * @param mushroomPrevent A  jelzés, hogy a Tectonon nem lehet gomba.
     * @param isKeepAlive megadja hogy fonaléletbentartó-e
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
        removeYarn(yarn, this);
        yarn.getTectons().remove(this);
    }

}
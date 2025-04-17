/**
 * A Spore osztály egy absztrakt osztály, amely a spórákat reprezentálja.
 * A spórák rendelkeznek tápanyag értékkel és hatásnévvel, amelyek különböző rovarokra kifejtett hatásokkal járhatnak.
 *
 * A Spore osztály példányosítható a tápanyag és a hatásnév paraméterek megadásával.
 * Az osztály tartalmaz getter és setter metódusokat, valamint egy absztrakt metódust a hatások alkalmazására.
 *
 * A spórák hatásokat adhatnak hozzá különböző rovarokhoz az addEffect metódus segítségével.
 */
public abstract class Spore {
    /** A spóra tápanyaga, amely meghatározza a spóra étkezési értékét. */
    private int nutrition;
    /** A spóra hatásának neve, amely megadja a spóra hatás típusát. */
    private String effectName;
    public String testID;

    /**
     * Konstruktor, amely inicializálja a spórát tápanyaggal és hatásnévvel.
     *
     * @param nutrition A spóra tápanyaga, amely meghatározza a tápértéket.
     * @param effectName A spóra hatásának neve.
     */
    public Spore(int nutrition, String effectName, String testID){
        this.nutrition = nutrition;
        this.effectName = effectName;
        this.testID = testID;
    }
    public Spore(int nutrition, String effectName){
        this.nutrition = nutrition;
        this.effectName = effectName;
        this.testID = null;
    }

    /**
     * Beállítja a spóra tápanyag értékét.
     *
     * @param nutrition Az új tápanyag érték.
     */
    public void setNutrition(int nutrition) {
        this.nutrition = nutrition;
    }
    /**
     * Visszaadja a spóra aktuális tápanyag értékét.
     *
     * @return A spóra tápanyaga.
     */
    public int getNutrition() {
        return nutrition;
    }
    /**
     * Beállítja a spóra hatásának nevét.
     *
     * @param effectName Az új hatásnév.
     */
    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }
    /**
     * Visszaadja a spóra hatásának nevét.
     *
     * @return A spóra hatásneve.
     */
    public String getEffectName() {
        return effectName;
    }

    /**
     * Ez az absztrakt metódus alkalmazza a spóra hatását a megadott rovarra.
     * Az implementálásnak figyelembe kell a rovart és a spóra hatását.
     *
     * @param insect Az a rovar, amelyre a spóra hatását alkalmazni kell.
     */
    public abstract void addEffect(Insect insect);

    public String getId(){
        return testID;
    }
}
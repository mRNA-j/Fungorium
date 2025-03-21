public abstract class Spore {
    private int nutrition;
    private String effectName;

    public Spore(int nutrition, String effectName){
        this.nutrition = nutrition;
        this.effectName = effectName;
    }

    public void setNutrition(int nutrition) {
        this.nutrition = nutrition;
    }
    public int getNutrition() {
        return nutrition;
    }
    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }
    public String getEffectName() {
        return effectName;
    }

    public abstract void addEffect(Insect insect);
}
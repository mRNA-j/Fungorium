public class ParalyzingSpore extends Spore {
    public ParalyzingSpore(){
        super(4, "paralyzing");
    }
    public void addEffect(Insect insect) {
        insect.setParalized(true);
    }
}
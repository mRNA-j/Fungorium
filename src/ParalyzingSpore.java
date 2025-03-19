public class ParalyzingSpore extends Spore {
    public void addEffect(Insect insect) {
        insect.setParalized(true);
    }
}
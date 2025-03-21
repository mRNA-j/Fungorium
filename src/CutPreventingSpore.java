public class CutPreventingSpore extends Spore {
    public void addEffect(Insect insect) {
        insect.setCutPrevented(true);
    }
}
public class CutPreventingSpore extends Spore {
    public CutPreventingSpore(){
        super(2, "cutpreventing");
    }
    public void addEffect(Insect insect) {
        insect.setCutPrevented(true);
    }
}
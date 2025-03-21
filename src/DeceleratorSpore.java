public class DeceleratorSpore extends Spore {
    public DeceleratorSpore(){
        super(3, "decelerator");
    }
    public void addEffect(Insect insect) {
        insect.setDecelerated(true);
    }
}
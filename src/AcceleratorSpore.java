public class AcceleratorSpore extends Spore {
    public AcceleratorSpore(){
        super(1, "accelerator");
    }
    public void addEffect(Insect insect) {
        insect.setAccelerated(true);
    }
}
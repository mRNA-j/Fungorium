public class AcceleratorSpore extends Spore {
    public AcceleratorSpore(){
        super(1, "accelerator");
    }

    public AcceleratorSpore(Tecton tecton) {
        super(1,"accelerating");
    }

    public void addEffect(Insect insect) {
        insect.setAccelerated(true);
    }
}
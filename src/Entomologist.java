public class Entomologist extends Player{
    private Insect insect;

    public Entomologist(String name, Insect insect){
        super(name, 0);
        this.insect = insect;
    }

    public void actionWatch() {
        return;     //?????
    }
    public void actionMove(Tecton targetTecton) {}
    public void actionEatSpore(Spore spore) {}
    public void actionCutYarn(Yarn yarn) {}
}
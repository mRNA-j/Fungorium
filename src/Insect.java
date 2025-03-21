public class Insect {
    private Tecton currentPlace;
    //változás: a spóra effectek változók
    //sporeEffect????
    private boolean decelerated;
    private boolean paralized;
    private boolean accelerated;
    private boolean cutPrevented;

    Insect(Tecton place){
        decelerated = false;
        paralized = false;
        accelerated = false;
        cutPrevented = false;

        currentPlace = place;
    }

    public void setDecelerated(boolean decelerated) {
        this.decelerated = decelerated;
    }
    public boolean getDecelerated() {
        return decelerated;
    }
    public void setParalized(boolean paralized) {
        this.paralized = paralized;
    }
    public boolean getParalized() {
        return paralized;
    }
    public void setAccelerated(boolean accelerated) {
        this.accelerated = accelerated;
    }
    public boolean getAccelerated() {
        return accelerated;
    }
    public void setCutPrevented(boolean cutPrevented) {
        this.cutPrevented = cutPrevented;
    }
    public boolean getCutPrevented() {
        return cutPrevented;
    }
    public Tecton getCurrentPlace(){
        return currentPlace;
    }
    public void setCurrentPlace(Tecton currentPlace){
        this.currentPlace = currentPlace;
    }

    public void cutYarn(Yarn yarn) {}
    public void eatSpore(Spore spore) {}
    public boolean move(Tecton targetTecton) {}
}
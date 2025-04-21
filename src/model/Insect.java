package model;

/**
 * Az Model.Insect osztály egy rovart reprezentál, amely egy adott tectonon helyezkedik el,
 * és különböző állapotváltozásokat szenvedhet el spórák hatására.
 */
public class Insect {
    private Tecton currentPlace; // Az aktuális tecton, ahol a rovar tartózkodik
    private Entomologist owner; //visszadja melyik rovarász játékoshoz tartozik a rovar
    // A rovarra ható spóra effektusok
    private boolean decelerated;   // Lassító spóra hatása
    private boolean paralized;     // Bénító spóra hatása
    private boolean accelerated;   // Gyorsító spóra hatása
    private boolean cutPrevented;  // Vágásgátló spóra hatása

    private String testID;

    public  Tecton getPlace(){
        return currentPlace;
    }
    /**
     * Gets the current effect acting on the insect.
     * Returns the name of the effect or "none" if no effect is active.
     *
     * @return String representing the current effect (accelerated, decelerated, paralized, cutPrevented) or "none"
     */
    public String getCurrentEffect() {
        if (accelerated) {
            return "accelerated";
        } else if (decelerated) {
            return "decelerated";
        } else if (paralized) {
            return "paralyzed";
        } else if (cutPrevented) {
            return "cutPrevented";
        } else {
            return null;
        }
    }

    /**
     * Konstruktor, amely létrehoz egy rovart egy adott tectonon.
     *
     * @param place A tecton, ahol a rovar elhelyezkedik kezdetben.
     */
    public Insect(Tecton place){
        decelerated = false;
        paralized = false;
        accelerated = false;
        cutPrevented = false;

        currentPlace = place;
        place.addInsect(this);
    }

    public Insect(Tecton place,Entomologist owner ,String testID){
        decelerated = false;
        paralized = false;
        accelerated = false;
        cutPrevented = false;
        this.testID = testID;
        this.owner = owner;
        currentPlace = place;
        place.addInsect(this);
    }

    // Getter és setter metódusok a spóra effektusokhoz

    /**
     * Beállítja, hogy a rovar lassító spóra hatása alatt áll-e.
     *
     * @param decelerated Igaz, ha a rovar lassított.
     */
    public void setDecelerated(boolean decelerated) {
        this.decelerated = decelerated;
    }
    /**
     * Visszaadja, hogy a rovar lassító spóra hatása alatt áll-e.
     *
     * @return Igaz, ha a rovar lassított.
     */
    public boolean getDecelerated() {
        return decelerated;
    }
    /**
     * Beállítja, hogy a rovar bénító spóra hatása alatt áll-e.
     *
     * @param paralized Igaz, ha a rovar bénított.
     */
    public void setParalized(boolean paralized) {
        this.paralized = paralized;
    }
    /**
     * Visszaadja, hogy a rovar bénító spóra hatása alatt áll-e.
     *
     * @return Igaz, ha a rovar le van bénítva.
     */
    public boolean getParalized() {
        return paralized;
    }
    /**
     * Beállítja, hogy a rovar gyorsító spóra hatása alatt áll-e.
     *
     * @param accelerated Igaz, ha a rovar gyorsított.
     */
    public void setAccelerated(boolean accelerated) {
        this.accelerated = accelerated;
    }
    /**
     * Visszaadja, hogy a rovar gyorsító spóra hatása alatt áll-e.
     *
     * @return Igaz, ha a rovar gyorsított.
     */
    public boolean getAccelerated() {
        return accelerated;
    }
    /**
     * Beállítja, hogy a rovar vágásgátló spóra hatása alatt áll-e.
     *
     * @param cutPrevented Igaz, ha a rovar vágásgátló spóra hatása alatt áll.
     */
    public void setCutPrevented(boolean cutPrevented) {
        this.cutPrevented = cutPrevented;
    }
    /**
     * Visszaadja, hogy a rovar lassító spóra hatása alatt áll-e.
     *
     * @return Igaz, ha a rovar vágásgátló spóra hatása alatt áll.
     */
    public boolean getCutPrevented() {
        return cutPrevented;
    }
    /**
     * Visszaadja a rovar aktuális tectonját.
     *
     * @return A tecton, ahol a rovar jelenleg tartózkodik.
     */
    public Tecton getCurrentPlace(){
        return currentPlace;
    }
    /**
     * Beállítja a rovar aktuális helyét egy új tectonra.
     *
     * @param currentPlace Az új tecton, ahova a rovar kerül.
     */
    public void setCurrentPlace(Tecton currentPlace){
        this.currentPlace = currentPlace;
    }
    /**
     * Visszaadja a rovar gazdáját (rovarász játékos)
     *
     * @return A rovarász, akihez a rovar tartozik
     */
    public Entomologist getOwner(){
        return owner;
    }

    /**
     * Beállítja a rovart birtokló rovarász játékost
     *
     * @param owner A rovarász, aihez a rovar tartozni fog
     */
    public void setOwner(Entomologist owner){
        this.owner = owner;
    }

    /**
     * Eltávolítja a megadott fonalat az aktuális tectonról.
     *
     * @param yarn Az eltávolítandó fonal.
     */
    public void cutYarn(Yarn yarn, Tecton errefeleVagunk) {

        //TODO - ez csak akkor mukodik ha a currentplace a yarn egyk vegen van

        int index = yarn.tectons.indexOf(currentPlace);
        if(index == 0 || index == yarn.getTectons().size()-1) {
            currentPlace.removeYarn(yarn, errefeleVagunk);
            errefeleVagunk.removeYarn(yarn, errefeleVagunk);
        } else {
            currentPlace.removeYarn(yarn, errefeleVagunk);
        }



        //ITT Nem a vegen van a tecton ahol all a cucc


    }

    /**
     * A rovar elfogyaszt egy adott spórát, és az alkalmazza a hatását a rovarra.
     *
     * @param spore A megevett spóra.
     */
    public void eatSpore(Spore spore) {
        currentPlace.removeSpore(spore);
        spore.addEffect(this);
    }

    public void resetEffect() {
        setAccelerated(false);
        setDecelerated(false);
        setParalized(false);
        setCutPrevented(false);
    }

    /**
     * A rovar megpróbál átlépni egy másik tectonra, ha az fonallal össze van kötve az aktuálissal.
     *
     * @param targetTecton A cél tecton, ahova a rovar mozogni próbál.
     * @return Igaz, ha a mozgás sikeres volt, különben hamis.
     */
    public boolean move(Tecton targetTecton) {

        if(targetTecton.isConnectedWithYarn(currentPlace)){
            currentPlace.removeInsect(this);
            targetTecton.addInsect(this);
            currentPlace = targetTecton;
            //System.out.println("A választott tektonra vezet gombafonal. A mozgás végrehajtva");
            return true;
        }
        else{
            //System.out.println("A választott tektonra erről a tektonról nem vezet gombafonal. A mozgás nem lehetséges.");
            return false;
        }
    }

    public String getId(){
        return testID;
    }
}
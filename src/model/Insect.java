package model;


import java.io.Serializable;

/**
 * Az Model.Insect osztály egy rovart reprezentál, amely egy adott tectonon helyezkedik el,
 * és különböző állapotváltozásokat szenvedhet el spórák hatására.
 */
public class Insect implements Serializable {
    private Tecton currentPlace; // Az aktuális tecton, ahol a rovar tartózkodik
    private Entomologist owner; //visszadja melyik rovarász játékoshoz tartozik a rovar
    // A rovarra ható spóra effektusok
    private boolean decelerated;   // Lassító spóra hatása
    private boolean paralized;     // Bénító spóra hatása
    private boolean accelerated;   // Gyorsító spóra hatása
    private boolean cutPrevented;  // Vágásgátló spóra hatása

    private String testID; //Csak tesztelésnél használt azonosító

    private int effectDuration = 0; // 0 jelentése: nincs effektus, pozitív szám: hátralévő körök száma

    /**
     * Visszaadja a rovar aktuálisan melyik tektonon van
     *
     * @return a tecton ahol található
     */
    public  Tecton getPlace(){
        return currentPlace;
    }
    /**
     * Megadja egy string formájában, hogy a rovar milyen effect alatt áll
     *
     * @return egy string ami megadja az effect nevát, vagy "none"-t ha noncs rajta éppen semmilyen effect
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
     * Konstruktor egy új Insect objektum létrehozásához.
     * A konstruktor inicializálja az élőlény nézetét, beállítja az alapértelmezett
     * állapotokat (nem gyorsított, nem lassított, nem bénult, vágás elleni védelem nincs aktiválva),
     * majd hozzárendeli az élőlényt a megadott helyhez.
     * @param place A {@code Tecton} típusú hely, ahol az élőlény megjelenik.
     * @param owner Az {@code Entomologist}, akihez az élőlény tartozik.
     */
    public Insect(Tecton place,Entomologist owner){
        decelerated = false;
        paralized = false;
        accelerated = false;
        cutPrevented = false;
        this.owner = owner;
        currentPlace = place;
        place.addInsect(this);
    }

    /**
     * Teszteláshez használt kontruktor, megegyezik a rendes kontruktorral,
     * csak annyiban tér el, hogy a tesztelésnél használ azonosítót is beállítja
     */
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
     * @param errefeleVagunk az aktualis irány, hogy melyik tekton fele kell vágni a fonalat
     */
    public void cutYarn(Yarn yarn, Tecton errefeleVagunk) {
        System.out.println("INSECT: " + getId() + " megpróbálja elvágni a " + yarn.getId() + " fonalat a " + errefeleVagunk.getId() + " tecton irányába");
        int index = yarn.tectons.indexOf(currentPlace);
        if(index == 0 || index == yarn.getTectons().size()-1) {
            System.out.println("INSECT: A fonal végpontján vágás történik");
            currentPlace.removeYarn(yarn, errefeleVagunk);
            errefeleVagunk.removeYarn(yarn, errefeleVagunk);
        } else {
            System.out.println("INSECT: A fonal közepén vágás történik");
            currentPlace.removeYarn(yarn, errefeleVagunk);
        }
        System.out.println("INSECT: A " + getId() + " rovar sikeresen elvágta a " + yarn.getId() + " fonalat.");
    }

    /**
     * A rovar elfogyaszt egy adott spórát, és az alkalmazza a hatását a rovarra.
     *
     * @param spore A megevett spóra.
     */
    public void eatSpore(Spore spore) {
        System.out.println("INSECT: " + getId() + " elfogyasztja a " + spore.getId() + " spórát");
        currentPlace.removeSpore(spore);
        spore.addEffect(this);
        System.out.println("INSECT: A " + spore.getId() + " spóra hatása érvényesült a " + getId() + " rovaron");
    }

    /**
     * Beállítja az aktuális effektus időtartamát körökben.
     * @param duration Az effektus időtartama körökben (általában 2 kör)
     */
    public void setEffectDuration(int duration) {
        this.effectDuration = duration;
    }

    /**
     * Visszaadja az effektus hátralévő időtartamát.
     * @return Az effektus hátralévő köreinek száma, vagy 0 ha nincs aktív effektus
     */
    public int getEffectDuration() {
        return effectDuration;
    }

    /**
     * Csökkenti az effektus időtartamát és törli az effektust, ha az időtartam eléri a 0-t.
     * Minden kör végén meg kell hívni.
     */
    public void decrementEffectDuration() {
        if (effectDuration > 0) {
            effectDuration--;
            System.out.println("INSECT: " + getId() + " effektus időtartama csökkent, hátralévő idő: " + effectDuration);
            if (effectDuration == 0) {
                System.out.println("INSECT: " + getId() + " effektusa lejárt, visszaállítás alapállapotba");
                resetEffect();
            }
        }
    }

    /**
     * Visszaállítja a rovaron lévő effektusokat alapállapotba
     */
    public void resetEffect() {
        String currentEffect = getCurrentEffect();
        setAccelerated(false);
        setDecelerated(false);
        setParalized(false);
        setCutPrevented(false);
        effectDuration = 0;
        System.out.println("INSECT: " + getId() + " " + (currentEffect != null ? currentEffect : "nincs") + " effektusa megszűnt");
    }

    /**
     * A rovar megpróbál átlépni egy másik tectonra, ha az fonallal össze van kötve az aktuálissal.
     *
     * @param targetTecton A cél tecton, ahova a rovar mozogni próbál.
     * @return Igaz, ha a mozgás sikeres volt, különben hamis.
     */
    public boolean move(Tecton targetTecton) {
        System.out.println("INSECT: " + getId() + " megpróbál átmozogni a " + targetTecton.getId() + " tectonra");
        if(!decelerated && targetTecton.isConnectedWithYarn(currentPlace)){
            currentPlace.removeInsect(this);
            targetTecton.addInsect(this);
            currentPlace = targetTecton;
            System.out.println("INSECT: " + getId() + " sikeresen átmozgott a " + targetTecton.getId() + " tectonra");
            return true;
        }
        else{
            System.out.println("INSECT: " + getId() + " nem tud átmozogni a " + targetTecton.getId() + " tectonra, mert nincs összekötve fonallal vagy lassító hatás alatt áll");
            return false;
        }
    }

    /** Vissszaadja a tesztelésnél használt id-t */
    public String getId(){
        return testID;
    }

    @Override
    public String toString(){
        return this.testID;
    }
}

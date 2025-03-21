import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A MushroomPicker osztály a Player osztályt kiterjeszti,
 * és a gombaszedő játékos speciális képességeit valósítja meg.
 */
public class MushroomPicker extends Player {
    /**
     * A játékos által birtokolt gombák listája.
     */
    private List<Mushroom> ownedMushrooms;
    /**
     * Statikus lista, amely minden példány számára közös birtokolt fonalakat tartalmazza.
     */
    private static List<Yarn> ownedYarns;

    /**
     * Konstruktor a MushroomPicker példány létrehozásához.
     *
     * @param name     A játékos neve.
     * @param mushroom Az induló gomba, amelyet a játékos birtokol.
     */
    public MushroomPicker(String name, Mushroom mushroom) {
        super(name, 0); // A szülő (Player) osztály konstruktorának meghívása a játékos névvel és induló értékkel (0)
        ownedMushrooms = new ArrayList<>(); // A ownedMushrooms lista inicializálása egy új ArrayList példánnyal
        ownedMushrooms.add(mushroom); // Az induló gomba hozzáadása a gombák listájához
        ownedYarns = new ArrayList<Yarn>(); // A statikus ownedYarns lista inicializálása egy új ArrayList példánnyal
    }

    /**
     * Visszaadja a játékos által birtokolt gombák listáját.
     *
     * @return A gombák listája.
     */
    public List<Mushroom> getOwnedMushrooms() {
        return ownedMushrooms;
    }

    /**
     * Visszaadja a statikus, minden játékos által használt fonalak listáját.
     *
     * @return A fonalak listája.
     */
    public List<Yarn> getOwnedYarns() {
        return ownedYarns;
    }

    /**
     * Ellenőrzi, hogy a megadott tecton elérhető-e a birtokolt fonalak alapján.
     *
     * @param tecton A vizsgálandó tecton.
     * @return Igaz, ha a tecton elérhető, különben hamis.
     */
    private static boolean isTectonInRange(Tecton tecton) {
        // Végigiterál a birtokolt fonalak listáján
        for (int i = 0; i < ownedYarns.size(); i++) { // Ciklus a fonalak listáján
            // Ellenőrzi, hogy az aktuális fonalhoz tartozó tectonok között szerepel-e a megadott tecton
            if (ownedYarns.get(i).getTectons().contains(tecton)) {
                return true; // Ha megtalálja, visszatérünk true értékkel
            }
        }
        return false; // Ha egyik fonal sem tartalmazza a tectont, visszatérünk false értékkel
    }

    /**
     * Eldönti, hogy a játékos képes-e meghódítani a megadott tectont a szabályok szerint.
     *
     * @param tecton A meghódítandó tecton.
     * @return Igaz, ha a tecton meghódítható, különben hamis.
     */
    private boolean canIConquerTecton(Tecton tecton){
        int limit = tecton.getYarnLimit();
        if(limit == 2){
            return true;
        }
        if(tecton.getYarns().size() == 0){
            return true;
        }
        if (limit == 1 && tecton.getYarns().get(0).getPlayer() == this){ //tipus lekerdezes??
            return true;
        }
        return false;
    }

    /**
     * Lekéri a megadott tecton szomszédait.
     *
     * @param tecton A tecton, amelynek szomszédjait szeretnénk lekérdezni.
     * @return A tecton szomszédainak listája.
     */
    private List<Tecton> addNeighbours(Tecton tecton){
        return tecton.getNeighbours();
    }

    /**
     * Lekéri az első szintű szomszédok szomszédait, azaz a másodfokú szomszédokat.
     *
     * @param tectons Az első szintű szomszédok listája.
     * @return A másodfokú szomszédok listája.
     */
    private List<Tecton> addsecondNeighbours(List<Tecton> tectons) {
        List<Tecton> secondNeighbours = tectons; // Másolat készítése az első szintű szomszédok listájáról
        for (int i = 0; i < tectons.size(); i++) { // Iterálás az első szintű szomszédokon
            // Iterálás az aktuális tecton szomszédain
            for (int j = 0; j < tectons.get(i).getNeighbours().size(); j++) {
                // Ha a másodfokú lista még nem tartalmazza az aktuális szomszédot
                if (!secondNeighbours.contains(tectons.get(i).getNeighbours().get(j))) {
                    //EZZZ NEM FIX JO
                    secondNeighbours.add(tectons.get(j)); // Hozzáadja az aktuális tectont a listához
                }
            }
        }
        return secondNeighbours; // Visszaadja a másodfokú szomszédok listáját
    }


    /**
     * A gomba növesztésének akciója a cél tectonon, ha a feltételek teljesülnek.
     *
     * @param targetTecton A cél tecton, ahol a gombát növeszteni szeretnénk.
     */
    public void actionGrowMushroom(Tecton targetTecton) {
        // Ellenőrzi, hogy a cél tecton nem gátolja-e a gomba növekedést
        if (!targetTecton.getMushroomPrevent()) {
            // Ellenőrzi, hogy a tecton már tartalmaz-e gombát
            if (targetTecton.getMushroom() != null) {
                // Ellenőrzi, hogy legalább 3 spóra van-e a tectonon
                if (targetTecton.getSpores().size() >= 3) {
                    // Ellenőrzi, hogy a tecton elérhető-e a birtokolt fonalakkal
                    if (isTectonInRange(targetTecton)) {
                        Mushroom newMushroom = new Mushroom(targetTecton); // Új gomba létrehozása a cél tecton alapján
                        targetTecton.addMushroom(newMushroom); // Új gomba hozzáadása a tectonhoz
                        ownedMushrooms.add(newMushroom); // Új gomba hozzáadása a játékos gombáihoz
                        // Három spóra eltávolítása a tectonról
                        targetTecton.removeSpore(targetTecton.getSpore().remove(0)); // Első spóra eltávolítása
                        targetTecton.removeSpore(targetTecton.getSpore().remove(0)); // Második spóra eltávolítása
                        targetTecton.removeSpore(targetTecton.getSpore().remove(0)); // Harmadik spóra eltávolítása
                    }
                }
            }
        }
    }
    /**
     * A fonal növesztésének akciója a cél tectonon a kiválasztott fonallal.
     *
     * @param targetTecton A cél tecton.
     * @param selectedYarn A kiválasztott fonal, amelyet felhasználunk.
     * @param firstTime    Jelzi, hogy ez az első próbálkozás.
     */
    public void actionGrowYarn(Tecton targetTecton, Yarn selectedYarn, boolean firstTime) {
        // Ellenőrzi, hogy a kiválasztott fonal egyik végpontja szomszédos-e a cél tectonnal
        if (selectedYarn.getTectons().get(0).isNeighbour(targetTecton) ||
                selectedYarn.getTectons().get(1).isNeighbour(targetTecton)) {
            // Ellenőrzi, hogy a tecton meghódítható-e és ez az első próbálkozás
            if (canIConquerTecton(targetTecton) && firstTime) {
                // Ellenőrzi, hogy a tecton tartalmaz-e spórát
                if (targetTecton.getSpore().size() != 0) { // Ha spóra található
                    targetTecton.removeSpore(targetTecton.getSpore().get(0)); // Távolítja az első spórát a tectonról
                    actionGrowYarn(targetTecton, selectedYarn, false); // Újra meghívja a metódust, immár nem első próbálkozásként
                }
                // Ha nincs spóra a tectonon
                else { // Nincs spóra
                    targetTecton.growYarn(selectedYarn); // Elindítja a fonal növesztését a kiválasztott fonallal
                }
            }
        }
    }

    /**
     * A spóra szórásának akciója, mely során a megadott gombából spórát próbál eljuttatni a cél tectonra.
     *
     * @param targetTecton A cél tecton.
     * @param mushroom   A forrás gomba, amelyből a spóra indul.
     */
    public void actionSporeDispersion(Tecton targetTecton, Mushroom mushroom) {
        // Ellenőrzi, hogy a gombának van-e spórája
        if (!mushroom.getHasSpore()) {
            System.out.println("Nincs spóra a gombatestben"); // Kiírja, hogy nincs spóra
            return; // Kilép a metódusból
        }

        int age = mushroom.getAge(); // Lekérdezi a gomba korát
        List<Tecton> neighbours = addNeighbours(targetTecton); // Lekéri a cél tecton szomszédait

        // Ha a gomba kora nagyobb, mint 10, bővíti a hatókört a másodfokú szomszédokra
        if (age > 10) {
            List<Tecton> secondNeighbours = addsecondNeighbours(neighbours); // Lekéri a másodfokú szomszédokat
            neighbours = secondNeighbours; // Frissíti a szomszédok listáját
        }
        // Ellenőrzi, hogy a cél tecton szerepel-e a szomszédok között
        if (neighbours.contains(targetTecton)) {
            System.out.println("A kiválasztott tekton elérhető"); // Kiírja, hogy a tecton elérhető
            mushroom.disperseSpore(targetTecton); // Elindítja a spóra szórását a cél tectonra
        } else {
            System.out.println("a target tekton nem elérhető"); // Kiírja, hogy a tecton nem elérhető
            return; // Kilép a metódusból
        }
    }


}
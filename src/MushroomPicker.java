import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public void addYarn(Yarn y) {
        ownedYarns.add(y);
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
        System.out.println("The tecton has been conquered already");
        return false;
    }

    /**
     * Lekéri az első szintű szomszédok szomszédait, azaz a másodfokú szomszédokat.
     *
     * @param tecton A kiinduló tekton.
     * @return A másodfokú szomszédok listája.
     */
        private List<Tecton> addSecondNeighbours(Tecton tecton) {
        List<Tecton> secondNeighbours = new ArrayList<>(); // Másolat készítése az első szintű szomszédok listájáról
        List<Tecton> firstNeighbours = tecton.getNeighbours();
        for (int i = 0; i < firstNeighbours.size(); i++) { // Iterálás az első szintű szomszédokon
            // Iterálás az aktuális tecton szomszédain
            for (int j = 0; j < firstNeighbours.get(i).getNeighbours().size(); j++) {
                // Ha a másodfokú lista még nem tartalmazza az aktuális szomszédot
                if (!secondNeighbours.contains(firstNeighbours.get(i).getNeighbours().get(j)) && (firstNeighbours.get(i).getNeighbours().get(j) != tecton)) {
                    secondNeighbours.add(firstNeighbours.get(i).getNeighbours().get(j)); // Hozzáadja az aktuális tectont a listához
                }
            }
        }
        for(int i = 0; i < firstNeighbours.size(); i++){
            if(!secondNeighbours.contains(firstNeighbours.get(i))){
                secondNeighbours.add(firstNeighbours.get(i));
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
        final String errorMessage = "Feltetelek nem teljesultek,";

        // Ellenőrzi, hogy a cél tecton nem gátolja-e a gomba növekedést
        if (!targetTecton.getMushroomPrevent()) {
            // Ellenőrzi, hogy a tecton már tartalmaz-e gombát
            if (!Objects.nonNull(targetTecton.getMushroom())) {
                // Ellenőrzi, hogy legalább 3 spóra van-e a tectonon
                //System.out.println(targetTecton.getSpores().size());
                if (targetTecton.getSpores().size() >= 3) {
                    // Ellenőrzi, hogy a tecton elérhető-e a birtokolt fonalakkal
                    if (isTectonInRange(targetTecton)) {
                        Mushroom newMushroom = new Mushroom(targetTecton); // Új gomba létrehozása a cél tecton alapján
                        ownedMushrooms.add(newMushroom); // Új gomba hozzáadása a játékos gombáihoz
                        // Három spóra eltávolítása a tectonról
                        targetTecton.removeSpore(targetTecton.getSpores().remove(0)); // Első spóra eltávolítása
                        targetTecton.removeSpore(targetTecton.getSpores().remove(0)); // Második spóra eltávolítása
                        targetTecton.removeSpore(targetTecton.getSpores().remove(0)); // Harmadik spóra eltávolítása
                        this.addPoints(1);
                    } else {
                        System.out.println(errorMessage + " Nem erheto el fonalakkal");
                    }
                } else {
                    System.out.println(errorMessage + " Nincs eleg spora a tektonon");
                }
            } else {
                System.out.println(errorMessage + " Van mar gomba a tektonon");
            }
        } else {
            System.out.println(errorMessage + " Nem lehet gombat noveszteni a tektonon");
        }
    }
    /**
     * A fonal növesztésének akciója a cél tectonon a kiválasztott fonallal.
     *
     * @param targetTecton A cél tecton.
     * @param selectedYarn A kiválasztott fonal, amelyet felhasználunk.
     */
    public void actionGrowYarn(Tecton targetTecton, Yarn selectedYarn) {
        // Ellenőrzi, hogy a kiválasztott fonal egyik pontja szomszédos-e a cél tektonnal
        //System.out.print("Fonal tektonjai: " + selectedYarn.getTectons() + "\n\n");
        for(int i = 0; i < selectedYarn.getTectons().size(); i++) {
            if(selectedYarn.getTectons().get(i).isNeighbour(targetTecton)){
                // Ellenőrzi, hogy a tecton meghódítható-e és ez az első próbálkozás
                if (canIConquerTecton(targetTecton)) {
                    targetTecton.growYarn(selectedYarn); // Elindítja a fonal növesztését a kiválasztott fonallal
                    return;
                } else {
                    System.out.println("Mas jatekosnak mar van a fonala ezen  a tektonon");
                    return;
                }
            }
            else {
                System.out.println("The selected tecton does not neighbour the yarn");
            }

        }
        System.out.println("A kivalasztott yarn-hoz tartozot tektonok kozul egyk se szomszedos a kivalasztott tektonnal, \nNem lehet fonalal növeszteni");

    }

    public boolean sporeCheck(Tecton targetTecton) {
        if(!targetTecton.getSpores().isEmpty()){
            targetTecton.getSpores().remove(0);
            for(int i=1; i<targetTecton.getSpores().size(); i++){
                targetTecton.getSpores().set(i-1, targetTecton.getSpores().get(i));
            }
            return true;
        }
       return false;
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
        if(age <= 10) {
            // Ellenőrzi, hogy a cél tecton szerepel-e a szomszédok között
            //Ha nem, akkor visszatér a függvény
            if (!mushroom.getTecton().getNeighbours().contains(targetTecton)) {
                System.out.println("a target tekton nem elérhető");
                return;
            }
            //Ha szerepel, akkor elindítja a szórást
            else{
                mushroom.disperseSpore(targetTecton);   // Elindítja a spóra szórását a cél tectonra
            }
        }
        // Ha a gomba kora nagyobb, mint 10, bővíti a hatókört a másodfokú szomszédokra
        else {
            // Ellenőrzi, hogy a cél tecton szerepel-e a szomszédok között
            //Ha nem, akkor visszatér a függvény
            if (!addSecondNeighbours(mushroom.getTecton()).contains(targetTecton)) {
                System.out.println("A target tekton nem elérhető");
                return;
            }
            //Ha szerepel, akkor elindítja a szórást
            else{
                mushroom.disperseSpore(targetTecton);   // Elindítja a spóra szórását a cél tectonra
            }
        }
    }
}

package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A MushroomPicker osztály a Player osztályt kiterjeszti,
 * és a gombaszedő játékos speciális képességeit valósítja meg.
 */
public class MushroomPicker extends Player implements Serializable {

    /**
     * A játékos által birtokolt gombák listája.
     */
    private List<Mushroom> ownedMushrooms;
    /**
     * Statikus lista, amely minden példány számára közös birtokolt fonalakat tartalmazza.
     */
    private List<Yarn> ownedYarns;



    /**
     * Konstruktor a MushroomPicker példány létrehozásához.
     *
     * @param name     A játékos neve.
     * @param mushroom Az induló gomba, amelyet a játékos birtokol.
     * @param testId A tesztelésnál használt azonosító
     */
    public MushroomPicker(String name, Mushroom mushroom, String testId) {
        super(name, 0, testId); // A szülő (Player) osztály konstruktorának meghívása a játékos névvel és induló értékkel (0)

        ownedMushrooms = new ArrayList<>(); // A ownedMushrooms lista inicializálása egy új ArrayList példánnyal
        ownedMushrooms.add(mushroom); // Az induló gomba hozzáadása a gombák listájához
        ownedYarns = new ArrayList<>(); // A statikus ownedYarns lista inicializálása egy új ArrayList példánnyal


    }

    /**
     * Konstruktor a MushroomPicker példány létrehozásához.
     *
     * @param name     A játékos neve.
     * @param testId A tesztelésnál használt azonosító
     */
    public MushroomPicker(String name, String testId) {
        super(name, 0, testId); // A szülő (Player) osztály konstruktorának meghívása a játékos névvel és induló értékkel (0)
        ownedMushrooms = new ArrayList<>(); // A ownedMushrooms lista inicializálása egy új ArrayList példánnyal
        ownedYarns = new ArrayList<>(); // A statikus ownedYarns lista inicializálása egy új ArrayList példánnyal
        //hard coded

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
     * Hozzáad egy gombát a gombászhoz.
     * @param mushroom A gomba
     */
    public void addMushroom(Mushroom mushroom){
        ownedMushrooms.add(mushroom);
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
     * Hozzáad egy fonalat az által birtokolt fonalakhoz
     *
     * @param y A hozzáadandó fonál
     * */
    public void addYarn(Yarn y) {
        ownedYarns.add(y);
    }

    public void removeYarn(Yarn y) {
        ownedYarns.remove(y);
    }

    /**
     * Ellenőrzi, hogy a megadott tecton elérhető-e a birtokolt fonalak alapján.
     *
     * @param tecton A vizsgálandó tecton.
     * @return Igaz, ha a tecton elérhető, különben hamis.
     */
    private boolean isTectonInRange(Tecton tecton) {
        for (Yarn yarn : ownedYarns) {
            if (yarn.getTectons().contains(tecton)) {
                return true;
            }
        }
        return false;
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
        //System.out.println("The tecton has been conquered already");
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
     * @param mushroomId tesztelésnél használt gomba azonosító
     * @param yarnId tesztelésnél használt gombafonal azonosító
     * @param yarnType a gombafonal, amit a gombából lehet majd növeszteni
     */
    public void actionGrowMushroom(Tecton targetTecton, String mushroomId, String yarnType, String yarnId) {
        System.out.println("MUSHROOMPICKER: " + getName() + " megpróbál gombát növeszteni a " + targetTecton.getId() + " tectonon");
        final String errorMessage = "Feltetelek nem teljesultek,";

        // Ellenőrzi, hogy a cél tecton nem gátolja-e a gomba növekedést
        if (!targetTecton.getMushroomPrevent()) {
            // Ellenőrzi, hogy a tecton már tartalmaz-e gombát
            if (!Objects.nonNull(targetTecton.getMushroom())) {
                // Ellenőrzi, hogy legalább 3 spóra van-e a tectonon
                if (targetTecton.getSpores().size() >= 3) {
                    // Ellenőrzi, hogy a tecton elérhető-e a birtokolt fonalakkal
                    if (isTectonInRange(targetTecton)) {
                        System.out.println("MUSHROOMPICKER: " + getName() + " - Minden feltétel teljesül a gomba növesztéséhez");
                        Mushroom newMushroom = new Mushroom(targetTecton, this, mushroomId); // Új gomba létrehozása a cél tecton alapján
                        // Új gomba hozzáadása a játékos gombáihoz
                        System.out.println("MUSHROOMPICKER: " + getName() + " - Új gomba létrehozva: " + mushroomId);
                        
                        // Három spóra eltávolítása a tectonról
                        targetTecton.removeSpore(targetTecton.getSpores().remove(0)); // Első spóra eltávolítása
                        targetTecton.removeSpore(targetTecton.getSpores().remove(0)); // Második spóra eltávolítása
                        targetTecton.removeSpore(targetTecton.getSpores().remove(0)); // Harmadik spóra eltávolítása
                        System.out.println("MUSHROOMPICKER: " + getName() + " - 3 spóra eltávolítva a " + targetTecton.getId() + " tectonról");

                        //new yarn initialized with the length 0
                        Yarn newYarn;
                        switch (yarnType){
                            case "Normal":
                                newYarn = new Yarn(newMushroom, this, yarnId);

                                break;
                            case "Killer":
                                newYarn = new KillerYarn(newMushroom, this, yarnId);

                                break;
                            default:
                                newYarn = new Yarn(newMushroom, this, yarnId);

                                break;
                        }
                        targetTecton.addYarn(newYarn);

                        System.out.println("MUSHROOMPICKER: " + getName() + " - Új fonál létrehozva: " + yarnId);
                        
                        addPoints(3);
                        System.out.println("MUSHROOMPICKER: " + getName() + " - 3 pont hozzáadva, új pontszám: " + getPoints());
                    } else {
                        System.out.println("MUSHROOMPICKER: " + getName() + " - " + errorMessage + " Nem érhető el fonalakkal");
                    }
                } else {
                    System.out.println("MUSHROOMPICKER: " + getName() + " - " + errorMessage + " Nincs elég spóra a tectonon (minimum 3 kell)");
                }
            } else {
                System.out.println("MUSHROOMPICKER: " + getName() + " - " + errorMessage + " Van már gomba a tectonon");
            }
        } else {
            System.out.println("MUSHROOMPICKER: " + getName() + " - " + errorMessage + " Nem lehet gombát növeszteni a tectonon");
        }
        notifyObservers();
    }


    /**
     * A fonal növesztésének akciója a cél tectonon a kiválasztott fonallal.
     *
     * @param sourceTecton A forrás tecton.
     * @param targetTecton A cél tecton.
     * @param selectedYarn A kiválasztott fonal, amelyet felhasználunk.
     */
   public void actionGrowYarn(Tecton sourceTecton, Tecton targetTecton, Yarn selectedYarn) {
        System.out.println("MUSHROOMPICKER: " + getName() + " megpróbál fonalat növeszteni a " + sourceTecton.getId() + 
                           " tectonról a " + targetTecton.getId() + " tectonra a " + selectedYarn.getId() + " fonállal");
        
        // Ellenőrzi, hogy a kiválasztott fonal egyik pontja szomszédos-e a cél tektonnal
        if(selectedYarn.getTectons().contains(sourceTecton)) {
            if (sourceTecton.isNeighbour(targetTecton)) {
                // Ellenőrzi, hogy a tecton meghódítható-e és ez az első próbálkozás
                if (canIConquerTecton(targetTecton)) {
                    System.out.println("MUSHROOMPICKER: " + getName() + " - Minden feltétel teljesül a fonál növesztéséhez");
                    targetTecton.growYarn(selectedYarn); // Elindítja a fonal növesztését a kiválasztott fonallal
                    System.out.println("MUSHROOMPICKER: " + getName() + " - A " + selectedYarn.getId() + " fonál sikeresen megnőtt a " + targetTecton.getId() + " tectonra");
                    
                    if(!targetTecton.getSpores().isEmpty()) {
                        targetTecton.getSpores().remove(targetTecton.getSpores().size() - 1); // utolsó sporat toroljuk ha volt a tektonon spora
                        System.out.println("MUSHROOMPICKER: " + getName() + " - Egy spóra eltávolítva a " + targetTecton.getId() + " tectonról");
                    }
                } else {
                    System.out.println("MUSHROOMPICKER: " + getName() + " - Más játékosnak már van fonala ezen a tectonon");
                }
            } else {
                System.out.println("MUSHROOMPICKER: " + getName() + " - A tectonok nem szomszédosak");
            }
        }
        else {
            System.out.println("MUSHROOMPICKER: " + getName() + " - A kiválasztott tecton nem szomszédos a fonállal");
        }
        notifyObservers();
    }

    /**
     * A spóra szórásának akciója, mely során a megadott gombából spórát próbál eljuttatni a cél tectonra.
     *
     * @param targetTecton A cél tecton.
     * @param mushroom   A forrás gomba, amelyből a spóra indul.
     */
    public void actionSporeDispersion(Tecton targetTecton, Mushroom mushroom, String type, String id) {
        System.out.println("MUSHROOMPICKER: " + getName() + " megpróbál " + type + " típusú spórát szórni a " + 
                           mushroom.getId() + " gombából a " + targetTecton.getId() + " tectonra");
        
        // Ellenőrzi, hogy a gombának van-e spórája
        if (!mushroom.getHasSpore()) {
            System.out.println("MUSHROOMPICKER: " + getName() + " - A gombának nincs spórája");
            return;
        }

        int age = mushroom.getAge(); // Lekérdezi a gomba korát
        System.out.println("MUSHROOMPICKER: " + getName() + " - A " + mushroom.getId() + " gomba kora: " + age);
        
        if(age <= 10) {
            // Ellenőrzi, hogy a cél tecton szerepel-e a szomszédok között
            if (mushroom.getTecton().getNeighbours().contains(targetTecton)) {
                System.out.println("MUSHROOMPICKER: " + getName() + " - A célpont szomszédos, spóra szórása folyamatban");
                mushroom.disperseSpore(targetTecton, type, id);   // Elindítja a spóra szórását a cél tectonra
                System.out.println("MUSHROOMPICKER: " + getName() + " - A spóra sikeresen elszórva a " + targetTecton.getId() + " tectonra");
            } else {
                System.out.println("MUSHROOMPICKER: " + getName() + " - A célpont nem szomszédos a gombával");
            }
        }
        // Ha a gomba kora nagyobb, mint 10, bővíti a hatókört a másodfokú szomszédokra
        else {
            System.out.println("MUSHROOMPICKER: " + getName() + " - A gomba kora > 10, másodfokú szomszédokra is szórhat spórát");
            // Ellenőrzi, hogy a cél tecton szerepel-e a szomszédok között
            if (addSecondNeighbours(mushroom.getTecton()).contains(targetTecton)) {
                System.out.println("MUSHROOMPICKER: " + getName() + " - A célpont másodfokú szomszéd, spóra szórása folyamatban");
                mushroom.disperseSpore(targetTecton, type, id);
                System.out.println("MUSHROOMPICKER: " + getName() + " - A spóra sikeresen elszórva a " + targetTecton.getId() + " tectonra");
            } else {
                System.out.println("MUSHROOMPICKER: " + getName() + " - A célpont nem érhető el (nem szomszéd és nem másodfokú szomszéd)");
            }
        }
        notifyObservers();
    }
}

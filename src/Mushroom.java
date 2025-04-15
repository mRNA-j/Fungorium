/**
 * A gomba entitást reprezentáló osztály.
 * Kezeli a gomba korát, spóráit és a tectonhoz való kapcsolódását.
 */
public class Mushroom {
    private String id; // Hozzáadott ID mező
    private int numberOfDispersions;
    private int newSporeGrowth;
    private int age;
    private boolean hasSpore;
    private Tecton tecton;
    private Spore currentSpore; // Hozzáadott mező a jelenlegi spóra tárolására

    /**
     * Létrehoz egy új gomba objektumot a megadott tectonon.
     * @param tecton A tecton, amin a gomba elhelyezkedik.
     */
    public Mushroom(Tecton tecton) {
        this.tecton = tecton;
        tecton.addMushroom(this);
        age = 0;
        numberOfDispersions = 0;
        newSporeGrowth = 5;
        hasSpore = false;
        currentSpore = null;
    }

    /**
     * Visszaadja a gomba azonosítóját.
     * @return A gomba azonosítója.
     */
    public String getId() {
        return id;
    }

    /**
     * Beállítja a gomba azonosítóját.
     * @param id Az új azonosító.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Visszaadja a gomba életkorát.
     * @return A gomba életkora.
     */
    public int getAge() {
        return age;
    }

    /**
     * Visszaadja, hogy a gombának van-e spórája.
     * @return Igaz, ha a gombának van spórája, egyébként hamis.
     */
    public boolean getHasSpore() {
        return hasSpore;
    }

    /**
     * Beállítja a gomba spóra állapotát.
     * @param hasSpore Igaz, ha a gombának van spórája, egyébként hamis.
     */
    public void setHasSpore(boolean hasSpore) {
        this.hasSpore = hasSpore;
    }

    /**
     * Visszaadja a gombában lévő jelenlegi spórát.
     * @return A jelenlegi spóra, vagy null ha nincs.
     */
    public Spore getCurrentSpore() {
        return currentSpore;
    }

    /**
     * Spórát növeszt a gombában.
     * @param spore A növesztendő spóra.
     */
    public void growSpore(Spore spore) {
        if (!hasSpore) {
            this.currentSpore = spore;
            this.hasSpore = true;
            System.out.println("Spóra sikeresen megnövesztve a gombában.");
        } else {
            System.out.println("A gombának már van spórája, nem lehet újat növeszteni.");
        }
    }

    /**
     * Spóra kilövését kezeli.
     * A gombában lévő spórát hozzáadja a megadott tectonhoz.
     * @param tecton A tecton, amire a spórát kilövi.
     */
    public void disperseSpore(Tecton tecton) {
        if (hasSpore && currentSpore != null) {
            System.out.println("Spóra kilőve");
            tecton.addSpore(currentSpore);
            numberOfDispersions++;
            hasSpore = false;
            currentSpore = null;
            restartSporeGrowth();
        } else {
            System.out.println("Nincs spóra a gombában, amit ki lehetne lőni.");
        }
    }

    /**
     * Újraindítja a spóra növekedési idejét.
     * Az új spóra növekedéséig hátralévő időt 5-re állítja.
     */
    public void restartSporeGrowth() {
        newSporeGrowth = 5;
    }

    /**
     * Visszaadja a tectont, amelyen a gomba található.
     * @return A tecton, amin a gomba elhelyezkedik.
     */
    public Tecton getTecton() {
        return tecton;
    }
}

/**
 * Spóra kilövését kezeli.
 * A felhasználótól bekéri a kilövendő spóra típusát,
 * létrehozza a megfelelő spórát és hozzáadja a tectonhoz.
 * @param tecton A tecton, amire a spórát kilövi.
 */
    /*public void disperseSpore(Tecton tecton) {
        System.out.println("Melyik sporat akarod kiloni?");
        System.out.println("1. Accelrator");
        System.out.println("2. Paralyzing");
        System.out.println("3. Declerator");
        System.out.println("4. CutPreventing");

        Scanner scanner = new Scanner(System.in);

        String spore = scanner.nextLine();
        Spore sp;

        switch (spore) {
            case "1":
                sp = new AcceleratorSpore();
                break;
            case "2":
                sp = new ParalyzingSpore();
                break;
            case "3":
                sp = new DeceleratorSpore();
                break;
            case "4":
                sp = new CutPreventingSpore();
                break;
            default:
                System.out.println("Invalid spore type selected!");
                return;
        }


        System.out.println("Spora kilove");
        tecton.addSpore(sp);
        numberOfDispersions++;
    }*/
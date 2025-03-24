import java.util.Scanner;

/**
 * A gomba entitást reprezentáló osztály.
 * Kezeli a gomba korát, spóráit és a tectonhoz való kapcsolódását.
 */
public class Mushroom {
    private int numberOfDispersions;
    private int newSporeGrowth;
    private int age;
    private boolean hasSpore;
    private Tecton tecton;

    /**
     * Létrehoz egy új gomba objektumot a megadott tectonon.
     * @param tecton A tecton, amin a gomba elhelyezkedik.
     */
    public Mushroom(Tecton tecton) {
        this.tecton = tecton;
        tecton.addMushroom(this);
        age =0;
        numberOfDispersions = 0;
        newSporeGrowth = 5;
    }

    /**
     * Visszaadja a gomba életkorát.
     * @return A gomba életkora.
     */
    public int getAge(){
        return age;
    }

    /**
     * Visszaadja, hogy a gombának van-e spórája.
     * @return Igaz, ha a gombának van spórája, egyébként hamis.
     */
    public boolean getHasSpore(){
        return hasSpore;
    }

    /**
     * Beállítja a gomba spóra állapotát.
     * @param hasSpore Igaz, ha a gombának van spórája, egyébként hamis.
     */
    public void setHasSpore(boolean hasSpore){
        this.hasSpore = hasSpore;
    }

    /**
     * Spóra kilövését kezeli.
     * A felhasználótól bekéri a kilövendő spóra típusát,
     * létrehozza a megfelelő spórát és hozzáadja a tectonhoz.
     * @param tecton A tecton, amire a spórát kilövi.
     */
    public void disperseSpore(Tecton tecton) {
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
    public Tecton getTecton(){
        return tecton;
    }
}
import java.util.Scanner;

public class Mushroom {
    private int numberOfDispersions;
    private int newSporeGrowth;
    private int age;
    private boolean hasSpore;

    public void disperseSpore(Tecton tecton) {
        System.out.println("Melyik sporat akarod letrehozni?");
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


        System.out.println("Spora hozzáadva");
        tecton.addSpore(sp);
        numberOfDispersions++;

    }
    public void restartSporeGrowth() {
        newSporeGrowth = 5;
    }
}
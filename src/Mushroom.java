import java.util.Scanner;

public class Mushroom {
    private int numberOfDispersions;
    private int newSporeGrowth;
    private int age;
    private boolean hasSpore;
    private Tecton tecton;

    public Mushroom(Tecton tecton) {
        this.tecton = tecton;
        tecton.addMushroom(this);
    }

    public int getAge(){
        return age;
    }
    public boolean getHasSpore(){
        return hasSpore;
    }

    public void setHasSpore(boolean hasSpore){
        this.hasSpore = hasSpore;
    }

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
    public void restartSporeGrowth() {
        newSporeGrowth = 5;
    }

    public Tecton getTecton(){
        return tecton;
    }
    public void setTecton(Tecton t){
        tecton=t;
    }
}
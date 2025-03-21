import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Game game;
    private static MushroomPicker mp;
    private static Entomologist el;
    private static List<Tecton> tectons;
    private static String playerName1;
    private static String playerName2;
    private static String vegString = "\n+++++++++++++++++++++++\nVeghelyzet helyzet.\n+++++++++++++++++++++++";
    private static String kezdoString = "+++++++++++++++++++++++\nAlap helyzet.\n+++++++++++++++++++++++";

    public static void main(String[] args) {
        System.out.println("Szia tesztelo");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Gombasz jatekos neve: ");
        playerName1= "cigo";
        System.out.println("Rovarasz jatekos neve: ");
        playerName2 = "feka";

        //test3();
        //test8();
        test18();
        //test20();
    }

    public static Game initTest() {
        Game game = new Game();
        game.start();

        mp = new MushroomPicker(playerName1, new Mushroom(game.getPlayField().getTectons().get(0)));
        el = new Entomologist(playerName2, new Insect(game.getPlayField().getTectons().get(1)));

        List<Player> pl = new ArrayList<>();
        pl.add(mp);
        pl.add(el);
        game.setPlayers(pl);

        tectons = game.getPlayField().getTectons();
        return game;
    }

    public static void printState() {
        System.out.println("Gombasz:");
        System.out.println("\t Pont: " + mp.getPoints());

        System.out.println("Rovarasz:");
        System.out.println("\t Pont: " + el.getPoints());
        if (!el.getInsect().getAccelerated() && !el.getInsect().getParalized() && !el.getInsect().getDecelerated() && !el.getInsect().getCutPrevented()) {

        }
        if(el.getInsect().getAccelerated()) {
            System.out.println("\t Rovaron levo sporahatas: gyorsito");
        }
        if(el.getInsect().getParalized()) {
            System.out.println("\t Rovaron levo sporahatas: benito");
        }
        if(el.getInsect().getDecelerated()) {
            System.out.println("\t Rovaron levo sporahatas: lassito");
        }
        if(el.getInsect().getCutPrevented()) {
            System.out.println("\t Rovaron levo sporahatas: vagasgatlo");
        }


        for (Tecton tecton : tectons) {
            System.out.println("Tecton" + tecton.getId());
            System.out.println("\tRovar: " + tecton.getInsects());
            System.out.println("\tSporak: " + tecton.getSpores());
            System.out.println("\tGomba: " + tecton.getMushroom());
            System.out.println("\tFonalak: " + tecton.getYarns());
        }

    }

    /*
    * 1:  Teszt1 - Spóra kilövés olyan tektonra, amit elér a gomba
    * */
    public static void test1() {

        game = initTest();

        mp.getOwnedMushrooms().get(0).setHasSpore(true);
        System.out.println(kezdoString);
        printState();
        System.out.println(kezdoString);

        mp.actionSporeDispersion(tectons.get(1),mp.getOwnedMushrooms().get(0));

        System.out.println(vegString);
        printState();
    }

    /*
    * 2:  Teszt2 - Spóra kilövés, de a tekton nem szomszédos
     */
    public static void test2() {
        game = initTest();

        //mp.getOwnedMushrooms().get(0).setHasSpore(true);
        System.out.println(kezdoString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest2 elkezdodott.+++++++++++++++++++++++");

        mp.actionSporeDispersion(tectons.get(2),mp.getOwnedMushrooms().get(0));

        System.out.println(kezdoString);
        printState();
    }

    /*
    * 3:  Teszt3 - Spóra kilövés, de nincs spóra termelve
     */
    public static void test3() {
        game = initTest();
        System.out.println("+++++++++++++++++++++++\nAlap helyzet.\n+++++++++++++++++++++++");
        printState();
        System.out.println("+++++++++++++++++++++++\nTest3 elkezdodott.+++++++++++++++++++++++");

        mp.actionSporeDispersion(tectons.get(1),mp.getOwnedMushrooms().get(0));

        System.out.println("\n+++++++++++++++++++++++\nVeghelyzet helyzet.\n+++++++++++++++++++++++");
        printState();
    }

    /*
     * 4:  Teszt4 - Tekton kettétörése, ha van rajta gombafonal
     */
    public static void test4() {

    }

    /*
     * 5:  Teszt5 - Tekton kettétörése,rovar van a tektonon
     */
    public static void test5() {


    }

    /*
     * 6:  Teszt6 - Tekton ketttörése, gombatest van a tektonon
     */
    public static void test6() {

    }

    /*
     * 7:  Teszt7 - Tekton ketttörése, tektonon nincsen semmi
     */
    public static void test7() {

    }

    /*
     * 8:  Teszt8 - Gombafonal növesztése nem szomszédos tektonra
     */
    public static void test8() {
        game = initTest();

        Yarn yarn= new Yarn(mp.getOwnedMushrooms().get(0));
        yarn.addTecton(tectons.get(0));
        yarn.addTecton(tectons.get(1));
        System.out.println(kezdoString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest8 elkezdodott.+++++++++++++++++++++++");

        mp.actionGrowYarn(tectons.get(3),yarn,true);

        System.out.println(kezdoString);
        printState();

    }

    /*
     * 9:  Teszt9 - Gombafonal növesztése más játékos fonala van a tektonon ( csak egyfjta fonal növezthető a tektonra)
     */
    public static void test9() {

    }

    /*
     * 10: Teszt10 - Gombafonal növesztése többfonalas tektonon
     */
    public static void test10() {

    }

    /*
     * 11: Teszt11 - Gombafonal növesztése olyan tektonra, ahol van spóra
     */
    public static void test11() {

    }

    /*
     * 12: Teszt12 - Gombafonal növesztése egyfonala tektonra (nincs rajta más játko fonala)
     */
    public static void test12() {

    }

    /*
     * 13: Teszt13 - Gombafonal felszívása
     */
    public static void test13() {

    }

    /*
     * 14: Teszt14 - Gomba növesztése, feltételek teljesülnek
     */
    public static void test14() {

    }

    /*
     * 15: Teszt15 - Gomba növesztse, nem teljesünek a fetételek (altesztesetek ?)
     */
    public static void test15() {

    }

    /*
     * 16: Teszt16 - Következő játékos jön
     */
    public static void test16() {

    }

    /*
     * 17: Teszt17 - Játékban eltelik egy kör
     */
    public static void test17() {

    }

    /*
     * 18: Teszt18 - Rovar fonalvágás akciója sikeres esetben
     */
    public static void test18() {

        game = initTest();

        Yarn y = new Yarn(mp.getOwnedMushrooms().get(0));

        //tectons.get(0).growYarn(y);


        // Print the starting state
        System.out.println(kezdoString);
        printState();
        System.out.println(kezdoString);

        // 3) The Entomologist instructs the Insect to cut the Yarn
        //    Since there is no paralysis or cut-prevention spore effect,
        //    the cut should be successful.
        el.actionCutYarn(y);

        // 4) Print the ending state
        System.out.println(vegString);
        printState();
        System.out.println(vegString);
    }

    /*
     * 19: Teszt19 - Rovar fonlvágás akciója vágágátló spóra hatása alatt
     */
    public static void test19() {

    }

    /*
     * 20: Teszt20 - Rovar spóraevés akciója sikeresen végrehajtva
     */
    public static void test20() {
        // 1) Initialize the game state
        game = initTest();


        // 2) Create an accelerator Spore on on Tecton(1)
        Spore spore = new AcceleratorSpore();
        tectons.get(1).addSpore(spore);


        // Print the starting state
        System.out.println(kezdoString);
        printState();
        System.out.println(kezdoString);

        //3) The Entomologist instructs the Insect to cut the Yarn
        //    Since there is no paralysis and there's spore on the tecton,
        //    the eating process should be successful.

        el.actionEatSpore(spore);

        // 4) Print the ending state
        System.out.println(vegString);
        printState();


    }

    /*
     * 21: Teszt21 - Rovar spóraevés akciója bénító spóra hatása alatt
     */
    public static void test21() {

    }

    /*
     * 22: Teszt22 - Rovar mozgatása gyorsító spóra hatása alatt
     */
    public static void test22() {

    }

    /*
     * 23: Teszt23 - Rovar mozgatása bénító spóra hatása alatt
     */
    public static void test23() {

    }

    /*
     * 24: Teszt24 - Rovar mozgatása spóra hatása nélkül sikeresen
     */
    public static void test24() {

    }

    /*
     * 25: Teszt25 - Rovar mozgatása két olyan tekton között amelyek között nem vezet gombafonal
     */
    public static void test25() {

    }

    /*
     * 26: Teszt26 - Rovar mozgatása lassító spóra hatása alatt
     */
    public static void test26() {

    }

}
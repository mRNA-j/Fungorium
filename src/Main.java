import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;



public class Main {
    private static Game game;
    private static MushroomPicker mp;
    private static Entomologist el;
    private static List<Tecton> tectons;
    private static String playerName1 = "Doma";
    private static String playerName2 = "Barni";
    private static String vegString = "\n+++++++++++++++++++++++\nVeghelyzet helyzet.\n+++++++++++++++++++++++";
    private static String kezdoString = "+++++++++++++++++++++++\nAlap helyzet.\n+++++++++++++++++++++++";

    public static void main(String[] args) {
        System.out.println("Szia tesztelo");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Add meg a valasztott teszt eset szamat (1-26): ");
        int chosenTestNumber = scanner.nextInt();

        if(chosenTestNumber < 1 || chosenTestNumber > 27) {
            System.out.println("Nincs ilyen teszteset! Kilépés!");
            return;
        }

        try {
            Class<?> clazz = Main.class;

            Object obj = clazz.getDeclaredConstructor().newInstance();

            String methodName = "test" + chosenTestNumber;
            Method method = clazz.getMethod(methodName);

            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        game.setActivePlayer(mp);
        tectons = game.getPlayField().getTectons();
        return game;
    }

    public static void printState() {
        System.out.println("Gombasz:");
        System.out.println("\t Pont: " + mp.getPoints());

        System.out.println("Rovarasz:");
        System.out.println("\t Pont: " + el.getPoints());
        if (!el.getInsect().getAccelerated() && !el.getInsect().getParalized() && !el.getInsect().getDecelerated() && !el.getInsect().getCutPrevented()) {
            System.out.println("\t Rovaron levo sporahatas: nincs rajta hatas");
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
            System.out.print("Szomszedok: [");
            for (Tecton neighbour : tecton.getNeighbours()) {
                neighbour.printfNameAndId();
            }
            System.out.println("]");
        }
    }

    /*
     * 1:  Teszt1 - Spóra kilövés olyan tektonra, amit elér a gomba
     * Tecton1-ről egy választható spóra átkerül a vele szomszédos Tecton-re
     * */
    public static void test1() {

        game = initTest();

        mp.getOwnedMushrooms().get(0).setHasSpore(true);
        System.out.println(kezdoString);
        printState();

        System.out.println("+++++++++++++++++++++++\nTest2 elkezdodott.\n+++++++++++++++++++++++");

        mp.actionSporeDispersion(tectons.get(1),mp.getOwnedMushrooms().get(0));

        System.out.println(vegString);
        printState();
    }

    /*
     * 2:  Teszt2 - Spóra kilövés, de a tekton nem szomszédos
     * A Tecton1-ről próbál meg a Tecton3-ra spórát lőni, de ezek nem szomszédosak, hibaüzenetet kapunk
     */
    public static void test2() {
        game = initTest();

        mp.getOwnedMushrooms().get(0).setHasSpore(true);
        System.out.println(kezdoString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest2 elkezdodott.\n+++++++++++++++++++++++\n");

        mp.actionSporeDispersion(tectons.get(2),mp.getOwnedMushrooms().get(0));

        System.out.println(kezdoString);
        printState();
    }

    /*
     * 3:  Teszt3 - Spóra kilövés, de nincs spóra termelve
     * A Tecton1-en lévő gombából nem lehet spórát kilőni mivel nem termelődött meg, így ezt jelzi a program hibaüzenetben
     */
    public static void test3() {
        game = initTest();
        System.out.println(kezdoString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest3 elkezdodott.\n+++++++++++++++++++++++\n");

        mp.actionSporeDispersion(tectons.get(1),mp.getOwnedMushrooms().get(0));

        System.out.println(vegString);
        printState();
    }

    /*
     * 4:  Teszt4 - Tekton kettétörése, ha van rajta gombafonal
     */
    public static void test4() {
        game = initTest();
        Yarn yarn = new Yarn(game.getPlayField().getTectons().get(0).getMushroom());
        game.getPlayField().getTectons().get(1).growYarn(yarn);
        game.getPlayField().getTectons().get(2).growYarn(yarn);
        System.out.println(kezdoString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest4 elkezdodott.\n+++++++++++++++++++++++\n");
        game.getPlayField().splitting(tectons.get(1));
        game.getPlayField().noConnection();
        System.out.println(vegString);
        printState();
    }

    /*
     * 5:  Teszt5 - Tekton kettétörése, rovar van a tektonon
     */
    public static void test5() {
        game = initTest();
        System.out.println(vegString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest5 elkezdodott.+++++++++++++++++++++++\n");

        game.getPlayField().splitting(tectons.get(1));
        System.out.println(vegString);
        printState();
    }

    /*
     * 6:  Teszt6 - Tekton ketttörése, gombatest van a tektonon
     */
    public static void test6() {
        game = initTest();
        System.out.println(kezdoString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest6 elkezdodott.\n+++++++++++++++++++++++\n");

        game.getPlayField().splitting(tectons.get(0));
        System.out.println(vegString);
        printState();
    }

    /*
     * 7:  Teszt7 - Tekton ketttörése, tektonon nincsen semmi
     */
    public static void test7() {
        game = initTest();
        System.out.println(kezdoString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest7 elkezdodott.+++++++++++++++++++++++\n");

        game.getPlayField().splitting(tectons.get(2));
        System.out.println(vegString);
        printState();
    }

    /*
     * 8:  Teszt8 - Gombafonal növesztése nem szomszédos tektonra
     */
    public static void test8() {
        game = initTest();

        Yarn yarn= new Yarn(mp.getOwnedMushrooms().get(0));
        //yarn.addTecton(tectons.get(0));
        //yarn.addTecton(tectons.get(1));
        System.out.println(kezdoString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest8 elkezdodott.\n+++++++++++++++++++++++\n");

        mp.actionGrowYarn(tectons.get(3),yarn);

        System.out.println(vegString);
        printState();

    }

    /*
     * 9:  Teszt9 - Gombafonal növesztése más játékos fonala van a tektonon ( csak egyfjta fonal növezthető a tektonra)
     */
    public static void test9() {
        game = initTest();

        Mushroom mushy=new Mushroom(tectons.get(2));
        MushroomPicker mptest=new MushroomPicker("Goldilocks",mushy);
        Yarn yarn= new Yarn(mptest.getOwnedMushrooms().get(0));
        mptest.actionGrowYarn(tectons.get(1),yarn);
        System.out.println(kezdoString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest9 elkezdodott.\n+++++++++++++++++++++++\n");

        mp.actionGrowYarn(tectons.get(1),yarn);

        System.out.println(kezdoString);
        printState();
    }

    /*
     * 10: Teszt10 - Gombafonal növesztése többfonalas tektonon
     */
    public static void test10() {
        game = initTest();

        Mushroom mushy=new Mushroom(tectons.get(2));
        MushroomPicker mptest = new MushroomPicker("Goldilocks",mushy);
        Yarn tyarn= new Yarn(mptest.getOwnedMushrooms().get(0));
        Yarn yarn=new Yarn(mp.getOwnedMushrooms().get(0));
        //tectons.get(1).growYarn(tyarn);
        mp.actionGrowYarn(tectons.get(1),tyarn);

        tectons.get(1).setYarnLimit(2);

        System.out.println(kezdoString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest10 elkezdodott.\n+++++++++++++++++++++++\n");

        mp.actionGrowYarn(tectons.get(1),yarn);

        System.out.println(vegString);
        printState();
    }

    /*
     * 11: Teszt11 - Gombafonal növesztése olyan tektonra, ahol van spóra
     */
    public static void test11() {
        game = initTest();
        Tecton t=tectons.get(1);

        Yarn yarn=new Yarn(mp.getOwnedMushrooms().get(0));
        Spore spore=new AcceleratorSpore(t);
        t.addSpore(spore);

        System.out.println(kezdoString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest11 elkezdodott.\n+++++++++++++++++++++++\n");

        //Ellenorzni hogy a bemeneti tektonon vane spora
        if(mp.sporeCheck(tectons.get(1))){
            mp.actionGrowYarn(t,yarn);
            mp.actionGrowYarn(tectons.get(2),yarn);
        }
        System.out.println(vegString);
        printState();
    }

    /*
     * 12: Teszt12 - Gombafonal növesztése egyfonala tektonra (nincs rajta más játko fonala)
     */
    public static void test12() {
        game = initTest();

        Yarn yarn=new Yarn(mp.getOwnedMushrooms().get(0));

        System.out.println(kezdoString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest12 elkezdodott.\n+++++++++++++++++++++++\n");

        mp.actionGrowYarn(tectons.get(1),yarn);

        System.out.println(vegString);
        printState();
    }

    /*
     * 13: Teszt13 - Gombafonal felszívása
     */
   public static void test13() {
        game = initTest();

        Yarn yarn=new Yarn(mp.getOwnedMushrooms().get(0));
        tectons.get(1).growYarn(yarn);
        tectons.get(2).growYarn(yarn);
        tectons.get(3).growYarn(yarn);
        tectons.get(4).growYarn(yarn);
        tectons.get(5).growYarn(yarn);



        System.out.println(kezdoString);
        printState();
        System.out.println("+++++++++++++++++++++++\nTest13 elkezdodott.\n+++++++++++++++++++++++\n");
        game.getPlayField().refresh();
        System.out.println(vegString);
        printState();
    }

    /*
     * 14: Teszt14 - Gomba növesztése, feltételek teljesülnek
     */
    public static void test14() {
        game =initTest();
        Yarn y = new Yarn(mp.getOwnedMushrooms().get(0));
        mp.addYarn(y);

        mp.actionGrowYarn(tectons.get(1), y);
        for (int i=0;i<5;i++) {
            tectons.get(1).addSpore(new AcceleratorSpore());
        }
        System.out.println(kezdoString);
        printState();

        System.out.println("+++++++++++++++++++++++\nTest14 elkezdodott.\n+++++++++++++++++++++++\n");

        mp.actionGrowMushroom(tectons.get(1));
        System.out.println(vegString);
        printState();
    }

    /*
     * 15: Teszt15 - Gomba növesztse, nem teljesünek a fetételek (altesztesetek ?)
     */
    public static void test15() {
        game = initTest();
        Yarn y = new Yarn(mp.getOwnedMushrooms().get(0));
        mp.addYarn(y);

        mp.actionGrowYarn(tectons.get(1), y);
        for (int i=0;i<5;i++) {
            tectons.get(1).addSpore(new AcceleratorSpore());
        }

        System.out.println(kezdoString);
        printState();

        System.out.println("+++++++++++++++++++++++\nTest15 elkezdodott.\n+++++++++++++++++++++++\n");

        mp.actionGrowMushroom(tectons.get(2));
        System.out.println(vegString);
        printState();
    }

    /*
     * 16: Teszt16 - Következő játékos jön
     */
    public static void test16() {
        game = initTest();

        System.out.println("+++++++++++++++++++++++\nTest16 elkezdodott.\n+++++++++++++++++++++++\n");
        System.out.println("Aktualis jatekos: " + game.getActivePlayer().getName());
        game.nextPlayer();
        System.out.println("Aktualis jatekos: " + game.getActivePlayer().getName());

    }

    /*
     * 17: Teszt17 - Játékban eltelik egy kör
     */
    public static void test17() {
        game = initTest();

        System.out.println("+++++++++++++++++++++++\nTest17 elkezdodott.\n+++++++++++++++++++++++\n");
        System.out.println("AKtualis kor sorszama: "+game.getCurrentTurn());
        game.nextTurn();
        System.out.println("Aktualis kor sorszama: "+game.getCurrentTurn());
    }

    /*
     * 18: Teszt18 - Rovar fonalvágás akciója sikeres esetben
     */
    public static void test18() {
        game = initTest();

        Yarn y = new Yarn(mp.getOwnedMushrooms().get(0));
        mp.actionGrowYarn(tectons.get(1),y);
        mp.actionGrowYarn(tectons.get(2),y);

        System.out.println(kezdoString);
        printState();

        System.out.println("+++++++++++++++++++++++\nTest18 elkezdodott.\n+++++++++++++++++++++++\n");
        el.actionCutYarn(y);
        game.getPlayField().noConnection();

        System.out.println(vegString);
        printState();
    }

    /*
     * 19: Teszt19 - Rovar fonlvágás akciója vágágátló spóra hatása alatt
     */
    public static void test19() {
        game = initTest();
        el.getInsect().setCutPrevented(true);

        Yarn y = new Yarn(mp.getOwnedMushrooms().get(0));

        mp.actionGrowYarn(tectons.get(1),y);
        mp.actionGrowYarn(tectons.get(2),y);

        System.out.println(kezdoString);
        printState();

        System.out.println("+++++++++++++++++++++++\nTest19 elkezdodott.\n+++++++++++++++++++++++\n");
        el.actionCutYarn(y);

        System.out.println(vegString);
        printState();
    }

    /*
     * 20: Teszt20 - Rovar spóraevés akciója sikeresen végrehajtva
     */
    public static void test20() {
        game = initTest();

        Spore spore = new AcceleratorSpore();
        tectons.get(1).addSpore(spore);

        System.out.println(kezdoString);
        printState();

        System.out.println("+++++++++++++++++++++++\nTest20 elkezdodott.\n+++++++++++++++++++++++\n");
        el.actionEatSpore(spore);

        System.out.println(vegString);
        printState();
    }

    /*
     * 21: Teszt21 - Rovar spóraevés akciója bénító spóra hatása alatt
     */
    public static void test21() {
        game = initTest();

        Spore spore = new AcceleratorSpore();
        tectons.get(1).addSpore(spore);
        el.getInsect().setParalized(true);

        System.out.println(kezdoString);
        printState();

        System.out.println("+++++++++++++++++++++++\nTest21 elkezdodott.\n+++++++++++++++++++++++\n");
        el.actionEatSpore(spore);

        System.out.println(vegString);
        printState();
    }

    /*
     * 22: Teszt22 - Rovar mozgatása gyorsító spóra hatása alatt
     */
    public static void test22() {
        game = initTest();

        Yarn yarn= new Yarn(mp.getOwnedMushrooms().get(0));

        mp.actionGrowYarn(tectons.get(1),yarn);
        mp.actionGrowYarn(tectons.get(2),yarn);
        mp.actionGrowYarn(tectons.get(3),yarn);

        // Print the starting state
        System.out.println(kezdoString);
        printState();

        System.out.println("+++++++++++++++++++++++\nTest22 elkezdodott.\n+++++++++++++++++++++++\n");
        el.actionMove(tectons.get(2));
        el.actionMove(tectons.get(3));

        System.out.println(vegString);
        printState();
    }

    /*
     * 23: Teszt23 - Rovar mozgatása bénító spóra hatása alatt
     */
    public static void test23() {
        game = initTest();

        el.getInsect().setParalized(true);

        Yarn yarn= new Yarn(mp.getOwnedMushrooms().get(0));
        mp.actionGrowYarn(tectons.get(1),yarn);
        mp.actionGrowYarn(tectons.get(2),yarn);

        System.out.println(kezdoString);
        printState();

        System.out.println("+++++++++++++++++++++++\nTest23 elkezdodott.\n+++++++++++++++++++++++\n");
        el.actionMove(tectons.get(2));

        System.out.println(vegString);
        printState();
    }

    /*
     * 24: Teszt24 - Rovar mozgatása spóra hatása nélkül sikeresen
     */
    public static void test24() {
        game = initTest();

        Yarn yarn= new Yarn(mp.getOwnedMushrooms().get(0));
        mp.actionGrowYarn(tectons.get(1),yarn);
        mp.actionGrowYarn(tectons.get(2),yarn);

        System.out.println(kezdoString);
        printState();

        System.out.println("+++++++++++++++++++++++\nTest24 elkezdodott.\n+++++++++++++++++++++++\n");
        el.actionMove(tectons.get(2));

        System.out.println(vegString);
        printState();
    }

    /*
     * 25: Teszt25 - Rovar mozgatása két olyan tekton között amelyek között nem vezet gombafonal
     */
    public static void test25() {
        game = initTest();

        System.out.println(kezdoString);
        printState();
        System.out.println(kezdoString);

        System.out.println("+++++++++++++++++++++++\nTest25 elkezdodott.\n+++++++++++++++++++++++\n");
        el.actionMove(tectons.get(2));

        System.out.println(vegString);
        printState();
    }

    /*
     * 26: Teszt26 - Rovar mozgatása lassító spóra hatása alatt
     */
    public static void test26() {
        game = initTest();

        el.getInsect().setDecelerated(true);

        Yarn yarn= new Yarn(mp.getOwnedMushrooms().get(0));
        mp.actionGrowYarn(tectons.get(1),yarn);
        mp.actionGrowYarn(tectons.get(2),yarn);

        System.out.println(kezdoString);
        printState();

        System.out.println("+++++++++++++++++++++++\nTest26 elkezdodott.\n+++++++++++++++++++++++\n");
        el.actionMove(tectons.get(2));

        System.out.println(vegString);
        printState();
    }

}
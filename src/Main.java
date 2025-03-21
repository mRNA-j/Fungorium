import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Game game;

    public static void main(String[] args) {
        System.out.println("Szia tesztelo");

        initTest();
    }

    public static void initTest() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Gombasz jatekos neve: ");
        String playerName1 = scanner.nextLine();
        System.out.println("Rovarasz jatekos neve: ");
        String playerName2 = scanner.nextLine();

        game = new Game();
        game.start();

        MushroomPicker mp = new MushroomPicker(playerName1, new Mushroom(game.getPlayField().getTectons().get(0)));
        Entomologist el = new Entomologist(playerName2, new Insect(game.getPlayField().getTectons().get(1)));

        List<Player> pl = new ArrayList<>();
        pl.add(mp);
        pl.add(el);
        game.setPlayers(pl);

        for (Tecton tecton : game.getPlayField().getTectons()) {
            System.out.println("Tecton" + tecton.getId());
        }
    }

    /*
    * 1:  Teszt1 - Spóra kilövés olyan tektonra, amit elér a gomba
    *
    * */
    public void teszt1() {

    }

    public void teszt2() {

    }

    public void teszt3() {

    }

    public void teszt4() {

    }

    public void teszt5() {

    }

    public void teszt6() {

    }

    public void teszt7() {

    }

    public void teszt8() {

    }

    public void teszt9() {

    }

    public void teszt10() {

    }




}
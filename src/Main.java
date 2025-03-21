import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Game game;

    public static void main(String[] args) {
        System.out.println("Szia tesztelo");
    }

    public void initTest() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Gombasz jatekos neve: ");
        String playerName1 = scanner.nextLine();
        System.out.println("Rovarasz jatekos neve: ");
        String playerName2 = scanner.nextLine();

        MushroomPicker mp = new MushroomPicker(playerName1, new Mushroom());
        Entomologist el = new Entomologist(playerName2, new Insect());

        List<Player> pl = new ArrayList<>();
        pl.add(mp);
        pl.add(el);
        game = new Game(pl);
        game.start();
    }

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
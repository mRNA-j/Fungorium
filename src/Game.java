import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private int currentTurn;
    private final int numberOfTurns;
    private int activePlayerIndex;
    private Player activePlayer;
    private List<Player> players;
    private final Map playField;

    public Game() {
        activePlayerIndex = 0;
        currentTurn = 0;
        playField = new Map();
        numberOfTurns = 50;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public int getCurrentTurn(){
        return currentTurn;
    }

    public Map getPlayField() {
        return playField;
    }

    public void setPlayers(List<Player> input) {
        players = input;
    }

    public void nextPlayer() {
        activePlayerIndex++;
        activePlayer = players.get(activePlayerIndex);
        System.out.println("Kovetkezo jatekos jon");
    }
    public void setActivePlayer(Player player){
        activePlayer = player;
    }
    public void start() {
        playField.generate();
    }
    public void nextTurn() {
        activePlayerIndex = 0;
        currentTurn++;
        if(currentTurn == numberOfTurns) {
            end();
        }
        System.out.println("Kovetkezo kor");
    }
    public void end() {
        System.out.println("Game ended");
    }
}
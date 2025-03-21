import java.util.List;
import java.util.Scanner;

public class Game {
    private int currentTurn;
    private final int numberOfTurns;
    private int activePlayerIndex;
    private Player activePlayer;
    private final List<Player> players;
    private final Map playField;

    public Game(List<Player> playersInput) {
        activePlayerIndex = 0;
        currentTurn = 0;
        playField = new Map();
        numberOfTurns = 50;
        players = playersInput;
    }

    public void nextPlayer() {
        activePlayerIndex++;
        //activePlayer = players[activePlayerIndex];
    }
    public void start() {
        //activePlayer = players[activePlayerIndex];
        playField.generate();
    }
    public void nextTurn() {
        activePlayerIndex = 0;
        currentTurn++;
        if(currentTurn == numberOfTurns) {
            end();
        }
    }
    public void end() {
        System.out.println("Game ended");
    }
}
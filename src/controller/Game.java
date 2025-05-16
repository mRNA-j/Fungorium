package controller;

import model.Entomologist;
import model.MushroomPicker;
import model.Player;
import model.Tecton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A játék alapvető működését kezelő osztály.
 * Tartalmazza a játék állapotát, a játékosokat és a játékteret.
 */
public class Game implements Serializable {
    /** Az aktuális kör száma */
    private int currentTurn;

    /** A játék maximális köreinek száma */
    private final int numberOfTurns;

    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }

    /** Az aktív játékos indexe a játékosok listájában */
    private int activePlayerIndex;

    /** Az éppen soron következő aktív játékos */
    private Player activePlayer;

    /** A játékban résztvevő játékosok listája */
    private List<Player> players;

    /** A játéktér, amin a játék zajlik */
    private final Map playField;

    private ArrayList<MushroomPicker> mps;

    public ArrayList<Entomologist> getEnts() {
        return ents;
    }

    public void setEnts(ArrayList<Entomologist> ents) {
        this.ents = ents;
    }

    private ArrayList<Entomologist> ents;

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
        if (currentTurn == 4) this.currentTurn = 0;
    }

    /**
     * Alapértelmezett konstruktor a játék inicializálásához.
     * Beállítja a kezdő értékeket és létrehozza a játékteret.
     */
    public Game() {
        activePlayerIndex = 0;
        currentTurn = 1;
        playField = new Map();
        numberOfTurns = 50;
        players = new ArrayList<>();
        mps = new ArrayList<>();
        ents = new ArrayList<>();
    }

    public void setMps(ArrayList<MushroomPicker> input) {
        mps = input;
    }

    public ArrayList<MushroomPicker> getMps() {
        return mps;
    }

    public Game(List<Player> inputPlayers) {
        activePlayerIndex = 0;
        currentTurn = 1;
        playField = new Map();
        numberOfTurns = 50;
        players = inputPlayers;
        mps = new ArrayList<>();
        ents = new ArrayList<>();
    }

    public List<Player> getPlayers(){return players;}

    public int getRound() { return currentTurn; }

    /**
     * Visszaadja az aktuális, aktív játékost.
     * @return Az aktív játékos.
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * Visszaadja az aktuális kör számát.
     * @return Az aktuális kör száma.
     */
    public int getCurrentTurn(){
        return currentTurn;
    }

    /**
     * Visszaadja a játékteret.
     * @return A játék térképe.
     */
    public Map getPlayField() {
        return playField;
    }

    /**
     * Beállítja a játékosok listáját.
     * @param input A játékosok listája.
     */
    public void setPlayers(List<Player> input) {
        players = input;
    }

    /**
     * A következő játékosra váltás.
     * Növeli az aktív játékos indexét és beállítja az új aktív játékost.
     */
    public void nextPlayer() {
        activePlayerIndex++;
        activePlayer = players.get(activePlayerIndex);
    }

    /**
     * Beállítja a megadott játékost aktív játékosnak.
     * @param player A beállítandó aktív játékos.
     */
    public void setActivePlayer(Player player){
        activePlayer = player;
    }

    /**
     * Elindítja a játékot.
     * Legenerálja a játékteret.
     */
    public void start() {
        playField.generate(mps, ents);
    }

    /**
     * A következő körre lépést kezeli.
     * Visszaállítja az aktív játékos indexét és növeli a kör számát.
     * Ha elértük a maximális körszámot, akkor befejezi a játékot.
     */
    public void nextTurn() {
        getPlayField().refresh();
        activePlayerIndex = 0;
        activePlayer = players.get(activePlayerIndex);
        currentTurn++;
        if(currentTurn == numberOfTurns) {
            end();
        }


    }

    /**
     * Befejezi a játékot.
     * Kiírja, hogy a játék véget ért.
     */
    public void end() {
        System.out.println("controller.Game ended");
    }
}
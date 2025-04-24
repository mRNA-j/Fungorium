package model;
import view.PlayerView;
import view.View;

import java.io.Serializable;

/**
 * Absztrakt osztály a játékosok reprezentálására.
 * Kezeli a játékos nevét és pontszámát.
 * Az osztályból származtatott konkrét játékos osztályokat kell létrehozni.
 */
public abstract class Player implements Serializable {
    private PlayerView playerView;

    /** A játékos neve */
    private String name;

    /** A játékos által összegyűjtött pontok */
    private int points;
    private String testID;
    /**
     * Létrehoz egy új játékos objektumot a megadott névvel és kezdeti pontszámmal.
     * @param name A játékos neve.
     * @param points A játékos kezdeti pontszáma.
     */
    public Player(String name, int points) {
        this.name = name;
        this.points = points;
        this.testID = null;
        this.playerView = null;
    }

    public Player(String name, int points, String testID) {
        this.name = name;
        this.points = points;
        this.testID = testID;
        this.playerView = null;
    }
    public void setPlayerView(PlayerView view) {
        this.playerView = view;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    /**
     * Beállítja a játékos nevét.
     * @param name A játékos új neve.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Visszaadja a játékos nevét.
     * @return A játékos neve.
     */
    public String getName(){
        return name;
    }

    /**
     * Visszaadja a játékos aktuális pontszámát.
     * @return A játékos pontszáma.
     */
    public int getPoints(){
        return points;
    }

    /**
     * Pontokat ad a játékos aktuális pontszámához.
     * Kiírja a konzolra a hozzáadott pontok számát.
     * @param numOfPoints A hozzáadandó pontok száma.
     */
    public void addPoints(int numOfPoints) {
        points += numOfPoints;
    }
    public String getId(){
        return testID;
    }
}
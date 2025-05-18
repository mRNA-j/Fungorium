import view.MainFrame;

/**
 * A Main osztály a program belépési pontja.
 * Ez az osztály felelős a játék elindításáért és a grafikus felhasználói felület megjelenítéséért.
 */
public class Main {

    /**
     * A program fő metódusa, amely elindítja a játékot.
     * Létrehozza a fő ablakot és megjeleníti azt.
     * 
     * @param args A parancssori argumentumok tömbje
     */
    public static void main(String[] args) {
        MainFrame mainFrame=new MainFrame();
        mainFrame.setVisible(true);
    }
}

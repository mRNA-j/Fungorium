package view;

/**
 * A PanelSwitcher interfész definiálja a panelek közötti váltáshoz szükséges metódust.
 * Ezt az interfészt implementáló osztályok képesek különböző panelek között váltani
 * a felhasználói felületen.
 */
public interface PanelSwitcher {
    /**
     * Megjeleníti a megadott nevű panelt.
     * 
     * @param panelName A megjelenítendő panel neve
     */
    void showPanel(String panelName);
}

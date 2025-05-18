package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * A MainFrame osztály a játék fő ablakát reprezentálja.
 * Ez az osztály felelős a különböző panelek közötti váltásért és a játék
 * felhasználói felületének megjelenítéséért.
 * Implementálja a PanelSwitcher interfészt, amely lehetővé teszi a panelek közötti váltást.
 */
public class MainFrame extends JFrame implements PanelSwitcher {
    CardLayout cardLayout = new CardLayout();
    JPanel cardPanel = new JPanel(cardLayout);
    StartPanel startPanel = new StartPanel();
    StartGamePanel startGamePanel = new StartGamePanel();

    MushroomPickerG mpPanel1;
    MushroomPickerG mpPanel2;
    EntomologistG ePanel1;
    EntomologistG ePanel2;

    Controller controller;

    /**
     * A MainFrame konstruktora.
     * Inicializálja az ablakot, beállítja a címet, a bezárási műveletet,
     * a méreteket és létrehozza a kezdeti paneleket.
     */
    public MainFrame() {
        setTitle("Pentagon 98 Fungorium");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));

        startPanel.setPanelSwitcher(this);
        startGamePanel.setPanelSwitcher(this);
        startGamePanel.setMainFrame(this);

        cardPanel.add(startPanel, "startPanel");
        cardPanel.add(startGamePanel, "startGamePanel");
        add(cardPanel);
        cardLayout.show(cardPanel,"startPanel");
        setVisible(true);
    }

    /**
     * Megjeleníti a megadott nevű panelt.
     * Implementálja a PanelSwitcher interfész showPanel metódusát.
     * 
     * @param panelName A megjelenítendő panel neve
     */
    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
        SwingUtilities.invokeLater(() -> {
            revalidate();
            repaint();
        });
    }

    /**
     * Beállítja a játék vezérlőjét.
     * 
     * @param controller A játék vezérlője
     */
    public void setGame(Controller controller) {
        this.controller = controller;
    }

    /**
     * Létrehozza és inicializálja a játékosok paneljeit.
     * Beállítja a panel váltókat és hozzáadja a paneleket a kártyapanelhez.
     */
    public void setUpPlayers(){
        // Create all panels first before calling methods on them
        ePanel1 = new EntomologistG(controller.getGame().getEnts().get(0),"mp2Panel", controller);
        ePanel2 = new EntomologistG(controller.getGame().getEnts().get(1),"mp1Panel", controller);
        mpPanel1 = new MushroomPickerG(controller.getGame().getMps().get(0),"ent1Panel", controller);
        mpPanel2 = new MushroomPickerG(controller.getGame().getMps().get(1),"ent2Panel", controller);

        // Now set panel switchers after all panels are created
        ePanel1.setPanelSwitcher(this);
        ePanel2.setPanelSwitcher(this);
        mpPanel1.setPanelSwitcher(this);
        mpPanel2.setPanelSwitcher(this);

        // Add all panels to the card panel
        cardPanel.add(ePanel1, "ent1Panel");
        cardPanel.add(ePanel2, "ent2Panel");
        cardPanel.add(mpPanel1, "mp1Panel");
        cardPanel.add(mpPanel2, "mp2Panel");
    }
}

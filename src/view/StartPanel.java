package view;

import javax.swing.*;
import java.awt.*;

/**
 * A StartPanel osztály a játék kezdőképernyőjét reprezentálja.
 * Tartalmazza a játék címét, valamint a játék indításához és bezárásához szükséges gombokat.
 */
public class StartPanel extends JPanel {
    /** Gomb a játék indításához */
    JButton startButton = new JButton("START");
    
    /** Gomb a játék bezárásához */
    JButton closeButton = new JButton("CLOSE");
    
    /** Címke a játék címének megjelenítéséhez */
    JLabel titleLabel = new JLabel("Fungórium - Pentagon - 98", SwingConstants.CENTER);
    
    /** A panel váltást kezelő objektum */
    private PanelSwitcher panelSwitcher;

    /**
     * Beállítja a panel váltást kezelő objektumot.
     * 
     * @param panelSwitcher A panel váltást kezelő objektum
     */
    public void setPanelSwitcher(PanelSwitcher panelSwitcher) {
        this.panelSwitcher = panelSwitcher;
    }

    /**
     * A StartPanel konstruktora.
     * Inicializálja a panelt, létrehozza a címkét és a gombokat,
     * valamint beállítja az eseménykezelőket.
     */
    public StartPanel() {
        this.setLayout(new BorderLayout());

        // Title label
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        this.add(titleLabel, BorderLayout.NORTH);

        // Start button centered
        startButton.setPreferredSize(new Dimension(160, 80));
        JPanel centerPanel = new JPanel();
        centerPanel.add(startButton);
        startButton.setVerticalAlignment(SwingConstants.CENTER);
        startButton.setHorizontalAlignment(SwingConstants.CENTER);

        startButton.addActionListener(e -> {
            if (panelSwitcher != null) {
                panelSwitcher.showPanel("startGamePanel"); // Or logic to decide which comes next
            }
        });

        this.add(centerPanel, BorderLayout.CENTER);

        // Close button at bottom-left
        closeButton.setPreferredSize(new Dimension(100, 40));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel closeButtonWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        closeButtonWrapper.add(closeButton);
        bottomPanel.add(closeButtonWrapper, BorderLayout.WEST);

        this.add(bottomPanel, BorderLayout.SOUTH);



        closeButton.addActionListener(e -> System.exit(0));
    }


    /**
     * Visszaadja a játék indításához használt gombot.
     * 
     * @return A játék indításához használt gomb
     */
    public JButton getStartButton() {
        return startButton;
    }
}

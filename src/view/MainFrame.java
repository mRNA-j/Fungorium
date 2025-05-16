package view;

import controller.Controller;
import controller.Game;
import model.MushroomPicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
        revalidate();
        repaint();
    }

    public void setGame(Controller controller) {
        this.controller = controller;

    }

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
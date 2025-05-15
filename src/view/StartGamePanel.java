package view;

import controller.Controller;
import controller.Game;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StartGamePanel extends JPanel {
    JButton startButton = new JButton("Start");
    JButton backButton = new JButton("Back");
    JTextField[] inputs = new JTextField[4];
    JLabel[] labels = new JLabel[4];
    static String[] labelNames = {"MushroomPicker1", "MushroomPicker2", "Entomologist1", "Entomologist2"};
    MushroomPicker mp1;
    MushroomPicker mp2;
    Entomologist e1;
    Entomologist e2;

    private PanelSwitcher panelSwitcher;

    public void setPanelSwitcher(PanelSwitcher panelSwitcher) {
        this.panelSwitcher = panelSwitcher;
    }

    private MainFrame mainFrame;

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    public StartGamePanel() {
        this.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 8, 8));


        for (int i = 0; i < 4; i++) {
            inputs[i] = new JTextField();
            inputs[i].setPreferredSize(new Dimension(200, 30));

            labels[i] = new JLabel(labelNames[i]);
            labels[i].setHorizontalAlignment(JLabel.LEFT);

            inputPanel.add(labels[i]);
            inputPanel.add(inputs[i]);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        startButton.setPreferredSize(new Dimension(120, 40));
        backButton.setPreferredSize(new Dimension(120, 40));

        buttonPanel.add(startButton);
        buttonPanel.add(backButton);

        contentPanel.add(inputPanel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(buttonPanel);

        startButton.addActionListener(e -> {
            if (panelSwitcher != null&&mainFrame != null) {

                    //Game game = new Game(createPlayers());
                    Controller controller = new Controller(createPlayers());
                    controller.getGame().setMps(createMps());
                    controller.getGame().setEnts(createEnts());
                    controller.getGame().start();
                    mainFrame.setGame(controller);
                    mainFrame.setUpPlayers();
                    mainFrame.showPanel("mp1Panel");


            }
        });
        backButton.addActionListener(e -> {
            if (panelSwitcher != null) {
                panelSwitcher.showPanel("startPanel"); // Or logic to decide which comes next
            }
        });

        this.add(contentPanel, BorderLayout.CENTER);
    }



    public ArrayList<Player> createPlayers() {
        //mp 1-2
        mp1 = new MushroomPicker(inputs[0].getText(), "sdbfsdjfbifbdsip");
        mp2 = new MushroomPicker(inputs[1].getText(), "dnudnbcicncnsdocncpisnmc");
        e1 = new Entomologist(inputs[2].getText(), "aichseocnw");
        e2 = new Entomologist(inputs[3].getText(), "jfnsdijvnewpivenveo");

        ArrayList<Player> players = new ArrayList<>();
        players.add(mp1);
        players.add(mp2);
        players.add(e1);
        players.add(e2);

        return players;
    }

    public ArrayList<MushroomPicker> createMps() {
        ArrayList<MushroomPicker> mps = new ArrayList<>();
        Mushroom mushroom = new Mushroom(new Tecton(2,false,false),mp1);
        mp1.addMushroom(mushroom);
        mps.add(mp1);
        mps.add(mp2);
        return mps;
    }

    public ArrayList<Entomologist> createEnts() {
        ArrayList<Entomologist> mps = new ArrayList<>();
        mps.add(e1);
        mps.add(e2);
        return mps;
    }
}

package view;

import controller.Controller;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A StartGamePanel osztály a játék indítását kezelő panelt reprezentálja.
 * Lehetővé teszi a játékosok neveinek megadását és a játék elindítását.
 */
public class StartGamePanel extends JPanel {
    /** Gomb a játék indításához */
    JButton startButton = new JButton("Start");
    
    /** Gomb a visszalépéshez */
    JButton backButton = new JButton("Back");
    
    /** Szövegmezők a játékosok neveinek megadásához */
    JTextField[] inputs = new JTextField[4];
    
    /** Címkék a szövegmezőkhöz */
    JLabel[] labels = new JLabel[4];
    
    /** A játékosok típusainak nevei */
    static String[] labelNames = {"MushroomPicker1", "MushroomPicker2", "Entomologist1", "Entomologist2"};
    
    /** Az első gombaszedő játékos */
    MushroomPicker mp1;
    
    /** A második gombaszedő játékos */
    MushroomPicker mp2;
    
    /** Az első rovarász játékos */
    Entomologist e1;
    
    /** A második rovarász játékos */
    Entomologist e2;

    /** A panel váltást kezelő objektum */
    private PanelSwitcher panelSwitcher;

    /** A fő ablak referenciája */
    private MainFrame mainFrame;

    /**
     * Beállítja a panel váltást kezelő objektumot.
     * 
     * @param panelSwitcher A panel váltást kezelő objektum
     */
    public void setPanelSwitcher(PanelSwitcher panelSwitcher) {
        this.panelSwitcher = panelSwitcher;
    }

    /**
     * Beállítja a fő ablak referenciáját.
     * 
     * @param mainFrame A fő ablak referenciája
     */
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
                    controller.getGame().setActivePlayer(controller.getGame().getPlayers().get(0));
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



    /**
     * Létrehozza a játékosokat a megadott nevek alapján.
     * 
     * @return A létrehozott játékosok listája
     */
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

    /**
     * Létrehozza a gombaszedő játékosok listáját.
     * 
     * @return A létrehozott gombaszedő játékosok listája
     */
    public ArrayList<MushroomPicker> createMps() {
        ArrayList<MushroomPicker> mps = new ArrayList<>();
        //Mushroom mushroom = new Mushroom(new Tecton(2,false,false),mp1);
        //mp1.addMushroom(mushroom);
        mps.add(mp1);
        mps.add(mp2);
        return mps;
    }

    /**
     * Létrehozza a rovarász játékosok listáját.
     * 
     * @return A létrehozott rovarász játékosok listája
     */
    public ArrayList<Entomologist> createEnts() {
        ArrayList<Entomologist> mps = new ArrayList<>();
        mps.add(e1);
        mps.add(e2);
        return mps;
    }
}

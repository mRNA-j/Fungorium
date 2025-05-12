package view;

import controller.Game;
import model.MushroomPicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame implements ActionListener {
    CardLayout cardLayout = new CardLayout();
    JPanel cardPanel = new JPanel(cardLayout);
    StartPanel startPanel = new StartPanel();
    StartGamePanel startGamePanel = new StartGamePanel();
    MushroomPickerG mpPanel1 = new MushroomPickerG();
    MushroomPickerG mpPanel2;
    EntomologistG ePanel1 = new EntomologistG();
    EntomologistG ePanel2;
    Game game;

    public MainFrame(){
        setTitle("Pentagon 98 Fungorium");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(800,600));

        startPanel.getStartButton().addActionListener(this);
        startGamePanel.getBackButton().addActionListener(this);
        startGamePanel.getStartButton().addActionListener(this);

        cardPanel.add(startPanel, "startPanel");
        cardPanel.add(startGamePanel, "startGamePanel");

        add(cardPanel);

        cardLayout.show(cardPanel,"startPanel");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==startPanel.getStartButton()){
            cardLayout.show(cardPanel,"startGamePanel");
            revalidate();  // Forces layout to update
            repaint();
        }
        if(e.getSource()==startGamePanel.getBackButton()){
            cardLayout.show(cardPanel,"startPanel");
            revalidate();  // Forces layout to update
            repaint();
        }

        //Ez inditja el a jatekot
        if(e.getSource() == startGamePanel.getStartButton()) {
            game = new Game(startGamePanel.createPlayers());
            game.setMps(startGamePanel.createMps());
            game.setEnts(startGamePanel.createEnts());


            mpPanel1 = new MushroomPickerG(game.getMps().get(0));
            cardPanel.add(mpPanel1, "mp1Panel");
            //cardLayout.show(cardPanel, "mp1Panel");
            System.out.println(game.getMps().get(0).getOwnedMushrooms().get(0).getTecton());

            mpPanel2 = new MushroomPickerG(game.getMps().get(0));
            cardPanel.add(mpPanel2, "mp2Panel");


            ePanel1 = new EntomologistG(game.getEnts().get(0));
            cardPanel.add(ePanel1, "entPanel1");

            ePanel2 = new EntomologistG(game.getEnts().get(1));
            cardPanel.add(ePanel2, "entPanel2");
            cardLayout.show(cardPanel, "mp1Panel");

        }
    }
}

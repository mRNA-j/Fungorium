package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    CardLayout cardLayout = new CardLayout();
    JPanel cardPanel = new JPanel(cardLayout);
    StartPanel startPanel = new StartPanel();
    StartGamePanel startGamePanel = new StartGamePanel();

    public MainFrame(){
        setTitle("Pentagon 98 Fungorium");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(800,600));

        startPanel.getStartButton().addActionListener(this);
        startGamePanel.getBackButton().addActionListener(this);


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
    }
}

package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    JButton startButton = new JButton("Start");
    JButton closeButton = new JButton("Close");
    StartPanel() {
        this.add(startButton);
        this.add(closeButton);
        //TODO
        closeButton.addActionListener(e -> {System.exit(0);});
    }

    public JButton getStartButton() {return startButton;}

}

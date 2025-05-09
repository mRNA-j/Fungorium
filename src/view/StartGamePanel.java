package view;

import javax.swing.*;
import java.awt.*;

public class StartGamePanel extends JPanel {
    JButton startButton = new JButton("Start");
    JButton backButton = new JButton("Back");
    JTextField[] inputs = new JTextField[4];
    JLabel[] labels = new JLabel[4];
    static String[] labelNames = {"MushroomPicker1", "MushroomPicker2", "Entomologist1", "Entomologist2"};

    public StartGamePanel() {
        this.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        for (int i = 0; i < 4; i++) {
            inputs[i] = new JTextField();
            inputs[i].setHorizontalAlignment(JTextField.RIGHT);
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

        this.add(contentPanel, BorderLayout.CENTER);
    }

    public JButton getBackButton() {
        return backButton;
    }
}

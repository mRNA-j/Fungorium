package view;

import controller.UpdateListener;
import model.Tecton;

import javax.swing.*;
import java.awt.*;

public class TectonG extends JComponent {

    private int height;
    private int width;
    private Tecton tecton;

    public TectonG(Tecton tecton) {
        height =10;
        width = 10;
        this.tecton = tecton;
        // Set red background
        this.setBackground(Color.RED);

        // Set fixed size (square)
        this.setPreferredSize(new Dimension(100, 100));

        // Centered label
        JLabel idLabel = new JLabel(tecton.getId());
        idLabel.setForeground(Color.WHITE); // Text color
        idLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Use BorderLayout to center the label
        this.setLayout(new BorderLayout());
        this.add(idLabel, BorderLayout.CENTER);
    }
}

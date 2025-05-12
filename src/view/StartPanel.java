package view;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    JButton startButton = new JButton("START");
    JButton closeButton = new JButton("CLOSE");
    JLabel titleLabel = new JLabel("Fungórium - Pentagon - 98", SwingConstants.CENTER);
    private PanelSwitcher panelSwitcher;

    public void setPanelSwitcher(PanelSwitcher panelSwitcher) {
        this.panelSwitcher = panelSwitcher;
    }

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


    public JButton getStartButton() {
        return startButton;
    }
}

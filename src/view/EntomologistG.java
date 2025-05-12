package view;

import controller.UpdateListener;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class EntomologistG extends JPanel implements UpdateListener {
    public Entomologist getEntomologist() {
        return entomologist;
    }

    public void setEntomologist(Entomologist entomologist) {
        this.entomologist = entomologist;
    }

    private Entomologist entomologist;
    private JLabel nameLabel = new JLabel();

    private final JButton eatButton = new JButton("eat Spore");
    private final JButton moveButton = new JButton("move Insect");
    private final JButton cutButton = new JButton("cut Yarn");
    private final JButton skipButton = new JButton("Skip");

    private TectonPanel upperStrip;
    private TectonPanel lowerStrip;
    private JScrollPane upperScroll;
    private JScrollPane lowerScroll;
    public final List<TectonG> upperTectons = new ArrayList<>();
    public final List<TectonG> lowerTectons = new ArrayList<>();

    public EntomologistG(Entomologist e) {
        this.entomologist = e;
        nameLabel.setText(entomologist.getName() + " - " + entomologist.getPoints());

        // Title label
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        this.add(nameLabel, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(Box.createHorizontalGlue(), BorderLayout.CENTER); // Push to right
        topPanel.add(nameLabel, BorderLayout.EAST);
        topPanel.setOpaque(false); // Optional: make it transparent
        this.add(topPanel, BorderLayout.NORTH);

        setLayout(new BorderLayout());

        // Prepare upper tectons from insects
        for (int i = 0; i < e.getInsect().size(); i++) {
            Insect insect = e.getInsect().get(i);
            String id = insect.getId();
            upperTectons.add(new TectonG(i * 80 + 10, 40, 30, id, insect.getCurrentPlace()));
        }

        // Initialize TectonPanels
        upperStrip = new TectonPanel(upperTectons, true, this::updateLowerStrip);
        lowerStrip = new TectonPanel(lowerTectons, false, null);

        upperScroll = new JScrollPane(upperStrip);
        lowerScroll = new JScrollPane(lowerStrip);
        upperScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        upperScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        lowerScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        lowerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonPanel.add(eatButton);
        buttonPanel.add(moveButton);
        buttonPanel.add(cutButton);
        buttonPanel.add(skipButton);

        // Center panel for tecton views
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(upperScroll, BorderLayout.NORTH);
        centerPanel.add(lowerScroll, BorderLayout.CENTER);

        // Add to main layout
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    public EntomologistG() {

    }

    private void updateLowerStrip(Tecton selectedTecton) {
        lowerTectons.clear();
        List<Tecton> neighbors = selectedTecton.getNeighbours();

        for (int i = 0; i < neighbors.size(); i++) {
            Tecton neighbor = neighbors.get(i);
            lowerTectons.add(new TectonG(i * 80 + 10, 40, 30, neighbor.getId(), neighbor));
        }

        lowerStrip.repaint();
    }

    @Override
    public void update() {
        repaint();
    }



    }



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
    private final JButton nextPlayerButton = new JButton("NEXT Player");

    private JComboBox<String> MI_insectSelect;
    private JComboBox<String> MI_tgtTectonSelect;
    private JComboBox<String> MI_yarnSelect;
    private JComboBox<String> ES_insectSelect;
    private JComboBox<String> ES_sporeSelect;
    private JComboBox<String> CY_insectSelect;
    private JComboBox<String> CY_yarnSelect;
    private JComboBox<String> CY_tgtTectonSelect;

    private TectonPanel upperStrip;
    private TectonPanel lowerStrip;
    private JScrollPane upperScroll;
    private JScrollPane lowerScroll;
    public final List<TectonG> upperTectons = new ArrayList<>();
    public final List<TectonG> lowerTectons = new ArrayList<>();
    private String nextPanelName;

    private PanelSwitcher panelSwitcher;

    public void setPanelSwitcher(PanelSwitcher panelSwitcher) {
        this.panelSwitcher = panelSwitcher;
    }

    public EntomologistG(Entomologist ento, String panelName) {
        this.entomologist = ento;
        nameLabel.setText(entomologist.getName() + " - " + entomologist.getPoints());
        nextPanelName=panelName;
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
        for (int i = 0; i < ento.getInsect().size(); i++) {
            Insect insect = ento.getInsect().get(i);
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
        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new GridLayout(7, 1, 5, 5));


        MI_insectSelect = new JComboBox<>(new String[] {"Insect 1", "Insect 2"});
        MI_tgtTectonSelect = new JComboBox<>(new String[] {"Target Tecton A", "Target Tecton B"});
        MI_yarnSelect = new JComboBox<>(new String[] {"Yarn 1", "Yarn 2"});
        ES_insectSelect = new JComboBox<>(new String[] {"Insect 1", "Insect 2"});
        ES_sporeSelect = new JComboBox<>(new String[] {"Spore 1", "Spore 2"});
        CY_insectSelect = new JComboBox<>(new String[] {"Insect 1", "Insect 2"});
        CY_yarnSelect = new JComboBox<>(new String[] {"Yarn 1", "Yarn 2"});
        CY_tgtTectonSelect = new JComboBox<>(new String[] {"Target Tecton A", "Target Tecton B"});

// Set all invisible initially
        MI_insectSelect.setVisible(false);
        MI_tgtTectonSelect.setVisible(false);
        MI_yarnSelect.setVisible(false);
        ES_insectSelect.setVisible(false);
        ES_sporeSelect.setVisible(false);
        CY_insectSelect.setVisible(false);
        CY_yarnSelect.setVisible(false);
        CY_tgtTectonSelect.setVisible(false);

// === Add them to your comboPanel ===
        comboPanel.add(MI_insectSelect);
        comboPanel.add(MI_tgtTectonSelect);
        comboPanel.add(MI_yarnSelect);
        comboPanel.add(ES_insectSelect);
        comboPanel.add(ES_sporeSelect);
        comboPanel.add(CY_insectSelect);
        comboPanel.add(CY_yarnSelect);
        comboPanel.add(CY_tgtTectonSelect);



// === Add the action listeners ===

// Move Insect
        moveButton.addActionListener(e -> {
            MI_insectSelect.setVisible(true);
            MI_insectSelect.setEnabled(true);
            MI_yarnSelect.setVisible(true);
            MI_yarnSelect.setEnabled(false);
            MI_tgtTectonSelect.setVisible(true);
            MI_tgtTectonSelect.setVisible(false);
            disableOtherButtons(moveButton);
        });

        MI_insectSelect.addActionListener(e -> {
            MI_insectSelect.setEnabled(false);
            MI_yarnSelect.setEnabled(true);
        });

        MI_yarnSelect.addActionListener(e -> {
            MI_yarnSelect.setEnabled(false);
            MI_tgtTectonSelect.setEnabled(true);


        });

        MI_tgtTectonSelect.addActionListener(e -> {
            Object insectSelected = MI_insectSelect.getSelectedItem();
            Object yarnSelected = MI_yarnSelect.getSelectedItem();
            Object tectonSelected = MI_tgtTectonSelect.getSelectedItem();
            // Call move insect action
            // mushroomPicker.actionMoveInsect(...);
        });

// Eat Spore
        eatButton.addActionListener(e -> {
            ES_insectSelect.setVisible(true);
            ES_insectSelect.setEnabled(true);
            ES_sporeSelect.setVisible(true);
            ES_sporeSelect.setEnabled(false);
            disableOtherButtons(eatButton);
        });

        ES_insectSelect.addActionListener(e -> {
            ES_insectSelect.setEnabled(false);
            ES_sporeSelect.setEnabled(true);
        });

        ES_sporeSelect.addActionListener(e -> {
            Object insectSelected = ES_insectSelect.getSelectedItem();
            Object sporeSelected = ES_sporeSelect.getSelectedItem();
            // Call eat spore action
            // mushroomPicker.actionEatSpore(...);
        });

// Cut Yarn
        cutButton.addActionListener(e -> {
            CY_insectSelect.setVisible(true);
            CY_insectSelect.setEnabled(true);
            CY_yarnSelect.setVisible(true);
            CY_yarnSelect.setEnabled(false);
            CY_tgtTectonSelect.setVisible(true); // Initially hidden
            CY_tgtTectonSelect.setEnabled(false);
            disableOtherButtons(cutButton);
        });

        CY_insectSelect.addActionListener(e -> {
            CY_insectSelect.setEnabled(false);
            CY_yarnSelect.setEnabled(true);
        });

        CY_yarnSelect.addActionListener(e -> {
            CY_yarnSelect.setEnabled(false);
            CY_tgtTectonSelect.setEnabled(true);
            // Populate target tectons based on yarn selection if needed
        });

        CY_tgtTectonSelect.addActionListener(e -> {
            Object insectSelected = CY_insectSelect.getSelectedItem();
            Object yarnSelected = CY_yarnSelect.getSelectedItem();
            Object tectonSelected = CY_tgtTectonSelect.getSelectedItem();
            // Call cut yarn action
            // mushroomPicker.actionCutYarn(...);
        });

// Update skip button to handle new combo boxes
        skipButton.addActionListener(e -> {
            eatButton.setEnabled(false);
            moveButton.setEnabled(false);
            cutButton.setEnabled(false);
        });

// Update nextPlayerButton to handle new combo boxes
        nextPlayerButton.addActionListener(e -> {
            if (panelSwitcher != null) {
                setAllComboBoxesVisible(false);
                enableAllButtons();
                panelSwitcher.showPanel(nextPanelName);
            }
        });

// === Add the new buttons to your button panel ===
        buttonPanel.add(moveButton);
        buttonPanel.add(eatButton);
        buttonPanel.add(cutButton);

        buttonPanel.add(skipButton);
        buttonPanel.add(nextPlayerButton);
        // Center panel for tecton views
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(upperScroll, BorderLayout.NORTH);
        centerPanel.add(lowerScroll, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(buttonPanel, BorderLayout.NORTH);
        rightPanel.add(comboPanel, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.EAST);

        // Add to main layout
        add(centerPanel, BorderLayout.CENTER);
    }

    // === Helper methods ===
    private void disableOtherButtons(JButton activeButton) {
        moveButton.setEnabled(false);
        eatButton.setEnabled(false);
        cutButton.setEnabled(false);
        skipButton.setEnabled(false);
        activeButton.setEnabled(false);
    }

    private void enableAllButtons() {
        moveButton.setEnabled(true);
        eatButton.setEnabled(true);
        cutButton.setEnabled(true);
        skipButton.setEnabled(true);
    }

    private void setAllComboBoxesVisible(boolean visible) {


        // New combo boxes
        MI_insectSelect.setVisible(visible);
        MI_tgtTectonSelect.setVisible(visible);
        MI_yarnSelect.setVisible(visible);
        ES_insectSelect.setVisible(visible);
        ES_sporeSelect.setVisible(visible);
        CY_insectSelect.setVisible(visible);
        CY_yarnSelect.setVisible(visible);
        CY_tgtTectonSelect.setVisible(visible);
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



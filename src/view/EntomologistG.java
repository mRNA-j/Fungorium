package view;

import controller.Controller;
import factory.TectonGFactory;
import interfaces.ITectonGFactory;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class EntomologistG extends JPanel implements BaseViewG {
    public Entomologist getEntomologist() {
        return entomologist;
    }

    public void setEntomologist(Entomologist entomologist) {
        this.entomologist = entomologist;
    }
    private Entomologist entomologist;
    private JLabel nameLabel = new JLabel();
    private Controller controller;

    private final JButton eatButton = new JButton("Eat Spore");
    private final JButton moveButton = new JButton("Move Insect");
    private final JButton cutButton = new JButton("Cut Yarn");
    private final JButton skipButton = new JButton("Skip");
    private final JButton nextPlayerButton = new JButton("NEXT Player");

    private JComboBox<Insect> MI_insectSelect;
    private JComboBox<Tecton> MI_tgtTectonSelect;
    private JComboBox<Yarn> MI_yarnSelect;
    private JComboBox<Insect> ES_insectSelect;
    private JComboBox<Spore> ES_sporeSelect;
    private JComboBox<Insect> CY_insectSelect;
    private JComboBox<Yarn> CY_yarnSelect;
    private JComboBox<Tecton> CY_tgtTectonSelect;

    private TectonPanel upperStrip;
    private TectonPanel lowerStrip;
    private JScrollPane upperScroll;
    private JScrollPane lowerScroll;
    public final List<TectonG> upperTectons = new ArrayList<>();
    public final List<TectonG> lowerTectons = new ArrayList<>();
    private String nextPanelName;

    private PanelSwitcher panelSwitcher;

    private Insect chosenInsect;
    private Tecton chosenTecton;
    private Yarn chosenYarn;
    private Spore chosenSpore;
    private ITectonGFactory tectonFactory;


    public void setPanelSwitcher(PanelSwitcher panelSwitcher) {
        this.panelSwitcher = panelSwitcher;
    }

    public EntomologistG(Entomologist ento, String panelName, Controller controllerIn) {

        ento.addObserver(this);
        this.controller = controllerIn;
        this.entomologist = ento;
        this.tectonFactory = new TectonGFactory();
        this.nextPanelName = panelName;
        nameLabel.setText(entomologist.getName() + " - "+ entomologist.getPoints());
        setLayout(new BorderLayout());
        // Title label
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(Box.createHorizontalGlue(), BorderLayout.CENTER);
        topPanel.add(nameLabel, BorderLayout.EAST);
        topPanel.setOpaque(false);
        add(topPanel, BorderLayout.NORTH);

        // Prepare upper tectons from insects
        for (int i = 0; i < ento.getInsect().size(); i++) {
            Insect insect = ento.getInsect().get(i);
            String id = insect.getCurrentPlace().getId();
            upperTectons.add(tectonFactory.onCreate(i * 80 + 10, 40, 30, id, insect.getCurrentPlace()));
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
        comboPanel.setLayout(new GridLayout(11, 1, 5, 5));

        MI_insectSelect = new JComboBox<Insect>();
        MI_tgtTectonSelect = new JComboBox<Tecton>();
        MI_yarnSelect = new JComboBox<Yarn>();
        ES_insectSelect = new JComboBox<Insect>();
        ES_sporeSelect = new JComboBox<Spore>();
        CY_insectSelect = new JComboBox<Insect>();
        CY_yarnSelect = new JComboBox<Yarn>();
        CY_tgtTectonSelect = new JComboBox<Tecton>();

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
        comboPanel.add(MI_insectSelect, 0);
        comboPanel.add(MI_tgtTectonSelect, 1);
        comboPanel.add(MI_yarnSelect, 2);
        comboPanel.add(ES_insectSelect, 3);
        comboPanel.add(ES_sporeSelect, 4);
        comboPanel.add(CY_insectSelect, 5);
        comboPanel.add(CY_yarnSelect, 6);
        comboPanel.add(CY_tgtTectonSelect, 7);



        // === Add the action listeners ===

        // Move Insect
        moveButton.addActionListener(e -> {
            List<Insect> insectList = entomologist.getInsect();
            insectList.removeIf(i -> i.getParalized());
            int insectSize = insectList.size();
            Insect[] insects = insectList.toArray(new Insect[insectSize]);
            MI_insectSelect.setModel(new DefaultComboBoxModel<>(insects));
            MI_insectSelect.setVisible(true);
            MI_insectSelect.setEnabled(true);
            MI_yarnSelect.setVisible(false);
            MI_yarnSelect.setEnabled(false);
            MI_tgtTectonSelect.setVisible(false);
            MI_tgtTectonSelect.setEnabled(false);
            disableOtherButtons(nextPlayerButton);
        });

        MI_insectSelect.addActionListener(e -> {
            chosenInsect = (Insect) MI_insectSelect.getSelectedItem();
            int tectonSize = chosenInsect.getPlace().tectonsConnectedWithYarn().size();
            MI_tgtTectonSelect.setModel(new DefaultComboBoxModel<>(chosenInsect.getPlace().tectonsConnectedWithYarn().toArray(new Tecton[tectonSize])));
            //revalidate();
            //repaint();
            System.out.println(chosenInsect.getPlace());
            MI_tgtTectonSelect.setVisible(true);
            MI_tgtTectonSelect.setEnabled(true);
            MI_insectSelect.setEnabled(false);
        });

        MI_tgtTectonSelect.addActionListener(e -> {
            MI_tgtTectonSelect.setEnabled(false);
            chosenTecton = (Tecton) MI_tgtTectonSelect.getSelectedItem();
            controller.action_move(chosenInsect, chosenTecton);
            // disable all buttons except for next player, if the insect has accelerator spore, then leave the move button enabled
            if(chosenInsect.getAccelerated()){
                disableOtherButtons(moveButton);
                moveButton.setEnabled(true);
                chosenInsect.resetEffect();
            } else {
                disableOtherButtons(nextPlayerButton);
            } 
        });

        // Eat Spore
        eatButton.addActionListener(e -> {
            List<Insect> insectList = entomologist.getInsect();
            insectList.removeIf(i -> i.getParalized());
            int insectSize = insectList.size();
            Insect[] insects = insectList.toArray(new Insect[insectSize]);
            ES_insectSelect.setModel(new DefaultComboBoxModel<>(insects));
            ES_insectSelect.setVisible(true);
            ES_insectSelect.setEnabled(true);
            ES_sporeSelect.setVisible(false);
            ES_sporeSelect.setEnabled(false);
            disableOtherButtons(nextPlayerButton);
        });

        ES_insectSelect.addActionListener(e -> {
            ES_insectSelect.setEnabled(false);
            chosenInsect = (Insect) ES_insectSelect.getSelectedItem();
            ES_sporeSelect.setEnabled(true);
            int sporeSize = chosenInsect.getPlace().getSpores().size();
            ES_sporeSelect.setModel(new DefaultComboBoxModel<>(chosenInsect.getPlace().getSpores().toArray(new Spore[sporeSize])));
            //revalidate();
            //repaint();
            ES_sporeSelect.setVisible(true);
            ES_sporeSelect.setEnabled(true);
        });

        ES_sporeSelect.addActionListener(e -> {
            chosenSpore = (Spore) ES_sporeSelect.getSelectedItem();
            controller.action_eat_spore(chosenSpore, chosenInsect);
            disableOtherButtons(nextPlayerButton);
        });

        // Cut Yarn
        cutButton.addActionListener(e -> {
            List<Insect> insectList = entomologist.getInsect();
            insectList.removeIf(i -> i.getParalized());
            int insectSize = insectList.size();
            Insect[] insects = insectList.toArray(new Insect[insectSize]);
            CY_insectSelect.setModel(new DefaultComboBoxModel<>(insects));
            CY_insectSelect.setVisible(true);
            CY_insectSelect.setEnabled(true);
            CY_yarnSelect.setVisible(false);
            CY_yarnSelect.setEnabled(false);
            CY_tgtTectonSelect.setVisible(false); // Initially hidden
            CY_tgtTectonSelect.setEnabled(false);
            disableOtherButtons(nextPlayerButton);
        });

        CY_insectSelect.addActionListener(e -> {
            chosenInsect = (Insect) CY_insectSelect.getSelectedItem();
            int yarnSize = chosenInsect.getPlace().getYarns().size();
            CY_yarnSelect.setModel(new DefaultComboBoxModel<>(chosenInsect.getPlace().getYarns().toArray(new Yarn[yarnSize])));
            //revalidate();
            //repaint();
            CY_yarnSelect.setVisible(true);
            CY_yarnSelect.setEnabled(true);
            CY_insectSelect.setEnabled(false);
        });

        CY_yarnSelect.addActionListener(e -> {
            chosenYarn = (Yarn) CY_yarnSelect.getSelectedItem();
            int tectonSize = chosenInsect.getPlace().tectonsConnectedByTheYarn(chosenYarn).size();
            Tecton[] tectons = chosenInsect.getPlace().tectonsConnectedByTheYarn(chosenYarn).toArray(new Tecton[tectonSize]);
            CY_tgtTectonSelect.setModel(new DefaultComboBoxModel<>(tectons));
            //revalidate();
            //repaint();
            CY_tgtTectonSelect.setVisible(true);
            CY_tgtTectonSelect.setEnabled(true);
            CY_yarnSelect.setEnabled(false);
        });

        CY_tgtTectonSelect.addActionListener(e -> {
            chosenTecton = (Tecton) CY_tgtTectonSelect.getSelectedItem();
            controller.action_cut_yarn(chosenInsect, chosenYarn, chosenTecton);
            update();
            disableOtherButtons(nextPlayerButton);
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
                controller.setNextActivePlayer();
            }
        });

        // === Add the new buttons to your button panel ===
        buttonPanel.add(moveButton);
        buttonPanel.add(eatButton);
        buttonPanel.add(cutButton);

        buttonPanel.add(skipButton);
        buttonPanel.add(nextPlayerButton);
        // Center panel for tecton views
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        upperScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120)); // limit scroll area height
        lowerScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        centerPanel.add(upperScroll);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // spacing
        centerPanel.add(lowerScroll);

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
        activeButton.setEnabled(true);
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
            lowerTectons.add(tectonFactory.onCreate(i * 80 + 10, 40, 30, neighbor.getId(), neighbor));
        }

        SwingUtilities.invokeLater(() -> {
            lowerStrip.update();
            lowerStrip.repaint();
        });
    }

    @Override
    public void update() {
        // Update the entomologist name and points display
        nameLabel.setText(entomologist.getName() + " - " + entomologist.getPoints());

        // Update the upper tectons based on current insects
        upperTectons.clear();
        for (int i = 0; i < entomologist.getInsect().size(); i++) {
            Insect insect = entomologist.getInsect().get(i);
            String id = insect.getId();
            upperTectons.add(tectonFactory.onCreate(i * 80 + 10, 40, 30, id, insect.getCurrentPlace()));
        }

        MI_insectSelect.setModel(new DefaultComboBoxModel<>());
        ES_insectSelect.setModel(new DefaultComboBoxModel<>());
        CY_insectSelect.setModel(new DefaultComboBoxModel<>());

        // Update the lower tecton panel if there's a selection
        if (upperStrip.getSelectedTecton() != null) {
            updateLowerStrip(upperStrip.getSelectedTecton().t);
        }

        // Reset button states
        enableAllButtons();
        setAllComboBoxesVisible(false);

        SwingUtilities.invokeLater(() -> {
            upperStrip.update();
            lowerStrip.update();
            revalidate();
            repaint();
        });

    }
}



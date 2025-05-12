package view;

import controller.UpdateListener;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class MushroomPickerG extends JPanel implements UpdateListener {
  private MushroomPicker mushroomPicker;
  private JLabel nameLabel = new JLabel();

  private final JButton growYarnButton = new JButton("Grow Yarn");
  private final JButton growMushroomButton = new JButton("Grow Mushroom");
  private final JButton disperseButton = new JButton("Disperse Spore");
  private final JButton skipButton = new JButton("Skip");
  private final JButton nextPlayerButton = new JButton("nextPlayer");

  //Disperse spore
  private JComboBox<String> DS_mushroomSelector;
  private JComboBox<String> DS_tectonSelector;
  //private JComboBox<String> growMushroom_sporeSelector;

  //Grow Mushroom
  private JComboBox<String> GM_yarnSelector;
  private JComboBox<String> GM_tectonSelector;

  //Grow Yarn
  private JComboBox<String> GY_yarnSelector;
  private JComboBox<String> GY_srcTectonSelector;
  private JComboBox<String> GY_tgtTectonSelector;


  private TectonPanel upperStrip;
  private TectonPanel lowerStrip;
  private JScrollPane upperScroll;
  private JScrollPane lowerScroll;
  public final List<TectonG> upperTectons = new ArrayList<>();
  public final List<TectonG> lowerTectons = new ArrayList<>();
  private String nextPanelName;

  Set<String> addedTectonIds = new HashSet<>();

  private PanelSwitcher panelSwitcher;

  public void setPanelSwitcher(PanelSwitcher panelSwitcher) {
    this.panelSwitcher = panelSwitcher;
  }


  public MushroomPickerG(MushroomPicker picker,String panelName) {
    this.mushroomPicker = picker;
    nameLabel.setText(picker.getName());
    this.nextPanelName = panelName;
    setLayout(new BorderLayout());

    // === Name Label ===
    nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
    nameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(Box.createHorizontalGlue(), BorderLayout.CENTER);
    topPanel.add(nameLabel, BorderLayout.EAST);
    topPanel.setOpaque(false);
    add(topPanel, BorderLayout.NORTH);

    // === Upper Tectons from Owned Mushrooms ===
    for (int i = 0; i < picker.getOwnedMushrooms().size(); i++) {
      Mushroom mushroom = picker.getOwnedMushrooms().get(i);
      Tecton t = mushroom.getTecton();
      if(t.getId() != null&& addedTectonIds.add(t.getId())) {
        upperTectons.add(new TectonG(i * 80 + 10, 40, 30, t.getId(), t));
      }
    }

    for (int i = 0; i < picker.getOwnedYarns().size(); i++) {
      Yarn yarn = picker.getOwnedYarns().get(i);
      for(int j=0;j<yarn.getTectons().size();j++){
        Tecton t = yarn.getTectons().get(j);

        if(t.getId() != null&& addedTectonIds.add(t.getId())){
          upperTectons.add(new TectonG(i * 80 + 10, 40, 30, t.getId(), t));
        }

      }

    }

    // === Combo Box Panel ===
    JPanel comboPanel = new JPanel();
    comboPanel.setLayout(new GridLayout(7, 1, 5, 5));


    //itt kell az adatkötés, késő van, nincs kedvem
    DS_mushroomSelector= new JComboBox<>(new String[] {"Mushroom 1", "Mushroom 2"});
    DS_mushroomSelector.setVisible(false);
    DS_tectonSelector = new JComboBox<>(new String[] {"Tecton A", "Tecton B"});
    DS_tectonSelector.setVisible(false);
   // GM = new JComboBox<>(new String[] {"Spore Type 1", "Spore Type 2"});
    GM_yarnSelector = new JComboBox<>(new String[] {"Yarn A", "Yarn B"});
    GM_yarnSelector.setVisible(false);
    GM_tectonSelector= new JComboBox<>(new String[] {"Tecton X", "Tecton Y"});
    GM_tectonSelector.setVisible(false);
    GY_yarnSelector=new JComboBox<>(new String[] {"Yarn A", "Yarn B"});
    GY_yarnSelector.setVisible(false);
    GY_srcTectonSelector = new JComboBox<>(new String[] {"Start 1", "Start 2"});
    GY_srcTectonSelector.setVisible(false);
    GY_tgtTectonSelector= new JComboBox<>(new String[] {"Target 1", "Target 2"});
    GY_tgtTectonSelector.setVisible(false);

    comboPanel.add(DS_mushroomSelector);
    comboPanel.add(DS_tectonSelector);
    comboPanel.add(GM_yarnSelector);
    comboPanel.add(GM_tectonSelector);
    comboPanel.add(GY_yarnSelector);
    comboPanel.add(GY_srcTectonSelector);
    comboPanel.add(GY_tgtTectonSelector);


    upperStrip = new TectonPanel(upperTectons, true, this::updateLowerStrip);
    lowerStrip = new TectonPanel(lowerTectons, false, null);

    upperScroll = new JScrollPane(upperStrip);
    lowerScroll = new JScrollPane(lowerStrip);
    upperScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    upperScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    lowerScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    lowerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

    // === Button Panel ===
    JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));

    growMushroomButton.addActionListener(e -> {
      GM_tectonSelector.setVisible(true);
      GM_tectonSelector.setEditable(false);
      GM_yarnSelector.setVisible(true);
      GM_yarnSelector.setEditable(true);
      growYarnButton.setEnabled(false);
      disperseButton.setEnabled(false);
      skipButton.setEnabled(false);
      growMushroomButton.setEnabled(false);
    });

    GM_yarnSelector.addActionListener(e -> {
      GM_yarnSelector.setEditable(false);
      GM_tectonSelector.setEditable(true);
    });

    GM_tectonSelector.addActionListener(e -> {
      Object yarnSelected = GM_yarnSelector.getSelectedItem();
      Object tectonSelected = GM_tectonSelector.getSelectedItem();

      //ide kell beadni, amint nem testId a paraméter!!
      //picker.actionGrowMushroom();
    });

    disperseButton.addActionListener(e -> {
      DS_mushroomSelector.setVisible(true);
      DS_mushroomSelector.setEditable(false);
      DS_tectonSelector.setVisible(true);
      DS_tectonSelector.setEditable(true);
      growYarnButton.setEnabled(false);
      growMushroomButton.setEnabled(false);
      skipButton.setEnabled(false);
      disperseButton.setEnabled(false);
    });

    DS_mushroomSelector.addActionListener(e -> {
      DS_mushroomSelector.setEditable(false);
      DS_tectonSelector.setEditable(true);
    });

    DS_tectonSelector.addActionListener(e -> {
      Object mushroomSelected = DS_mushroomSelector.getSelectedItem();
      Object tectonSelected = DS_tectonSelector.getSelectedItem();

      // Call disperse spore action
      // mushroomPicker.actionDisperseSpore(...);
    });

    growYarnButton.addActionListener(e -> {
      GY_yarnSelector.setVisible(true);
      GY_yarnSelector.setEditable(false);
      GY_srcTectonSelector.setVisible(true);
      GY_srcTectonSelector.setEditable(true);
      GY_tgtTectonSelector.setVisible(false); // Initially hidden
      growMushroomButton.setEnabled(false);
      disperseButton.setEnabled(false);
      skipButton.setEnabled(false);
      growYarnButton.setEnabled(false);
    });

    GY_yarnSelector.addActionListener(e -> {
      GY_yarnSelector.setEditable(false);
      GY_srcTectonSelector.setEditable(true);
    });

    GY_srcTectonSelector.addActionListener(e -> {
      GY_srcTectonSelector.setEditable(false);
      GY_tgtTectonSelector.setVisible(true);
      GY_tgtTectonSelector.setEditable(true);

      // You might want to populate target tectons based on source selection
    });

    GY_tgtTectonSelector.addActionListener(e -> {
      Object yarnSelected = GY_yarnSelector.getSelectedItem();
      Object srcTectonSelected = GY_srcTectonSelector.getSelectedItem();
      Object tgtTectonSelected = GY_tgtTectonSelector.getSelectedItem();

      // Call grow yarn action
      // mushroomPicker.actionGrowYarn(...);
    });

    skipButton.addActionListener(e -> {
      growMushroomButton.setEnabled(false);
      disperseButton.setEnabled(false);
      growYarnButton.setEnabled(false);
    });





    buttonPanel.add(growMushroomButton);
    buttonPanel.add(growYarnButton);
    buttonPanel.add(disperseButton);
    buttonPanel.add(skipButton);

    nextPlayerButton.addActionListener(e -> {
      if (panelSwitcher != null) {
        GM_yarnSelector.setVisible(false);
        DS_mushroomSelector.setVisible(false);
        DS_tectonSelector.setVisible(false);
        GM_tectonSelector.setVisible(false);
        GY_yarnSelector.setVisible(false);
        GY_srcTectonSelector.setVisible(false);
        GY_tgtTectonSelector.setVisible(false);
        growMushroomButton.setEnabled(true);
        disperseButton.setEnabled(true);
        skipButton.setEnabled(true);
        growYarnButton.setEnabled(true);
        panelSwitcher.showPanel(nextPanelName); // Or logic to decide which comes next
      }
    });

    buttonPanel.add(nextPlayerButton);

    // === Center Panel ===
    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.add(upperScroll, BorderLayout.NORTH);
    centerPanel.add(lowerScroll, BorderLayout.CENTER);

    JPanel rightPanel = new JPanel();
    rightPanel.setLayout(new BorderLayout());
    rightPanel.add(buttonPanel, BorderLayout.NORTH);
    rightPanel.add(comboPanel, BorderLayout.CENTER);

    add(rightPanel, BorderLayout.EAST);

    add(centerPanel, BorderLayout.CENTER);
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

  public MushroomPickerG() {}

  public MushroomPicker getMushroomPicker() {
    return mushroomPicker;
  }

  public void setMushroomPicker(MushroomPicker mushroomPicker) {
    this.mushroomPicker = mushroomPicker;
  }

  @Override
  public void update() {
    repaint();
  }
}

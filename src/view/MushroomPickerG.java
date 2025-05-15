package view;

import controller.Controller;
import factory.TectonGFactory;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import interfaces.ITectonGFactory;

public class MushroomPickerG extends JPanel implements BaseViewG {
  private MushroomPicker mushroomPicker;
  private JLabel nameLabel = new JLabel();

  private final JButton growYarnButton = new JButton("Grow Yarn");
  private final JButton growMushroomButton = new JButton("Grow Mushroom");
  private final JButton disperseButton = new JButton("Disperse Spore");
  private final JButton skipButton = new JButton("Skip");
  private final JButton nextPlayerButton = new JButton("nextPlayer");

  //Disperse spore
  private JComboBox<Mushroom> DS_mushroomSelector;
  private JComboBox<Tecton> DS_tectonSelector;
  private JComboBox<String> DS_growMushroom_sporeSelector;

  //Grow Mushroom
  private JComboBox<Yarn> GM_yarnSelector;
  private JComboBox<Tecton> GM_tectonSelector;

  //Grow Yarn
  private JComboBox<Yarn> GY_yarnSelector;
  private JComboBox<Tecton> GY_srcTectonSelector;
  private JComboBox<Tecton> GY_tgtTectonSelector;

  private Yarn chosenYarn;
  private Tecton chosenTecton1;
  private Tecton chosenTecton2;
  private Mushroom chosenMushroom;
  private String chosenSporeType;


  private TectonPanel upperStrip;
  private TectonPanel lowerStrip;
  private JScrollPane upperScroll;
  private JScrollPane lowerScroll;
  public final List<TectonG> upperTectons = new ArrayList<>();
  public final List<TectonG> lowerTectons = new ArrayList<>();
  private String nextPanelName;
  private ITectonGFactory tectonFactory;

  Set<String> addedTectonIds = new HashSet<>();

  private PanelSwitcher panelSwitcher;

  public void setPanelSwitcher(PanelSwitcher panelSwitcher) {
    this.panelSwitcher = panelSwitcher;
  }


  public MushroomPickerG(MushroomPicker picker, String panelName, Controller controller) {
    this.mushroomPicker = picker;
    this.tectonFactory = new TectonGFactory();
    nameLabel.setText(mushroomPicker.getName() + " - "+ mushroomPicker.getPoints());
    nextPanelName = panelName;
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
    for (int i = 0; i < mushroomPicker.getOwnedMushrooms().size(); i++) {
      Mushroom mushroom = mushroomPicker.getOwnedMushrooms().get(i);
      Tecton t = mushroom.getTecton();
      if(t.getId() != null&& addedTectonIds.add(t.getId())) {
        upperTectons.add(tectonFactory.onCreate(0, 0, 30, t.getId(), t));
      }
    }

    for (int i = 0; i < mushroomPicker.getOwnedYarns().size(); i++) {
      Yarn yarn = mushroomPicker.getOwnedYarns().get(i);
      for(int j=0;j<yarn.getTectons().size();j++){
        Tecton t = yarn.getTectons().get(j);

        if(t.getId() != null&& addedTectonIds.add(t.getId())){
          upperTectons.add(tectonFactory.onCreate(0, 0, 30, t.getId(), t));
        }

      }

    }

    // === Combo Box Panel ===
    JPanel comboPanel = new JPanel();
    comboPanel.setLayout(new GridLayout(8, 1, 5, 5));



    DS_mushroomSelector= new JComboBox<>();
    DS_mushroomSelector.setVisible(false);
    DS_tectonSelector = new JComboBox<>();
    DS_tectonSelector.setVisible(false);
    DS_growMushroom_sporeSelector = new JComboBox<>(new String[] {"Accelerator", "Decelerator", "Cut Preventing", "Insect Duplicating", "Paralyzing"});
    DS_growMushroom_sporeSelector.setVisible(false);
    //GM = new JComboBox<>(new String[] {"Spore Type 1", "Spore Type 2"});
    GM_yarnSelector = new JComboBox<>();
    GM_yarnSelector.setVisible(false);
    GM_tectonSelector= new JComboBox<>();
    GM_tectonSelector.setVisible(false);
    GY_yarnSelector=new JComboBox<>();
    GY_yarnSelector.setVisible(false);
    GY_srcTectonSelector = new JComboBox<>();
    GY_srcTectonSelector.setVisible(false);
    GY_tgtTectonSelector= new JComboBox<>();
    GY_tgtTectonSelector.setVisible(false);

    comboPanel.add(DS_mushroomSelector);
    comboPanel.add(DS_tectonSelector);
    comboPanel.add(DS_growMushroom_sporeSelector);
    comboPanel.add(GM_yarnSelector);
    comboPanel.add(GM_tectonSelector);
    comboPanel.add(GY_yarnSelector);
    comboPanel.add(GY_srcTectonSelector);
    comboPanel.add(GY_tgtTectonSelector);


    upperStrip = new TectonPanel(upperTectons, true, this::updateLowerStrip);
    lowerStrip = new TectonPanel(lowerTectons, false, null);
    upperStrip.getPreferredSize();

    lowerStrip.setPreferredSize(
            new Dimension(lowerStrip.getPreferredSize().width,
                    upperStrip.getPreferredSize().height) // Match upper strip height
    );

    upperScroll = new JScrollPane(upperStrip);
    lowerScroll = new JScrollPane(lowerStrip);
    upperScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    upperScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    lowerScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    lowerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

    // === Button Panel ===
    JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));

    growMushroomButton.addActionListener(e -> {
      GM_tectonSelector.setVisible(false);
      GM_tectonSelector.setEnabled(false);
      GM_yarnSelector.setVisible(true);
      GM_yarnSelector.setEnabled(true);
      disableOtherButtons(growMushroomButton);
    });

    GM_yarnSelector.addActionListener(e -> {
      chosenYarn = (Yarn) GY_yarnSelector.getSelectedItem();
      List<Tecton> tectonsList= chosenYarn.getTectons();

      int tectonSize = tectonsList.size();
      Tecton[] tectons = tectonsList.toArray(new Tecton[tectonSize]);
      GM_tectonSelector.setModel(new DefaultComboBoxModel<>(tectons));
      GM_tectonSelector.setEnabled(true);
      GM_tectonSelector.setVisible(true);
      GM_yarnSelector.setEnabled(false);
    });

    GM_tectonSelector.addActionListener(e -> {
      chosenTecton1 = (Tecton) GM_tectonSelector.getSelectedItem();
      mushroomPicker.actionGrowMushroom(chosenTecton1, "MEG KELL CSINÁNI A NÉVADÁST");
      //TODO a névadásnak boxot
    });

    disperseButton.addActionListener(e -> {
      List<Mushroom> mushroomList = mushroomPicker.getOwnedMushrooms();
      mushroomList.removeIf(m -> !m.getHasSpore());
      int mushroomSize = mushroomList.size();
      Mushroom[] mushrooms = mushroomList.toArray(new Mushroom[mushroomSize]);
      DS_mushroomSelector.setModel(new DefaultComboBoxModel<>(mushrooms));
      DS_mushroomSelector.setVisible(true);
      DS_mushroomSelector.setEnabled(true);
      DS_tectonSelector.setVisible(false);
      DS_tectonSelector.setEnabled(false);
      disableOtherButtons(disperseButton);

    });

    DS_mushroomSelector.addActionListener(e -> {
      chosenMushroom = (Mushroom) DS_mushroomSelector.getSelectedItem();
      List<Tecton> tectonList = chosenMushroom.getTecton().getNeighbours();
      if(chosenMushroom.getAge()>10){
        for(Tecton t : tectonList){
          for(Tecton t2 : t.getNeighbours()){
            if(!tectonList.contains(t2)){
              tectonList.add(t2);
            }
          }
        }
      }
      int tectonSize = tectonList.size();
      Tecton[] tectons = tectonList.toArray(new Tecton[tectonSize]);
      DS_tectonSelector.setModel(new DefaultComboBoxModel<>(tectons));
      DS_tectonSelector.setEnabled(true);
      DS_tectonSelector.setVisible(true);
      DS_mushroomSelector.setEnabled(false);
    });

    DS_tectonSelector.addActionListener(e -> {
      chosenTecton1 = (Tecton) DS_tectonSelector.getSelectedItem();
      DS_growMushroom_sporeSelector.setEnabled(true);
      DS_growMushroom_sporeSelector.setVisible(true);
      DS_tectonSelector.setEnabled(false);
    });

    DS_growMushroom_sporeSelector.addActionListener(e -> {
      chosenSporeType = (String) DS_growMushroom_sporeSelector.getSelectedItem();
      mushroomPicker.actionSporeDispersion(chosenTecton1, chosenMushroom, chosenSporeType, "MEG KELL CSINALNI");
    });

    growYarnButton.addActionListener(e -> {
      int yarnSize = mushroomPicker.getOwnedYarns().size();
      Yarn[] yarns = mushroomPicker.getOwnedYarns().toArray(new Yarn[yarnSize]);
      GY_yarnSelector.setModel(new DefaultComboBoxModel<>(yarns));
      GY_yarnSelector.setVisible(true);
      GY_yarnSelector.setEnabled(true);
      GY_srcTectonSelector.setVisible(false);
      GY_srcTectonSelector.setEnabled(false);
      GY_tgtTectonSelector.setVisible(false);
      GY_tgtTectonSelector.setEnabled(false);
      disableOtherButtons(growYarnButton);
    });

    GY_yarnSelector.addActionListener(e -> {
      chosenYarn = (Yarn) GY_yarnSelector.getSelectedItem();
      List<Tecton> tectonList = new ArrayList<>();
      for(Tecton t1: chosenYarn.getTectons()){
        for(Tecton t: t1.getNeighbours()){
          if(!tectonList.contains(t)){
            if(t.getYarnLimit()<1) {
              if (t.getYarns() != null && t.getYarns().get(0).getPlayer() != mushroomPicker) {
                if (!tectonList.contains(t1)) {
                  tectonList.add(t1);
                }
              }
              else {
                tectonList.add(t1);
              }
            }
          }
        }
      }
      int tectonSize = tectonList.size();
      Tecton[] tectons = tectonList.toArray(new Tecton[tectonSize]);
      GY_tgtTectonSelector.setModel(new DefaultComboBoxModel<>(tectons));
      GY_tgtTectonSelector.setEnabled(true);
      GY_tgtTectonSelector.setVisible(true);
      GY_yarnSelector.setEnabled(false);
    });

    GY_tgtTectonSelector.addActionListener(e -> {
      chosenTecton2 = (Tecton) GY_tgtTectonSelector.getSelectedItem();
      List<Tecton> tectonList = new ArrayList<>();
      for(Tecton t: chosenTecton2.getNeighbours()){
        if(!tectonList.contains(t) && t.getYarns().contains(chosenYarn)){
          tectonList.add(t);
        }
      }
      int tectonSize = tectonList.size();
      Tecton[] tectons = tectonList.toArray(new Tecton[tectonSize]);
      GY_srcTectonSelector.setModel(new DefaultComboBoxModel<>(tectons));
      GY_srcTectonSelector.setEnabled(true);
      GY_srcTectonSelector.setVisible(true);
      GY_tgtTectonSelector.setEnabled(false);
    });

    GY_srcTectonSelector.addActionListener(e -> {
      chosenTecton1 = (Tecton) GY_srcTectonSelector.getSelectedItem();
      mushroomPicker.actionGrowYarn(chosenTecton1, chosenTecton2, chosenYarn);
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

    add(centerPanel, BorderLayout.CENTER);
  }

  private void updateLowerStrip(Tecton selectedTecton) {
    lowerTectons.clear();
    List<Tecton> neighbors = selectedTecton.getNeighbours();

    for (int i = 0; i < neighbors.size(); i++) {
      Tecton neighbor = neighbors.get(i);
      lowerTectons.add(tectonFactory.onCreate(i * 80 + 10, 40, 30, neighbor.getId(), neighbor));
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

  private void enableAllButtons() {
    growMushroomButton.setEnabled(true);
    growYarnButton.setEnabled(true);
    disperseButton.setEnabled(true);
    skipButton.setEnabled(true);
  }

  private void disableOtherButtons(JButton activeButton) {
    growMushroomButton.setEnabled(false);
    growYarnButton.setEnabled(false);
    disperseButton.setEnabled(false);
    skipButton.setEnabled(false);
    activeButton.setEnabled(false);
  }

  @Override
  public void update() {
    // Update the mushroom picker name and points display
    nameLabel.setText(mushroomPicker.getName() + " - " + mushroomPicker.getPoints());

    // Update the upper tectons based on current mushrooms and yarns
    upperTectons.clear();
    addedTectonIds.clear();

    // Add tectons from owned mushrooms
    for (int i = 0; i < mushroomPicker.getOwnedMushrooms().size(); i++) {
      Mushroom mushroom = mushroomPicker.getOwnedMushrooms().get(i);
      Tecton t = mushroom.getTecton();
      if (t.getId() != null && addedTectonIds.add(t.getId())) {
        upperTectons.add(tectonFactory.onCreate(i * 80 + 10, 40, 30, t.getId(), t));
      }
    }

    // Add tectons from owned yarns
    for (int i = 0; i < mushroomPicker.getOwnedYarns().size(); i++) {
      Yarn yarn = mushroomPicker.getOwnedYarns().get(i);
      for (int j = 0; j < yarn.getTectons().size(); j++) {
        Tecton t = yarn.getTectons().get(j);
        if (t.getId() != null && addedTectonIds.add(t.getId())) {
          upperTectons.add(tectonFactory.onCreate((upperTectons.size()) * 80 + 10, 40, 30, t.getId(), t));
        }
      }
    }
/*
    // Update combobox models with current data
    int mushroomSize = mushroomPicker.getOwnedMushrooms().size();
    Mushroom[] mushrooms = mushroomPicker.getOwnedMushrooms().toArray(new Mushroom[mushroomSize]);

    int yarnSize = mushroomPicker.getOwnedYarns().size();
    Yarn[] yarns = mushroomPicker.getOwnedYarns().toArray(new Yarn[yarnSize]);
    // Update the mushroom selector for Disperse Spore action
    DS_mushroomSelector.setModel(new DefaultComboBoxModel<>(mushrooms));

    // Update the yarn selectors
    GM_yarnSelector.setModel(new DefaultComboBoxModel<>(yarns));
    GY_yarnSelector.setModel(new DefaultComboBoxModel<>(yarns));
*/
    // Update the lower tecton panel if there's a selection
    if (upperStrip.getSelectedTecton() != null) {
      updateLowerStrip(upperStrip.getSelectedTecton().t);
    }

    // Reset button states
    enableAllButtons();

    // Hide all combo boxes
    DS_mushroomSelector.setVisible(false);
    DS_tectonSelector.setVisible(false);
    DS_growMushroom_sporeSelector.setVisible(false);
    GM_yarnSelector.setVisible(false);
    GM_tectonSelector.setVisible(false);
    GY_yarnSelector.setVisible(false);
    GY_srcTectonSelector.setVisible(false);
    GY_tgtTectonSelector.setVisible(false);

    revalidate();
    // Request visual update
    upperStrip.repaint();
    lowerStrip.repaint();

    repaint();
  }
}

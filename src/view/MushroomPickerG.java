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

/**
 * A MushroomPickerG osztály a gombaszedő játékos grafikus felületét reprezentálja.
 * Megjeleníti a játékos által birtokolt gombákat, fonalakat, és lehetővé teszi
 * a különböző akciók végrehajtását (gomba növesztése, fonal növesztése, spóra szórása).
 * Implementálja a BaseViewG interfészt, amely lehetővé teszi a modell változásainak követését.
 */
public class MushroomPickerG extends JPanel implements BaseViewG {
  /** A megjelenített gombaszedő játékos modellje */
  private MushroomPicker mushroomPicker;
  
  /** A játékos nevét és pontszámát megjelenítő címke */
  private JLabel nameLabel = new JLabel();
  
  /** A játék vezérlője */
  private Controller controller;

  /** Gomb a fonal növesztéséhez */
  private final JButton growYarnButton = new JButton("Grow Yarn");
  
  /** Gomb a gomba növesztéséhez */
  private final JButton growMushroomButton = new JButton("Grow Mushroom");
  
  /** Gomb a spóra szórásához */
  private final JButton disperseButton = new JButton("Disperse Spore");
  
  /** Gomb a kör kihagyásához */
  private final JButton skipButton = new JButton("Skip");
  
  /** Gomb a következő játékosra váltáshoz */
  private final JButton nextPlayerButton = new JButton("NEXT Player");
  
  /** Jelzi, hogy ez-e az első fonal növesztés a körben */
  private boolean firstGrow=true;

  /** Legördülő lista a spóra szórásához kiválasztható gombákhoz */
  private JComboBox<Mushroom> DS_mushroomSelector;
  
  /** Legördülő lista a spóra szórásához kiválasztható tectonokhoz */
  private JComboBox<Tecton> DS_tectonSelector;
  
  /** Legördülő lista a spóra típusának kiválasztásához */
  private JComboBox<String> DS_sporeSelector;
  
  /** Szövegmező a spóra azonosítójának megadásához */
  private JTextField DS_sporeID;

  /** Legördülő lista a gomba növesztéséhez kiválasztható fonalakhoz */
  private JComboBox<Yarn> GM_yarnSelector;
  
  /** Legördülő lista a gomba növesztéséhez kiválasztható tectonokhoz */
  private JComboBox<Tecton> GM_tectonSelector;
  
  /** Szövegmező a gomba azonosítójának megadásához */
  private JTextField GM_mushroomID;

  /** Legördülő lista a gombából növő gombafonal típusának kiválasztásához */
  private JComboBox<String>  GM_yarnTypeSelector;

  /** Szövegmező a gombafonal azonosítójának megadásához */
  private JTextField GM_yarnID;

  /** Legördülő lista a fonal növesztéséhez kiválasztható fonalakhoz */
  private JComboBox<Yarn> GY_yarnSelector;
  
  /** Legördülő lista a fonal növesztéséhez kiválasztható forrás tectonokhoz */
  private JComboBox<Tecton> GY_srcTectonSelector;
  
  /** Legördülő lista a fonal növesztéséhez kiválasztható cél tectonokhoz */
  private JComboBox<Tecton> GY_tgtTectonSelector;

  /** A kiválasztott fonal */
  private Yarn chosenYarn;
  
  /** Az első kiválasztott tecton */
  private Tecton chosenTecton1;
  
  /** A második kiválasztott tecton */
  private Tecton chosenTecton2;
  
  /** A kiválasztott gomba */
  private Mushroom chosenMushroom;
  
  /** A kiválasztott spóra típusa */
  private String chosenSporeType;

  /** A kiválasztott yarn típusa */
  private String chosenYarnType;

  /** A növeszteni kívánt gomba azonosítója */
  private String chosenMushroomId;

  /** A felső tecton sáv panelje */
  private TectonPanel upperStrip;
  
  /** Az alsó tecton sáv panelje */
  private TectonPanel lowerStrip;
  
  /** Görgetősáv a felső tecton sávhoz */
  private JScrollPane upperScroll;
  
  /** Görgetősáv az alsó tecton sávhoz */
  private JScrollPane lowerScroll;
  
  /** A felső sávban megjelenített tectonok listája */
  public final List<TectonG> upperTectons = new ArrayList<>();
  
  /** Az alsó sávban megjelenített tectonok listája */
  public final List<TectonG> lowerTectons = new ArrayList<>();
  
  /** A következő panel neve, amelyre váltani kell */
  private String nextPanelName;
  
  /** A tecton grafikus objektumok gyártója */
  private ITectonGFactory tectonFactory;

  /** A már hozzáadott tectonok azonosítóinak halmaza a duplikációk elkerülésére */
  Set<String> addedTectonIds = new HashSet<>();

  /** A panel váltást kezelő objektum */
  private PanelSwitcher panelSwitcher;

  /**
   * Beállítja a panel váltást kezelő objektumot.
   * 
   * @param panelSwitcher A panel váltást kezelő objektum
   */
  public void setPanelSwitcher(PanelSwitcher panelSwitcher) {
    this.panelSwitcher = panelSwitcher;
  }


  /**
   * A MushroomPickerG osztály konstruktora.
   * Inicializálja a gombaszedő játékos grafikus felületét, beállítja a megfigyelőket,
   * létrehozza a gombokat, legördülő listákat és a tecton sávokat.
   * 
   * @param picker A megjelenítendő gombaszedő játékos modellje
   * @param panelName A következő panel neve, amelyre váltani kell
   * @param controllerIn A játék vezérlője
   */
  public MushroomPickerG(MushroomPicker picker, String panelName, Controller controllerIn) {
    this.mushroomPicker = picker;
    this.tectonFactory = new TectonGFactory();

    picker.addObserver(this);
    picker.addObserver(this);
    this.controller = controllerIn;
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
        upperTectons.add(tectonFactory.onCreate(0, 0, 30, t.getId()+" (" + t.getType()+")", t));
      }
    }

    for (int i = 0; i < mushroomPicker.getOwnedYarns().size(); i++) {
      Yarn yarn = mushroomPicker.getOwnedYarns().get(i);
      for(int j=0;j<yarn.getTectons().size();j++){
        Tecton t = yarn.getTectons().get(j);

        if(t.getId() != null&& addedTectonIds.add(t.getId())){
          upperTectons.add(tectonFactory.onCreate(0, 0, 30, t.getId() + " (" + t.getType() + ")", t));
        }

      }

    }

    // === Combo Box Panel ===
    JPanel comboPanel = new JPanel();
    comboPanel.setLayout(new GridLayout(13, 1, 5, 5));



    DS_mushroomSelector= new JComboBox<Mushroom>();
    DS_mushroomSelector.setVisible(false);
    DS_tectonSelector = new JComboBox<Tecton>();
    DS_tectonSelector.setVisible(false);
    DS_sporeSelector = new JComboBox<String>(new String[] {"Accelerator", "Decelerator", "Cut Preventing", "Insect Duplicating", "Paralyzing"});
    DS_sporeSelector.setVisible(false);
    DS_sporeID = new JTextField("id");
    DS_sporeID.setVisible(false);
    DS_sporeSelector.setVisible(false);
    GM_yarnSelector = new JComboBox<Yarn>();
    GM_yarnSelector.setVisible(false);
    GM_tectonSelector= new JComboBox<Tecton>();
    GM_tectonSelector.setVisible(false);
    GM_mushroomID = new JTextField("mushroom id");
    GM_mushroomID.setVisible(false);
    GM_yarnTypeSelector = new JComboBox<>(new String[] {"Normal", "Killer"});
    GM_yarnTypeSelector.setVisible(false);
    GM_yarnID = new JTextField("yarn id");
    GM_yarnID.setVisible(false);
    GY_yarnSelector=new JComboBox<Yarn>();
    GY_yarnSelector.setVisible(false);
    GY_srcTectonSelector = new JComboBox<Tecton>();
    GY_srcTectonSelector.setVisible(false);
    GY_tgtTectonSelector= new JComboBox<Tecton>();
    GY_tgtTectonSelector.setVisible(false);

    comboPanel.add(DS_mushroomSelector);
    comboPanel.add(DS_tectonSelector);
    comboPanel.add(DS_sporeSelector);
    comboPanel.add(DS_sporeID);
    comboPanel.add(GM_yarnSelector);
    comboPanel.add(GM_tectonSelector);
    comboPanel.add(GM_mushroomID);
    comboPanel.add(GM_yarnTypeSelector);
    comboPanel.add(GM_yarnID);
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
      int yarnSize = mushroomPicker.getOwnedYarns().size();
      Yarn[] yarns = mushroomPicker.getOwnedYarns().toArray(new Yarn[yarnSize]);
      GM_yarnSelector.setModel(new DefaultComboBoxModel<>(yarns));

      disableOtherButtons(nextPlayerButton);
    });

    GM_yarnSelector.addActionListener(e -> {
      chosenYarn = (Yarn) GM_yarnSelector.getSelectedItem();
      List<Tecton> tectonsList= chosenYarn.getTectons();

      int tectonSize = tectonsList.size();
      Tecton[] tectons = tectonsList.toArray(new Tecton[tectonSize]);
      GM_tectonSelector.setModel(new DefaultComboBoxModel<>(tectons));
      GM_tectonSelector.setEnabled(true);
      GM_tectonSelector.setVisible(true);
      GM_yarnSelector.setEditable(false);
    });

    GM_tectonSelector.addActionListener(e -> {
      chosenTecton1 = (Tecton) GM_tectonSelector.getSelectedItem();
      GM_mushroomID.setVisible(true);
      GM_mushroomID.setEnabled(true);
      GM_tectonSelector.setEnabled(false);
    });

    GM_mushroomID.addActionListener(e->{
      chosenMushroomId = GM_mushroomID.getText();
      GM_yarnTypeSelector.setVisible(true);
      GM_yarnTypeSelector.setEnabled(true);
      GM_mushroomID.setEnabled(false);
    });

    GM_yarnTypeSelector.addActionListener(e->{
      chosenYarnType = (String) GM_yarnTypeSelector.getSelectedItem();
      GM_yarnID.setVisible(true);
      GM_yarnID.setEnabled(true);
      GM_yarnTypeSelector.setEnabled(false);
    });

    GM_yarnID.addActionListener(e->{
      String yarnId = GM_yarnID.getText();
      controller.action_grow_mushroom(chosenTecton1, chosenMushroomId, chosenYarnType, yarnId);
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
      disableOtherButtons(nextPlayerButton);
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
      DS_sporeSelector.setEnabled(true);
      DS_sporeSelector.setVisible(true);
      DS_tectonSelector.setEnabled(false);
    });

    DS_sporeSelector.addActionListener(e -> {
      chosenSporeType = (String) DS_sporeSelector.getSelectedItem();
      DS_sporeID.setEnabled(true);
      DS_sporeID.setVisible(true);
      DS_sporeSelector.setEnabled(false);
    });
    DS_sporeID.addActionListener(e->{
      String id = DS_sporeID.getText();
      // Disable the text box
      DS_sporeID.setEnabled(false);
      controller.action_spore_dispersion(chosenTecton1, chosenMushroom, chosenSporeType, id);
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
      disableOtherButtons(nextPlayerButton);
    });

    // Fix for the IndexOutOfBoundsException in GY_yarnSelector.addActionListener

    GY_yarnSelector.addActionListener(e -> {
      chosenYarn = (Yarn) GY_yarnSelector.getSelectedItem();
      List<Tecton> tectonList = new ArrayList<>();

      // The issue is here - when chosenYarn.getTectons() is empty,
      // the loop doesn't execute but we still try to access elements later

      if (chosenYarn != null && !chosenYarn.getTectons().isEmpty()) {
        for(Tecton t1: chosenYarn.getTectons()){
            if(!tectonList.contains(t1)){
              if(t1.getYarnLimit() >=t1.getYarns().size()) {
                if (t1.getYarns() != null && !t1.getYarns().isEmpty() && t1.getYarns().get(0).getPlayer() != mushroomPicker) {
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
      GY_srcTectonSelector.setModel(new DefaultComboBoxModel<>(tectons));
      GY_srcTectonSelector.setEnabled(true);
      GY_srcTectonSelector.setVisible(true);
      GY_yarnSelector.setEnabled(false);
    });

    GY_srcTectonSelector.addActionListener(e -> {
      chosenTecton1 = (Tecton) GY_srcTectonSelector.getSelectedItem();
      List<Tecton> tectonList = new ArrayList<>();
      for (Tecton t : chosenTecton1.getNeighbours()) {
        if (!tectonList.contains(t)) {
          tectonList.add(t);
        }
      }
      Tecton[] tectons = tectonList.toArray(new Tecton[0]);
      GY_tgtTectonSelector.setModel(new DefaultComboBoxModel<>(tectons));
      GY_tgtTectonSelector.setEnabled(true);
      GY_tgtTectonSelector.setVisible(true);
      GY_srcTectonSelector.setEnabled(false);
    });


    GY_tgtTectonSelector.addActionListener(e -> {
      chosenTecton2 = (Tecton) GY_tgtTectonSelector.getSelectedItem();
      if(!chosenTecton2.getSpores().isEmpty()&&firstGrow==true){
        firstGrow=false;
        controller.action_grow_yarn(chosenTecton1, chosenTecton2, chosenYarn);
        SwingUtilities.invokeLater(() -> {

          growYarnButton.setEnabled(false);
          nextPlayerButton.setEnabled(false);
          GY_yarnSelector.setVisible(true);
          GY_yarnSelector.setEnabled(false);

          GY_srcTectonSelector.setVisible(true);
          GY_srcTectonSelector.setEnabled(false);

          // Repopulate the target selector with neighbors
          List<Tecton> neighbors = new ArrayList<>(chosenTecton2.getNeighbours());

          Tecton temp=chosenTecton2;
          chosenTecton2=chosenTecton1;
          chosenTecton1=temp;

          GY_tgtTectonSelector.setModel(new DefaultComboBoxModel<>(neighbors.toArray(new Tecton[0])));
          GY_tgtTectonSelector.setVisible(true);
          GY_tgtTectonSelector.setEnabled(true);
        });


      }
      else {
        firstGrow=true;
        controller.action_grow_yarn(chosenTecton1, chosenTecton2, chosenYarn);
        nextPlayerButton.setEnabled(true);
      }

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
        DS_sporeID.setVisible(false);
        DS_sporeSelector.setVisible(false);
        GM_tectonSelector.setVisible(false);
        GM_mushroomID.setVisible(false);
        GY_yarnSelector.setVisible(false);
        GY_srcTectonSelector.setVisible(false);
        GY_tgtTectonSelector.setVisible(false);
        growMushroomButton.setEnabled(true);
        disperseButton.setEnabled(true);
        skipButton.setEnabled(true);
        growYarnButton.setEnabled(true);
        panelSwitcher.showPanel(nextPanelName); // Or logic to decide which comes next
        controller.setNextActivePlayer();

        enableAllButtons();

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

  /**
   * Frissíti az alsó tecton sávot a kiválasztott tecton szomszédaival.
   * 
   * @param selectedTecton A kiválasztott tecton, amelynek szomszédait meg kell jeleníteni
   */
  private void updateLowerStrip(Tecton selectedTecton) {
    lowerTectons.clear();
    List<Tecton> neighbors = selectedTecton.getNeighbours();

    for (int i = 0; i < neighbors.size(); i++) {
      Tecton neighbor = neighbors.get(i);
      lowerTectons.add(tectonFactory.onCreate(i * 80 + 10, 40, 30, neighbor.getId() + " (" + neighbor.getType() +")", neighbor));
    }

    lowerStrip.repaint();
  }

  /**
   * Alapértelmezett konstruktor.
   * Tesztelési célokra használható.
   */
  public MushroomPickerG() {}

  /**
   * Visszaadja a megjelenített gombaszedő játékos modelljét.
   * 
   * @return A gombaszedő játékos modellje
   */
  public MushroomPicker getMushroomPicker() {
    return mushroomPicker;
  }

  /**
   * Beállítja a megjelenítendő gombaszedő játékos modelljét.
   * 
   * @param mushroomPicker A beállítandó gombaszedő játékos modellje
   */
  public void setMushroomPicker(MushroomPicker mushroomPicker) {
    this.mushroomPicker = mushroomPicker;
  }

  /**
   * Engedélyezi az összes akciógombot.
   * A játékos körének kezdetén hívódik meg.
   */
  private void enableAllButtons() {
    growMushroomButton.setEnabled(true);
    growYarnButton.setEnabled(true);
    disperseButton.setEnabled(true);
    skipButton.setEnabled(true);
  }

  /**
   * Letiltja az összes akciógombot az aktív gomb kivételével.
   * Egy akció kiválasztásakor hívódik meg.
   * 
   * @param activeButton Az aktív gomb, amely engedélyezve marad
   */
  private void disableOtherButtons(JButton activeButton) {
    growMushroomButton.setEnabled(false);
    growYarnButton.setEnabled(false);
    disperseButton.setEnabled(false);
    skipButton.setEnabled(false);
    nextPlayerButton.setEnabled(false);
    activeButton.setEnabled(true);

  }

  /**
   * Frissíti a grafikus felületet a modell változásai alapján.
   * Implementálja a BaseViewG interfész update metódusát.
   * Frissíti a játékos nevét, pontszámát, a tecton sávokat és a legördülő listákat.
   */
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
        upperTectons.add(tectonFactory.onCreate(i * 80 +10, 40, 30, t.getId() + " (" + t.getType() + ")", t));
      }
    }

    // Add tectons from owned yarns
    for (int i = 0; i < mushroomPicker.getOwnedYarns().size(); i++) {
      Yarn yarn = mushroomPicker.getOwnedYarns().get(i);
      for (int j = 0; j < yarn.getTectons().size(); j++) {
        Tecton t = yarn.getTectons().get(j);
        if (t.getId() != null && addedTectonIds.add(t.getId())) {
          upperTectons.add(tectonFactory.onCreate((upperTectons.size()) * 80 + 10, 40, 30, t.getId()+" ("+ t.getType() + ")", t));
        }
      }
    }

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

    // Update the lower tecton panel if there's a selection
    if (upperStrip.getSelectedTecton() != null) {
      updateLowerStrip(upperStrip.getSelectedTecton().t);
    }



    // Hide all combo boxes
    DS_mushroomSelector.setVisible(false);
    DS_tectonSelector.setVisible(false);
    DS_sporeSelector.setVisible(false);
    GM_yarnSelector.setVisible(false);
    GM_tectonSelector.setVisible(false);
    GM_yarnTypeSelector.setVisible(false);
    GM_yarnID.setVisible(false);
    GY_yarnSelector.setVisible(false);
    GY_srcTectonSelector.setVisible(false);
    GY_tgtTectonSelector.setVisible(false);


      upperStrip.update();
      lowerStrip.update();
      revalidate();
      repaint();

  }
}

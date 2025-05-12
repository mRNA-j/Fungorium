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

  private TectonPanel upperStrip;
  private TectonPanel lowerStrip;
  private JScrollPane upperScroll;
  private JScrollPane lowerScroll;
  public final List<TectonG> upperTectons = new ArrayList<>();
  public final List<TectonG> lowerTectons = new ArrayList<>();

  Set<String> addedTectonIds = new HashSet<>();


  public MushroomPickerG(MushroomPicker picker) {
    this.mushroomPicker = picker;
    nameLabel.setText(picker.getName());

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
    buttonPanel.add(growMushroomButton);
    buttonPanel.add(growYarnButton);
    buttonPanel.add(disperseButton);
    buttonPanel.add(skipButton);

    // === Center Panel ===
    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.add(upperScroll, BorderLayout.NORTH);
    centerPanel.add(lowerScroll, BorderLayout.CENTER);

    add(centerPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.EAST);
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

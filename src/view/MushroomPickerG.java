package view;

import controller.UpdateListener;

import javax.swing.*;
import model.MushroomPicker;

import java.awt.*;

public class MushroomPickerG extends JComponent implements UpdateListener {
  public MushroomPicker getMushroomPicker() {
    return mushroomPicker;
  }

  public void setMushroomPicker(MushroomPicker mushroomPicker) {
    this.mushroomPicker = mushroomPicker;
  }

  private MushroomPicker mushroomPicker;
    JLabel nameLabel = new JLabel();
    JButton growYarnButton=new JButton("Grow Yarn");
    JButton growMushroomButton=new JButton("Grow Mushroom");
    JButton disperseButton=new JButton("Disperse Spore");
    JButton skipButton=new JButton("Skip");

   public MushroomPickerG(MushroomPicker mushroomPicker){
     this.mushroomPicker = mushroomPicker;
     nameLabel.setText(mushroomPicker.getName());
     //this.mushroomPicker = new MushroomPicker("semmi", "semmi");
     this.setLayout(new BorderLayout());

     // Title label
     nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
     nameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
     this.add(nameLabel, BorderLayout.NORTH);

     // Start button centered
     growMushroomButton.setPreferredSize(new Dimension(160, 80));
     disperseButton.setPreferredSize(new Dimension(100, 40));
     growYarnButton.setPreferredSize(new Dimension(100, 40));
     skipButton.setPreferredSize(new Dimension(100,40));
     // Create a vertical panel for the buttons

    // === Horizontal scrollable tecton panel ===
     JPanel tectonContainer = new JPanel();
     tectonContainer.setLayout(new BoxLayout(tectonContainer, BoxLayout.X_AXIS));

    // Add TectonG for each owned mushroom's place
     mushroomPicker.getOwnedMushrooms().forEach(mushroom -> {
       tectonContainer.add(new TectonG(mushroom.getTecton()));
       tectonContainer.add(Box.createHorizontalStrut(10)); // spacing
     });

    // Add TectonG for each tecton in every yarn
     mushroomPicker.getOwnedYarns().forEach(yarn -> {
       yarn.getTectons().forEach(tecton -> {
         tectonContainer.add(new TectonG(tecton));
         tectonContainer.add(Box.createHorizontalStrut(10));
       });
     });

    // Scroll pane for tectonContainer
     JScrollPane scrollPane = new JScrollPane(tectonContainer);
     scrollPane.setPreferredSize(new Dimension(500, 120));
     scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
     scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
     scrollPane.setBorder(BorderFactory.createTitledBorder("Tectons"));

    // Add to the bottom of the main panel
     this.add(scrollPane, BorderLayout.SOUTH);


     JPanel centerPanel = new JPanel();
     centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
     centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Optional padding

     growMushroomButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
     growYarnButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
     skipButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
     disperseButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

     centerPanel.add(growMushroomButton);
     centerPanel.add(Box.createVerticalStrut(10));
     centerPanel.add(growYarnButton);
     centerPanel.add(Box.createVerticalStrut(10));
     centerPanel.add(skipButton);
     centerPanel.add(Box.createVerticalStrut(10));
     centerPanel.add(disperseButton);

// Add the button panel to the right side
     this.add(centerPanel, BorderLayout.EAST);

   }

   public MushroomPickerG() {

   }
    public void update(){}
}

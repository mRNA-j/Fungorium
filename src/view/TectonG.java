package view;

import model.Tecton;
import model.Insect;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TectonG extends JComponent implements BaseViewG {
    int x, y, radius;
    String id;
    Tecton t;
    boolean hasMushroom;
    boolean hasInsect;
    boolean hasYarn;
    int sporeCount;
    private List<InsectG> insectGs;

    public TectonG(int x, int y, int radius, String id, Tecton t) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.id = id;
        this.t = t;
        hasMushroom = t.getMushroom() != null;
        hasInsect = !t.getInsects().isEmpty();
        hasYarn = !t.getYarns().isEmpty();
        sporeCount = t.getSpores().size();

        t.addObserver(this);


        // Create InsectG instances for all insects
        createInsectGs();
    }

    private void createInsectGs() {
        insectGs = new ArrayList<>();
        if (hasInsect) {
            List<Insect> insects = t.getInsects();
            for (Insect insect : insects) {
                InsectG insectG = new InsectG(insect);
                insectG.setSize(20, 20);
                insectGs.add(insectG);
            }
        }
    }

    public boolean contains(Point p) {
        return p.distance(x, y) <= radius;
    }

    public void draw(Graphics g, boolean selected) {
        // Background color
        if (selected) {
            g.setColor(new Color(255, 150, 150)); // Red
        } else if (hasInsect) {
            g.setColor(Color.CYAN); // Blue
        } else {
            g.setColor(new Color(180, 255, 180)); // Light green
        }
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        g.setColor(Color.BLACK);
        g.drawOval(x - radius, y - radius, radius * 2, radius * 2);

        // Spore count
        g.drawString(sporeCount > 0 ? "+" + sporeCount : "0", x - 5, y + 5);

        // Mushroom marker
        if (hasMushroom) {
            g.drawString("+", x - 3, y - radius - 10);
        }

        // Yarn indicator
        if (hasYarn) {
            //g.fillRect(x + radius - 10, y + radius - 10, 5, 5);
            g.drawString("*", x-10, y - radius - 10);
        }

        // Tecton ID
        g.drawString(id, x - 5, y + radius + 15);

        // Draw all insects to the left of the tecton in a vertical column
        drawInsects(g);
    }

    private void drawInsects(Graphics g) {
        if (!hasInsect || t.getInsects().isEmpty()) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g.create();

        int insectSize = 20;
        int spacing = 5;

        // Get insects directly from the model to ensure we have the latest
        List<Insect> insects = t.getInsects();

        // Draw each insect using the corresponding InsectG for correct icon selection
        for (int i = 0; i < insects.size() && i < insectGs.size(); i++) {
            Insect insect = insects.get(i);
            InsectG insectG = insectGs.get(i);

            // Calculate position - directly to the left of the tecton
            int insectX = x + radius - 5; // 5 pixels spacing from the tecton
            int insectY = y - radius + (i * (insectSize + spacing));

            // Draw a debug rectangle around the insect area
            g2d.setColor(Color.RED);
            g2d.drawRect(insectX, insectY, insectSize, insectSize);

            // Create temporary ImageIcon using the same logic as InsectG.getCurrentIcon()
            ImageIcon icon = getInsectIcon(insect);

            // Draw the insect with the appropriate icon based on its current effect
            if (icon != null) {
                icon.paintIcon(this, g2d, insectX, insectY);
            }
        }

        g2d.dispose();
    }

    /**
     * Returns the appropriate icon for an insect based on its current effect.
     * This method follows the same logic as InsectG.getCurrentIcon()
     *
     * @param insect The insect to get the icon for
     * @return The appropriate ImageIcon based on the insect's current effect
     */
    private ImageIcon getInsectIcon(Insect insect) {
        String effect = insect.getCurrentEffect();
        int iconSize = 20; // Standard size for insect icons in this context

        if (effect == null) {
            return ImageLoader.load("/view/images/insect_default.png", iconSize, iconSize);
        }

        switch (effect) {
            case "accelerated":
                return ImageLoader.load("/view/images/insect_accelerated.png", iconSize, iconSize);
            case "paralyzed":
                return ImageLoader.load("/view/images/insect_paralyzed.png", iconSize, iconSize);
            case "decelerated":
                return ImageLoader.load("/view/images/insect_decelerated.png", iconSize, iconSize);
            case "cutPrevented":
                return ImageLoader.load("/view/images/insect_cut_prevented.png", iconSize, iconSize);
            default:
                return ImageLoader.load("/view/images/insect_default.png", iconSize, iconSize);
        }
    }

    @Override
    public void update() {
        // Update view state from model
        hasMushroom = t.getMushroom() != null;
        hasInsect = !t.getInsects().isEmpty();
        hasYarn = !t.getYarns().isEmpty();
        sporeCount = t.getSpores().size();

        // Update or recreate InsectG instances
        if (hasInsect) {
            List<Insect> currentInsects = t.getInsects();

            // Check if we need to recreate the insect graphics
            boolean needsRecreation = insectGs == null || insectGs.size() != currentInsects.size();

            if (needsRecreation) {
                // Recreate all insect graphics
                createInsectGs();
            } else {
                // Just update the existing ones
                for (InsectG insectG : insectGs) {
                    insectG.update();
                }
            }
        } else {
            // No insects, clear the list
            if (insectGs != null) {
                insectGs.clear();
            }
        }

        // Request repaint to reflect updated state
        SwingUtilities.invokeLater(() -> {
            revalidate();
            repaint();
        });
    }
}
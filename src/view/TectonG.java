package view;

import model.Tecton;
import model.Insect;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A TectonG osztály a Tecton modell grafikus megjelenítéséért felelős.
 * Megjeleníti a tecton állapotát, beleértve a rajta lévő gombákat, rovarokat,
 * fonalakat és spórákat.
 * Implementálja a BaseViewG interfészt, így a modell változásakor automatikusan frissül.
 */
public class TectonG extends JComponent implements BaseViewG {
    /** A tecton x koordinátája a megjelenítésben */
    int x;
    
    /** A tecton y koordinátája a megjelenítésben */
    int y;
    
    /** A tecton sugarának mérete */
    int radius;
    
    /** A tecton azonosítója */
    String id;
    
    /** A megjelenített tecton modell */
    Tecton t;
    
    /** Jelzi, hogy van-e gomba a tectonon */
    boolean hasMushroom;
    
    /** Jelzi, hogy van-e rovar a tectonon */
    boolean hasInsect;
    
    /** Jelzi, hogy van-e fonal a tectonon */
    boolean hasYarn;
    
    /** A tectonon lévő spórák száma */
    int sporeCount;
    
    /** A tectonon lévő rovarok grafikus megjelenítései */
    private List<InsectG> insectGs;

    /**
     * A TectonG osztály konstruktora.
     * Inicializálja a tecton grafikus megjelenítését és beállítja a megfigyelőt.
     * 
     * @param x A tecton x koordinátája
     * @param y A tecton y koordinátája
     * @param radius A tecton sugara
     * @param id A tecton azonosítója
     * @param t A megjelenítendő tecton modell
     */
    public TectonG(int x, int y, int radius, String id, Tecton t) {

        this.x = x;
        this.y = y;
        this.radius = radius;
        this.id = id;
        this.t = t;
        this.t.addObserver(this);

        hasMushroom = t.getMushroom() != null;
        hasInsect = !t.getInsects().isEmpty();
        hasYarn = !t.getYarns().isEmpty();
        sporeCount = t.getSpores().size();

        // Create InsectG instances for all insects
        createInsectGs();
    }

    /**
     * Létrehozza a rovarok grafikus megjelenítéseit.
     * Minden rovarhoz létrehoz egy InsectG példányt.
     */
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

    /**
     * Ellenőrzi, hogy a megadott pont a tectonon belül van-e.
     * 
     * @param p Az ellenőrizendő pont
     * @return Igaz, ha a pont a tectonon belül van, egyébként hamis
     */
    public boolean contains(Point p) {
        return p.distance(x, y) <= radius;
    }

    /**
     * Kirajzolja a tectont a megadott grafikus kontextusra.
     * Megjeleníti a tecton állapotát, beleértve a rajta lévő gombákat, rovarokat,
     * fonalakat és spórákat.
     * 
     * @param g A grafikus kontextus
     * @param selected Jelzi, hogy a tecton ki van-e választva
     */
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
        g.drawString(id, x - radius, y + radius + 15);

        // Draw all insects to the left of the tecton in a vertical column
        drawInsects(g);
    }

    /**
     * Kirajzolja a tectonon lévő rovarokat.
     * 
     * @param g A grafikus kontextus
     */
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
     * Visszaadja a megfelelő ikont egy rovarhoz annak aktuális hatása alapján.
     * 
     * @param insect A rovar, amelyhez az ikont kérjük
     * @return A megfelelő ikon a rovar aktuális hatása alapján
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

    /**
     * Frissíti a tecton grafikus megjelenítését a modell változásai alapján.
     * Implementálja a BaseViewG interfész update metódusát.
     */
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

package view;
import model.Tecton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    }

    public boolean contains(Point p) {
        return p.distance(x, y) <= radius;
    }

    public void draw(Graphics g, boolean selected) {
        // Background color
        if (selected) {
            g.setColor(new Color(255, 150, 150));  // Red
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
            g.fillRect(x + radius - 10, y + radius - 10, 5, 5);
        }

        // Tecton ID
        g.drawString(id, x - 5, y + radius + 15);
    }

    @Override
    public void update() {
        // Update view state from model
        hasMushroom = t.getMushroom() != null;
        hasInsect = !t.getInsects().isEmpty();
        hasYarn = !t.getYarns().isEmpty();
        sporeCount = t.getSpores().size();

        // Request repaint to reflect updated state
        repaint();
    }
}






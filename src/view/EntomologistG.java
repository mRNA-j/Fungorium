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

    private final List<TectonCircle> upperTectons = new ArrayList<>();
    private final List<TectonCircle> lowerTectons = new ArrayList<>();

    private TectonCircle selectedTecton = null;

    private TectonPanel upperStrip;
    private TectonPanel lowerStrip;

    private JScrollPane upperScroll;
    private JScrollPane lowerScroll;

    public EntomologistG(Entomologist e) {
        this.entomologist = e;
        nameLabel.setText(entomologist.getName() + " - " + entomologist.getPoints());

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
        for (int i = 0; i < e.getInsect().size(); i++) {
            Insect insect = e.getInsect().get(i);
            String id = insect.getId();
            upperTectons.add(new TectonCircle(i * 80 + 10, 40, 30, id, insect.getCurrentPlace()));
        }

        // Initialize TectonPanels
        upperStrip = new TectonPanel(upperTectons, true);
        lowerStrip = new TectonPanel(lowerTectons, false);

        upperScroll = new JScrollPane(upperStrip);
        lowerScroll = new JScrollPane(lowerStrip);
        upperScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        upperScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        lowerScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        lowerScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonPanel.add(eatButton);
        buttonPanel.add(moveButton);
        buttonPanel.add(cutButton);
        buttonPanel.add(skipButton);

        // Center panel for tecton views
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(upperScroll, BorderLayout.NORTH);
        centerPanel.add(lowerScroll, BorderLayout.CENTER);

        // Add to main layout
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    public EntomologistG() {

    }

    @Override
    public void update() {
        repaint();
    }

    private void updateLowerTectons(Tecton selectedTecton) {
        lowerTectons.clear();
        List<Tecton> neighbors = selectedTecton.getNeighbours();

        for (int i = 0; i < neighbors.size(); i++) {
            Tecton neighbor = neighbors.get(i);
            lowerTectons.add(new TectonCircle(i * 80 + 10, 40, 30, neighbor.getId(), neighbor));
        }

        lowerStrip.repaint();
    }

    private class TectonPanel extends JPanel {
        private final List<TectonCircle> tectons;
        private final boolean selectable;

        public TectonPanel(List<TectonCircle> tectons, boolean selectable) {
            this.tectons = tectons;
            this.selectable = selectable;
            setPreferredSize(new Dimension(Math.max(tectons.size() * 80, 400), 100));
            setBackground(Color.WHITE);

            if (selectable) {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        for (TectonCircle circle : tectons) {
                            if (circle.contains(e.getPoint())) {
                                if (selectedTecton == circle) {
                                    selectedTecton = null;
                                    lowerTectons.clear();
                                } else {
                                    selectedTecton = circle;
                                    updateLowerTectons(circle.t);
                                }
                                repaint();
                                lowerStrip.repaint();
                                break;
                            }
                        }
                    }
                });
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (TectonCircle circle : tectons) {
                circle.draw(g, circle == selectedTecton);
            }
        }
    }

    private static class TectonCircle {
        int x, y, radius;
        String id;
        Tecton t;
        boolean hasMushroom;
        boolean hasInsect;
        boolean hasYarn;
        int sporeCount;

        public TectonCircle(int x, int y, int radius, String id, Tecton t) {
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
    }
}

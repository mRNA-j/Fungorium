package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

import model.Insect;

/**
 * A rovar grafikus megjelenítéséért felelős osztály.
 * Az InsectG osztály egy JComponent, amely megjeleníti a rovar aktuális állapotát
 * a különböző spóra effektusoknak megfelelően.
 */
public class InsectG extends JComponent implements BaseViewG {
    private Insect insect;
    private int height;
    private int width;
    private ImageIcon defaultIcon;
    private ImageIcon acceleratedIcon;
    private ImageIcon paralyzedIcon;
    private ImageIcon deceleratedIcon;
    private ImageIcon cutPreventedIcon;

    /**
     * Konstruktor, amely inicializálja a rovar grafikus komponensét.
     *
     * @param i A rovar modell objektum, amelynek a megjelenítéséért ez az osztály felelős
     */
    public InsectG(Insect i) {
        this.insect = i;
        this.height = 30; // alapértelmezett magasság
        this.width = 30;  // alapértelmezett szélesség

        // Ikonok betöltése
        loadIcons();

        // Komponens méretének beállítása
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));

        // Átlátszóság beállítása
        setOpaque(false);

        // Kezdeti megjelenítés frissítése
        update();
    }

    /**
     * Az ikonok betöltése az ImageLoader segítségével
     */
    private void loadIcons() {
        defaultIcon      = ImageLoader.load("/view/images/insect_default.png",      width, height);
        acceleratedIcon  = ImageLoader.load("/view/images/insect_accelerated.png",  width, height);
        paralyzedIcon    = ImageLoader.load("/view/images/insect_paralyzed.png",    width, height);
        deceleratedIcon  = ImageLoader.load("/view/images/insect_decelerated.png",  width, height);
        cutPreventedIcon = ImageLoader.load("/view/images/insect_cut_prevented.png",width, height);
    }

    /**
     * A rovar aktuális állapotának megfelelő ikont adja vissza
     *
     * @return A megfelelő ImageIcon az aktuális effektus alapján
     */
    private ImageIcon getCurrentIcon() {
        String effect = insect.getCurrentEffect();

        if (effect == null) {
            return defaultIcon;
        }

        switch (effect) {
            case "accelerated":
                return acceleratedIcon;
            case "paralyzed":
                return paralyzedIcon;
            case "decelerated":
                return deceleratedIcon;
            case "cutPrevented":
                return cutPreventedIcon;
            default:
                return defaultIcon;
        }
    }

    /**
     * A komponens kirajzolásáért felelős metódus.
     * A rovar aktuális állapotának megfelelő képet rajzolja ki.
     *
     * @param g Graphics objektum, amelyre rajzolunk
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        ImageIcon icon = getCurrentIcon();

        if (icon != null) {
            icon.paintIcon(this, g2d, 0, 0);
        }

        g2d.dispose();
    }

    /**
     * A komponens méretét állítja be.
     *
     * @param width Az új szélesség
     * @param height Az új magasság
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;

        // Újratöltjük az ikonokat az új mérettel
        loadIcons();

        // Frissítjük a komponens méretét
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));

        repaint();
    }

    /**
     * A BaseViewG interfész update metódusának implementációja.
     * Frissíti és újrarajzolja a komponenst a rovar aktuális állapota alapján.
     */
    @Override
    public void update() {
        repaint();
    }
}
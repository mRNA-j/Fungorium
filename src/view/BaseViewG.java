package view;

import controller.UpdateListener;

import javax.swing.*;

/**
 * Alap osztály a grafikus komponensek számára.
 * Minden G-re végződő nézet osztály ebből származik.
 * Implementálja az UpdateListener interfészt, így a modell változásakor
 * automatikusan frissül a megjelenítés.
 */
public abstract class BaseViewG extends JPanel {
    
    /**
     * Konstruktor a BaseViewG osztályhoz.
     */
    public BaseViewG() {
        super();
    }
    
    /**
     * Az UpdateListener interfész update metódusának implementációja.
     * Alapértelmezetten meghívja a repaint() metódust, hogy újrarajzolja a komponenst.
     */
    public void update() {
        repaint();
    }
}
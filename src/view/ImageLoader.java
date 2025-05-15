package view;

import java.awt.Image;
import javax.swing.*;

/**
 * Segédosztály képek betöltésére és átméretezésére.
 * Ez az osztály egyszerűsíti a képek betöltését és átméretezését a program különböző komponensei számára.
 */
public class ImageLoader {

    /**
     * Betölt egy képet a megadott elérési útról és átméretezi a megadott méretekre.
     *
     * @param path A kép elérési útja (relatív vagy abszolút)
     * @param width A kívánt szélesség
     * @param height A kívánt magasság
     * @return Az átméretezett képet tartalmazó ImageIcon
     */
    public static ImageIcon load(String path, int width, int height) {
        // Kép betöltése
        ImageIcon icon = new ImageIcon(ImageLoader.class.getResource(path));

        // Kép átméretezése
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Új ImageIcon létrehozása az átméretezett képből
        return new ImageIcon(newImg);
    }
}
package view;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.File;

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
        try {
            // Kérjük le a projekt könyvtárat
            String projectDir = System.getProperty("user.dir");

            // Tisztítsuk meg az elérési útvonalat
            String cleanPath = path;
            if (cleanPath.startsWith("/")) {
                cleanPath = cleanPath.substring(1);
            }

            // Hozzuk létre a teljes elérési utat
            File imageFile = new File(projectDir + "/src/" + cleanPath);

            // Ellenőrizzük, hogy létezik-e a fájl
            if (!imageFile.exists()) {
                System.err.println("Nem található kép: " + imageFile.getAbsolutePath());
                return createPlaceholder(width, height);
            }

            // Kép betöltése
            ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());

            // Kép átméretezése
            Image img = icon.getImage();
            Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            // Új ImageIcon létrehozása az átméretezett képből
            return new ImageIcon(newImg);
        } catch (Exception e) {
            System.err.println("Hiba a kép betöltése során: " + path);
            e.printStackTrace();
            return createPlaceholder(width, height);
        }
    }

    /**
     * Létrehoz egy üres placeholder képet hiba esetére
     */
    private static ImageIcon createPlaceholder(int width, int height) {
        BufferedImage placeholder = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // Rajzoljunk egy egyszerű "X"-et a placeholderre
        java.awt.Graphics2D g2d = placeholder.createGraphics();
        g2d.setColor(java.awt.Color.RED);
        g2d.drawLine(0, 0, width, height);
        g2d.drawLine(0, height, width, 0);
        g2d.dispose();
        return new ImageIcon(placeholder);
    }
}
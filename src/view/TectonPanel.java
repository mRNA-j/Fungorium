    package view;

    import model.Tecton;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.util.List;

    /**
     * A TectonPanel osztály a tectonok megjelenítésére szolgáló panel.
     * Megjeleníti a tectonok listáját vízszintes sorban, és lehetővé teszi
     * a tectonok kiválasztását.
     * Implementálja a BaseViewG interfészt, így a modell változásakor automatikusan frissül.
     */
    public class TectonPanel extends JPanel implements BaseViewG{
        /** A megjelenítendő tectonok listája */
        private final List<TectonG> tectons;
        
        /** Jelzi, hogy a tectonok kiválaszthatók-e */
        private final boolean selectable;
        
        /** A jelenleg kiválasztott tecton */
        private TectonG selectedTecton = null;
        
        /** A tecton kiválasztását kezelő listener */
        private final TectonSelectionListener selectionListener;
        
        /** A tectonok közötti távolság pixelben */
        private final int spacing = 80;

        /**
         * Frissíti a panel méretét és megjelenítését a tectonok listája alapján.
         * Implementálja a BaseViewG interfész update metódusát.
         */
        @Override
        public void update() {

            // Update panel size based on current tectons list
            setPreferredSize(new Dimension(Math.max(tectons.size() * 80, 400), 100));

            // Request repaint to reflect updated state
            SwingUtilities.invokeLater(() -> {            
                revalidate();
                repaint();
            });

        }

        /**
         * Interfész a tecton kiválasztásának kezeléséhez.
         * A tecton kiválasztásakor meghívódik az onTectonSelected metódus.
         */
        public interface TectonSelectionListener {
            /**
             * Meghívódik, amikor egy tectont kiválasztanak.
             * 
             * @param selected A kiválasztott tecton
             */
            void onTectonSelected(Tecton selected);
        }

        /**
         * A TectonPanel konstruktora.
         * Inicializálja a panelt, beállítja a tectonok listáját és a kiválasztás kezelőjét.
         * 
         * @param tectons A megjelenítendő tectonok listája
         * @param selectable Jelzi, hogy a tectonok kiválaszthatók-e
         * @param listener A tecton kiválasztását kezelő listener
         */
        public TectonPanel(List<TectonG> tectons, boolean selectable, TectonSelectionListener listener) {
            this.tectons = tectons;
            this.selectable = selectable;
            this.selectionListener = listener;

            setPreferredSize(new Dimension(Math.max(tectons.size() * 80, 400), 100));
            setBackground(Color.WHITE);

            if (selectable) {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        for (TectonG circle : tectons) {
                            if (circle.contains(e.getPoint())) {
                                if (selectedTecton == circle) {
                                    selectedTecton = null;
                                } else {
                                    selectedTecton = circle;
                                }
                                SwingUtilities.invokeLater(() -> {
                                    repaint();
                                });

                                if (selectionListener != null && selectedTecton != null) {
                                    selectionListener.onTectonSelected(selectedTecton.t);
                                }
                                break;
                            }
                        }
                    }
                });
            }
        }

        /**
         * Visszaadja a jelenleg kiválasztott tectont.
         * 
         * @return A kiválasztott tecton, vagy null, ha nincs kiválasztva tecton
         */
        public TectonG getSelectedTecton() {
            return selectedTecton;
        }

        /**
         * Kirajzolja a panel tartalmát, beleértve a tectonokat.
         * 
         * @param g A grafikus kontextus
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int totalWidth = tectons.size() * spacing;
            int startX = 40; // Fixed left padding
            int yPos = getHeight() / 2; // Keep vertical centering

            for (int i = 0; i < tectons.size(); i++) {
                TectonG tecton = tectons.get(i);
                tecton.x = startX + i * 80; // 80px spacing between circles
                tecton.y = yPos;
                tecton.draw(g, tecton == selectedTecton);
            }
        }

        /**
         * Visszaadja a panel preferált méretét a tectonok száma alapján.
         * 
         * @return A panel preferált mérete
         */
        @Override
        public Dimension getPreferredSize() {
            int width = Math.max(tectons.size() * 80, 400);
            return new Dimension(width, 100); // Fixed height for all tecton panels
        }
    }

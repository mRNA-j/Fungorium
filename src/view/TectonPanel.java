    package view;

    import model.Tecton;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.util.List;

    public class TectonPanel extends JPanel implements BaseViewG{
        private final List<TectonG> tectons;
        private final boolean selectable;
        private TectonG selectedTecton = null;
        private final TectonSelectionListener selectionListener;
        private final int spacing = 80;



        @Override
        public void update() {

            // Update panel size based on current tectons list
            setPreferredSize(new Dimension(Math.max(tectons.size() * 80, 400), 100));

            // Request repaint to reflect updated state
            revalidate();
            repaint();
        }

        public interface TectonSelectionListener {
            void onTectonSelected(Tecton selected);
        }

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
                                repaint();

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

        public TectonG getSelectedTecton() {
            return selectedTecton;
        }

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

        @Override
        public Dimension getPreferredSize() {
            int width = Math.max(tectons.size() * 80, 400);
            return new Dimension(width, 100); // Fixed height for all tecton panels
        }
    }

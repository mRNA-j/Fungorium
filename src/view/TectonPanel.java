    package view;

    import model.Tecton;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.util.List;

    public class TectonPanel extends JPanel {
        private final List<TectonG> tectons;
        private final boolean selectable;
        private TectonG selectedTecton = null;
        private final TectonSelectionListener selectionListener;

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
            for (TectonG circle : tectons) {
                circle.draw(g, circle == selectedTecton);
            }
        }
    }

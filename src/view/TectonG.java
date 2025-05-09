package view;

import controller.UpdateListener;
import model.Tecton;

import javax.swing.*;
import java.awt.*;

public class TectonG extends JComponent implements UpdateListener {

    private int height;
    private int width;
    private Tecton tecton;

    public TectonG(Tecton tecton) {}
    @Override
    public void update(){}

    public void draw(Graphics g){}
}

package view;

import controller.UpdateListener;
import model.Entomologist;

import javax.swing.*;

public class EntomologistG extends JComponent implements UpdateListener {
    private Entomologist entomologist;
    JButton eatButton=new JButton("eat Spore");
    JButton moveButton=new JButton("move Insect");
    JButton cutButton=new JButton("cut Yarn");
    JButton skipButton=new JButton("Skip");

    public EntomologistG( Entomologist e){}



    @Override
    public void update() {

    }
}

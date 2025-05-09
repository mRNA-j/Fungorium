package view;

import model.MushroomPicker;

import javax.swing.*;


public class StartGamePanel extends JPanel {
    JButton startButton=new JButton("Start");
    JButton backButton=new JButton("Back");
    JTextField[] inputs=new JTextField[4];
    JLabel[] labels=new JLabel[4];
    static String[] labelNames={"MushroomPicker1","MushroomPicker2","Entomologist1","Entomologist2"};

    //TODO
    StartGamePanel() {
        this.add(startButton);
        this.add(backButton);
        for(int i=0;i<4;i++){
            inputs[i]=new JTextField();
            labels[i]=new JLabel();
            labels[i].setHorizontalAlignment(JLabel.LEFT);
            labels[i].setText(labelNames[i]);
        }
    }

    public JButton getBackButton() {
        return backButton;
    }

}

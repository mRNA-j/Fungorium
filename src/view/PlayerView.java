package view;
import java.io.File;
import java.io.Serializable;

public  abstract class PlayerView implements View, Serializable {
    public void printObject(){}
    public void printToFile(File f){}
}

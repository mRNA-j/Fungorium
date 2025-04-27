package view;
import java.io.File;
import java.io.Serializable;

/**
 * A nézetek ősosztálya
 */
public  abstract class PlayerView implements View, Serializable {
    /**
     * A Model konzolra való kiírásáért felelős függvény. Minden leszármazott felüldefiniálja.
     */
    public void printObject(){}
}
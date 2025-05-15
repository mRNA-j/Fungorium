package interfaces;

import model.Tecton;
import view.TectonG;

public interface ITectonGFactory {
    TectonG  onCreate(int x, int y, int radius, String id, Tecton t);
}

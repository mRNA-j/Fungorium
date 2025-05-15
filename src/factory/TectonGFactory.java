package factory;

import model.Tecton;
import view.TectonG;
import interfaces.ITectonGFactory;

public class TectonGFactory implements ITectonGFactory{

    @Override
    public TectonG onCreate(int x, int y, int radius, String id, Tecton t) {
        { return new TectonG(x, y, radius, id, t); }
    }
}

package model;

import view.TectonG;

public class JFactory implements IFactory{

    @Override
    public TectonG onCreateTectonG(int x, int y, int radius, String id, Tecton t) {
        { return new TectonG(x, y, radius, id, t); }
    }


}

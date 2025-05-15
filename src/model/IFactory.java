package model;

import view.TectonG;

public interface IFactory {
    TectonG  onCreateTectonG(int x, int y, int radius, String id, Tecton t);
}

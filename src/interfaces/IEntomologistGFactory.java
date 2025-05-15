package interfaces;

import model.Entomologist;
import view.EntomologistG;

public interface IEntomologistGFactory {
    EntomologistG onCreate(Entomologist entomologist, String nextPanelName);
}
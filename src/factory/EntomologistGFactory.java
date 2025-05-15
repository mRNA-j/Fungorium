package factory;

import controller.Controller;
import model.Entomologist;
import view.EntomologistG;
import interfaces.IEntomologistGFactory;

public class EntomologistGFactory implements IEntomologistGFactory {
    
    @Override
    public EntomologistG onCreate(Entomologist entomologist, String nextPanelName, Controller controller) {
        return new EntomologistG(entomologist, nextPanelName, controller);
    }
}
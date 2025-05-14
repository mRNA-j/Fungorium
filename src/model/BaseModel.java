package model;

import view.BaseViewG;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Az Observer minta alapjául szolgáló BaseModel osztály.
 * Minden modell osztály ebből származik le.
 */
public abstract class BaseModel implements Serializable {
    private List<BaseViewG> observers = new ArrayList<>();
    
    /**
     * Új megfigyelő hozzáadása
     * @param observer A hozzáadandó megfigyelő
     */
    public void addObserver(BaseViewG observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    /**
     * Megfigyelő eltávolítása
     * @param observer Az eltávolítandó megfigyelő
     */
    public void removeObserver(BaseViewG observer) {
        observers.remove(observer);
    }
    
    /**
     * Értesíti az összes megfigyelőt a változásról
     */
    protected void notifyObservers() {
        for (BaseViewG observer : observers) {
            observer.update();
        }
    }
}
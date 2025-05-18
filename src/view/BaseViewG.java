package view;

/**
 * Alap interfész a grafikus komponensek számára.
 * Minden G-re végződő nézet osztály ezt az interfészt implementálja.
 * Lehetővé teszi a modell változásainak követését és a nézet frissítését.
 */
public interface BaseViewG {
    /**
     * Frissíti a nézetet a modell változásai alapján.
     * Ezt a metódust a modell hívja meg, amikor változás történik.
     */
    public void update();
}

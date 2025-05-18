package controller;

import model.*;

import java.util.*;

public class Controller {
    /**
     * Visszaadja a játék objektumot.
     * @return A játék objektum.
     */
    public Game getGame() {
        return game;
    }

    private final Game game;

    private final Map map;

    /**
     * Konstruktor a Controller osztályhoz.
     * Inicializálja a játékot a megadott játékosokkal és beállítja a játékteret.
     * 
     * @param players A játékban résztvevő játékosok listája.
     */
    public Controller(ArrayList<Player> players) {
        game = new Game(players);
        map = game.getPlayField();
    }

    /**
     * Beállítja a következő aktív játékost a játékban.
     * A játékosok sorrendje: MP1, ENT1, MP2, ENT2.
     * Ha a kör végére értünk, új kört indít.
     */
    public void setNextActivePlayer() {
        int currentTurn = game.getCurrentTurn();
        int nextTurn = (currentTurn + 1) % 4;  // Ez biztosítja hogy 0,1,2,3,0,1,2,3...
        game.setCurrentTurn(nextTurn);

        currentTurn = game.getCurrentTurn();


        if (currentTurn == 1) {
            game.setActivePlayer(game.getMps().get(0));
        } else if (currentTurn == 2) {
            game.setActivePlayer(game.getEnts().get(0));
        } else if (currentTurn == 3) {
            game.setActivePlayer(game.getMps().get(1));
        } else if (currentTurn == 0) {
            game.setActivePlayer(game.getEnts().get(1));
        }

        System.out.println("JÁTÉKOSVÁLTÁS: Kör " + game.getCurrentFullTurn() + " - " + game.getActivePlayer().getName() + " következik");

        if(currentTurn %4==1) {
            next_round();
        }
    }

    /**
     * Elindítja a játékot.
     * Inicializálja a játék kezdeti állapotát.
     */
    public void start() {}

    /**
     * Befejezi az aktuális játékos körét.
     * Ha az utolsó játékos fejezte be a körét, új kört indít.
     */
    public void action_phase_end() {
        if(game.getActivePlayerIndex() == 3) {
            game.nextTurn();
        } else {
            game.nextPlayer();
        }
    }

    /**
     * Új kört indít a játékban.
     * 5% eséllyel véletlenszerűen kettéhasít egy tektont.
     * Csökkenti az összes rovar effektusának időtartamát.
     */
    public void next_round() {
        Random random = new Random();
        int index = random.nextInt(0,map.getTectons().size());
        Tecton potentialSplitTecton = map.getTectons().get(index);

        int randNum = random.nextInt(0,100);

        if(randNum<5) {
            map.splitting(potentialSplitTecton);
        }

        // Csökkenti az összes rovar effektusának időtartamát
        decreaseAllInsectEffectDuration();
        game.nextTurn();
    }

    /**
     * Csökkenti az összes rovar effektusának időtartamát a játékban.
     * Ezt minden kör végén meg kell hívni.
     */
    private void decreaseAllInsectEffectDuration() {
        List<Tecton> tectons = game.getPlayField().getTectons();
        int insectCount = 0;
        for (Tecton tecton : tectons) {
            insectCount += tecton.getInsects().size();
            for (Insect insect : tecton.getInsects()) {
                insect.decrementEffectDuration();
            }
        }
    }

    /**
     * Végrehajtja a spóra szórás akciót a megadott célpontra.
     * 
     * @param targetTecton A célpont tekton, ahová a spórát szórjuk.
     * @param mushroom A gomba, amelyből a spórát szórjuk.
     * @param type A spóra típusa.
     * @param id A spóra azonosítója.
     */
    public void action_spore_dispersion(Tecton targetTecton ,Mushroom mushroom, String type, String id) {
        MushroomPicker mp = (MushroomPicker) game.getActivePlayer();
        mp.actionSporeDispersion(targetTecton, mushroom, type, id);
    }

    /**
     * Végrehajtja a gombafonal növesztés akciót.
     * 
     * @param fromTecton A kiindulási tekton.
     * @param targetTecton A célpont tekton, ahová a fonalat növesztjük.
     * @param yarn A növesztendő gombafonal.
     */
    public void action_grow_yarn(Tecton fromTecton ,Tecton targetTecton, Yarn yarn) {
        MushroomPicker mushroomPicker = (MushroomPicker) game.getActivePlayer();
        mushroomPicker.actionGrowYarn(fromTecton, targetTecton, yarn); //Luca: itt nekünk csak a céltekton kell bemenetnek sztem
    }

    /**
     * Végrehajtja a gomba növesztés akciót a megadott tektonon.
     * 
     * @param targetTecton A célpont tekton, ahová a gombát növesztjük.
     * @param id A gomba azonosítója.
     */
    public void action_grow_mushroom(Tecton targetTecton, String mushroomId, String yarnType, String yarnId) {
        MushroomPicker mushroomPicker =(MushroomPicker) game.getActivePlayer();
        mushroomPicker.actionGrowMushroom(targetTecton, mushroomId, yarnType, yarnId);
    }

    /**
     * Végrehajtja a fonalvágás akciót a megadott rovarral.
     * 
     * @param insect A rovar, amellyel a fonalat vágjuk.
     * @param yarn A vágandó gombafonal.
     * @param amerreVagunk A tekton, amerre a vágás történik.
     */
    public void action_cut_yarn(Insect insect, Yarn yarn, Tecton amerreVagunk) {
        Entomologist entomologist = (Entomologist) game.getActivePlayer();
        entomologist.actionCutYarn(yarn, insect, amerreVagunk);
    }

    /**
     * Végrehajtja a spóra evés akciót a megadott rovarral.
     * 
     * @param spore A megevendő spóra.
     * @param insect A rovar, amelyik megeszi a spórát.
     */
    public void action_eat_spore(Spore spore, Insect insect) {
        Entomologist entomologist = (Entomologist)game.getActivePlayer();
        entomologist.actionEatSpore(spore, insect);
    }

    /**
     * Végrehajtja a mozgás akciót a megadott rovarral.
     * 
     * @param insect A mozgatandó rovar.
     * @param targetTecton A célpont tekton, ahová a rovar mozog.
     */
    public void action_move(Insect insect, Tecton targetTecton) {
        Entomologist entomologist = (Entomologist) game.getActivePlayer();
        entomologist.actionMove(targetTecton, insect);
    }

    /**
     * Végrehajtja a várakozás akciót a megadott rovarral.
     * A rovar ebben a körben nem csinál semmit.
     * 
     * @param insect A várakozó rovar.
     */
    public void action_wait(Insect insect) {
        Entomologist entomologist = (Entomologist)game.getActivePlayer();
        entomologist.actionWait(insect);
    }

}

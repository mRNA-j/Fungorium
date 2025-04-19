import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CmdParser {
    private static Game game = new Game();
    private static Map map = new Map();

    /**
     * Searches for a MushroomPicker player among the game players using the provided ID.
     *
     * @param id The ID of the MushroomPicker.
     * @return The MushroomPicker if found; otherwise, null.
     */
    private static MushroomPicker findMushroomPickerById(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        for (Player player : game.getPlayers()) {
            if (player.getId().equals(id)) {
                return (MushroomPicker) player;
            }
        }
        return null;
    }

    /**
     * Searches globally for a Yarn with the specified ID.
     *
     * @param yarnId The identifier of the Yarn to search for.
     * @return The Yarn object if found; otherwise, null.
     */
    private static Yarn findYarnById(String yarnId) {
        if (yarnId == null || yarnId.isEmpty()) {
            return null;
        }
        // Iterate over all tectons in the map
        for (Tecton tecton : map.getTectons()) {
            // Search within each tecton's yarn collection.
            for (Yarn yarn : tecton.getYarns()) {
                if (yarn.getId().equals(yarnId)) {
                    return yarn;
                }
            }
        }
        return null;
    }



    /**
     * Kezeli az argumentumok szamabol adodo hibakat
     *
     * @param args parancs argumentumlistaja
     * @param count szukseges argumentumszam
     * @return hibas-e az argumentumlista
     */
    private static boolean handleArgCount(String[] args, int count)
    {
        if(args.length < count)
        {
            System.out.println("Tul keves argumentum!");
            return true;
        }
        else if(args.length > count)
        {
            System.out.println("Tul sok argumentum!");
            return true;
        }

        return false;
    }
    /**
     * Finds a Tecton by its ID in the map.
     *
     * @param id The ID of the Tecton to find
     * @return The Tecton object if found, null otherwise
     */
    private static Tecton findTectonById(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }

        for (Tecton tecton : map.getTectons()) {
            if (tecton.getId().equals(id)) {
                return tecton;
            }
        }

        return null;
    }

    /**
     * Finds an Insect by its ID across all Tectons in the map.
     *
     * @param id The ID of the Insect to find
     * @return The Insect object if found, null otherwise
     */
    private static Insect findInsectById(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }

        for (Tecton tecton : map.getTectons()) {
            for (Insect insect : tecton.getInsects()) {
                if (insect.getId().equals(id)) {
                    return insect;
                }
            }
        }

        return null;
    }

    /**
     * Finds a Mushroom by its ID across all Tectons in the map.
     *
     * @param id The ID of the Mushroom to find
     * @return The Mushroom object if found, null otherwise
     */
    private static Mushroom findMushroomById(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }

        for (Tecton tecton : map.getTectons()) {
            Mushroom mushroom = tecton.getMushroom();
            if (mushroom != null && mushroom.getId().equals(id)) {
                return mushroom;
            }
        }

        return null;
    }

    /**
     * Finds an Entomologist by their name from the game's player list.
     *
     * @param name The name of the Entomologist to find
     * @return The Entomologist object if found, null otherwise
     */
    private static Player findPlayerByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        for (Player player : game.getPlayers()) {
            if (player.getName().equals(name)) {
                return  player;
            }
        }

        return null;
    }

    /**
     * Finds the Entomologist who controls a specific insect.
     *
     * @param insect The Insect to find the controller for
     * @return The Entomologist object if found, null otherwise
     */
    private static Entomologist findEntomologistByInsect(Insect insect) {
        if (insect == null) {
            return null;
        }

        for (Player player : game.getPlayers()) {
            if (player instanceof Entomologist) {
                Entomologist entomologist = (Entomologist) player;
                if (entomologist.getInsect() == insect) {
                    return entomologist;
                }
            }
        }

        return null;
    }

    /**
     * Finds the Tecton that contains a specific Insect.
     *
     * @param insect The Insect to find the Tecton for
     * @return The Tecton object if found, null otherwise
     */
    private static Tecton findTectonByInsect(Insect insect) {
        if (insect == null) {
            return null;
        }

        for (Tecton tecton : map.getTectons()) {
            if (tecton.getInsects().contains(insect)) {
                return tecton;
            }
        }

        return null;
    }


    /**
     * Értelmezi és futtatja az adott parancsot.
     * @param cmd a parancs szöveges formában.
     */
    public static void parse(String cmd) {
        String[] args = cmd.split("\\s+");
        if (args.length == 0 || args[0].isEmpty()) return;

        switch (args[0]) {
            case "create_entomologist": create_entomologist(args); break;
            case "create_mushroom_picker": create_mushroom_picker(args); break;
            case "create_mushroom": create_mushroom(args); break;
            case "create_tecton": create_tecton(args); break;
            case "create_yarn": create_yarn(args); break;
            case "create_spore_on_tecton": create_spore_on_tecton(args); break;
            case "create_insect": create_insect(args); break;
            case "add_neighbour_to_tecton": add_neighbour_to_tecton(args); break;
            case "add_insect_to_tecton": add_insect_to_tecton(args); break;
            case "add_mushroom_to_tecton": add_mushroom_to_tecton(args); break;
            case "add_effect_to_insect": add_effect_to_insect(args); break;
            case "add_yarn": add_yarn(args); break;
            case "grow_spore": grow_spore(args); break;
            case "split_tecton": split_tecton(args); break;
            case "start_game": start_game(args); break;
            case "action_wait": action_wait(args); break;
            case "action_move": action_move(args); break;
            case "action_eat_spore": action_eat_spore(args); break;
            case "action_cut_yarn": action_cut_yarn(args); break;
            case "action_grow_mushroom": action_grow_mushroom(args); break;
            case "action_grow_yarn": action_grow_yarn(args); break;
            case "action_spore_dispersion": action_spore_dispersion(args); break;
            case "action_phase_end": action_phase_end(args); break;
            case "next_round": next_round(args); break;
            case "game_end": game_end(args); break;
            case "randomize": randomize(args); break;
            case "save": save(args); break;
            case "load": load(args); break;
            case "stat": stat(args); break;
            case "statRound": statRound(args); break;
            case "run": run(args); break;
            default: System.out.println("Rossz parancs: " + args[0]); break;
        }
    }

    /**
     * Displays statistics about the current round and active player.
     * Expected output format: ActivePlayer: [name], Current round: [round_number]
     * @param args Command arguments (not used)
     */
    private static void statRound(String[] args) {
        // Get the active player from the game
        Player activePlayer = game.getActivePlayer();

        // Get the current round number
        int currentRound = game.getRound();

        // Display the active player's name
        System.out.println("ActivePlayer: " + (activePlayer != null ? activePlayer.getName() : "None"));

        // Display the current round number
        System.out.println("Current round: " + currentRound);
    }

    /**
     * Displays statistics about the current game state.
     * Command syntax: stat
     * @param args Command arguments (none expected)
     */
    private static void stat(String[] args) {
        if (handleArgCount(args, 1)) return;

        /* ----------  TECTONS  ---------- */
        for (Tecton tecton : map.getTectons()) {
            System.out.println("Tecton: "   + tecton.getId());
            System.out.println("Type: "     + tecton.getType());

            System.out.println("Mushroom: " + (tecton.getMushroom() != null
                    ? tecton.getMushroom().getId()
                    : "none"));

            System.out.println("Insects: "  + (tecton.getInsects().isEmpty()
                    ? "none"
                    : tecton.getInsects()
                    .stream()
                    .map(Insect::getId)
                    .collect(Collectors.joining(" "))));

            System.out.println("Yarns: "    + (tecton.getYarns().isEmpty()
                    ? "none"
                    : tecton.getYarns()
                    .stream()
                    .map(Yarn::getId)
                    .collect(Collectors.joining(" "))));

            System.out.println("Neighbours: "+ (tecton.getNeighbours().isEmpty()
                    ? "none"
                    : tecton.getNeighbours()
                    .stream()
                    .map(Tecton::getId)
                    .collect(Collectors.joining(" "))));

            System.out.println("Spores: "   + (tecton.getSpores().isEmpty()
                    ? "none"
                    : tecton.getSpores()
                    .stream()
                    .map(Spore::getId)
                    .collect(Collectors.joining(" "))));
            System.out.println();
        }

        /* ----------  MUSHROOMS  ---------- */
        for (Mushroom m : getAllMushroomsInGame()) {
            System.out.println("Mushroom: " + m.getId());
            System.out.println("HasSpore: " + m.getHasSpore());
            System.out.println("Place: "    + m.getTecton().getId());
            MushroomPicker owner = findMushroomPickerByMushroom(m);
            System.out.println("Owner: "    + (owner == null ? "none" : owner.getName()));
            System.out.println();
        }

        /* ----------  YARNS  ---------- */
        for (Yarn y : getAllYarnsInGame()) {
            System.out.println("Yarn: " + y.getId());
            System.out.println("Type: " + y.getName());            //  ←  kill / protect / …
            System.out.print  ("Tectons in yarn: ");
            System.out.println(y.getTectons().stream()
                    .map(Tecton::getId)
                    .collect(Collectors.joining(" ")));
            System.out.println();
        }

        /* ----------  PLAYERS  ---------- */
        for (Player p : game.getPlayers()) {
            System.out.println("Player: " + p.getName());
            System.out.println("Type: "   + getPlayerType(p));
            System.out.println();                       //  ← blank line before Points
            System.out.println("Points: " + p.getPoints());
            System.out.println();
        }
    }

    /**
     * Helper method to get all yarns in the game
     */
    private static List<Yarn> getAllYarnsInGame() {
        List<Yarn> allYarns = new ArrayList<>();
        for (Tecton tecton : map.getTectons()) {
            for (Yarn yarn : tecton.getYarns()) {
                if (!allYarns.contains(yarn)) {
                    allYarns.add(yarn);
                }
            }
        }
        return allYarns;
    }

    /**
     * Helper method to get all mushrooms in the game
     */
    private static List<Mushroom> getAllMushroomsInGame() {
        List<Mushroom> allMushrooms = new ArrayList<>();
        for (Tecton tecton : map.getTectons()) {
            if (tecton.getMushroom() != null && !allMushrooms.contains(tecton.getMushroom())) {
                allMushrooms.add(tecton.getMushroom());
            }
        }
        return allMushrooms;
    }

    /**
     * Helper method to get all insects in the game
     */
    private static List<Insect> getAllInsectsInGame() {
        List<Insect> allInsects = new ArrayList<>();
        for (Tecton tecton : map.getTectons()) {
            for (Insect insect : tecton.getInsects()) {
                if (!allInsects.contains(insect)) {
                    allInsects.add(insect);
                }
            }
        }
        return allInsects;
    }

    /**
     * Helper method to get player type string
     */
    private static String getPlayerType(Player player) {
        if (player instanceof Entomologist) {
            return "Entomologist";
        } else if (player instanceof MushroomPicker) {
            return "MushroomPicker";
        } else {
            return "Unknown";
        }
    }




    /**
     * Saves the current game state to a file.
     * Command syntax: save <filename>
     *
     * @param args Command arguments: filename
     */
    private static void save(String[] args) {
        // Check if we have exactly one argument (the filename)
        if (handleArgCount(args, 2)) {
            return;
        }

        String filename = args[1];

        // Add .ser extension if not present
        if (!filename.endsWith(".ser")) {
            filename += ".ser";
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            // Create a container object to hold both game and map
            GameState gameState = new GameState(game, map);

            // Write the game state to the file
            out.writeObject(gameState);
            System.out.println("Game state successfully saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving game state: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads a previously saved game state from a file.
     * Command syntax: load <filename>
     *
     * @param args Command arguments: filename
     */
    private static void load(String[] args) {
        // Check if we have exactly one argument (the filename)
        if (handleArgCount(args, 2)) {
            return;
        }

        String filename = args[1];

        // Add .ser extension if not present
        if (!filename.endsWith(".ser")) {
            filename += ".ser";
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            // Read the game state from the file
            GameState gameState = (GameState) in.readObject();

            // Update the static game and map references
            game = gameState.getGame();
            map = gameState.getMap();

            System.out.println("Game state successfully loaded from " + filename);

            // Optional: Print a summary of the loaded state
            System.out.println("Game loaded with " + game.getPlayers().size() + " players and "
                    + map.getTectons().size() + " tectons.");
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found: " + filename);
        } catch (IOException e) {
            System.out.println("Error loading game state: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading game state: Class not found - " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * A container class to hold both Game and Map objects for serialization.
     * This class must be Serializable, as must all objects it references.
     */
    private static class GameState implements Serializable {
        private static final long serialVersionUID = 1L;

        private final Game game;
        private final Map map;

        public GameState(Game game, Map map) {
            this.game = game;
            this.map = map;
        }

        public Game getGame() {
            return game;
        }

        public Map getMap() {
            return map;
        }
    }
    /**
     * A parancsokat értelmezi és feldolgozza.
     * @param is az input stream, amelyről olvas.
     */
    public static void start(InputStream is) {
        // Using try-with-resources to automatically close the Scanner.
        try (Scanner sc = new Scanner(is)) {
            while (sc.hasNextLine()) {
                String cmd = sc.nextLine();
                parse(cmd);
            }
        }
    }

    /**
     * Egy paraméterben meghatározott teszteset futtatása.
     * @param args a tesztesethez tartozó bemeneti file.
     */
    private static void run(String[] args) {
        if (handleArgCount(args, 2)) {
            return;
        }

        String fileName = args[1];
        // Using try-with-resources to automatically close the FileInputStream.
        try (FileInputStream fis = new FileInputStream(fileName)) {
            start(fis);
        } catch (FileNotFoundException e) {
            System.out.println("Nincs ilyen fajl!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void randomize(String[] args) {
        //TODO implement
    }

    private static void game_end(String[] args) {
        //nem hasznaljuk
    }

    private static void action_phase_end(String[] args) {
        game.nextPlayer();
    }

    private static void next_round(String[] args) {
        game.nextTurn();
    }

    /**
     * Executes the spore dispersion action.
     * Command syntax: action_spore_dispersion <target_tecton_ID> <mushroom_ID>
     *
     * Leírás: A gombász spórakilövés akciót hajt végre a körben a megadott tektonra
     * a megadott gombatestből.
     *
     * @param args Command arguments: target_tecton_ID, mushroom_ID
     */
    private static void action_spore_dispersion(String[] args) {
        // Ensure exactly 2 arguments are provided.
        if (handleArgCount(args, 2)) {
            return;
        }

        String targetTectonId = args[0];
        String mushroomId = args[1];

        // Locate the target tecton by its ID.
        Tecton targetTecton = findTectonById(targetTectonId);
        if (targetTecton == null) {
            System.out.println("Hiba: Nem található tecton azonosítóval: " + targetTectonId);
            return;
        }

        // Locate the mushroom globally using its ID.
        Mushroom mushroom = findMushroomById(mushroomId);
        if (mushroom == null) {
            System.out.println("Hiba: Nem található gombatest azonosítóval: " + mushroomId);
            return;
        }

        // Determine the MushroomPicker that owns the mushroom.
        MushroomPicker picker = findMushroomPickerByMushroom(mushroom);
        if (picker == null) {
            System.out.println("Hiba: Nem található gombász a megadott gombával (" + mushroomId + ").");
            return;
        }

        // Execute the spore dispersion action.
        picker.actionSporeDispersion(targetTecton, mushroom);
    }

    /**
     * Searches for the MushroomPicker player who owns the specified Mushroom.
     *
     * @param mushroom The Mushroom for which to find the owning MushroomPicker.
     * @return The MushroomPicker if found; otherwise, null.
     */
    private static MushroomPicker findMushroomPickerByMushroom(Mushroom mushroom) {
        if (mushroom == null) {
            return null;
        }
        for (Player player : game.getPlayers()) {
            if (player instanceof MushroomPicker) {
                MushroomPicker picker = (MushroomPicker) player;
                if (picker.getOwnedMushrooms().contains(mushroom)) {
                    return picker;
                }
            }
        }
        return null;
    }

    /**
     * Executes the gombász yarn growing action.
     * Command syntax: action_grow_yarn <from_tecton_ID> <target_tecton_ID> <yarn_ID>
     *
     * Leírás: A gombász gombafonal növesztés akciót hajt végre a körben a megadott tectonra.
     *
     * @param args Command arguments: from_tecton_ID, target_tecton_ID, yarn_ID
     */
    private static void action_grow_yarn(String[] args) {
        // Ensure exactly 3 arguments are provided.
        if (handleArgCount(args, 4)) {
            return;
        }

        String fromTectonId = args[1];
        String targetTectonId = args[2];
        String yarnId = args[3];

        // Find the "from" tecton.
        Tecton fromTecton = findTectonById(fromTectonId);
        if (fromTecton == null) {
            System.out.println("Hiba: Nem található forrás tecton azonosítóval: " + fromTectonId);
            return;
        }

        // Find the yarn globally.
        Yarn yarn = findYarnById(yarnId);
        if (yarn == null) {
            System.out.println("Hiba: Nem található gombafonal azonosítóval: " + yarnId);
            return;
        }

        // Ensure that the yarn is located on the specified "from" tecton.
        Tecton yarnTecton = findTectonByYarn(yarn);
        if (yarnTecton == null || !yarnTecton.getId().equals(fromTectonId)) {
            System.out.println("Hiba: A gombafonal (" + yarnId + ") nem található a megadott forrás tectonban (" + fromTectonId + ").");
            return;
        }

        // Find the target tecton.
        Tecton targetTecton = findTectonById(targetTectonId);
        if (targetTecton == null) {
            System.out.println("Hiba: Nem található cél tecton azonosítóval: " + targetTectonId);
            return;
        }

        // Find the mushroom picker (actor) among the players
        MushroomPicker mushroomPicker = findMushroomPickerById(yarn.getPlayer().getId()); //szerintem ez így megoldható- Erna
        if (mushroomPicker == null) {
            System.out.println("Hiba: Nem található gombász játékos!");
            return;
        }

        // Execute the yarn growing action.
        // mushroomPicker.actionGrowYarn(targetTecton, yarn);//Luca: sztem így - nem biztos, hogy ez így jó lesz
        //- itt lehet baj lesz az, hogy ha nem négyzetrácsos a grid/sarok is szomszéd - nem egyértelműen eldönthető, hogy honnan növesztjük - Erna
        mushroomPicker.actionGrowYarn(fromTecton, targetTecton, yarn); //Luca: itt nekünk csak a céltekton kell bemenetnek sztem
    }

    private static void action_grow_mushroom(String[] args) {
        //senki nem hasznalja
    }

    /**
     * Executes the rovarász fonalvágás (yarn cutting) action for the entomologist controlling the specified insect.
     * Command syntax: action_cut_yarn <insect_ID> <yarn_ID> <target_tecton_ID>
     *
     * Leírás: A rovarász végrehajtja a fonalvágás akciót a körben a megadott rovarral a megadott gombafonálon.
     *
     * @param args Command arguments: insect_ID, yarn_ID, target_tecton_ID
     */
    private static void action_cut_yarn(String[] args) {
        // Ensure exactly 3 arguments are passed: insect_ID, yarn_ID, target_tecton_ID
        if (handleArgCount(args, 3)) {
            return;
        }

        String insectId = args[0];
        String yarnId = args[1];
        String tectonId = args[2];

        // Look up the insect by its ID.
        Insect targetInsect = findInsectById(insectId);
        if (targetInsect == null) {
            System.out.println("Hiba: Nem található rovar azonosítóval: " + insectId);
            return;
        }

        // Globally search for the yarn using its ID.
        Yarn targetYarn = findYarnById(yarnId);
        if (targetYarn == null) {
            System.out.println("Hiba: Nem található gombafonal azonosítóval: " + yarnId);
            return;
        }

        // Verify that the yarn is located on the tecton with the given target_tecton_ID.
        Tecton yarnTecton = findTectonByYarn(targetYarn);
        if (yarnTecton == null || !yarnTecton.getId().equals(tectonId)) {
            System.out.println("Hiba: A gombafonal (" + yarnId + ") nincs a megadott tectonban (" + tectonId + ").");
            return;
        }

        // Find the entomologist controlling the insect.
        Entomologist entomologist = findEntomologistByInsect(targetInsect);
        if (entomologist == null) {
            System.out.println("Hiba: Nem található rovarászt a rovarral azonosítóval: " + insectId);
            return;
        }

        // Execute the yarn cutting action.
        entomologist.actionCutYarn(targetYarn, targetInsect); //Luca: kell az insect második bemenetnek
    }



    /**
     * Finds the Tecton that contains the specified Yarn.
     *
     * @param yarn The Yarn whose parent tecton is to be determined.
     * @return The Tecton that contains the Yarn, or null if not found.
     */
    private static Tecton findTectonByYarn(Yarn yarn) {
        if (yarn == null) {
            return null;
        }
        for (Tecton tecton : map.getTectons()) {
            if (tecton.getYarns().contains(yarn)) {
                return tecton;
            }
        }
        return null;
    }


    /**
     * Executes the eat spore action for an insect with the specified ID on a target spore.
     * This command makes the insect eat a spore to gain nutrition points.
     *
     * @param args Command arguments, where args[0] is the insect ID and args[1] is the spore ID
     */
    private static void action_eat_spore(String[] args) {
        // Check if we have exactly two arguments (insect ID and spore ID)
        if (handleArgCount(args, 2)) {
            return;
        }

        String insectId = args[0];
        String sporeId = args[1];

        // Find the insect with the specified ID
        Insect targetInsect = findInsectById(insectId);
        if (targetInsect == null) {
            System.out.println("Error: Insect with ID " + insectId + " not found.");
            return;
        }

        // Find the tecton containing the insect
        Tecton insectTecton = findTectonByInsect(targetInsect);
        if (insectTecton == null) {
            System.out.println("Error: Tecton containing insect with ID " + insectId + " not found.");
            return;
        }

        // Find the spore with the specified ID on the same Tecton as the insect
        Spore targetSpore = null;
        for (Spore spore : insectTecton.getSpores()) {
            if (spore.getId().equals(sporeId)) {
                targetSpore = spore;
                break;
            }
        }

        if (targetSpore == null) {
            System.out.println("Error: Spore with ID " + sporeId + " not found on the same Tecton as the insect.");
            return;
        }

        // Find the entomologist who controls this insect
        Entomologist entomologist = findEntomologistByInsect(targetInsect);
        if (entomologist == null) {
            System.out.println("Error: No entomologist controls this insect.");
            return;
        }

        // Execute the eat spore action
        entomologist.actionEatSpore(targetSpore, targetInsect); //Luca: kell az insect bemenetnek
    }

    /**
     * Executes the move action for an insect with the specified ID to a target Tecton.
     * This command makes the insect move to another Tecton.
     *
     * @param args Command arguments, where args[0] is the insect ID and args[1] is the target Tecton ID
     */
    private static void action_move(String[] args) {
        // Check if we have exactly two arguments (insect ID and target Tecton ID)
        if (handleArgCount(args, 2)) {
            return;
        }

        String insectId = args[0];
        String targetTectonId = args[1];

        // Find the insect with the specified ID
        Insect targetInsect = findInsectById(insectId);
        if (targetInsect == null) {
            System.out.println("Error: Insect with ID " + insectId + " not found.");
            return;
        }

        // Find the target Tecton with the specified ID
        Tecton targetTecton = findTectonById(targetTectonId);
        if (targetTecton == null) {
            System.out.println("Error: Tecton with ID " + targetTectonId + " not found.");
            return;
        }

        // Find the entomologist who controls this insect
        Entomologist entomologist = findEntomologistByInsect(targetInsect);
        if (entomologist == null) {
            System.out.println("Error: No entomologist controls this insect.");
            return;
        }

        // Execute the move action
        entomologist.actionMove(targetTecton, targetInsect);
    }

    /**
     * Executes the wait action for an insect with the specified ID.
     * This command makes the insect do nothing for the current turn.
     *
     * @param args Command arguments, where args[0] is the ID of the insect
     */
    private static void action_wait(String[] args) {
        // Check if we have exactly one argument (the insect ID)
        if (handleArgCount(args, 1)) {
            return;
        }

        String insectId = args[0];

        // Find the insect with the specified ID
        Insect targetInsect = findInsectById(insectId);
        if (targetInsect == null) {
            System.out.println("Error: Insect with ID " + insectId + " not found.");
            return;
        }

        // Find the entomologist who controls this insect
        Entomologist entomologist = findEntomologistByInsect(targetInsect);
        if (entomologist == null) {
            System.out.println("Error: No entomologist controls this insect.");
            return;
        }

        // Execute the wait action
        entomologist.actionWait(targetInsect);
    }

    private static void start_game(String[] args) {
        //senki nem használja
    }

    /**
     * Splits a Tecton into two separate Tectons.
     * This method finds the target Tecton by ID and uses the Map's splitting method.
     *
     * @param args Command arguments, where args[0] is the ID of the Tecton to split
     */
    private static void split_tecton(String[] args) {
        // Check if we have exactly one argument (the Tecton ID)
        if (handleArgCount(args, 1)) {
            return;
        }

        String targetId = args[0];

        // Find the Tecton with the specified ID
        Tecton targetTecton = findTectonById(targetId);
        if (targetTecton == null) {
            System.out.println("Error: Tecton with ID " + targetId + " not found.");
            return;
        }

        // Split the Tecton using the existing splitting method
        map.splitting(targetTecton);
    }


    /**
     * Létrehoz egy megadott típusú spórát a megadott gombatestben.
     * @param args A parancs argumentumai (mushroom_id, spore_type)
     */
    private static void grow_spore(String[] args) {
        // Ellenőrizzük, hogy pontosan 3 argumentum van-e (beleértve a parancs nevét)
        if (handleArgCount(args, 3)) {
            System.out.println("Használat: grow_spore <mushroom_id> <spore_type>");
            return;
        }

        String mushroomId = args[1];
        String sporeType = args[2].toLowerCase();

        // Használjuk a findMushroomById metódust a gomba megkereséséhez
        Mushroom targetMushroom = findMushroomById(mushroomId);

        if (targetMushroom == null) {
            System.out.println("Nem található gomba a megadott azonosítóval: " + mushroomId);
            return;
        }

        // Ellenőrizzük, hogy a gombának van-e már spórája
        if (targetMushroom.getHasSpore()) {
            System.out.println("A gombának már van spórája.");
            return;
        }

        // Létrehozzuk a megfelelő típusú spórát
        Spore newSpore = null;
        switch (sporeType) {
            case "accelerator":
                newSpore = new AcceleratorSpore();
                break;
            case "paralyze":
                newSpore = new ParalyzingSpore();
                break;
            case "decelerator":
                newSpore = new DeceleratorSpore();
                break;
            case "insectduplicating":
                // Ezt az esetet külön kellene implementálni, ha szükséges
                System.out.println("Az InsectDuplicating spóra még nincs implementálva.");
                return;
            case "cutpreventing":
                newSpore = new CutPreventingSpore();
                break;
            default:
                System.out.println("Ismeretlen spóra típus: " + sporeType);
                System.out.println("Támogatott típusok: accelerator, paralyze, decelerator, insectDuplicating, cutPreventing");
                return;
        }

        // Hozzáadjuk a spórát a gombához
        targetMushroom.growSpore(newSpore);
    }

    private static void add_yarn(String[] args) {
        //Nem hasznaljuk
    }

    /**
     * A megadott típusú spóra hatást rárakja a megadott rovarra.
     * @param args A parancs argumentumai (spore_type, insect_ID)
     */
    private static void add_effect_to_insect(String[] args) {
        // Ellenőrizzük, hogy pontosan 3 argumentum van-e (beleértve a parancs nevét)
        if (handleArgCount(args, 3)) {
            System.out.println("Használat: add_effect_to_insect <spore_type> <insect_ID>");
            return;
        }

        String sporeType = args[1].toLowerCase();
        String insectId = args[2];

        // Használjuk a findInsectById metódust a rovar megkereséséhez
        Insect targetInsect = findInsectById(insectId);

        if (targetInsect == null) {
            System.out.println("Nem található rovar a megadott azonosítóval: " + insectId);
            return;
        }

        // Alkalmazzuk a megfelelő spóra hatást a rovarra
        switch (sporeType) {
            case "accelerator":
                targetInsect.setAccelerated(true);
                break;
            case "paralyze":
                targetInsect.setParalized(true);
                break;
            case "decelerator":
                targetInsect.setDecelerated(true);
                break;
            case "insectduplicating":
                // Ezt az esetet külön kellene implementálni, mivel nincs hozzá beépített metódus az Insect osztályban
                System.out.println("A rovar duplikálása még nincs implementálva.");
                break;
            case "cutpreventing":
                targetInsect.setCutPrevented(true);
                break;
            default:
                System.out.println("Ismeretlen spóra típus: " + sporeType);
                System.out.println("Támogatott típusok: accelerator, paralyze, decelerator, insectDuplicating, cutPreventing");
                break;
        }
    }

    /**
     * Hozzáadja a megadott gombatestet a megadott tektonhoz.
     * @param args A parancs argumentumai (targetTecton_ID, mushroom_ID)
     */
    private static void add_mushroom_to_tecton(String[] args) {
        // Ellenőrizzük, hogy pontosan 3 argumentum van-e (beleértve a parancs nevét)
        if (handleArgCount(args, 3)) {
            System.out.println("Használat: add_mushroom_to_tecton <targetTecton_ID> <mushroom_ID>");
            return;
        }

        String tectonId = args[1];
        String mushroomId = args[2];

        // Használjuk a findTectonById metódust a tecton megkereséséhez
        Tecton targetTecton = findTectonById(tectonId);

        if (targetTecton == null) {
            System.out.println("Nem található tecton a megadott azonosítóval: " + tectonId);
            return;
        }

        // Ellenőrizzük, hogy a tectonon lehet-e gomba
        if (targetTecton.isMushroomPrevent()) {
            System.out.println("Ezen a tectonon nem lehet gomba.");
            return;
        }

        // Ellenőrizzük, hogy van-e már gomba a tectonon
        if (targetTecton.getMushroom() != null) {
            System.out.println("A megadott tectonon már van gomba.");
            return;
        }

        // Új gomba létrehozása a megadott ID-vel és hozzáadása a tectonhoz
        Mushroom newMushroom = new Mushroom(targetTecton, mushroomId);

        // Hozzáadjuk a gombát a tectonhoz
        targetTecton.addMushroom(newMushroom);
    }

    private static void add_insect_to_tecton(String[] args) {

        ///nem hasznalja senki felesleg
    }

    /**
     * Kölcsönösen hozzáadja a megadott két tectont egymásnak szomszédként.
     * @param args A parancs argumentumai (tecton_id, neighbour_tecton_id)
     */
    private static void add_neighbour_to_tecton(String[] args) {
        // Ellenőrizzük, hogy pontosan 3 argumentum van-e (beleértve a parancs nevét)
        if (handleArgCount(args, 3)) {
            System.out.println("Használat: add_neighbour_to_tecton <tecton_id> <neighbour_tecton_id>");
            return;
        }

        String tectonId = args[1];
        String neighbourTectonId = args[2];

        // Ellenőrizzük, hogy nem ugyanaz-e a két tecton
        if (tectonId.equals(neighbourTectonId)) {
            System.out.println("Egy tecton nem lehet önmagának szomszédja!");
            return;
        }

        // Használjuk a findTectonById metódust a tectonok megkereséséhez
        Tecton tecton = findTectonById(tectonId);
        Tecton neighbourTecton = findTectonById(neighbourTectonId);

        // Ellenőrizzük, hogy mindkét tecton létezik-e
        if (tecton == null) {
            System.out.println("Nem található tecton a megadott azonosítóval: " + tectonId);
            return;
        }

        if (neighbourTecton == null) {
            System.out.println("Nem található tecton a megadott azonosítóval: " + neighbourTectonId);
            return;
        }

        // Ellenőrizzük, hogy már szomszédok-e
        if (tecton.isNeighbour(neighbourTecton)) {
            System.out.println("A megadott tectonok már szomszédosak egymással.");
            return;
        }

        // Hozzáadjuk őket egymáshoz szomszédként
        tecton.addNeighbour(neighbourTecton);
    }

    /**
     * Létrehoz egy rovart a megadott rovarászhoz és lerakja a megadott tektonra.
     * @param args A parancs argumentumai (entomologist_NAME, tecton_id, test_name_id)
     */
    private static void create_insect(String[] args) {
        // Ellenőrizzük, hogy pontosan 4 argumentum van-e (beleértve a parancs nevét)
        if (handleArgCount(args, 4)) {
            System.out.println("Használat: create_insect <entomologist_NAME> <tecton_id> <test_name_id>");
            return;
        }

        String entomologistName = args[1];
        String tectonId = args[2];
        String nameId = args[3];

        // Használjuk a findTectonById metódust a tecton megkereséséhez
        Tecton targetTecton = findTectonById(tectonId);

        if (targetTecton == null) {
            System.out.println("Nem található tecton a megadott azonosítóval: " + tectonId);
            return;
        }

        Entomologist entomologist = (Entomologist) findPlayerByName(entomologistName);

        if (entomologist == null) {
            System.out.println("Nincs entomologist");
            return;
        }

        List<Player> players = game.getPlayers();
        players.add(entomologist);
        game.setPlayers(players);


        // Létrehozzuk a rovart a megadott tectonon
        Insect newInsect = new Insect(targetTecton,  entomologist, nameId);

        // Hozzáadjuk a rovart a rovarászhoz
        entomologist.addInsect(newInsect);

        // Hozzáadjuk a rovart a tectonhoz
        targetTecton.addInsect(newInsect);
    }

    //**
 /* Létrehoz egy spórát a megadott tectonon.
            * Spóra típusok: paralyzing, decelerator, cutpreventing
 * @param args A parancs argumentumai (tecton_id, spore_type)
 */
    private static void create_spore_on_tecton(String[] args) {
        // Ellenőrizzük, hogy pontosan 3 argumentum van-e (beleértve a parancs nevét)
        if (handleArgCount(args, 3)) {
            System.out.println("Használat: create_spore_on_tecton <tecton_id> <spore_type>");
            return;
        }

        String tectonId = args[1];
        String sporeType = args[2].toLowerCase();

        // Használjuk a findTectonById metódust a tecton megkereséséhez
        Tecton targetTecton = findTectonById(tectonId);

        if (targetTecton == null) {
            System.out.println("Nem található tecton a megadott azonosítóval: " + tectonId);
            return;
        }

        Spore newSpore = null;

        // Létrehozzuk a megfelelő típusú spórát
        switch (sporeType) {
            case "paralyze":
                newSpore = new ParalyzingSpore();
                break;
            case "decelerator":
                newSpore = new DeceleratorSpore();
                break;
            case "cutpreventing":
                newSpore = new CutPreventingSpore();
                break;
            default:
                System.out.println("Ismeretlen spóra típus: " + sporeType);
                System.out.println("Támogatott típusok: paralyze, decelerator, cutpreventing");
                return;
        }

        // Hozzáadjuk a spórát a tectonhoz
        targetTecton.addSpore(newSpore);
    }

    /**
     * Létrehoz egy megadott típusú gombafonalat és hozzákapcsolja a megadott gombához.
     * Opciók: normal, killer (killer nincs még implementálva)
     * @param args A parancs argumentumai (mushroom_id, yarn_type, test_name_id)
     */
    private static void create_yarn(String[] args) {
        // Ellenőrizzük, hogy pontosan 4 argumentum van-e (beleértve a parancs nevét)
        if (handleArgCount(args, 4)) {
            System.out.println("Használat: create_yarn <mushroom_id> <yarn_type> <test_name_id>");
            return;
        }

        String mushroomId = args[1];
        String yarnType = args[2].toLowerCase();
        String nameId = args[3];

        // Használjuk a findMushroomById metódust a gomba megkereséséhez
        Mushroom targetMushroom = findMushroomById(mushroomId);

        if (targetMushroom == null) {
            System.out.println("Nem található gomba a megadott azonosítóval: " + mushroomId);
            return;
        }
        MushroomPicker mp = findMushroomPickerByMushroom(targetMushroom);
        if (mp == null)
            System.out.println("Nem található mushroom picker ezzel a gombaval: " + targetMushroom.getId());

        Yarn newYarn = null;
        switch (yarnType) {
            case "normal":
                newYarn = new Yarn(targetMushroom,mp ,nameId);
                break;
            case "kill":
                // KillerYarn subclass implementation would be needed
                newYarn = new KillerYarn(targetMushroom, mp,nameId);
                break;
            default:
                System.out.println("Ismeretlen gombafonal típus: " + yarnType);
                System.out.println("Támogatott típusok: normal, kill");
                return;
        }
        Tecton targetTecton = findTectonById(targetMushroom.getTecton().getId());

        targetTecton.addYarn(newYarn);
        // A 'Yarn(Mushroom mushroom)' konstruktor már hozzáadja a Yarn-t a mushroom Tectonjához
        // és beállítja a gombafonalat a gombához is
    }
    /**
     * Létrehoz egy tektont a megadott típusból és hozzáadja a térképhez.
     * Opciók: normal, keepAlive, yarnAbsorbant, multiplePlayer, mushroomPrevent
     * @param args A parancs argumentumai (tecton_type, test_name_id)
     */
    private static void create_tecton(String[] args) {
        // Ellenőrizzük, hogy pontosan 3 argumentum van-e (beleértve a parancs nevét)
        if (handleArgCount(args, 3)) {
            System.out.println("Használat: create_tecton <tecton_type> <test_name_id>");
            return;
        }

        String tectonType = args[1].toLowerCase();
        String nameId = args[2];

        // Default values
        int yarnLimit = 1; // Alapértelmezett érték
        boolean mushroomPrevent = false;

        Tecton newTecton = null;
        switch (tectonType) {
            case "normal":
                newTecton = new Tecton(nameId, yarnLimit, mushroomPrevent, false);
                break;
            case "keepalive":
                // KeepAliveTecton subclass implementation would be needed
                //newTecton = new Tecton(nameId, yarnLimit, mushroomPrevent, true);//Luca sztem így kell
                return;
            case "yarnabsorbant":
                newTecton = new YarnAbsorbantTecton(nameId, yarnLimit, mushroomPrevent, false);
                break;
            case "multipleplayer":
                // MultiplePlayerTecton subclass implementation would be needed
                // newTecton = new MultiplePlayerTecton(id, yarnLimit, mushroomPrevent);
                System.out.println("MultiplePlayer Tecton létrehozása még nem implementált.");
                return;
            case "mushroomprevent":
                newTecton = new Tecton(nameId, yarnLimit, true, false); // mushroomPrevent = true
                break;
            default:
                System.out.println("Ismeretlen Tecton típus: " + tectonType);
                System.out.println("Támogatott típusok: normal, keepAlive, yarnAbsorbant, multiplePlayer, mushroomPrevent");
                return;
        }

        // Hozzáadjuk a létrehozott Tecton-t a térképhez
        map.addTecton(newTecton);
    }
    /**
     * Creates a new Mushroom on a specified Tecton.
     * Expected format: create_mushroom [tecton_id]
     * @param args Command arguments
     */
    private static void create_mushroom(String[] args) {
        // Check for correct number of arguments (command name + 1 argument)
        if (handleArgCount(args, 4)) {
            return;
        }
        String mushroomId = args[3];
        String tectonId = args[2];
        String ownerName = args[1];

        // Use findTectonById method to find the tecton
        Tecton targetTecton = findTectonById(tectonId);

        if (targetTecton == null) {
            System.out.println("Tecton with ID " + tectonId + " not found.");
            return;
        }

        // Check if the tecton already has a mushroom
        if (targetTecton.getMushroom() != null) {
            System.out.println("Tecton already has a mushroom.");
            return;
        }

        // Check if the tecton prevents mushroom growth
        if (targetTecton.isMushroomPrevent()) {
            System.out.println("This tecton prevents mushroom growth.");
            return;
        }

        // Create the mushroom on the tecton
        Mushroom mushroom = new Mushroom(targetTecton, mushroomId);
        targetTecton.addMushroom(mushroom);

        MushroomPicker mp = (MushroomPicker) findPlayerByName(ownerName);
        mp.addMushroom(mushroom);

    }

    /**
     * Creates a new MushroomPicker player and adds it to the game.
     * Expected format: create_mushroom_picker [name] [mushroom_id]
     * @param args Command arguments
     */
    private static void create_mushroom_picker(String[] args) {
        // Check for correct number of arguments (command name + 2 arguments)
        if (handleArgCount(args, 3)) {
            return;
        }

        String name = args[1];
        String mushroomPickerId = args[2];


        // Create the mushroom picker
        MushroomPicker mushroomPicker = new MushroomPicker(name, mushroomPickerId);

        
        List<Player> players = game.getPlayers();
        players.add(mushroomPicker);
        game.setPlayers(players);

    }

    /**
     * Creates a new Entomologist player and adds it to the game.
     * Expected format: create_entomologist [name] [insect_id]
     * @param args Command arguments
     */
    private static void create_entomologist(String[] args) {
        // Check for correct number of arguments (command name + 2 arguments)
        if (handleArgCount(args, 3)) {
            return;
        }

        String name = args[1];
        String entomologistId = args[2];




        // Create the entomologist
        Entomologist entomologist = new Entomologist(name, entomologistId);

        // Add the entomologist to the game's player list
        List<Player> players = game.getPlayers();
        players.add(entomologist);
        game.setPlayers(players);
    }

}

import java.util.ArrayList;
import java.util.List;

public class CmdParser {
    private static Game game = new Game();
    private static Map map = new Map();

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

    private static void add_insect_to_tecton(String[] args) {
    }

    /**
     * Kölcsönösen hozzáadja a megadott két tectont egymásnak szomszédként.
     *
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

        // Keressük meg a megadott ID-jű tectonokat a térképen
        Tecton tecton = null;
        Tecton neighbourTecton = null;

        for (Tecton t : map.getTectons()) {
            if (t.getId().equals(tectonId)) {
                tecton = t;
            }
            if (t.getId().equals(neighbourTectonId)) {
                neighbourTecton = t;
            }
            // Ha mindkét tectont megtaláltuk, kilépünk a ciklusból
            if (tecton != null && neighbourTecton != null) {
                break;
            }
        }

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
     *
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

        // Keressük meg a megadott ID-jű tectont a térképen
        Tecton targetTecton = null;
        for (Tecton tecton : map.getTectons()) {
            if (tecton.getId().equals(tectonId)) {
                targetTecton = tecton;
                break;
            }
        }

        if (targetTecton == null) {
            System.out.println("Nem található tecton a megadott azonosítóval: " + tectonId);
            return;
        }

        // Keressük meg vagy hozzuk létre a megadott nevű rovarászt
        Entomologist entomologist = null;
        for (Player player : game.getPlayers()) {
            if (player instanceof Entomologist && player.getName().equals(entomologistName)) {
                entomologist = (Entomologist) player;
                break;
            }
        }

        if (entomologist == null) {
            return;
        }
        List<Player> players = game.getPlayers();
        players.add(entomologist);
        game.setPlayers(players);

        // Létrehozzuk a rovart a megadott tectonon
        Insect newInsect = new Insect(targetTecton, nameId);

        // Hozzáadjuk a rovart a rovarászhoz
        entomologist.addInsect(newInsect);

        // Hozzáadjuk a rovart a tectonhoz
        targetTecton.addInsect(newInsect);

    }

    /**
     * Létrehoz egy spórát a megadott tectonon.
     * Spóra típusok: paralyzing, decelerator, cutpreventing
     *
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

        // Keressük meg a megadott ID-jű tectont a térképen
        Tecton targetTecton = null;
        for (Tecton tecton : map.getTectons()) {
            if (String.valueOf(tecton.getId()).equals(tectonId)) {
                targetTecton = tecton;
                break;
            }
        }

        if (targetTecton == null) {
            System.out.println("Nem található tecton a megadott azonosítóval: " + tectonId);
            return;
        }

        Spore newSpore = null;

        // Létrehozzuk a megfelelő típusú spórát
        switch (sporeType) {
            case "paralyzing":
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
                System.out.println("Támogatott típusok: paralyzing, decelerator, cutpreventing");
                return;
        }

        // Hozzáadjuk a spórát a tectonhoz
        targetTecton.addSpore(newSpore);

    }

    /**
     * Létrehoz egy megadott típusú gombafonalat és hozzákapcsolja a megadott gombához.
     * Opciók: normal, killer (killer nincs még implementálva)
     *
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

        // Meg kell találnunk a mushroom_id alapján a gombát
        Mushroom targetMushroom = null;

        // Végig kell járnunk a tectonokat, és keresni a megfelelő ID-jű gombát
        for (Tecton tecton : map.getTectons()) {
            Mushroom mushroom = tecton.getMushroom();
            if (mushroom != null && mushroom.getId().equals(mushroomId)) {
                targetMushroom = mushroom;
                break;
            }
        }

        if (targetMushroom == null) {
            System.out.println("Nem található gomba a megadott azonosítóval: " + mushroomId);
            return;
        }

        Yarn newYarn = null;

        switch (yarnType) {
            case "normal":
                newYarn = new Yarn(targetMushroom);
                break;

            case "killer":
                // KillerYarn subclass implementation would be needed
                // newYarn = new KillerYarn(targetMushroom);
                return;

            default:
                System.out.println("Ismeretlen gombafonal típus: " + yarnType);
                System.out.println("Támogatott típusok: normal, killer");
                return;
        }

        // A 'Yarn(Mushroom mushroom)' konstruktor már hozzáadja a Yarn-t a mushroom Tectonjához
        // és beállítja a gombafonalat a gombához is

    }

    /**
     * Létrehoz egy tektont a megadott típusból és hozzáadja a térképhez.
     * Opciók: normal, keepAlive, yarnAbsorbant, multiplePlayer, mushroomPrevent
     *
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
        int yarnLimit = 5; // Alapértelmezett érték
        boolean mushroomPrevent = false;

        Tecton newTecton = null;

        switch (tectonType) {
            case "normal":
                newTecton = new Tecton(nameId, yarnLimit, mushroomPrevent);
                break;

            case "keepalive":
                // KeepAliveTecton subclass implementation would be needed
                // newTecton = new KeepAliveTecton(id, yarnLimit, mushroomPrevent);
                return;

            case "yarnabsorbant":
                newTecton = new YarnAbsorbantTecton(nameId, yarnLimit, mushroomPrevent);
                break;

            case "multipleplayer":
                // MultiplePlayerTecton subclass implementation would be needed
                // newTecton = new MultiplePlayerTecton(id, yarnLimit, mushroomPrevent);
                System.out.println("MultiplePlayer Tecton létrehozása még nem implementált.");
                return;

            case "mushroomprevent":
                newTecton = new Tecton(nameId, yarnLimit, true); // mushroomPrevent = true
                break;

            default:
                System.out.println("Ismeretlen Tecton típus: " + tectonType);
                System.out.println("Támogatott típusok: normal, keepAlive, yarnAbsorbant, multiplePlayer, mushroomPrevent");
                return;
        }

        // Hozzáadjuk a létrehozott Tecton-t a térképhez
        map.addTecton(newTecton);

        System.out.println("Tecton sikeresen létrehozva és hozzáadva a térképhez. ID: " + id + ", Név: " + nameId);
    }
    /**
     * Creates a new Mushroom on a specified Tecton.
     * Expected format: create_mushroom [tecton_id]
     * @param args Command arguments
     */
    private static void create_mushroom(String[] args) {
        // Check for correct number of arguments (command name + 1 argument)
        if (handleArgCount(args, 2)) {
            return;
        }

        String tectonId = args[1];



        // Find the tecton with the given ID
        Tecton targetTecton = null;
        for (Tecton tecton : map.getTectons()) {
            if (tecton.getId().equals(tectonId)) {
                targetTecton = tecton;
                break;
            }
        }

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
        Mushroom mushroom = new Mushroom(targetTecton);
        targetTecton.addMushroom(mushroom);

        System.out.println("Mushroom created successfully on tecton with ID: " + tectonId);
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
        int mushroomId;

        try {
            mushroomId = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid mushroom ID format: " + args[2]);
            return;
        }

        // Create the mushroom picker
        MushroomPicker mushroomPicker = new MushroomPicker(name);

        
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
        int insectId;

        try {
            insectId = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid insect ID format: " + args[2]);
            return;
        }

        // Find the insect with the given ID
        Insect targetInsect = null;
        for (Tecton tecton : map.getTectons()) {
            for (Insect insect : tecton.getInsects()) {
                if (insect.getId().equals(insectId)) {
                    targetInsect = insect;
                    break;
                }
            }
            if (targetInsect != null) break;
        }

        if (targetInsect == null) {
            System.out.println("Insect with ID " + insectId + " not found.");
            return;
        }

        // Create the entomologist
        Entomologist entomologist = new Entomologist(name, targetInsect);

        // Add the entomologist to the game's player list
        List<Player> players = game.getPlayers();
        players.add(entomologist);
        game.setPlayers(players);
    }


}

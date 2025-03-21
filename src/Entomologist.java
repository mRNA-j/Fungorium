import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Entomologist extends Player{
    private Insect insect;

    public Entomologist(String name, Insect insect){
        super(name, 0);
        this.insect = insect;
    }



    private int getInputTecton(){

        int selected_tecton=-1;
        try{
            System.out.println("Choose tecton ["+game.getPlayfield().length()+"]");
            InputStreamReader is= new InputStreamReader(System.in);
            BufferedReader br= new BufferedReader(is);
            String input= br.readLine();

            selected_tecton=Integer.parseInt(input)-1;

        }
        catch(IOException e){

        }
        return selected_tecton;
    }
    public void actionWatch() {
        return;     //?????
    }
    public void actionMove(Tecton targetTecton) {
        Tecton currentTecton = insect.getCurrentPlace();
        if(insect.getParalized()){
            System.out.println("A rovar bénítóspóra hatása alatt van, a mozgás nem lehetéges");
            return;
        }
        if(insect.getDecelerated()){
            System.out.println("A rovar lassító spóra hatása alatt van, a mozgás nem lehetséges");
            return;
        }

        int tectonFromList=getInputTecton();




        //ha a mozgást nem gátolja spóra hatása, akkor bekérjük majd a tektont, azután fogjuk vizsgálni hogy jó-e7
        //bekérjük a tektont és tovább adjuk a bogárnak
        if(insect.move(game.getPlayfield().get(tectonFromList))){
            System.out.println(" mozgás sikeresen végrehajtva");
            if(insect.getAccelerated()){
                insect.setAccelerated(false);
                int next= getInputTecton();
                this.actionMove();
            }
        }
        else{
            System.out.println("Mozgás végrehajtása sikertelen");
        }
    }
    public void actionEatSpore(Spore spore) {
        if(insect.getParalized()){
            System.out.println("A rovar bénítóspóra hatása alatt van, az evés nem lehetéges");
            return;
        }
        //itt be kell kérni az insect currentPlace spórái közül az egyiket
        try{
            System.out.println("Choose a spore from the tecton ["+insect.getCurrentPlace().getSpores().size()+"]");
            InputStreamReader is= new InputStreamReader(System.in);
            BufferedReader br= new BufferedReader(is);
            String input= br.readLine();

            int selected_spore=Integer.parseInt(input)-1;
            insect.eatSpore(insect.getCurrentPlace().getSpores().get(selected_spore));
            this.addPoints(spore.getNutrition());
            System.out.println("A játékos pontjai megnőttek: " + spore.getNutrition() + " ponttal");

        }
        catch(IOException e){

        }


    }
    public void actionCutYarn(Yarn yarn) {
        if(insect.getParalized()){
            System.out.println("A rovar bénítóspóra hatása alatt van, a fonalvágás nem lehetéges");
            return;
        }
        if(insect.getCutPrevented()){
            System.out.println("A rovar vágásgátló spóra hatása alatt van, az fonalvágás nem lehetéges");
            return;
        }
        int selected_yarn=-1;
        try{
            System.out.println("Choose yarn ["+insect.getCurrentPlace().getYarns().size()+"]");
            InputStreamReader is= new InputStreamReader(System.in);
            BufferedReader br= new BufferedReader(is);
            String input= br.readLine();

            selected_yarn=Integer.parseInt(input)-1;

        }
        catch(IOException e){

        }

        //be kell kérni magát a yarnt
        if(selected_yarn !=-1){
            insect.cutYarn(insect.getCurrentPlace().getYarns().size());
        }

    }
}
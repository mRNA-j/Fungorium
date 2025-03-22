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

    public Insect getInsect(){
      return insect;
    }

    public void actionWait() {
        return;
    }

    public void actionWatch() {return;}

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
        boolean sikeres = insect.move(targetTecton);
        if(!sikeres){
            System.out.println("Mozgás végrehajtása sikertelen");
        }
    }

    public void actionEatSpore(Spore spore) {
        if(insect.getParalized()){
            System.out.println("A rovar bénítóspóra hatása alatt van, az evés nem lehetéges");
            return;
        }
        insect.eatSpore(spore);
        this.addPoints(spore.getNutrition());
        System.out.println("A játékos pontjai megnőttek: " + spore.getNutrition() + " ponttal");
        //itt be kell kérni az insect currentPlace spórái közül az egyiket
        /*try{
            System.out.println("Choose a spore from the tecton ["+insect.getCurrentPlace().getSpores().size()+"]");
            InputStreamReader is= new InputStreamReader(System.in);
            BufferedReader br= new BufferedReader(is);
            String input= br.readLine();

            int selected_spore=Integer.parseInt(input)-1;


        }
        catch(IOException e){

        }*/


    }

    public void actionCutYarn(Yarn yarn) {
        if(insect.getParalized()){
            System.out.println("A rovar bénítóspóra hatása alatt van, a fonalvágás nem lehetéges");
            actionWait();
            return;
        }
        if(insect.getCutPrevented()){
            System.out.println("A rovar vágásgátló spóra hatása alatt van, az fonalvágás nem lehetéges");
            actionWait();
            return;
        }
        insect.cutYarn(yarn);
        /*
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

        if(selected_yarn !=-1){

        }*/

    }
}
public class Entomologist extends Player{
    private Insect insect;

    public Entomologist(String name, Insect insect){
        super(name, 0);
        this.insect = insect;
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


        //ha a mozgást nem gátolja spóra hatása, akkor bekérjük majd a tektont, azután fogjuk vizsgálni hogy jó-e7
        //bekérjük a tektont és tovább adjuk a bogárnak
        if(insect.move(targetTecton)){
            System.out.println(" mozgás sikeresen végrehajtva");
        }
        else{
            System.out.println("Mozgás végrehajtása sikertelen");
        }

        if(currentTecton.getYarns().size() == 0){
            System.out.println("Nem csatlakozik gombafonal a tektonhoz, a mozgás sajnos nem lehetséges");
        }

        boolean targetTectonIsInReach = false;
        for(int i = 0; i < currentTecton.getYarns().size(); i++){
            if(currentTecton.getYarns().getTectons().contains(targetTecton)){
                targetTectonIsInReach = true;
            }
        }

        if(targetTectonIsInReach){
            System.out.println("a tecton elérhető,");
        }
        else{
            System.out.println("A választott tektonra erről a tektonról nem vezet gombafonal. A mozgás nem lehetséges.");
            return;
        }


        if(insect.getAccelerated()){
            insect.move();
        }



        //szerintem itt kell bekérni a tektont



        //össze van e kötve a 2 tekton

    }
    public void actionEatSpore(Spore spore) {
        //itt kell bekérni a spórát

    }
    public void actionCutYarn(Yarn yarn) {


    }
}
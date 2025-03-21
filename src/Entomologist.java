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
            if(insect.getAccelerated()){
                insect.setAccelerated(false);
                this.actionMove(targetTecton);
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
        insect.eatSpore(spore);
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

        //be kell kérni magát a yarnt
        insect.cutYarn(yarn);
    }
}
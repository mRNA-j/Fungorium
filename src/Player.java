public abstract class Player {
    private String name;
    private int points;

    public Player(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public int getPoints(){
        return points;
    }

    public void addPoints(int numOfPoints) {
        points += numOfPoints;
        System.out.println("Points added: " + numOfPoints);
    }

}
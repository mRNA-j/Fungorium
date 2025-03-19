public abstract class Player {
    private String name;
    private int points;

    public void addPoints(int numOfPoints) {
        points += numOfPoints;
        return;
    }
}
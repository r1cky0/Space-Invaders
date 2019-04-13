package game.environment;

public class Coordinate {

    private double x,y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Controlla se 2 coordinate sono uguali.
     *
     * @param coordinate: coordinata da confrontare
     * @return
     */
    public boolean equals(Coordinate coordinate){

        if(this.x == coordinate.getX() && this.y == coordinate.getY()){
            return true;
        }
        return false;
    }
}

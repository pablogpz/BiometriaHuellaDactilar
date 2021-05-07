package es.unex.giiis.bss.jgarciapft.model;

import static es.unex.giiis.bss.jgarciapft.model.MinutiaTypes.CUT;

public class Minutia {

    private final int x;
    private final int y;
    private final MinutiaTypes type;
    private final Triplet<Double, Double, Double> angles;

    public Minutia(int x, int y, MinutiaTypes type) {
        this.x = x;
        this.y = y;
        this.type = type;

        angles = new Triplet<>(0d, 0d, 0d);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MinutiaTypes getType() {
        return type;
    }

    public Triplet<Double, Double, Double> getAngles() {
        return angles;
    }

    public double meanAngle() {
        return type == CUT ? angles.first() : (angles.first() + angles.second() + angles.third()) / 3;
    }

    public void setFirstAngle(double angle) {
        angles.setFirst(angle);
    }

    public void setSecondAngle(double angle) {
        angles.setSecond(angle);
    }

    public void setThirdAngle(double angle) {
        angles.setThird(angle);
    }

}

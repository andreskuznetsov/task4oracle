package es.ahs.oracle_task.utils;

/**
 * Created by akuznetsov on 07.09.2016.
 */
public class Coord {
    private float coordX;
    private float coordY;

    public Coord setCoord(float coordX, float coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
        return this;
    }

    public Coord setCoord(Coord coord) {
        this.coordX = coord.coordX;
        this.coordY = coord.coordY;
        return this;
    }

    public float getXcoord() {
        return this.coordX;
    }

    public float getYcoord() {
        return this.coordY;
    }
}

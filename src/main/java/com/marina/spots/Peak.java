package com.marina.spots;

import java.util.ArrayList;

/**
 * Created by Marry on 21.09.2016.
 */
public class Peak {

    private int x;
    private int y;
    private boolean visited;
    private SpotValues spotValues;

    public SpotValues getSpotValues() {
        return spotValues;
    }

    public void setSpotValues(SpotValues spotValues) {
        this.spotValues = spotValues;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Peak(int x, int y, SpotValues spotValues) {
        this.x = x;
        this.y = y;
        this.spotValues = spotValues;
    }

    public void clear(ArrayList<Peak> peaks) {
        for (Peak peak : peaks) {
            peak.setVisited(false);
        }
    }

    public boolean isNeighbour(Peak peak) {
        if ((peak.getX() - 1 == this.getX() && peak.getY() == this.getY())
                || (peak.getX() + 1 == this.getX() && peak.getY() == this.getY())
                || (peak.getY() - 1 == this.getY() && peak.getX() == this.getX())
                || (peak.getY() + 1 == this.getY() && peak.getX() == this.getX())
                || (peak.getX() + 1 == this.getX() && peak.getY() + 1 == this.getY())
                || (peak.getX() + 1 == this.getX() && peak.getY() - 1 == this.getY())
                || (peak.getX() - 1 == this.getX() && peak.getY() + 1 == this.getY())
                || (peak.getX() - 1 == this.getX() && peak.getY() - 1 == this.getY())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf("Peak (" + this.getX() + "," + this.getY() + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Peak)) return false;

        Peak peak = (Peak) o;

        if (x != peak.x) return false;
        if (y != peak.y) return false;
        return spotValues == peak.spotValues;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + spotValues.hashCode();
        return result;
    }
}

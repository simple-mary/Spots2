package com.marina.spots;

import java.util.ArrayList;

/**
 * Created by Marry on 21.09.2016.
 */
public class Peak {
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

    private int x;
    private int y;

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    private boolean visited;

    public Peak(int x, int y) {
        this.x = x;
        this.y = y;
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
                || peak.getX() - 1 == this.getX() && peak.getY() + 1 == this.getY()
                || peak.getX() - 1 == this.getX() && peak.getY() - 1 == this.getY()) {
            return true;
        }
        return false;
    }


}

package com.marina.spots;

import com.marina.spots.dto.DotDTO;

import java.util.ArrayList;

/**
 * Created by Marry on 21.09.2016.
 */
public class Dot implements Comparable<Dot> {

    private int x;
    private int y;
    private boolean visited;
    private DotValues dotValues;

    public DotValues getDotValues() {
        return dotValues;
    }

    public void setDotValues(DotValues dotValues) {
        this.dotValues = dotValues;
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

    public Dot(int x, int y, DotValues dotValues) {
        this.x = x;
        this.y = y;
        this.dotValues = dotValues;
    }

    public Dot(DotDTO dotDTO) {
        this.x = dotDTO.getX();
        this.y = dotDTO.getY();
        this.dotValues = dotDTO.getDotValues();
    }

    public void clear(ArrayList<Dot> dots) {
        for (Dot dot : dots) {
            dot.setVisited(false);
        }
    }

    public boolean isNeighbour(Dot dot) {
        if ((dot.getY() + 1 == this.getY() && dot.getX() == this.getX())
                || (dot.getX() - 1 == this.getX() && dot.getY() == this.getY())
                || (dot.getY() - 1 == this.getY() && dot.getX() == this.getX())
                || (dot.getX() + 1 == this.getX() && dot.getY() == this.getY())
                || (dot.getX() - 1 == this.getX() && dot.getY() + 1 == this.getY())
                || (dot.getX() - 1 == this.getX() && dot.getY() - 1 == this.getY())
                || (dot.getX() + 1 == this.getX() && dot.getY() - 1 == this.getY())
                || (dot.getX() + 1 == this.getX() && dot.getY() + 1 == this.getY())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf("Dot (" + this.getX() + "," + this.getY() + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dot)) return false;

        Dot dot = (Dot) o;

        if (x != dot.x) return false;
        if (y != dot.y) return false;
        return dotValues == dot.dotValues;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + dotValues.hashCode();
        return result;
    }

    @Override
    public int compareTo(Dot o) {
        if(this.getY() > o.getY())
        {
            return 1;
        }
        else if(this.getY() < o.getY())
        {
            return -1;
        }
        else {
            if (this.getX() > o.getX()) {
                return 1;
            } else if (this.getX() < o.getX()) {
                return -1;
            }
        }

        return 0;
    }
}

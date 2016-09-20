package com.marina.spots;

/**
 * Created by Marry on 20.09.2016.
 */
public class Spot
{
    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    private boolean visited = false; //by default

}

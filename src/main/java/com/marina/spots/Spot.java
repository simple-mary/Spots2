package com.marina.spots;

import java.util.ArrayList;
import java.util.LinkedHashSet;

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

    public ArrayList<Peak> getSpots(int user)
    {
        ArrayList<Peak> userSpots = new ArrayList<Peak>();
        int[][] array = GameField.fieldPoints;
        int length = GameField.fieldPoints.length;
        for(int i=0; i<length; i++)
        {
            for(int j=0; j<length; j++)
            {
                if (array[i][j] == user)
                {
                    userSpots.add(new Peak(i, j, user));
                }
            }
        }
        return userSpots;
    }


}

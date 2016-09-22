package com.marina.spots;

import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Marry on 20.09.2016.
 */
public class WideSearchAlgorithm {

    java.util.Queue queue = new LinkedList<Peak>();

    public java.util.Queue getQueue() {
        return queue;
    }

    public void setQueue(java.util.Queue queue) {
        this.queue = queue;
    }


    public ArrayList<Peak> getAllNeighbours(ArrayList<Peak> peaks, Peak currentPeak)
    {
        ArrayList<Peak> list = new ArrayList<Peak>();
        for (Peak peak:peaks)
        {
            if (currentPeak.isNeighbour(peak) && !peak.isVisited())
            {
                list.add(peak);
            }
        }
        return list;
    }

    public void dfs(ArrayList<Peak> peaksOfUser, Peak peak, Peak goalPeak)
    {
        if(peak.isVisited() && peak.equals(goalPeak))
        {
            System.out.println("Goal peak was found");
            return;
        }
        if(peak.isVisited())
        {
            System.out.println("Peak is already visited coordinate X: " + peak.getX() + " and Y: " + peak.getY());
        }

        peak.setVisited(true);
        getQueue().add(peak);
        System.out.println(peak.toString());
        ArrayList<Peak> neighbours = this.getAllNeighbours(peaksOfUser, peak);

        for(Peak neighbour : neighbours)
        {
            dfs(peaksOfUser, neighbour, goalPeak);
        }

        if(peak.isNeighbour(goalPeak))
        {
            System.out.println("!!!!We have find chain!!! " + getQueue().toString());
            return;
        }
    }

    public void clearAll(ArrayList<Peak> peaks, Peak peak) {
        getQueue().clear();
        peak.clear(peaks);
    }

}

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


    final static Chain chain = new Chain();

    public void getQueue(ArrayList<Peak> peaks, Peak goal) {
        Queue queue = new LinkedList();
        int x = goal.getX();
        int y = goal.getY();
        for (Peak peak : peaks) {
            if (peak.getX() - 1 == goal.getX()
                    || peak.getX() + 1 == goal.getX()
                    || peak.getY() + 1 == goal.getY()
                    || peak.getY() - 1 == goal.getY()) {
                queue.add(peak);
            }
        }
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
        if(peak.isVisited())
        {
            System.out.println("Peak is already visited coordinate X: " + peak.getX() + " and Y: " + peak.getY());
        }

        peak.setVisited(true);
        chain.getQueue().add(peak);
        System.out.println(peak.toString());
        ArrayList<Peak> neighbours = this.getAllNeighbours(peaksOfUser, peak);

        for(Peak neighbour : neighbours)
        {
            dfs(peaksOfUser, neighbour, goalPeak);
        }

        if(peak.isNeighbour(goalPeak))
        {
            System.out.println("!!!!We have find chain!!! " + chain.getQueue().toString());
            chain.getQueues().add(chain.getQueue());
            return;
        }
    }

    public void clearAll(ArrayList<Peak> peaks, Peak peak) {
        chain.getQueue().clear();
        peak.clear(peaks);
    }

}

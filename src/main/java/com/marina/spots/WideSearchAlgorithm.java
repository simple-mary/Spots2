package com.marina.spots;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Marry on 20.09.2016.
 */
public class WideSearchAlgorithm {

    Queue queue = new LinkedList<Peak>();

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
//                getQueue(peaks.remove(peak), peak); // need to think how to remove
            }

        }

    }

    public ArrayList<Peak> getAllNeighbours(ArrayList<Peak> peaks, Peak currentPeak)
    {
        ArrayList<Peak> list = new ArrayList<Peak>();
        for (Peak peak:peaks)
        {
            if (currentPeak.isNeighbour(peak))
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
            return;
        }
        if(peak.isVisited() && peak.equals(goalPeak))
        {
            System.out.println("Goal peak was found");
            System.out.println(queue.toString());
            return;
        }
        peak.setVisited(true);
        queue.add(peak);
        System.out.println("X: " + peak.getX() + " Y: " + peak.getY());
//        System.out.println(queue);
        for(Peak peak1 : this.getAllNeighbours(peaksOfUser, peak))
        {
            dfs(peaksOfUser, peak1, goalPeak);
        }
    }

}

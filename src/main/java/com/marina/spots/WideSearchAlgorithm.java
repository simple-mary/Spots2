package com.marina.spots;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Marry on 20.09.2016.
 */
public class WideSearchAlgorithm {
    public void findChain(Spot spot) {
        // BFS uses Queue data structure
        Queue queue = new LinkedList();
        ArrayList<Integer> adj[][];
        queue.add(spot);
        spot.setVisited(true);
        while (!queue.isEmpty()) {
        }
    }

    public void getChain(ArrayList<Peak> peaks, Peak goal) {
        Queue queue = new LinkedList();
        for (Peak peak : peaks) {
            if (peak.isVisited()) {
                return;
            }
            peak.setVisited(true);
            if (peak == goal) {
                System.out.println("Hooray! The peak was found!");
                return;
            }
            queue.add(peak);

            for (int i = 0; i < 4; ++i)
            {

            }
//

        }
    }
}

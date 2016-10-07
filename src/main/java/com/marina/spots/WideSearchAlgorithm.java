package com.marina.spots;

import java.util.*;

/**
 * Created by Marry on 20.09.2016.
 */
public class WideSearchAlgorithm {

    final Deque<Dot> queue = new LinkedList<Dot>();

    public boolean isCycleFound() {
        return isCycleFound;
    }

    private boolean isCycleFound = false;

    public Deque<Dot> getQueue() {
        return queue;
    }

    public ArrayList<Dot> getAllNeighbours(ArrayList<Dot> dots, Dot currentDot) {
        ArrayList<Dot> list = new ArrayList<Dot>();
        for (Dot dot : dots) {
            if (currentDot.isNeighbour(dot) && !dot.isVisited()) {
                list.add(dot);
            }
        }
        return list;
    }

    public void dfs(ArrayList<Dot> peaksOfUser, Dot dot, Dot goalDot) {
        if (dot.isVisited() && dot.equals(goalDot)) {
            if (getQueue().size() > 3) {
                isCycleFound = true;
            }
            return;
        }
        if (dot.isVisited()) {
            if (!isCycleFound && !queue.isEmpty()) {
                queue.removeLast().setVisited(false);
            }
            return;
        }

        dot.setVisited(true);
        queue.add(dot);
        ArrayList<Dot> neighbours = this.getAllNeighbours(peaksOfUser, dot);

        for (Dot neighbour : neighbours) {
            if (!isCycleFound) {
                dfs(peaksOfUser, neighbour, goalDot);
            } else {
                break;
            }
        }

        if (!isCycleFound && dot.isNeighbour(goalDot)) {
            if (getQueue().size() > 3) {
                System.out.println("!!!!We have find chain!!! " + getQueue().toString());
                isCycleFound = true;
            }
            if (!isCycleFound && !queue.isEmpty()) {
                queue.removeLast().setVisited(false);
            }
            return;
        }
        if (!isCycleFound && !queue.isEmpty()) {
            queue.removeLast().setVisited(false);
        }
    }
}

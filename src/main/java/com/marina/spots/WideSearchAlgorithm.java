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

    public List<Dot> getAllNeighbours(ArrayList<Dot> dots, Dot currentDot) {
        ArrayList<Dot> list = new ArrayList<>();
        for (Dot dot : dots) {
            if (currentDot.isNeighbour(dot) && !dot.isVisited()) {
                list.add(dot);
            }
        }
        List<Dot> result = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            result.add(null);
        }
        for (Dot dot : list) {
            if (dot.getY() + 1 == currentDot.getY() && dot.getX() == currentDot.getX()) {
                result.set(0, dot);
            }
            if (dot.getY() == currentDot.getY() && dot.getX() - 1 == currentDot.getX()) {
                result.set(1, dot);
            }
            if (dot.getY() - 1 == currentDot.getY() && dot.getX() == currentDot.getX()) {
                result.set(2, dot);
            }
            if (dot.getY() == currentDot.getY() && dot.getX() + 1 == currentDot.getX()) {
                result.set(3, dot);
            }
            if (dot.getY() + 1 == currentDot.getY() && dot.getX() - 1 == currentDot.getX()) {
                result.set(4, dot);
            }

            if (dot.getY() - 1 == currentDot.getY() && dot.getX() - 1 == currentDot.getX()) {
                result.set(5, dot);
            }

            if (dot.getY() - 1 == currentDot.getY() && dot.getX() + 1 == currentDot.getX()) {
                result.set(6, dot);
            }

            if (dot.getY() + 1 == currentDot.getY() && dot.getX() + 1 == currentDot.getX()) {
                result.set(7, dot);
            }
        }
        result.removeAll(Collections.singletonList(null));
        return result;
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
        List<Dot> neighbours = this.getAllNeighbours(peaksOfUser, dot);

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

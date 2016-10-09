package com.marina.spots;

import com.google.common.base.Stopwatch;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Marry on 20.09.2016.
 */
public class DeepSearchAlgorithm {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DeepSearchAlgorithm.class);
    private final Deque<Dot> queue = new LinkedList<Dot>();
    private Stopwatch stopwatch = Stopwatch.createStarted();
    private boolean isCycleFound = false;

    public boolean isCycleFound() {
        return isCycleFound;
    }

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
        LOG.info("New dfs cycle {} ms.", stopwatch.elapsed(TimeUnit.MILLISECONDS));
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
                LOG.info("We have find chain:  {}  Time = {}", getQueue(), stopwatch.elapsed(TimeUnit.SECONDS));
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

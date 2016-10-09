package com.marina.spots;

import com.marina.spots.dto.DotDTO;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by Marry on 19.09.2016.
 */
@Controller
public class Game {

    public int fieldSize = 15;
    private String activePlayer = "PLAYER1";

    public String getActivePlayer() {
        return activePlayer;
    }

    public GameField getField() {
        return field;
    }

    private final GameField field = new GameField(fieldSize);
    public ArrayList<Queue<Dot>> globalListWithAllFoundCycle = new ArrayList<Queue<Dot>>();

    @PostConstruct
    public void init() {
        field.initializeField();
        globalListWithAllFoundCycle.clear();
        activePlayer = "PLAYER1";
    }

    public List<Queue<Dot>> game(DotDTO dotDto) {
        String user = dotDto.getDotValues().getValue();
        field.setSpot(dotDto);
        ArrayList<Queue<Dot>> cycles = findAllCycles(user, new Dot(dotDto));
        if (!cycles.isEmpty()) {
            field.printField();
            System.out.println(cycles.toString());
            Queue<Dot> uniqueCycle = field.paintAllCyclesAndReturnUnique(cycles);
            globalListWithAllFoundCycle.add(uniqueCycle);
        }
//        activePlayer = dotDto.getDotValues().equals(DotValues.PLAYER1)
//                ? DotValues.PLAYER2.name() : DotValues.PLAYER1.name();
        return globalListWithAllFoundCycle;
    }

    private ArrayList<Queue<Dot>> findAllCycles(String player, Dot dot) {

        Collection<Dot> uniqueDots = new HashSet<>(field.getUserDots(dot.getDotValues()));
        uniqueDots.remove(dot);
        uniqueDots.add(dot);
        ArrayList<Dot> dots = new ArrayList<>(uniqueDots);
        Collections.sort(dots);
        ArrayList<Queue<Dot>> cycles = new ArrayList<Queue<Dot>>();
        dot.clear(dots);
        DeepSearchAlgorithm algorithm = new DeepSearchAlgorithm();
        if (algorithm.getAllNeighbours(dots, dot).size() > 1) {
            algorithm.dfs(dots, dot, dot);
        }
        if (algorithm.isCycleFound()) {
            cycles.add(algorithm.getQueue());
        }

        return cycles;
    }

    public boolean isFinish() {
        int i = field.countEmptyFields();
        return i == 0;
    }
}




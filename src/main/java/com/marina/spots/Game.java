package com.marina.spots;

import com.marina.spots.dto.DotDTO;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * Created by Marry on 19.09.2016.
 */
@Controller
public class Game {

    public int fieldSize = 20;

    public GameField getField() {
        return field;
    }

    private final GameField field = new GameField(fieldSize);
    public ArrayList<Queue<Dot>> globalListWithAllFoundCycle = new ArrayList<Queue<Dot>>();

    @PostConstruct
    private void init()
    {
        field.initializeField();
    }

    public static JSONObject generateJsonObject(String x, String y, String user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("x", x);
        jsonObject.put("y", y);
        jsonObject.put("user", user);
        jsonObject.put("session", "900");
        return jsonObject;
    }

    public List<Queue<Dot>> game(DotDTO dotDto)  {
        String user = dotDto.getDotValues().getValue();
//        while (!isFinish()) {

//            setRandomSpot(user);
            field.setSpot(dotDto);
            ArrayList<Queue<Dot>> cycles = findAllCycles(user, new Dot(dotDto));
            if (!cycles.isEmpty())
            {
                field.printField();
                System.out.println(cycles.toString());
                Queue<Dot> uniqueCycle = field.paintAllCyclesAndReturnUnique(cycles);
                globalListWithAllFoundCycle.add(uniqueCycle);
                return globalListWithAllFoundCycle;
            }
//        }

        return Collections.emptyList();
    }

    private ArrayList<Queue<Dot>> findAllCycles(String player, Dot dot) {
        ArrayList<Dot> dots = field.getUserDots(dot.getDotValues());
        ArrayList<Queue<Dot>> cycles = new ArrayList<Queue<Dot>>();
        dot.clear(dots);
        WideSearchAlgorithm algorithm = new WideSearchAlgorithm();
        algorithm.dfs(dots, dot, dot);
        if (algorithm.isCycleFound()) {
            cycles.add(algorithm.getQueue());
        }

        return cycles;
    }

    public boolean isFinish() {
        int i = field.countEmptyFields();
        if (i <= (Math.pow(fieldSize - 2, 2) * 30 / 100)) {
            return true;
        }
//        return !field.isEmptyFields();
        return false;
    }


}




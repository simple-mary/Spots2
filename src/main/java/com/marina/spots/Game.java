package com.marina.spots;

import com.marina.spots.dto.DotDTO;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by Marry on 19.09.2016.
 */
@Controller
public class Game {

    public static int fieldSize = 20;
    private static final GameField field = new GameField(fieldSize);


    public static JSONObject generateJsonObject(String x, String y, String user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("x", x);
        jsonObject.put("y", y);
        jsonObject.put("user", user);
        jsonObject.put("session", "900");
        return jsonObject;
    }

    public boolean game(DotDTO dotDto) throws InterruptedException {
        boolean isCycleFound = false;
        field.initializeField();
        String user = dotDto.getDotValues().getValue();
        while (!isFinish()) {

//            setRandomSpot(user);
            field.setSpot(dotDto);
            ArrayList<Queue<Dot>> cycles = findAllCycles(user, new Dot(dotDto));
            if (!cycles.isEmpty()) {
                isCycleFound = true;
                field.printField();
                System.out.println(cycles.toString());
                field.paintAllCycles(cycles);
            }
        }

        return isCycleFound;
    }

    private static ArrayList<Queue<Dot>> findAllCycles(String player, Dot dot) {
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

//    private static Dot setRandomSpot(String user) {
//        try {
//            JSONObject jsonObject = new JSONObject();
//            Random random = new Random();
//            int x = random.nextInt(fieldSize - 1) + 1;
//            int y = random.nextInt(fieldSize - 1) + 1;
//            jsonObject.put("user", user);
//            jsonObject.put("x", x);
//            jsonObject.put("y", y);
//            field.setSpot(jsonObject);
//            return new Dot(x, y, DotValues.valueOf(user));
//        } catch (IllegalArgumentException p_ex) {
//            return setRandomSpot(user);
//        }
//    }

    public static boolean isFinish() {
        int i = field.countEmptyFields();
        if (i <= (Math.pow(fieldSize - 2, 2) * 30 / 100)) {
            return true;
        }
//        return !field.isEmptyFields();
        return false;
    }


}




package com.marina.spots;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Marry on 19.09.2016.
 */
public class Main {

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

    public void main(String[] args) throws InterruptedException {
        field.initializeField();
        String user = "PLAYER1";
        while (!isFinish())
        {

//            setRandomSpot(user);
            ArrayList<Queue<Peak>> cycles = findAllCycles(user, setRandomSpot(user));
            if (!cycles.isEmpty()){
                field.printField();
                System.out.println(cycles.toString());
//                Thread.sleep(5000);
            }
            field.paintAllCycles(cycles);
            field.printField();
//            Thread.sleep(500);

            System.out.println();
            System.out.println();
            user = user.equals("PLAYER1") ? "PLAYER2" :"PLAYER1";
        }

//        System.out.println(cycles.toString());
//        field.paintAllCycles(cycles);
//        field.printField();
        System.out.println("Captured spots for player1 " + field.computeCapturedSpots("PLAYER1"));
        System.out.println("Captured spots for player2 " + field.computeCapturedSpots("PLAYER2"));

        System.out.println("Square for player1 " + field.computeSquareByPlayer("PLAYER1"));
        System.out.println("Square for player2 " + field.computeSquareByPlayer("PLAYER2"));

    }

    private static ArrayList<Queue<Peak>> findAllCycles(String player, Peak peak) {
        ArrayList<Peak> peaks = field.getUserPeaks(SpotValues.valueOf(player));
        ArrayList<Queue<Peak>> cycles = new ArrayList<Queue<Peak>>();
//        for (Peak peak : peaks)
//        {
//            System.out.println("Begin search from peak with X: "
//                    + peak.getX() + " and Y :" + peak.getY());
            peak.clear(peaks);
            WideSearchAlgorithm algorithm = new WideSearchAlgorithm();
            algorithm.dfs(peaks, peak, peak);
            if (algorithm.isCycleFound()) {
                cycles.add(algorithm.getQueue());
//            }
        }
        return cycles;
    }

    private static Peak setRandomSpot(String user) {
        try {
            JSONObject jsonObject = new JSONObject();
            Random random = new Random();
            int x = random.nextInt(fieldSize-1) + 1;
            int y = random.nextInt(fieldSize-1) + 1;
            jsonObject.put("user", user);
            jsonObject.put("x", x);
            jsonObject.put("y", y);
            field.setSpot(jsonObject);
            return new Peak(x, y, SpotValues.valueOf(user));
        }
        catch (IllegalArgumentException p_ex)
        {
            return setRandomSpot(user);
        }
    }

    public static boolean isFinish()
    {
        int i = field.countEmptyFields();
        if(i<=(Math.pow(fieldSize-2,2)*30/100))
        {
            return true;
        }
//        return !field.isEmptyFields();
        return false;
    }


}




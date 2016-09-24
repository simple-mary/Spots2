package com.marina.spots;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Marry on 19.09.2016.
 */
public class Main {

    private static final GameField field = new GameField(10);
    private static final Spot spot = new Spot();
//    private static ArrayList<Queue<Peak>> cycles = new ArrayList<Queue<Peak>>();
    private final static Utility utility = new Utility();

    public static JSONObject generateJsonObject(String x, String y, String user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("x", x);
        jsonObject.put("y", y);
        jsonObject.put("user", user);
        jsonObject.put("session", "900");
        return jsonObject;
    }

    public static void main(String[] args) throws InterruptedException {
        field.initializeField();
//        field.setSpot(generateJsonObject("5", "10", "PLAYER1"));
//        field.setSpot(generateJsonObject("6", "10", "PLAYER2"));
//        field.setSpot(generateJsonObject("18", "12", "PLAYER1"));
//        field.setSpot(generateJsonObject("19", "3", "PLAYER2"));
//        field.setSpot(generateJsonObject("7", "11", "PLAYER1"));
//        field.setSpot(generateJsonObject("3", "19", "PLAYER2"));
//        field.setSpot(generateJsonObject("7", "15", "PLAYER1"));
//        field.setSpot(generateJsonObject("1", "13", "PLAYER2"));
//        field.setSpot(generateJsonObject("3", "13", "PLAYER1"));
//        field.setSpot(generateJsonObject("3", "12", "PLAYER1"));
//        field.setSpot(generateJsonObject("4", "14", "PLAYER1"));
//        field.setSpot(generateJsonObject("5", "14", "PLAYER1"));
//        field.setSpot(generateJsonObject("6", "14", "PLAYER1"));
//        field.setSpot(generateJsonObject("2", "13", "PLAYER1"));
//        field.setSpot(generateJsonObject("10", "15", "PLAYER1"));
//
//        field.setSpot(generateJsonObject("2", "1", "PLAYER1"));
//        field.setSpot(generateJsonObject("4", "1", "PLAYER1"));
//        field.setSpot(generateJsonObject("3", "1", "PLAYER1"));
//        field.setSpot(generateJsonObject("1", "2", "PLAYER1"));
//        field.setSpot(generateJsonObject("3", "2", "PLAYER1"));
//        field.setSpot(generateJsonObject("2", "3", "PLAYER1"));
//
//
//        field.setSpot(generateJsonObject("10", "10", "PLAYER1"));
//        field.setSpot(generateJsonObject("11", "9", "PLAYER1"));
//        field.setSpot(generateJsonObject("12", "8", "PLAYER1"));
//        field.setSpot(generateJsonObject("11", "7", "PLAYER1"));
//        field.setSpot(generateJsonObject("10", "6", "PLAYER1"));
//        field.setSpot(generateJsonObject("9", "7", "PLAYER1"));
//        field.setSpot(generateJsonObject("9", "8", "PLAYER1"));
//        field.setSpot(generateJsonObject("9", "9", "PLAYER1"));
//
//        field.setSpot(generateJsonObject("9", "6", "PLAYER1"));
//        field.setSpot(generateJsonObject("8", "6", "PLAYER1"));
//        field.setSpot(generateJsonObject("9", "6", "PLAYER1"));

        String user = "PLAYER1";
        while (!isFinish())
        {

            setRandomSpot(user);
            ArrayList<Queue<Peak>> cycles = findAllCycles(user);
            if (!cycles.isEmpty()){
                field.printAllSpotsFromField();
                System.out.println(cycles.toString());
//                Thread.sleep(5000);
            }
            user = user.equals("PLAYER1") ? "PLAYER2" :"PLAYER1";
            field.getLines(cycles);
            field.printAllSpotsFromField();
//            Thread.sleep(500);

            System.out.println();
            System.out.println();
        }

//        System.out.println(cycles.toString());
//        field.getLines(cycles);
//        field.printAllSpotsFromField();
        System.out.println("Captured spots for player1 " + field.computeCapturedSpots("PLAYER1"));
        System.out.println("Captured spots for player2 " + field.computeCapturedSpots("PLAYER2"));

        System.out.println("Square for player1 " + field.computeSquareByPlayer("PLAYER1"));
        System.out.println("Square for player2 " + field.computeSquareByPlayer("PLAYER2"));

    }

    private static ArrayList<Queue<Peak>> findAllCycles(String player) {
        ArrayList<Peak> peaks = field.getUserPeaks(SpotValues.valueOf(player));
        ArrayList<Queue<Peak>> cycles = new ArrayList<Queue<Peak>>();
        for (Peak peak : peaks)
        {
//            System.out.println("Begin search from peak with X: "
//                    + peak.getX() + " and Y :" + peak.getY());
            peak.clear(peaks);
            WideSearchAlgorithm algorithm = new WideSearchAlgorithm();
            algorithm.dfs(peaks, peak, peak);
            if (algorithm.isCycleFound()) {
                cycles.add(algorithm.getQueue());
            }
        }
        return cycles;
    }

    private static void setRandomSpot(String user) {
        try {


            JSONObject jsonObject = new JSONObject();
            Random random = new Random();
            jsonObject.put("user", user);
            jsonObject.put("x", random.nextInt(9) + 1);
            jsonObject.put("y", random.nextInt(9) + 1);
            field.setSpot(jsonObject);
        }
        catch (IllegalArgumentException p_ex)
        {
            setRandomSpot(user);
        }

    }

    public static boolean isFinish()
    {
        return !field.isEmptyFields();
    }


}




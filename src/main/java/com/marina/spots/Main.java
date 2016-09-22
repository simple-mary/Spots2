package com.marina.spots;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by Marry on 19.09.2016.
 */
public class Main {

    private static final GameField field = new GameField(30);
    private static final Spot spot = new Spot();
    private static final WideSearchAlgorithm algorithm = new WideSearchAlgorithm();

    public static JSONObject generateJsonObject(String x, String y, String user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("x", x);
        jsonObject.put("y", y);
        jsonObject.put("user", user);
        jsonObject.put("session", "900");
        return jsonObject;
    }

    public static void main(String[] args) {
        field.initializeField();
        field.setSpot(generateJsonObject("5", "10", "5"));
        field.setSpot(generateJsonObject("6", "10", "7"));
        field.setSpot(generateJsonObject("25", "12", "5"));
        field.setSpot(generateJsonObject("19", "3", "7"));
        field.setSpot(generateJsonObject("7", "11", "5"));
        field.setSpot(generateJsonObject("3", "19", "7"));
        field.setSpot(generateJsonObject("7", "15", "5"));
        field.setSpot(generateJsonObject("1", "13", "7"));
        field.setSpot(generateJsonObject("3", "13", "5"));
        field.setSpot(generateJsonObject("3", "12", "5"));
        field.setSpot(generateJsonObject("4", "14", "5"));
        field.setSpot(generateJsonObject("5", "14", "5"));
        field.setSpot(generateJsonObject("6", "14", "5"));
        field.setSpot(generateJsonObject("2", "13", "5"));
        field.setSpot(generateJsonObject("10", "15", "5"));

        field.setSpot(generateJsonObject("2", "1", "5"));
        field.setSpot(generateJsonObject("1", "2", "5"));
        field.setSpot(generateJsonObject("3", "2", "5"));
        field.setSpot(generateJsonObject("2", "3", "5"));


        field.setSpot(generateJsonObject("10", "10", "5"));
        field.setSpot(generateJsonObject("11", "9", "5"));
        field.setSpot(generateJsonObject("12", "8", "5"));
        field.setSpot(generateJsonObject("11", "7", "5"));
        field.setSpot(generateJsonObject("10", "6", "5"));
        field.setSpot(generateJsonObject("9", "7", "5"));
        field.setSpot(generateJsonObject("9", "8", "5"));
        field.setSpot(generateJsonObject("9", "9", "5"));

        field.setSpot(generateJsonObject("9", "6", "5"));
        field.setSpot(generateJsonObject("8", "6", "5"));
        field.setSpot(generateJsonObject("9", "6", "5"));


        field.getAllSpotsFromField();
        ArrayList<Peak> peaks = spot.getSpots(5);
        for (Peak peak : peaks) {
            System.out.println("Begin search from peak with X: "
                    + peak.getX() + " and Y :" + peak.getY());
            // search
            algorithm.clearAll(peaks, peak);
            algorithm.dfs(peaks, peak, peak);
        }
        algorithm.chain.getQueues();

    }


}


//}

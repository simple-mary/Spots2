package com.marina.spots;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Marry on 19.09.2016.
 */
public class Main {

    private static final GameField field = new GameField(30);
    private static final Spot spot = new Spot();
    private static final WideSearchAlgorithm algorithm = new WideSearchAlgorithm();

    public static JSONObject generateJsonObject(String x, String y, String user)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("x", x);
        jsonObject.put("y", y);
        jsonObject.put("user", user);
        jsonObject.put("session", "900");
        return jsonObject;
    }

    public static void main(String[]args)
    {
        field.initializeField();
        field.setSpot(generateJsonObject("5","10","5"));
        field.setSpot(generateJsonObject("6","10","7"));
        field.setSpot(generateJsonObject("25","12","5"));
        field.setSpot(generateJsonObject("19","3","7"));
        field.setSpot(generateJsonObject("7","11","5"));
        field.setSpot(generateJsonObject("3","19","7"));
        field.setSpot(generateJsonObject("7","15","5"));
        field.setSpot(generateJsonObject("1","13","7"));
        field.setSpot(generateJsonObject("3","13","5"));
        field.setSpot(generateJsonObject("3","12","5"));
        field.setSpot(generateJsonObject("4","14","5"));
        field.setSpot(generateJsonObject("2","13","5"));
//        field.getAllSpotsFromField();
        ArrayList<Peak> peaks = spot.getSpots(5);
        for(Peak peak : peaks)
        {
            algorithm.dfs(peaks, peak, new Peak(10, 15));
        }
    }


}

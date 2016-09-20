package com.marina.spots;

import org.json.JSONObject;

/**
 * Created by Marry on 19.09.2016.
 */
public class Main {

    private static final GameField field = new GameField(30);

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
        field.setSpot(generateJsonObject("6","12","5"));
        field.getAllSpotsFromField();
    }


}

package com.marina.spots;

import org.json.JSONObject;

/**
 * Created by Marry on 19.09.2016.
 */
public class GameField {

    private int fieldSize;
    public static int[][] fieldPoints;
    public int getFieldSize() {
        return fieldSize;
    }

    private void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }


    public GameField()
    {
        this.setFieldSize(30);
    }

    public GameField(int fieldSize)
    {
        this.setFieldSize(fieldSize);
    }

    public void initializeField()
    {
        int size = getFieldSize();
        fieldPoints = new int[size][size];
        for (int i=0; i < size; i++)
        {
            for(int j=0; j < size; j++)
            {
                fieldPoints[i][j] = -1;
                System.out.print(fieldPoints[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }

    public void setSpot(JSONObject jsonObject)
    {
        int x = Integer.parseInt(jsonObject.get("x").toString());
        int y = Integer.parseInt(jsonObject.get("y").toString());
        int user = Integer.parseInt(jsonObject.get("user").toString());
        fieldPoints[x][y] = user;
    }

    public void getAllSpotsFromField()
    {
        for(int i=0; i < fieldPoints.length; i++)
        {
            for(int j=0; j < fieldPoints.length; j++)
            {
                System.out.print(fieldPoints[i][j] + "\t");
            }
            System.out.println();
        }
    }


}

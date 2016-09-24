package com.marina.spots;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;

/**
 * Created by Marry on 19.09.2016.
 */
public class GameField {

    private int fieldSize;
    public SpotValues[][] fieldPoints;

    public int getFieldSize() {
        return fieldSize;
    }

    private void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }


    public GameField() {
        this.setFieldSize(20);
    }

    public GameField(int fieldSize) {
        this.setFieldSize(fieldSize);
    }

    public void initializeField() {
        int size = getFieldSize();
        fieldPoints = new SpotValues[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || j == 0 || i == size - 1 || j == size - 1) {
                    fieldPoints[i][j] = SpotValues.EDGE;
                } else {
                    fieldPoints[i][j] = SpotValues.FREE;
                }
            }
        }
    }

    public void setSpot(JSONObject jsonObject) {
        int x = Integer.parseInt(jsonObject.get("x").toString());
        int y = Integer.parseInt(jsonObject.get("y").toString());
        SpotValues user = SpotValues.valueOf(jsonObject.get("user").toString());
        if (fieldPoints[x][y].equals(SpotValues.FREE)) {
            fieldPoints[x][y] = user;
        }
    }

    public void printAllSpotsFromField() {
        for (int j = 0; j < fieldPoints.length; j++) {
            for (int i = 0; i < fieldPoints.length; i++) {
                System.out.print(fieldPoints[i][j] + "\t");
            }
            System.out.println();
        }
    }


    public ArrayList<Peak> getUserPeaks(SpotValues user) {
        ArrayList<Peak> userSpots = new ArrayList<Peak>();
        int length = fieldPoints.length;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (fieldPoints[i][j].equals(user)) {
                    userSpots.add(new Peak(i, j, user));
                }
            }
        }
        return userSpots;
    }


    private void paintArea(Queue<Peak> peaks) {
        for (Peak peak : peaks) {
            for (Peak peak1 : peaks) {
                if (peak.getY() == peak1.getY() && peak.getX() != peak1.getX()) {
                    if (peak.getX() - peak1.getX() > 1) {
                        System.out.println("Peak between " + peak + " " + peak1);
                        blockPeakBetween(peak, peak1);
                    }
                }
            }
        }
    }

    private void blockPeakBetween(Peak peak, Peak peak1) {
        SpotValues cycleOwner = peak.getSpotValues();
        int minX = Math.min(peak.getX(), peak1.getX());
        int maxX = Math.max(peak.getX(), peak1.getX());
        for (int i = minX + 1; i < maxX; i++) {
            if (fieldPoints[i][peak.getY()].equals(SpotValues.FREE)) {
                fieldPoints[i][peak.getY()] =
                        peak.getSpotValues().equals(SpotValues.PLAYER1) ? SpotValues.BLOCK1 : SpotValues.BLOCK2;
            }

            if (!cycleOwner.equals(fieldPoints[i][peak.getY()])) {
                fieldPoints[i][peak.getY()] =
                        peak.getSpotValues().equals(SpotValues.PLAYER1) ? SpotValues.BLOCKED_BY_PLAYER1 : SpotValues.BLOCKED_BY_PLAYER2;
            }
        }

    }

    public void getLines(ArrayList<Queue<Peak>> arrayList) {
        ArrayList<Queue<Peak>> cuted = getUniqueCycles(arrayList);
        for (Queue<Peak> p : cuted) {
            System.out.println("start new cycle" + p);
            paintArea(p);
        }
    }

    public boolean isQueueHasSameElements(Queue<Peak> first, Queue<Peak> second) {
        if (first.size() != second.size()) {
            return false;
        }

        return new HashSet<Peak>(first).equals(new HashSet<Peak>(second));
    }

    public ArrayList<Queue<Peak>> getUniqueCycles(ArrayList<Queue<Peak>> arrayList) {

        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                if (isQueueHasSameElements(arrayList.get(i), arrayList.get(j))) {
                    arrayList.remove(arrayList.get(i));
                }
            }
        }
        return arrayList;
    }


    public int computeCapturedSpots(String player)
    {
        SpotValues x;
        if(SpotValues.valueOf(player).equals(SpotValues.PLAYER1))
        {
            x = SpotValues.BLOCKED_BY_PLAYER1;
        }
        else
        {
            x = SpotValues.BLOCKED_BY_PLAYER2;
        }
        int result = 0;
        for (SpotValues[] rows : fieldPoints) {
            for (SpotValues value : rows) {
                if(value.equals(x))
                {
                    result++;
                }
            }
        }
        return result;
    }


    public int computeSquareByPlayer(String player)
    {
        SpotValues x;
        if(SpotValues.valueOf(player).equals(SpotValues.PLAYER1))
        {
            x = SpotValues.PLAYER1;
        }
        else
        {
            x = SpotValues.PLAYER2;
        }
        int result = 0;
        for (SpotValues[] rows : fieldPoints) {
            for (SpotValues value : rows) {
                if(value.equals(x))
                {
                    result++;
                }
            }
        }
        return result;
    }
}

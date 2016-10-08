package com.marina.spots;

import com.google.common.collect.Sets;
import com.marina.spots.dto.DotDTO;

import java.util.*;

/**
 * Created by Marry on 19.09.2016.
 */
public class GameField {

    private int fieldSize;
    public DotValues[][] fieldPoints;

    public int getFieldSize() {
        return fieldSize;
    }

    private void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    public GameField(int fieldSize) {
        this.setFieldSize(fieldSize);
    }

    Comparator<Queue> queueSizeComparator = new Comparator<Queue>() {
        @Override
        public int compare(Queue o1, Queue o2) {
            return Integer.compare(o1.size(), o2.size());
        }
    };

    public void initializeField() {
        int size = getFieldSize();
        fieldPoints = new DotValues[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                fieldPoints[i][j] = DotValues.FREE;
            }
        }
    }

    public void setSpot(DotDTO dotDTO) {
        fieldPoints[dotDTO.getX()][dotDTO.getY()] = dotDTO.getDotValues();
    }

    public void printField() {
        System.out.print("\t");

        for (int j = 0; j < fieldPoints.length; j++) {
            System.out.print(j + "\t");
        }
        System.out.println();
        for (int j = 0; j < fieldPoints.length; j++) {
            System.out.print(j + ": ");
            for (int i = 0; i < fieldPoints.length; i++) {
                System.out.print(fieldPoints[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public ArrayList<Dot> getUserDots(DotValues user) {
        ArrayList<Dot> userDots = new ArrayList<Dot>();
        int length = fieldPoints.length;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (fieldPoints[i][j].equals(user)) {
                    userDots.add(new Dot(i, j, user));
                }
            }
        }
        return userDots;
    }

    public Set<Dot> findDotOnX(Queue<Dot> dots) {
        Set<Dot> dotList = new HashSet<>();
        for (Dot dot : dots) {
            for (Dot dot1 : dots) {
                if (dot.getY() == dot1.getY() && dot.getX() != dot1.getX()) {
                    if (dot.getX() - dot1.getX() > 1) {
                        int minX = Math.min(dot.getX(), dot1.getX());
                        int maxX = Math.max(dot.getX(), dot1.getX());
                        for (int i = minX + 1; i < maxX; i++) {
                            dotList.add(new Dot(i, dot.getY(), fieldPoints[i][dot.getY()]));
                        }
                    }
                }
            }
        }
        return dotList;
    }

    public Set<Dot> findDotOnY(Queue<Dot> dots) {
        Set<Dot> dotList = new HashSet<>();
        for (Dot dot : dots) {
            for (Dot dot1 : dots) {
                if (dot.getX() == dot1.getX() && dot.getY() != dot1.getY()) {
                    if (dot.getY() - dot1.getY() > 1) {
                        int minY = Math.min(dot.getY(), dot1.getY());
                        int maxY = Math.max(dot.getY(), dot1.getY());
                        for (int i = minY + 1; i < maxY; i++) {
                            dotList.add(new Dot(dot.getX(), i, fieldPoints[dot.getX()][i]));
                        }
                    }
                }
            }
        }
        return dotList;
    }


    private void paintArea(Queue<Dot> dots) {
        Set<Dot> dotToPaint = Sets.intersection(findDotOnX(dots), findDotOnY(dots));
        for (Dot dot : dotToPaint) {
            blockDot(dot, dots.element().getDotValues());
        }
    }

    private void blockDot(Dot dot, DotValues cycleOwner) {
        if (fieldPoints[dot.getX()][dot.getY()].equals(DotValues.FREE)) {
            fieldPoints[dot.getX()][dot.getY()] =
                    cycleOwner.equals(DotValues.PLAYER1) ? DotValues.BLOCK1 : DotValues.BLOCK2;
        }

        if (!cycleOwner.equals(fieldPoints[dot.getX()][dot.getY()]) &&
                !fieldPoints[dot.getX()][dot.getY()].equals(DotValues.BLOCK1) &&
                !fieldPoints[dot.getX()][dot.getY()].equals(DotValues.BLOCK2)) {
            fieldPoints[dot.getX()][dot.getY()] =
                    cycleOwner.equals(DotValues.PLAYER1) ? DotValues.CAPTURED_BY_PLAYER1 : DotValues.CAPTURED_BY_PLAYER2;
        }
    }

    public Queue<Dot> paintAllCyclesAndReturnUnique(ArrayList<Queue<Dot>> arrayList) {
        ArrayList<Queue<Dot>> cuted = getUniqueCycles(arrayList);
        for (Queue<Dot> p : cuted) {
            System.out.println("start new cycle" + p);
            paintArea(p);
        }
        return findMaxLengthCycle(cuted);
    }

    private Queue<Dot> findMaxLengthCycle(ArrayList<Queue<Dot>> cuted) {

        Collections.sort(cuted, queueSizeComparator);
        return cuted.get(cuted.size() - 1);
    }

    public boolean isQueueHasSameElements(Queue<Dot> first, Queue<Dot> second) {
        if (first.size() != second.size()) {
            return false;
        }

        return new HashSet<Dot>(first).equals(new HashSet<Dot>(second));
    }

    public ArrayList<Queue<Dot>> getUniqueCycles(ArrayList<Queue<Dot>> arrayList) {
        ArrayList<Queue<Dot>> uniqueList = new ArrayList<>(arrayList);
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                if (isQueueHasSameElements(arrayList.get(i), arrayList.get(j))) {
                    uniqueList.remove(arrayList.get(i));
                }
            }
        }
        return uniqueList;
    }


    public int computeCapturedDots(DotValues player) {
        DotValues x;
        if (player.equals(DotValues.PLAYER1)) {
            x = DotValues.CAPTURED_BY_PLAYER1;
        } else {
            x = DotValues.CAPTURED_BY_PLAYER2;
        }
        int result = 0;
        for (DotValues[] rows : fieldPoints) {
            for (DotValues value : rows) {
                if (value.equals(x)) {
                    result++;
                }
            }
        }
        return result;
    }


    public int computeSquareByPlayer(String player) {
        DotValues x;
        if (DotValues.valueOf(player).equals(DotValues.PLAYER1)) {
            x = DotValues.PLAYER1;
        } else {
            x = DotValues.PLAYER2;
        }
        int result = 0;
        for (DotValues[] rows : fieldPoints) {
            for (DotValues value : rows) {

                if (value.isDotBelongsToUser1() && x.equals(DotValues.PLAYER1)
                        || value.isDotBelongsToUser2() && x.equals(DotValues.PLAYER2)) {
                    result++;
                }
            }
        }
        return result;
    }

    public boolean isEmptyFields() {
        for (DotValues[] fieldPoint : fieldPoints) {
            for (DotValues values : fieldPoint) {
                if (values.equals(DotValues.FREE)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int countEmptyFields() {
        int i = 0;
        for (DotValues[] fieldPoint : fieldPoints) {
            for (DotValues values : fieldPoint) {
                if (values.equals(DotValues.FREE)) {
                    i++;
                }
            }
        }
        return i;
    }

    public void blockAllFreeDots() {
        for (int j = 0; j < fieldPoints.length; j++) {
            for (int i = 0; i < fieldPoints.length; i++) {
                if(fieldPoints[i][j].equals(DotValues.FREE))
                {
                    fieldPoints[i][j] = DotValues.BLOCK;
                }
            }
        }
    }
}

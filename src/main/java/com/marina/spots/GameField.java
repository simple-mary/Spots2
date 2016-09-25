package com.marina.spots;

import com.google.common.collect.Sets;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by Marry on 19.09.2016.
 */
public class GameField
{

    private int fieldSize;
    public SpotValues[][] fieldPoints;

    public int getFieldSize()
    {
        return fieldSize;
    }

    private void setFieldSize(int fieldSize)
    {
        this.fieldSize = fieldSize;
    }


    public GameField()
    {
        this.setFieldSize(20);
    }

    public GameField(int fieldSize)
    {
        this.setFieldSize(fieldSize);
    }

    public void initializeField()
    {
        int size = getFieldSize();
        fieldPoints = new SpotValues[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (i == 0 || j == 0 || i == size - 1 || j == size - 1)
                {
                    fieldPoints[i][j] = SpotValues.EDGE;
                } else
                {
                    fieldPoints[i][j] = SpotValues.FREE;
                }
            }
        }
    }

    public void setSpot(JSONObject jsonObject)
    {
        int x = Integer.parseInt(jsonObject.get("x").toString());
        int y = Integer.parseInt(jsonObject.get("y").toString());
        SpotValues user = SpotValues.valueOf(jsonObject.get("user").toString());
        if (fieldPoints[x][y].equals(SpotValues.FREE))
        {
            fieldPoints[x][y] = user;
        } else
        {
            throw new IllegalArgumentException(jsonObject.toString());
        }
    }

    public void printField()
    {
        System.out.print("\t");

        for (int j = 0; j < fieldPoints.length; j++)
        {
            System.out.print(j + "\t");
        }
        System.out.println();
        for (int j = 0; j < fieldPoints.length; j++)
        {
            System.out.print(j + ": ");
            for (int i = 0; i < fieldPoints.length; i++)
            {
                System.out.print(fieldPoints[i][j] + "\t");
            }
            System.out.println();
        }
    }


    public ArrayList<Peak> getUserPeaks(SpotValues user)
    {
        ArrayList<Peak> userSpots = new ArrayList<Peak>();
        int length = fieldPoints.length;

        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < length; j++)
            {
                if (fieldPoints[i][j].equals(user))
                {
                    userSpots.add(new Peak(i, j, user));
                }
            }
        }
        return userSpots;
    }


    public Set<Peak> findPeakOnX(Queue<Peak> peaks)
    {
        Set<Peak> peakList = new HashSet<>();
        for (Peak peak : peaks)
        {
            for (Peak peak1 : peaks)
            {
                if (peak.getY() == peak1.getY() && peak.getX() != peak1.getX())
                {
                    if (peak.getX() - peak1.getX() > 1)
                    {
                        int minX = Math.min(peak.getX(), peak1.getX());
                        int maxX = Math.max(peak.getX(), peak1.getX());
                        for (int i = minX + 1; i < maxX; i++)
                        {
                            peakList.add(new Peak(i, peak.getY(), fieldPoints[i][peak.getY()]));
                        }
                    }
                }
            }
        }
        return peakList;
    }

    public Set<Peak> findPeakOnY(Queue<Peak> peaks)
    {
        Set<Peak> peakList = new HashSet<>();
        for (Peak peak : peaks)
        {
            for (Peak peak1 : peaks)
            {
                if (peak.getX() == peak1.getX() && peak.getY() != peak1.getY())
                {
                    if (peak.getY() - peak1.getY() > 1)
                    {
                        int minY = Math.min(peak.getY(), peak1.getY());
                        int maxY = Math.max(peak.getY(), peak1.getY());
                        for (int i = minY + 1; i < maxY; i++)
                        {
                            peakList.add(new Peak(peak.getX(), i, fieldPoints[peak.getX()][i]));
                        }
                    }
                }
            }
        }
        return peakList;
    }


    private void paintArea(Queue<Peak> peaks)
    {
        Set<Peak> peakToPaint = Sets.intersection(findPeakOnX(peaks), findPeakOnY(peaks));
        for (Peak peak : peakToPaint)
        {
            blockPeak(peak, peaks.element().getSpotValues());
        }
    }

    private void blockPeak(Peak peak, SpotValues cycleOwner)
    {
        if (fieldPoints[peak.getX()][peak.getY()].equals(SpotValues.FREE))
        {
            fieldPoints[peak.getX()][peak.getY()] =
                   cycleOwner.equals(SpotValues.PLAYER1) ? SpotValues.BLOCK1 : SpotValues.BLOCK2;
        }

        if (!cycleOwner.equals(fieldPoints[peak.getX()][peak.getY()]))
        {
            fieldPoints[peak.getX()][peak.getY()] =
                    cycleOwner.equals(SpotValues.PLAYER1) ? SpotValues.CAPTURED_BY_PLAYER1 : SpotValues.CAPTURED_BY_PLAYER2;
        }
    }

    public void paintAllCycles(ArrayList<Queue<Peak>> arrayList)
    {
        ArrayList<Queue<Peak>> cuted = getUniqueCycles(arrayList);
        for (Queue<Peak> p : cuted)
        {
            System.out.println("start new cycle" + p);
            paintArea(p);
        }
    }

    public boolean isQueueHasSameElements(Queue<Peak> first, Queue<Peak> second)
    {
        if (first.size() != second.size())
        {
            return false;
        }

        return new HashSet<Peak>(first).equals(new HashSet<Peak>(second));
    }

    public ArrayList<Queue<Peak>> getUniqueCycles(ArrayList<Queue<Peak>> arrayList)
    {

        /*Iterator<Queue<Peak>> iteratorI = arrayList.iterator();
        Iterator<Queue<Peak>> iteratorJ = arrayList.listIterator();
        while (iteratorI.hasNext())
        {
            Queue<Peak> itI = iteratorI.next();
            while (iteratorJ.hasNext())
            {
                Queue<Peak> itJ = iteratorJ.next();
                if (itI!=itJ && isQueueHasSameElements(itI, itJ))
                {
                    iteratorI.remove();
                    iteratorJ.remove();
                }
            }
        }*/
        ArrayList<Queue<Peak>> uniqueList = new ArrayList<>(arrayList);
        for (int i = 0; i < arrayList.size(); i++)
        {
            for (int j = i+1; j < arrayList.size(); j++)
            {
                if (isQueueHasSameElements(arrayList.get(i), arrayList.get(j)))
                {
                    uniqueList.remove(arrayList.get(i));
                }
            }
        }
        return uniqueList;
    }


    public int computeCapturedSpots(String player)
    {
        SpotValues x;
        if (SpotValues.valueOf(player).equals(SpotValues.PLAYER1))
        {
            x = SpotValues.CAPTURED_BY_PLAYER1;
        } else
        {
            x = SpotValues.CAPTURED_BY_PLAYER2;
        }
        int result = 0;
        for (SpotValues[] rows : fieldPoints)
        {
            for (SpotValues value : rows)
            {
                if (value.equals(x))
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
        if (SpotValues.valueOf(player).equals(SpotValues.PLAYER1))
        {
            x = SpotValues.PLAYER1;
        } else
        {
            x = SpotValues.PLAYER2;
        }
        int result = 0;
        for (SpotValues[] rows : fieldPoints)
        {
            for (SpotValues value : rows)
            {

                if (value.isSpotBelongsToUser1() && x.equals(SpotValues.PLAYER1)
                        || value.isSpotBelongsToUser2() && x.equals(SpotValues.PLAYER2))
                {
                    result++;
                }
            }
        }
        return result;
    }

    public boolean isEmptyFields()
    {
        for (SpotValues[] fieldPoint : fieldPoints)
        {
            for (SpotValues values : fieldPoint)
            {
                if (values.equals(SpotValues.FREE))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public int countEmptyFields()
    {
        int i = 0;
        for (SpotValues[] fieldPoint : fieldPoints)
        {
            for (SpotValues values : fieldPoint)
            {
                if (values.equals(SpotValues.FREE))
                {
                   i++;
                }
            }
        }
        return i;
    }
}

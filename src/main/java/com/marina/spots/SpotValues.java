package com.marina.spots;

/**
 * Created by Marry on 24.09.2016.
 */
enum  SpotValues {
    PLAYER1("P1"),
    PLAYER2("P2"),
    FREE("F"),
    EDGE("E"),
    BLOCK1("B1"),
    BLOCK2("B2"),
    BLOCKED_BY_PLAYER1("X1"),
    BLOCKED_BY_PLAYER2("X2");

    private String value;

    SpotValues(String b2) {
        value = b2;

    }

    public String getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return value;
    }
}


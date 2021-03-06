package com.marina.spots;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by Marry on 24.09.2016.
 */
@JsonSerialize(using = TypeSerializer.class)
public enum DotValues {
    PLAYER1("P1"),
    PLAYER2("P2"),
    FREE("F"),
    EDGE("E"),
    BLOCK1("B1"),
    BLOCK2("B2"),
    BLOCK("B"),
    CAPTURED_BY_PLAYER1("C1"),
    CAPTURED_BY_PLAYER2("C2");

    private String value;

    DotValues(String b2) {
        value = b2;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean isDotBelongsToUser1() {
        return (this.equals(PLAYER1) || this.equals(BLOCK1) || this.equals(CAPTURED_BY_PLAYER1));

    }

    public boolean isDotBelongsToUser2() {
        return (this.equals(PLAYER2) || this.equals(BLOCK2) || this.equals(CAPTURED_BY_PLAYER2));
    }
}


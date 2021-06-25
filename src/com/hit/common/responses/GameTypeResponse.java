package com.hit.common.responses;

public enum GameTypeResponse {
    None(0),
    League(1),
    Trophy(2),
    Playoff(3);

    private final int value;

    private GameTypeResponse(int value) {
        this.value = value;
    }

    public static GameTypeResponse fromValue(int value) {
        for(GameTypeResponse type : GameTypeResponse.values()) {
            if(type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

    public String toString() {
        return switch (this) {
            case None -> "אימון";
            case League -> "ליגה";
            case Trophy -> "גביע";
            case Playoff -> "פלייאוף";
        };
    }


    public int getValue() {
        return value;
    }
}

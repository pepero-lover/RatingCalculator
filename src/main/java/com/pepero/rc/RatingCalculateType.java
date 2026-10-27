package com.pepero.rc;

public enum RatingCalculateType {
    GLICKO_1("Glicko-1"),
    GLICKO_2("Glicko-2"),
    ELO("Elo");

    final String displayName;

    RatingCalculateType(String displayName) {
        this.displayName = displayName;
    }
}

package com.pepero.rc;

/**
 * The match winner constant
 */
public enum MatchWinner {
    PLAYER1(1),
    DRAW(0.5),
    PLAYER2(0);

    private final double matchResultValue;

    MatchWinner(double matchResultValue) {
        this.matchResultValue = matchResultValue;
    }

    public double getMatchResultValue(boolean playerOne) {
        return playerOne ? matchResultValue : 1 - matchResultValue;
    }
}

package com.pepero.rc;

import java.util.Objects;

/**
 * Stores the finished match info for calculating result elo
 */
public class MatchInfo {
    private final PlayerRating player1Rating;
    private final PlayerRating player2Rating;
    private final MatchWinner winner;
    private final RatingCalculateType calculateType;
    private final double tau;

    private static final double DEFAULT_TAU = 0.5;

    private MatchInfo(PlayerRating player1Rating, PlayerRating player2Rating, MatchWinner winner,
                      RatingCalculateType calculateType, double tau) {
        this.player1Rating = player1Rating;
        this.player2Rating = player2Rating;
        this.winner = winner;
        this.calculateType = calculateType;
        this.tau = tau;
    }

    /**
     * Validate players rating calculate type
     *
     * @param expected expected calculate type
     * @param player1 player 1 rating calculate type
     * @param player2 player 2 rating calculate type
     */
    private static void validateCalculateType(RatingCalculateType expected, RatingCalculateType player1,
                                              RatingCalculateType player2) {
        if(expected != player1) {
            throw new IllegalArgumentException("The given player 1 rating isn't " + expected.displayName + " type! (given : "
                    + player1.displayName + ")");
        }

        if(expected != player2) {
            throw new IllegalArgumentException("The given player 2 rating isn't " + expected.displayName + " type! (given : "
                    + player2.displayName + ")");
        }
    }

    /**
     * Constructor for ELO system
     *
     * @param player1Rating player 1 rating
     * @param player2Rating player 2 rating
     * @param winner winner data
     */
    public static MatchInfo elo(PlayerRating player1Rating, PlayerRating player2Rating, MatchWinner winner) {
        validateCalculateType(RatingCalculateType.ELO, player1Rating.calculateType(), player2Rating.calculateType());
        Objects.requireNonNull(winner, "Winner should not be a null!");

        return new MatchInfo(player1Rating, player2Rating, winner, RatingCalculateType.ELO, 0.0);
    }

    /**
     * Constructor for Glicko-1 system
     *
     * @param player1Rating player 1 rating
     * @param player2Rating player 2 rating
     * @param winner winner data
     */
    public static MatchInfo glicko1(PlayerRating player1Rating, PlayerRating player2Rating, MatchWinner winner) {
        validateCalculateType(RatingCalculateType.GLICKO_1, player1Rating.calculateType(), player2Rating.calculateType());
        Objects.requireNonNull(winner, "Winner should not be a null!");

        return new MatchInfo(player1Rating, player2Rating, winner, RatingCalculateType.GLICKO_1, 0.0);
    }

    /**
     * Constructor for Glicko-2 system
     *
     * @param player1Rating player 1 rating
     * @param player2Rating player 2 rating
     * @param winner winner data
     * @param tau tau value (on Glicko-2)
     */
    public static MatchInfo glicko2(PlayerRating player1Rating, PlayerRating player2Rating, MatchWinner winner,
                                    double tau) {
        if (tau <= 0) throw new IllegalArgumentException("The tau value should be a positive number!");
        Objects.requireNonNull(winner, "Winner should not be a null!");

        validateCalculateType(RatingCalculateType.GLICKO_2, player1Rating.calculateType(), player2Rating.calculateType());

        return new MatchInfo(player1Rating, player2Rating, winner, RatingCalculateType.GLICKO_2, tau);
    }

    /**
     * Constructor for Glicko-2 system <br>
     * The {@code tau} value's default value is 0.5. If you want to change, use {@link #glicko2(PlayerRating, PlayerRating, MatchWinner, double)}
     *
     * @param player1Rating player 1 rating
     * @param player2Rating player 2 rating
     * @param winner winner data
     */
    public static MatchInfo glicko2(PlayerRating player1Rating, PlayerRating player2Rating, MatchWinner winner) {
        return glicko2(player1Rating, player2Rating, winner, DEFAULT_TAU);
    }


    PlayerRating player1Rating() {
        return player1Rating;
    }

    PlayerRating player2Rating() {
        return player2Rating;
    }

    MatchWinner winner() {
        return winner;
    }

    RatingCalculateType calculateType() {
        return calculateType;
    }

    double tau() {
        return tau;
    }
}

package com.pepero.rc;

/**
 * Stores the finished match info for calculating result elo
 */
public class MatchInfo {
    private final PlayerRating player1Rating;
    private final PlayerRating player2Rating;
    private final MatchWinner winner;
    private final RatingCalculateType calculateType;
    private final double tau;

    private MatchInfo(PlayerRating player1Rating, PlayerRating player2Rating, MatchWinner winner,
                      RatingCalculateType calculateType, double tau) {
        this.player1Rating = player1Rating;
        this.player2Rating = player2Rating;
        this.winner = winner;
        this.calculateType = calculateType;
        this.tau = tau;
    }

    /**
     * Constructor for ELO system
     *
     * @param player1Rating player 1 rating
     * @param player2Rating player 2 rating
     * @param winner winner data
     */
    public static MatchInfo elo(PlayerRating player1Rating, PlayerRating player2Rating, MatchWinner winner) {
        if(player1Rating.calculateType() != RatingCalculateType.ELO) {
            throw new IllegalArgumentException("The given player 1 rating isn't ELO type! (given : "
                    + player1Rating.calculateType() + ")");
        }
        if(player2Rating.calculateType() != RatingCalculateType.ELO) {
            throw new IllegalArgumentException("The given player 2 rating isn't ELO type! (given : "
                    + player2Rating.calculateType() + ")");
        }

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
        if(player1Rating.calculateType() != RatingCalculateType.GLICKO_1) {
            throw new IllegalArgumentException("The given player 1 rating isn't Glicko-1 type! (given : "
                    + player1Rating.calculateType() + ")");
        }
        if(player2Rating.calculateType() != RatingCalculateType.GLICKO_1) {
            throw new IllegalArgumentException("The given player 2 rating isn't Glicko-1 type! (given : "
                    + player2Rating.calculateType() + ")");
        }

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
        if(player1Rating.calculateType() != RatingCalculateType.GLICKO_2) {
            throw new IllegalArgumentException("The given player 1 rating isn't Glicko-2 type! (given : "
                    + player1Rating.calculateType() + ")");
        }
        if(player2Rating.calculateType() != RatingCalculateType.GLICKO_2) {
            throw new IllegalArgumentException("The given player 2 rating isn't Glicko-2 type! (given : "
                    + player2Rating.calculateType() + ")");
        }

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
        if(player1Rating.calculateType() != RatingCalculateType.GLICKO_2) {
            throw new IllegalArgumentException("The given player 1 rating isn't Glicko-2 type! (given : "
                    + player1Rating.calculateType() + ")");
        }
        if(player2Rating.calculateType() != RatingCalculateType.GLICKO_2) {
            throw new IllegalArgumentException("The given player 2 rating isn't Glicko-2 type! (given : "
                    + player2Rating.calculateType() + ")");
        }

        return new MatchInfo(player1Rating, player2Rating, winner, RatingCalculateType.GLICKO_2, 0.5);
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

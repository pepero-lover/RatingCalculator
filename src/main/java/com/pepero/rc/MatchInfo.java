package com.pepero.rc;

/**
 * Stores the finished match info for calculating result elo
 */
public class MatchInfo {
    private final PlayerRating player1Rating;
    private final PlayerRating player2Rating;
    private final MatchWinner winner;
    private final CalculateType calculateType;
    private final double tau;

    private MatchInfo(PlayerRating player1Rating, PlayerRating player2Rating, MatchWinner winner,
                      CalculateType calculateType, double tau) {
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
        return new MatchInfo(player1Rating, player2Rating, winner, CalculateType.ELO, 0.0);
    }

    /**
     * Constructor for Glicko-1 system
     *
     * @param player1Rating player 1 rating
     * @param player2Rating player 2 rating
     * @param winner winner data
     */
    public static MatchInfo glicko1(PlayerRating player1Rating, PlayerRating player2Rating, MatchWinner winner) {
        return new MatchInfo(player1Rating, player2Rating, winner, CalculateType.GLICKO_1, 0.0);
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
        return new MatchInfo(player1Rating, player2Rating, winner, CalculateType.GLICKO_2, tau);
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
        return new MatchInfo(player1Rating, player2Rating, winner, CalculateType.GLICKO_2, 0.5);
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

    CalculateType calculateType() {
        return calculateType;
    }

    double tau() {
        return tau;
    }
}

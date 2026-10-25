package com.pepero.rc;

public class EloCalculator {
    /**
     * Calculate the player's rating after the game (for elo system only)
     *
     * @param match finished match
     * @return rating dto value
     */
    public static RatingCalculateResult calculate(MatchInfo match) {
        if(match.calculateType() != RatingCalculateType.ELO) throw new
                IllegalArgumentException("The given MatchInfo's type is incorrect! This class calculates only " +
                "Elo system matches! (Glicko system is on com.pepero.rc.GlickoCalculator)");

        PlayerRating player1 = elo(match, true);
        PlayerRating player2 = elo(match, false);

        return new RatingCalculateResult(player1, player2);
    }

    private static PlayerRating elo(MatchInfo match, boolean calculatePlayer1) {
        PlayerRating me = calculatePlayer1 ? match.player1Rating() : match.player2Rating();
        PlayerRating opp = calculatePlayer1 ? match.player2Rating() : match.player1Rating();

        // calculate the difference of rating (delta r)
        double r = me.rating() - opp.rating();

        // calculate the winning chance for player 1

        //             1
        // E = ---------------------
        //      1 + 10 ^ (-r / 400)

        double E = 1 / (1 + Math.pow(10., -r / 400.));

        // calculate new rating

        // r` (new rating) = {original rating} + K * (match result - E)

        double newRating = me.rating() + me.k() * (match.winner().getMatchResultValue(calculatePlayer1) - E);

        return PlayerRating.elo(newRating, me.k());
    }
}

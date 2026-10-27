package com.pepero.rc;

import static com.pepero.rc.Glicko2Calculator.*;

public class GlickoCalculator {
    /**
     * Calculate the player's rating after the game (for glicko-1, glicko-2)
     *
     * @param match finished match
     * @return rating dto value
     */
    public static RatingCalculateResult calculate(MatchInfo match) {
        PlayerRating player1;
        PlayerRating player2;
        if (match.calculateType() == RatingCalculateType.GLICKO_1) {
            player1 = glicko1(match, true);
            player2 = glicko1(match, false);
        }
        else if (match.calculateType() == RatingCalculateType.GLICKO_2){
            player1 = glicko2(match, true);
            player2 = glicko2(match, false);
        } else {
            throw new IllegalArgumentException("The given MatchInfo's type is incorrect! This class calculates only " +
                    "Glicko matches!");
        }

        return new RatingCalculateResult(player1, player2);
    }

    // Glicko-1 constants / functions


    //     ln 10
    // q = ----- =~ 0.0057565
    //      400

    // for equalizing scale constant

    private static final double q = Math.log(10) / 400.;

    //                      1
    // g(RD) = ------------------------------
    //          sqrt(1 + 3q^2(RD)^2) / PI^2)

    // the correction factor for reducing the influence of my rating when the opponent's RD is high

    private static double g1(double rd) {
        return 1 / (Math.sqrt(1 + 3 * q*q * rd*rd / (Math.PI * Math.PI)));
    }

    private static PlayerRating glicko1(MatchInfo match, boolean calculatePlayer1) {
        PlayerRating me = calculatePlayer1 ? match.player1Rating() : match.player2Rating();
        PlayerRating opp = calculatePlayer1 ? match.player2Rating() : match.player1Rating();

        double gOpp = g1(opp.rd());

        // calculate the difference of rating (delta r)
        double r = me.rating() - opp.rating();

        // calculate the winning chance for player 1

        //                       1
        // E = -----------------------------------------
        //      1 + 10 ^ (-g({opponent's rd} * r / 400)

        double E = 1 / (1 + Math.pow(10., -gOpp * r / 400.));


        // calculate the d^2, showing how reliable the information was

        // d^2 = (q^2 * g(opponent's RD)^2 * E * (1 - E)) ^ -1
        double dSquared = 1. / (q * q * gOpp * gOpp * E * (1 - E));

        // calculate new rating

        //                                                      1
        // r` (new rating) = {original rating} + q * ------------------------- * g(oppRD) * (match result - E)
        //                                            (1/{my RD}^2) + (1/d^2)

        double newRating = me.rating() + q * 1 / ((1 / (me.rd() * me.rd())) + (1 / dSquared)) * gOpp *
                (match.winner().getMatchResultValue(calculatePlayer1) - E);

        //                    ( {      1          1   } ^ -1 )
        // RD` (new RD) = sqrt( { ----------- + ----- }      )
        //                    ( {  {my RD}^2     d^2  }      )

        double newRD = 1. / Math.sqrt(1 / (me.rd() * me.rd()) + 1 / dSquared);

        return PlayerRating.glicko1(newRating, newRD);
    }

    private static PlayerRating glicko2(MatchInfo match, boolean calculatePlayer1) {
        PlayerRating me = calculatePlayer1 ? match.player1Rating() : match.player2Rating();
        PlayerRating opp = calculatePlayer1 ? match.player2Rating() : match.player1Rating();

        double mu = me.mu();
        double phi = me.phi();
        double sigma = me.volatility();


        double gPhi = g2(opp.phi());

        // calculate winning chance
        double expVal = E(mu, opp.mu(), gPhi);

        // calculate estimated variance
        // smaller when the game result is so predictable (like 2700 elo vs 200 elo)
        // and bigger when the game result is unpredictable (like 2000 elo vs 2000 elo)
        double vInv = gPhi * gPhi * expVal * (1.0 - expVal);


        double v = 1.0 / vInv;

        double score = match.winner().getMatchResultValue(calculatePlayer1);

        // estimated improvement (delta)
        // the subtracted value between expected winning chance, and the actual result
        double deltaSum = gPhi * (score - expVal);


        double delta = v * deltaSum;

        // calculate the new volatility value (sigma)
        double newSigma = calculateNewVolatility(phi, v, delta, sigma, match.tau());

        // calculate new rd (phi)
        double phiStar = Math.sqrt(phi * phi + newSigma * newSigma);
        double newPhi = 1.0 / Math.sqrt((1.0 / (phiStar * phiStar)) + (1.0 / v));

        // calculate rating value (mu)
        double newMu = mu + (newPhi * newPhi) * deltaSum;

        // and finally, convert the value and return
        double convertedRating = CONVERSION_FACTOR * newMu + 1500.0;
        double convertedRd = CONVERSION_FACTOR * newPhi;

        return PlayerRating.glicko2(convertedRating, convertedRd, newSigma);
    }
}

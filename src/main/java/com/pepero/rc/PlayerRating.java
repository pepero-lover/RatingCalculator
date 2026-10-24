package com.pepero.rc;

import static com.pepero.rc.Glicko2Calculator.CONVERSION_FACTOR;

/**
 * The class containing the value of player's rating and other values like rd, volatility, k
 */
public class PlayerRating {
    private final double rating;
    private final double rd;
    private final double volatility;
    private final double k;

    private PlayerRating(double rating, double rd, double volatility, double k) {
        this.rating = rating;
        this.rd = rd;
        this.volatility = volatility;
        this.k = k;
    }

    /**
     * Constructor for ELO system (use {@link EloCalculator#calculate(MatchInfo)} to calculate)
     *
     * @param rating rating value
     * @param k k value
     */
    public static PlayerRating elo(double rating, double k) {
        return new PlayerRating(rating, 0, 0, k);
    }

    /**
     * Constructor for Glicko-1 system (use {@link GlickoCalculator#calculate(MatchInfo)} to calculate)
     *
     * @param rating rating value
     * @param rd rd value
     */
    public static PlayerRating glicko1(double rating, double rd) {
        return new PlayerRating(rating, rd, -1, 0);
    }

    /**
     * Constructor for Glicko-2 system (use {@link GlickoCalculator#calculate(MatchInfo)} to calculate)
     *
     * @param rating rating value
     * @param rd rd value
     * @param volatility volatility value
     */
    public static PlayerRating glicko2(double rating, double rd, double volatility) {
        return new PlayerRating(rating, rd, volatility, 0);
    }

    double mu() {
        return (this.rating - 1500.0) / CONVERSION_FACTOR;
    }

    double phi() {
        return this.rd / CONVERSION_FACTOR;
    }

    public double rating() {
        return rating;
    }

    public double rd() {
        return rd;
    }

    public double volatility() {
        return volatility;
    }

    public double k() {
        return k;
    }

    @Override
    public String toString() {
        return "rating : " + rating + "  ";
    }
}

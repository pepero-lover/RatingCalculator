package com.pepero.rc;

/**
 * The result of calculated rating value (used on {@link GlickoCalculator}, {@link EloCalculator})
 *
 * @param player1Rating
 * @param player2Rating
 */
public record RatingCalculateResult(
        PlayerRating player1Rating,
        PlayerRating player2Rating
) {
}

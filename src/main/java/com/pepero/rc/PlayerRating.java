package com.pepero.rc;

/**
 * The value of player's rating and rd value
 *
 * @param rating rating value like 2000.0, 1521.3
 * @param rd rating deviation
 */
public record Rating(double rating, double rd) {
}

package com.pepero.rc;

import java.util.function.DoubleUnaryOperator;

final class Glicko2Calculator {
    private Glicko2Calculator() {}

    static final double CONVERSION_FACTOR = 173.7178;
    static final double EPSILON = 0.000001;

    //                    1
    // g(phi) = ------------------------
    //           sqrt(1 + 3phi^2 / PI^2)

    static double g2(double phi) {
        return 1.0 / Math.sqrt(1.0 + 3.0 * phi * phi / (Math.PI * Math.PI));
    }

    //                                   1
    // E(mu, muI, phiI) = --------------------------------
    //                     1 + exp(-g(phiI) * (mu - muI))

    static double E(double mu, double muI, double gPhi) {
        return 1.0 / (1.0 + Math.exp(-gPhi * (mu - muI)));
    }

    static double calculateNewVolatility(double phi, double v, double delta, double sigma, double tau) {
        // a value (going to be converted on returning)
        double a = Math.log(sigma * sigma);

        //         e^x(delta^2 - phi^2 - v - e^x)       x - a
        // f(x) =  ------------------------------  -  --------
        //              2(phi^2 + v + e^x)^2            tau^2

        double tau2 = tau * tau;
        double phi2 = phi * phi;
        double delta2 = delta * delta;

        double base = phi2 + v;

        DoubleUnaryOperator f = (x) -> {
            double ex = Math.exp(x);

            double num1 = ex * (delta2 - base - ex);
            double s = base + ex;
            double den1 = 2.0 * s * s;
            double term2 = (x - a) / tau2;
            return (num1 / den1) - term2;
        };

        // 'A' is negative
        double A = a;

        // 'B' is going to be positive
        double B;

        // if the game result is completely not expected
        if (delta2 > base) {
            B = Math.log(delta2 - base);
            // and its result is positive
        }

        // or just the game result is normal
        else {
            // find the sign changing point
            double k = 1.0;

            // and if the found f(x) value is positive, set the x value to B
            while (f.applyAsDouble(a - k * tau) < 0) {
                k += 1.0;
            }

            B = a - k * tau;
        }

        // and find the range of its roots
        double fA = f.applyAsDouble(A);
        double fB = f.applyAsDouble(B);

        while (Math.abs(B - A) > EPSILON) {
            double C = A + (A - B) * fA / (fB - fA);
            double fC = f.applyAsDouble(C);

            if (fC * fB < 0) {
                A = B;
                fA = fB;
            } else {
                fA = fA / 2.0;
            }
            B = C;
            fB = fC;
        }

        // and should convert to the original value (on 'a' definition, double a = Math.log(sigma * sigma);)
        return Math.exp(A / 2.0);
    }
}
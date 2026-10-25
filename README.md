# Rating Calculator

![Java](https://img.shields.io/badge/Java-21%2B-blue)
![License](https://img.shields.io/badge/License-MIT-green)

## About

A glicko-1, glicko-2, elo (fide) calculator on Java 21

## Example

```java
import com.pepero.rc.*;

public class CalculatorExample {
    public static void main(String[] args) {
        // -------------------
        // |       elo       |
        // -------------------

        RatingCalculateResult eloResult = EloCalculator.calculate(MatchInfo.elo(
                // player 1
                PlayerRating.elo(
                        1589, // rating value
                        20 // k value
                ),

                // player 2
                PlayerRating.elo(
                        1721, // rating value
                        40 // k value
                ),

                // player 2 won in the match
                MatchWinner.PLAYER2
        ));

        System.out.println("Elo system (fide)");
        System.out.println("1589 (with k=20)" + " vs 1721 (with k=40)");
        System.out.println();
        System.out.println("Player 2 won, ");
        System.out.println("Player 1 Rating : " + eloResult.player1Rating().rating());
        System.out.println("Player 2 Rating : " + eloResult.player2Rating().rating());
        System.out.println();

        // -------------------
        // |    glicko-1     |
        // -------------------

        RatingCalculateResult glicko1Result = GlickoCalculator.calculate(MatchInfo.glicko1(
                // player 1
                PlayerRating.glicko1(
                        2102, // rating value
                        60 // rd value
                ),

                // player 2
                PlayerRating.glicko1(
                        1862, // rating value
                        120 // rd value
                ),

                // player 2 won in the match
                MatchWinner.PLAYER2
        ));

        System.out.println("Glicko-1");
        System.out.println("2102 (with rd=60) vs 1862 (with rd=120)");
        System.out.println();
        System.out.println("Player 2 won, ");
        System.out.println("Player 1 Rating : " + glicko1Result.player1Rating().rating());
        System.out.println("Player 2 Rating : " + glicko1Result.player2Rating().rating());
        System.out.println();

        // -------------------
        // |    glicko-2     |
        // -------------------

        RatingCalculateResult glicko2Result = GlickoCalculator.calculate(MatchInfo.glicko2(
                // player 1
                PlayerRating.glicko2(
                        2762, // rating value
                        65, // rd value
                        0.02 // volatility value
                ),

                // player 2
                PlayerRating.glicko2(
                        2691, // rating value
                        72, // rd value
                        0.04 // volatility value
                ),

                // player 1 won in the match
                MatchWinner.PLAYER1
        ));

        System.out.println("Glicko-2");
        System.out.println("2762 (with rd=65, volatility = 0.02) vs 2691 (with rd=72, volatility=0.04)");
        System.out.println();
        System.out.println("Player 1 won, ");
        System.out.println("Player 1 Rating : " + glicko2Result.player1Rating().rating());
        System.out.println("Player 2 Rating : " + glicko2Result.player2Rating().rating());
        System.out.println();
    }
}
```

## License
This project is licensed under the MIT License.
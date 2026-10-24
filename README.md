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
        // Calculates the elo after the game,
        RatingCalculateResult result = EloCalculator.calculate(MatchInfo.elo(
                // player 1's rating is 1589, and k value is 20,
                PlayerRating.elo(1589, 20),
                // and player 2's rating is 1721 and k value is 40.
                PlayerRating.elo(1721, 40),
                // And player 2 won.
                MatchWinner.PLAYER2
        ));

        // prints player 1, player 2 rating
        System.out.print(result.player1Rating());
        System.out.print(result.player2Rating());
    }
}
```

## License
This project is licensed under the MIT License.
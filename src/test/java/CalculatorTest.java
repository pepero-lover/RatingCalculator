import com.pepero.rc.*;

public class CalculatorTest {
    public static void main(String[] args) {
        RatingCalculateResult result = EloCalculator.calculate(MatchInfo.elo(
                PlayerRating.elo(1589, 20),
                PlayerRating.elo(1721, 40),
                MatchWinner.PLAYER2
        ));

        System.out.print(result.player1Rating());
        System.out.print(result.player2Rating());
    }
}

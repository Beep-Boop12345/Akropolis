package comp1140.ass2;

import comp1140.ass2.testdata.ScoreDataLoader;
import comp1140.ass2.testdata.StandardHouseScoreLoader;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
public class StandardCalculateHouseScoresTest extends CalculateScoresTest {
    @Override
    protected ScoreDataLoader fetchScoreLoader() {
        return new StandardHouseScoreLoader();
    }

    @Override
    protected int[] fetchScore(String state) {
        return Akropolis.calculateHouseScores(state);
    }

    @Override
    protected boolean isVariant() {
        return false;
    }
}

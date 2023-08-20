package comp1140.ass2;

import comp1140.ass2.testdata.ScoreDataLoader;
import comp1140.ass2.testdata.StandardTempleScoreLoader;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
public class StandardCalculateTempleScoresTest extends CalculateScoresTest {
    @Override
    protected ScoreDataLoader fetchScoreLoader() {
        return new StandardTempleScoreLoader();
    }

    @Override
    protected int[] fetchScore(String state) {
        return Akropolis.calculateTempleScores(state);
    }

    @Override
    protected boolean isVariant() {
        return false;
    }
}

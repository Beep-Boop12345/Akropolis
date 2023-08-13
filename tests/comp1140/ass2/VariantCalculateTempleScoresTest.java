package comp1140.ass2;

import comp1140.ass2.testdata.ScoreDataLoader;
import comp1140.ass2.testdata.VariantTempleScoreLoader;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
public class VariantCalculateTempleScoresTest extends CalculateScoresTest {
    @Override
    protected ScoreDataLoader fetchScoreLoader() {
        return new VariantTempleScoreLoader();
    }

    @Override
    protected int[] fetchScore(String state) {
        return Akropolis.calculateTempleScores(state);
    }

    @Override
    protected boolean isVariant() {
        return true;
    }
}

package comp1140.ass2;

import comp1140.ass2.testdata.ScoreDataLoader;
import comp1140.ass2.testdata.VariantGardenScoreLoader;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
public class VariantCalculateGardenScoresTest extends CalculateScoresTest {
    @Override
    protected ScoreDataLoader fetchScoreLoader() {
        return new VariantGardenScoreLoader();
    }

    @Override
    protected int[] fetchScore(String state) {
        return Akropolis.calculateGardenScores(state);
    }

    @Override
    protected boolean isVariant() {
        return true;
    }
}

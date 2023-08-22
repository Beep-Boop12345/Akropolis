package comp1140.ass2;

import comp1140.ass2.testdata.ScoreDataLoader;
import comp1140.ass2.testdata.VariantCompleteScoreLoader;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 8000, unit = TimeUnit.MILLISECONDS)
public class VariantCalculateCompleteScoresTest extends CalculateScoresTest {
    @Override
    protected ScoreDataLoader fetchScoreLoader() {
        return new VariantCompleteScoreLoader();
    }

    @Override
    protected int[] fetchScore(String state) {
        return Akropolis.calculateCompleteScores(state);
    }

    @Override
    protected boolean isVariant() {
        return true;
    }
}

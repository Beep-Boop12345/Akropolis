package comp1140.ass2;

import comp1140.ass2.testdata.ScoreDataLoader;
import comp1140.ass2.testdata.VariantHouseScoreLoader;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
public class VariantCalculateHouseScoresTest extends CalculateScoresTest {
    @Override
    protected ScoreDataLoader fetchScoreLoader() {
        return new VariantHouseScoreLoader();
    }

    @Override
    protected int[] fetchScore(String state) {
        return Akropolis.calculateHouseScores(state);
    }

    @Override
    protected boolean isVariant() {
        return true;
    }
}

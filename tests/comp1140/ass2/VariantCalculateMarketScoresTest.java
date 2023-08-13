package comp1140.ass2;

import comp1140.ass2.testdata.ScoreDataLoader;
import comp1140.ass2.testdata.VariantMarketScoreLoader;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
public class VariantCalculateMarketScoresTest extends CalculateScoresTest {
    @Override
    protected ScoreDataLoader fetchScoreLoader() {
        return new VariantMarketScoreLoader();
    }

    @Override
    protected int[] fetchScore(String state) {
        return Akropolis.calculateMarketScores(state);
    }

    @Override
    protected boolean isVariant() {
        return true;
    }
}

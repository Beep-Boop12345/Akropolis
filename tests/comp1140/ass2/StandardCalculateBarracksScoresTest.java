package comp1140.ass2;

import comp1140.ass2.testdata.ScoreDataLoader;
import comp1140.ass2.testdata.StandardBarracksScoreLoader;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 4000, unit = TimeUnit.MILLISECONDS)
public class StandardCalculateBarracksScoresTest extends CalculateScoresTest {
    @Override
    protected ScoreDataLoader fetchScoreLoader() {
        return new StandardBarracksScoreLoader();
    }

    @Override
    protected int[] fetchScore(String state) {
        return Akropolis.calculateBarracksScores(state);
    }

    @Override
    protected boolean isVariant() {
        return false;
    }
}

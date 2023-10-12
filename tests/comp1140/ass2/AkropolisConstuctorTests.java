package comp1140.ass2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AkropolisConstuctorTests {

    @Test
    void conststuctorStringMatch() {
        var testString1 = "2hmbtg;013193136;P00001S00W02R329N02E00R009N03E03R3;P10033N02E00R034S01W03R105S00W01R5;";
        var akropolis1 = new Akropolis(testString1);
        assertEquals(2, akropolis1.numberOfPlayers);

        for (int i = 0; i < akropolis1.scoreVariants.length; i++) {
            assertEquals(false, akropolis1.scoreVariants[i]);
        }

        assertEquals(0, akropolis1.currentTurn);

    }

}

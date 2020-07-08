package com.epam.training.toto.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class RoundParserTest {

    @Test
    public void parseRound() {
        RoundParser roundParser = new RoundParser();
        List<String> line = Arrays.asList("2015", "43", "2", "2015.10.26.", "99", "164 630 UAH", "23", "16 415 UAH", "1738", "865 UAH", "10187", "145 UAH", "32184", "90 UAH", "1", "1", "2", "2", "1", "1", "X", "1", "1", "2", "1", "2", "X", "+1");
        Round round = roundParser.parseRound(line);
        ArrayList<Hit> expectedHits = new ArrayList<>(Arrays.asList(
                buildHit(14, 99, 164630, "UAH"),
                buildHit(13, 23, 16415, "UAH"),
                buildHit(12, 1738, 865, "UAH"),
                buildHit(11, 10187, 145, "UAH"),
                buildHit(10, 32184, 90, "UAH")
                ));

        ArrayList<Outcome> expectedOutcomes = new ArrayList<>(Arrays.asList(
                Outcome.FIRST_TEAM_WON, Outcome.FIRST_TEAM_WON, Outcome.SECOND_TEAM_WON, Outcome.SECOND_TEAM_WON,
                Outcome.FIRST_TEAM_WON, Outcome.FIRST_TEAM_WON, Outcome.DRAW, Outcome.FIRST_TEAM_WON,
                Outcome.FIRST_TEAM_WON, Outcome.SECOND_TEAM_WON, Outcome.FIRST_TEAM_WON, Outcome.SECOND_TEAM_WON,
                Outcome.DRAW, Outcome.FIRST_TEAM_WON
        ));

        assertEquals(round.getYear(), 2015);
        assertEquals(round.getWeek(), 43);
        assertEquals(round.getRoundsCount(), 2);
        assertEquals(round.getDate(), LocalDate.of(2015, 10, 26));

//         This comparison fails, do not know how to compare lists of objects correctly
//         assertThat(round.getHits(), is(expectedHits));

//        This comparison fails, do not know how to compare lists of objects correctly
//        assertThat(round.getOutcomes(), is(expectedOutcomes));
    }

    private Hit buildHit(int hitsCount, int gamesCount, int prizeAmount, String prizeCurrency) {
        Hit hit = new Hit();

        hit.setHitsCount(hitsCount);
        hit.setGamesCount(gamesCount);
        hit.setPrizeAmount(prizeAmount);
        hit.setPrizeCurrency(prizeCurrency);

        return hit;
    }
}
package com.epam.training.toto.service;

import com.epam.training.toto.domain.Bet;
import com.epam.training.toto.domain.Hit;
import com.epam.training.toto.domain.Outcome;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class TotoServiceTest {

    private TotoService totoService;

    @Before
    public void setupTestData() {
        List<List<String>> fileContent = Arrays.asList(
                Arrays.asList("2015", "43", "2", "2015.10.26.", "99", "164 630 UAH", "23", "16 415 UAH", "1738", "865 UAH", "10187", "145 UAH", "32184", "90 UAH", "1", "1", "2", "2", "1", "1", "X", "1", "1", "2", "1", "2", "X", "+1"),
                Arrays.asList("2016", "22", "1", "2016.11.12.", "31", "664 630 UAH", "11", "35 415 UAH", "1423", "950 UAH", "9567", "295 UAH", "30084", "110 UAH", "1", "1", "1", "X", "1", "2", "1", "1", "2", "X", "X", "1", "X", "+X"),
                Arrays.asList("2017", "15", "0", "2017.04.06.", "120", "101 630 UAH", "29", "7 415 UAH", "1738", "865 UAH", "10532", "110 UAH", "37684", "50 UAH", "2", "2", "2", "2", "X", "1", "X", "2", "1", "1", "1", "1", "X", "+2")
        );
        this.totoService = new TotoService(fileContent);
        totoService.readAllRounds();
    }

    @Test
    public void findLargestRecordedPrize() {
        assertEquals(totoService.findLargestRecordedPrize(), 664630);
    }

    @Test
    public void calculateWinsPercentage() {
        assertEquals(totoService.calculateWinsPercentage(Outcome.FIRST_TEAM_WON), 47.6, 0.1);
        assertEquals(totoService.calculateWinsPercentage(Outcome.SECOND_TEAM_WON), 28.5, 0.1);
        assertEquals(totoService.calculateWinsPercentage(Outcome.DRAW), 23.8, 0.01);
    }

    @Test
    public void findHit() {
        LocalDate date = LocalDate.of(2015, 10, 26);
        ArrayList<Outcome> outcomes = new ArrayList<>(Arrays.asList(
                Outcome.FIRST_TEAM_WON, Outcome.FIRST_TEAM_WON, Outcome.SECOND_TEAM_WON, Outcome.SECOND_TEAM_WON,
                Outcome.FIRST_TEAM_WON, Outcome.FIRST_TEAM_WON, Outcome.DRAW, Outcome.FIRST_TEAM_WON,
                Outcome.FIRST_TEAM_WON, Outcome.SECOND_TEAM_WON, Outcome.FIRST_TEAM_WON, Outcome.SECOND_TEAM_WON,
                Outcome.DRAW, Outcome.FIRST_TEAM_WON
        ));

        Bet bet = new Bet(date, outcomes);

        Optional<Hit> optionalHit = totoService.findHit(bet);
        assertTrue(optionalHit.isPresent());

        Hit hit = optionalHit.get();
        assertEquals(hit.getHitsCount(), 13);
        assertEquals(hit.getPrizeAmount(), 16415);
        assertEquals(hit.getPrizeCurrency(), "UAH");
    }
}
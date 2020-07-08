package com.epam.training.toto.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoundParser {
    public static String DATE_FORMAT = "yyyy.MM.dd.";
    public static String DATE_FORMAT_REGEXP = "^\\d{4}.\\d{2}.\\d{2}.$";
    public static String HIT_PRIZE_CURRENCY_REGEXP = "\\s([a-zA-Z]{3})$";

    public RoundParser() {};

    public Round parseRound(List<String> row) {
        Round round = new Round();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        round.setYear(Integer.parseInt(row.get(0)));
        round.setWeek(Integer.parseInt(row.get(1)));

        String roundsCount = row.get(2);
        if (roundsCount.equals("-")) {
            round.setRoundsCount(0);
        } else {
            round.setRoundsCount(Integer.parseInt(roundsCount));
        }

        String date = row.get(3);
        if (date.matches(DATE_FORMAT_REGEXP)) {
            LocalDate localDate = LocalDate.parse(date, formatter);

            round.setDate(localDate);
        }

        round.setHits(parseHits(row));
        round.setOutcomes(parseOutcomes(row));

        return round;
    }

    private ArrayList<Hit> parseHits(List<String> row) {
        ArrayList<Hit> hits = new ArrayList<>();
        int hitsCount = 14;
        int hitGamesCountIndex = 4;
        int hitPrizeIndex = 5;

        String hitPrize = row.get(hitPrizeIndex);
        Pattern hitPrizePattern = Pattern.compile(HIT_PRIZE_CURRENCY_REGEXP);
        Matcher hitPrizeMatcher = hitPrizePattern.matcher(hitPrize);

        while(hitPrizeMatcher.find()) {
            Hit hit = new Hit();
            String currency = hitPrizeMatcher.group(1);

            int hitGamesCount = Integer.parseInt(row.get(hitGamesCountIndex));

            String hitPrizeAmount = hitPrize.replace(currency, "").replace(" ", "");

            hit.setHitsCount(hitsCount);
            hit.setGamesCount(hitGamesCount);
            hit.setPrizeAmount(Integer.parseInt(hitPrizeAmount));

            hit.setPrizeCurrency(currency);

            hits.add(hit);

            hitsCount--;
            hitGamesCountIndex += 2;
            hitPrizeIndex += 2;
            hitPrize = row.get(hitPrizeIndex);
            hitPrizeMatcher = hitPrizePattern.matcher(hitPrize);
        }

        return hits;
    }

    private ArrayList<Outcome> parseOutcomes(List<String> row) {
        ArrayList<Outcome> outcomes = new ArrayList<>();
        int outcomesCount = 13;
        int lastOutcomeIndex = row.size() - 1;
        int firstOutcomeIndex = lastOutcomeIndex - outcomesCount;
        String outcomeCode;

        for (int i = lastOutcomeIndex; i >= firstOutcomeIndex; i--) {
            outcomeCode = row.get(i).replace("+", "");
            Optional<Outcome> outcome = Outcome.findByOutcomeCode(outcomeCode);

            if (outcome.isPresent()) {
                outcomes.add(outcome.get());
            } else {
                System.out.println("OUTCOME NOT FOUND FOR CODE " + outcomeCode);
            }
        }

        // Reverse outcomes as we've started to collect them from the tail
        Collections.reverse(outcomes);

        return outcomes;
    }
}

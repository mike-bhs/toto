package com.epam.training.toto.service;

import com.epam.training.toto.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TotoService {
    private final List<List<String>> fileContent;
    private ArrayList<Round> rounds;

    public TotoService(List<List<String>> fileContent) {
        this.fileContent = fileContent;
        this.rounds = new ArrayList<>();
    }

    public void readAllRounds() {
        RoundParser roundParser = new RoundParser();

        fileContent.stream().forEach((row) -> {
            Round round = roundParser.parseRound(row);
            rounds.add(round);
        });
    }

    public int findLargestRecordedPrize() {
        return rounds
                .stream()
                .flatMap(round -> round.getRecordedHitPrizes().stream())
                .max(Comparator.naturalOrder())
                .get();
    }

    public double calculateWinsPercentage(Outcome outcome) {
        List<Outcome> allOutcomes = rounds
                .stream()
                .flatMap(round -> round.getOutcomes().stream())
                .collect(Collectors.toList());

        double specificOutcomesCount = allOutcomes
                .stream()
                .filter(o -> o.equals(outcome))
                .count();

        return (specificOutcomesCount / allOutcomes.size()) * 100;
    }

    public Optional<Hit> findHit(Bet bet) {
        Optional<Round> optionalRound = rounds.stream().filter(round -> {
            LocalDate roundDate = round.getDate();
            LocalDate betDate = bet.getDate();

            return roundDate != null && roundDate.equals(betDate);
        }).findFirst();
        
        if (optionalRound.isEmpty()) {
            return Optional.empty();
        }
        
        Round round = optionalRound.get();
        final int hitsCount = calculateHitsCount(round.getOutcomes(), bet.getOutcomes());

        return round
                .getHits()
                .stream()
                .filter(hit -> hit.getHitsCount() == hitsCount)
                .findFirst();
    }

    private int calculateHitsCount(ArrayList<Outcome> outcomes1, ArrayList<Outcome> outcomes2) {
        int hitsCount = 0;

        for (int i = 0; i < outcomes1.size() - 1; i++) {
            if (outcomes1.get(i) == outcomes2.get(i)) {
                hitsCount++;
            }
        }

        return hitsCount;
    }
}

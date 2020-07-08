package com.epam.training.toto.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Round {
    @Setter @Getter
    private int year;

    @Setter @Getter
    private int week;

    @Setter @Getter
    private int roundsCount;

    @Setter @Getter
    private LocalDate date;

    @Setter @Getter
    private ArrayList<Hit> hits;

    @Setter @Getter
    private ArrayList<Outcome> outcomes;

    public List<Integer> getRecordedHitPrizes() {
        return hits.stream()
                .filter(hit -> hit.getGamesCount() > 0)
                .map(Hit::getPrizeAmount)
                .collect(Collectors.toList());
    }
}

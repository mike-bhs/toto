package com.epam.training.toto.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bet {
    @Getter
    private LocalDate date;
    @Getter
    private ArrayList<Outcome> outcomes;

    public Bet (LocalDate date, ArrayList<Outcome> outcomes) {
        this.date = date;
        this.outcomes = outcomes;
    }
}

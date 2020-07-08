package com.epam.training.toto.domain;

import lombok.Getter;

import java.util.Optional;

public enum  Outcome {
    FIRST_TEAM_WON("1"),
    SECOND_TEAM_WON("2"),
    DRAW("x");

    @Getter
    public final String outcomeCode;

    Outcome(String outcomeCode) {
        this.outcomeCode = outcomeCode;
    }

    public static Optional<Outcome> findByOutcomeCode(String outcomeCode) {
        for (Outcome outcome : values()) {
            if (outcome.getOutcomeCode().equals(outcomeCode.toLowerCase())) {
                return Optional.of(outcome);
            }
        }

        return Optional.empty();
    }
}

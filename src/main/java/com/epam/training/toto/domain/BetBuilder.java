package com.epam.training.toto.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;

public class BetBuilder {
    public static String DATE_FORMAT = "yyyy.MM.dd";
    public static int REQUIRED_OUTCOMES_LENGTH = 14;
    public static String OUTCOMES_FORMAT_REGEXP = "^(1|2|x){14}$";


    private LocalDate date;
    private ArrayList<Outcome> outcomes;

    public BetBuilder() {}

    public BetBuilder withDate(String date) throws  InvalidBetException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            this.date = LocalDate.parse(date, formatter);

            return this;
        } catch(DateTimeParseException e) {
            throw new InvalidBetException(String.format("Date %s date has invalid format", date));
        }
    }

    public BetBuilder withOutcomes(String outcomeCodes) throws InvalidBetException {
        if (outcomeCodes.length() != REQUIRED_OUTCOMES_LENGTH) {
            throw new InvalidBetException(String.format("Outcomes %s should contain exactly %d characters", outcomeCodes, REQUIRED_OUTCOMES_LENGTH));
        }

        if (outcomeCodes.matches(OUTCOMES_FORMAT_REGEXP)) {
            ArrayList<Outcome> outcomes = new ArrayList<>();

            for (String outcomeCode : outcomeCodes.split("")) {
                Optional<Outcome> outcome = Outcome.findByOutcomeCode(outcomeCode);

                if (outcome.isPresent()) {
                    outcomes.add(outcome.get());
                } else {
                    throw new InvalidBetException(String.format("Outcome %s is invalid", outcomeCode));
                }
            }

            this.outcomes = outcomes;

            return this;
        } else {
            throw new InvalidBetException(String.format("Outcomes %s have invalid format", outcomeCodes));
        }
    }

    public Bet build() {
        return new Bet(date, outcomes);
    }
}

package com.epam.training.toto.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class BetBuilder {
    public static String DATE_FORMAT = "yyyy.MM.dd";
    public static String DATE_FORMAT_REGEXP = "^\\d{4}.\\d{2}.\\d{2}$";
    public static int REQUIRED_OUTCOMES_LENGTH = 14;
    public static String OUTCOMES_FORMAT_REGEXP = "^(1|2|x){14}$";


    private LocalDate date;
    private ArrayList<Outcome> outcomes;

    public BetBuilder() {}

    public BetBuilder withDate(String date) throws InvalidBetException {
        if (date.matches(DATE_FORMAT_REGEXP)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            this.date = LocalDate.parse(date, formatter);

            return this;
        } else {
            throw new InvalidBetException("Date " + date + " has invalid format");
        }
    }

    public BetBuilder withOutcomes(String outcomeCodes) throws InvalidBetException {
        if (outcomeCodes.length() != REQUIRED_OUTCOMES_LENGTH) {
            throw new InvalidBetException("Outcomes " + outcomeCodes + " should contain exactly " + REQUIRED_OUTCOMES_LENGTH + " characters");
        }

        if (outcomeCodes.matches(OUTCOMES_FORMAT_REGEXP)) {
            ArrayList<Outcome> outcomes = new ArrayList<>();

            for (String outcomeCode : outcomeCodes.split("")) {
                Optional<Outcome> outcome = Outcome.findByOutcomeCode(outcomeCode);

                if (outcome.isPresent()) {
                    outcomes.add(outcome.get());
                } else {
                    throw new InvalidBetException("Outcome " + outcomeCode + " is invalid");
                }
            }

            this.outcomes = outcomes;

            return this;
        } else {
            throw new InvalidBetException("Outcomes " + outcomeCodes + " have invalid format");
        }
    }

    public Bet build() {
        return new Bet(date, outcomes);
    }
}

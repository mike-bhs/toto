package com.epam.training.toto.domain;

import lombok.Getter;
import lombok.Setter;

public class Hit {
    @Setter @Getter
    private int hitsCount;

    @Setter @Getter
    private int gamesCount;

    @Setter @Getter
    private int prizeAmount;

    @Setter @Getter
    private String prizeCurrency;
}

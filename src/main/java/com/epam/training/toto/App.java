package com.epam.training.toto;

import com.epam.training.toto.csv.CsvReader;
import com.epam.training.toto.domain.*;
import com.epam.training.toto.service.TotoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public class App {
    public static void main( String[] args ) throws IOException {
        CsvReader csvReader = new CsvReader("materials/toto.csv", ";");
        List<List<String>> fileContent;

        try {
             fileContent = csvReader.readAll();
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            return;
        }

        TotoService totoService = new TotoService(fileContent);
        totoService.readAllRounds();

        printLargestRecordedPrize(totoService);
        printWinsDistribution(totoService);

        Bet bet;

        try {
            bet = readBetFromConsole();
        } catch (IOException e) {
            System.out.println("Failed to read input");
            return;
        } catch (InvalidBetException e) {
            System.out.println("Failed to read your bet: " + e.getMessage());
            return;
        }

        Optional<Hit> hitOptional = totoService.findHit(bet);

        if (hitOptional.isPresent()) {
            Hit hit = hitOptional.get();
            System.out.println("Result: hits: " + hit.getHitsCount() + ", amount: " + hit.getPrizeAmount() + " " + hit.getPrizeCurrency());
        } else {
            System.out.println("Result: better luck next time");
        }
    }

    private static Bet readBetFromConsole() throws IOException, InvalidBetException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter date in following format (YYYY.MM.DD): ");
        String date = reader.readLine().trim().toLowerCase();

        System.out.print("Enter 14 outcomes (1/2/x): ");
        String outcomes = reader.readLine().trim().toLowerCase();

        BetBuilder betBuilder = new BetBuilder();

        return betBuilder
                .withDate(date)
                .withOutcomes(outcomes)
                .build();
    }

    private static void printWinsDistribution(TotoService totoService) {
        System.out.printf("Team #1 won: %.2f\n", totoService.calculateWinsPercentage(Outcome.FIRST_TEAM_WON));
        System.out.printf("Team #2 won: %.2f\n", totoService.calculateWinsPercentage(Outcome.SECOND_TEAM_WON));
        System.out.printf("Draw %.2f\n", totoService.calculateWinsPercentage(Outcome.DRAW));
    }

    private static void printLargestRecordedPrize(TotoService totoService) {
        System.out.println("Largest prize ever recorded: " + totoService.findLargestRecordedPrize());
    }
}

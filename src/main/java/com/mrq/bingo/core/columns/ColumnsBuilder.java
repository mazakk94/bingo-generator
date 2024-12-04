package com.mrq.bingo.core.columns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mrq.bingo.core.BingoStripGenerator.TICKETS_COUNT;
import static com.mrq.bingo.core.BingoStripGenerator.TICKET_COLUMNS_COUNT;

public class ColumnsBuilder {

    private static final int BINGO_NUMBERS_COUNT = 90;

    public List<List<Integer>> prepareShuffledNumbersInColumns() {
        List<Integer> numbers = initializeShuffledList();
        return prepareAvailableNumbersList(numbers);
    }

    public List<List<List<Integer>>> initializeEmptyTickets() {
        // Initialize 6 tickets - each ticket has 9 lists representing future ticket columns
        List<List<List<Integer>>> tickets = new ArrayList<>();
        for (int i = 0; i < TICKETS_COUNT; i++) {
            List<List<Integer>> ticket = new ArrayList<>();
            for (int j = 0; j < TICKET_COLUMNS_COUNT; j++) {
                ticket.add(new ArrayList<>());
            }
            tickets.add(ticket);
        }
        return tickets;
    }

    private List<Integer> initializeShuffledList() {
        // Generate a shuffled list of numbers from 1 to 90
        List<Integer> numbersList = new ArrayList<>();
        for (int i = 1; i <= BINGO_NUMBERS_COUNT; i++) {
            numbersList.add(i);
        }
        Collections.shuffle(numbersList);
        return numbersList;
    }

    private List<List<Integer>> prepareAvailableNumbersList(List<Integer> numbers) {
        // Split into 9 lists grouping them by ticket columns
        List<List<Integer>> availableNumbers = new ArrayList<>();
        for (int i = 0; i < TICKET_COLUMNS_COUNT; i++) {
            availableNumbers.add(new ArrayList<>());
        }
        for (int number : numbers) {
            int columnIndex = getColumnIndexForNumber(number); // Determine the column index (0-8)
            availableNumbers.get(columnIndex).add(number);
        }
        return availableNumbers;
    }

    private int getColumnIndexForNumber(int number) {
        if (number < 10) {
            return 0;
        } else if (number < 20) {
            return 1;
        } else if (number == 90) {
            return 8;
        } else {
            return number / 10;
        }
    }
}

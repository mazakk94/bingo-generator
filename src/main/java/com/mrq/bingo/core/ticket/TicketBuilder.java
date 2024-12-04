package com.mrq.bingo.core.ticket;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.mrq.bingo.core.BingoStripGenerator.TICKETS_COUNT;
import static com.mrq.bingo.core.BingoStripGenerator.TICKET_COLUMNS_COUNT;
import static com.mrq.bingo.core.blanks.BlanksProcessor.TICKET_COLUMN_SIZE;

public class TicketBuilder {

    public static final int NUMBERS_TO_DISTRIBUTE = 6;
    private static final int LAST_COLUMN_INDEX = 8;

    public static void fillEachTicketColumnWithOneNumber(List<List<Integer>> availableNumbers, List<List<List<Integer>>> tickets) {
        for (int i = 0; i < TICKETS_COUNT; i++) {
            for (int col = 0; col < TICKET_COLUMNS_COUNT; col++) {
                insertNumberToColumn(availableNumbers.get(col), tickets.get(i).get(col));
            }
        }
    }

    public static void processNthTicket(List<List<Integer>> availableNumbers, List<List<Integer>> ticket) {
        for (int i = 0; i < NUMBERS_TO_DISTRIBUTE; i++) {
            int columnNumber = getRandomAvailableColumnNumber(availableNumbers, ticket);
            insertNumberToColumn(availableNumbers.get(columnNumber), ticket.get(columnNumber));
        }
    }

    public static void processFourthTicket(List<List<Integer>> availableNumbers, List<List<Integer>> ticket) {
        for (int i = 0; i < NUMBERS_TO_DISTRIBUTE; i++) {
            int columnNumber = getFourthTicketColumnNumber(availableNumbers, ticket);
            insertNumberToColumn(availableNumbers.get(columnNumber), ticket.get(columnNumber));
        }
    }

    public static void processFifthTicket(List<List<Integer>> availableNumbers, List<List<Integer>> ticket) {
        int distributedNumbers = 0;

        // take 2 numbers from each column of available numbers where there are 4 numbers left
        for (int index : getIndicesWithSize(availableNumbers, 4)) {
            // Distribute two numbers
            for (int i = 0; i < 2; i++) {
                insertNumberToColumn(availableNumbers.get(index), ticket.get(index));
                distributedNumbers++;
            }
        }

        // take at least 1 number from columns that has 3 left
        for (int index : getIndicesWithSize(availableNumbers, 3)) {
            if (distributedNumbers < NUMBERS_TO_DISTRIBUTE) {
                insertNumberToColumn(availableNumbers.get(index), ticket.get(index));
                distributedNumbers++;
            } else {
                break;
            }
        }

        // distribute normally what's left
        while (distributedNumbers < NUMBERS_TO_DISTRIBUTE) {
            int columnNumber = getRandomAvailableColumnNumber(availableNumbers, ticket);
            insertNumberToColumn(availableNumbers.get(columnNumber), ticket.get(columnNumber));
            distributedNumbers++;
        }
    }

    private static void insertNumberToColumn(List<Integer> availableNumbersColumn, List<Integer> selectedColumn) {
        int number = availableNumbersColumn.remove(0);
        // Find the correct position to insert the number in ascending order
        int index = 0;
        while (index < selectedColumn.size() && selectedColumn.get(index) < number) {
            index++;
        }
        selectedColumn.add(index, number);
    }

    private static int getFourthTicketColumnNumber(List<List<Integer>> availableNumbers, List<List<Integer>> ticket) {
        int columnNumber;
        if (availableNumbers.get(LAST_COLUMN_INDEX).size() >= 5) {
            columnNumber = LAST_COLUMN_INDEX;
        } else {
            columnNumber = getRandomAvailableColumnNumber(availableNumbers, ticket);
        }
        return columnNumber;
    }

    private static int getRandomAvailableColumnNumber(List<List<Integer>> availableNumbers, List<List<Integer>> ticket) {
        int randomizedColumnNumber;
        boolean columnHasSpaceForNewNumber;
        // we only take columns indices that have existing numbers to distribute
        List<Integer> availableColumnNumbers = getAvailableColumnNumbers(availableNumbers);
        do {
            randomizedColumnNumber = getRandomNumberFromList(availableColumnNumbers);
            columnHasSpaceForNewNumber = ticket.get(randomizedColumnNumber).size() < TICKET_COLUMN_SIZE;
        } while (!columnHasSpaceForNewNumber);
        return randomizedColumnNumber; // return number only if there is space in ticket column to be added
    }

    private static List<Integer> getIndicesWithSize(List<List<Integer>> availableNumbers, int x) {
        return IntStream.range(0, availableNumbers.size())
                .filter(i -> availableNumbers.get(i).size() == x)
                .boxed()
                .toList();
    }

    private static List<Integer> getAvailableColumnNumbers(List<List<Integer>> availableNumbers) {
        return IntStream.range(0, availableNumbers.size())
                .filter(i -> !availableNumbers.get(i).isEmpty())
                .boxed()
                .collect(Collectors.toList());
    }

    private static int getRandomNumberFromList(List<Integer> numbers) {
        int index = ThreadLocalRandom.current().nextInt(0, numbers.size());
        return numbers.get(index);
    }
}

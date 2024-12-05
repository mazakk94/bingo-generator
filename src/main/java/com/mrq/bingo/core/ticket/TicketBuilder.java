package com.mrq.bingo.core.ticket;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.mrq.bingo.core.BingoStripGenerator.*;

public class TicketBuilder {

    private static final int NUMBERS_TO_DISTRIBUTE = 6;
    private static final int LAST_COLUMN_INDEX = 8;
    private static final int FOURTH_TICKET_INDEX = 3;
    private static final int FIFTH_TICKET_INDEX = 4;
    private static final int SIXTH_TICKET_INDEX = 5;

    private final List<List<Integer>> availableNumbers;
    private final List<List<List<Integer>>> ticketsStrip;

    public TicketBuilder(List<List<Integer>> availableNumbers, List<List<List<Integer>>> ticketsStrip) {
        this.availableNumbers = availableNumbers;
        this.ticketsStrip = ticketsStrip;
    }

    public List<List<List<Integer>>> distributeNumbers() {
        fillEachTicketColumnWithOneNumber();
        processFirstThreeTickets();
        processFourthTicket();
        processFifthTicket();
        processSixthTicket();
        return ticketsStrip;
    }

    private void fillEachTicketColumnWithOneNumber() {
        for (int i = 0; i < TICKETS_COUNT; i++) {
            for (int col = 0; col < TICKET_COLUMNS_COUNT; col++) {
                insertNumberToColumn(availableNumbers.get(col), ticketsStrip.get(i).get(col));
            }
        }
    }

    private void processNthTicket(List<List<Integer>> ticket) {
        for (int i = 0; i < NUMBERS_TO_DISTRIBUTE; i++) {
            int columnNumber = getRandomAvailableColumnNumber(ticket);
            insertNumberToColumn(availableNumbers.get(columnNumber), ticket.get(columnNumber));
        }
    }

    private void processFirstThreeTickets() {
        for (int n = 0; n < 3; n++) {
            processNthTicket(ticketsStrip.get(n));
        }
    }

    private void processFourthTicket() {
        List<List<Integer>> ticket = ticketsStrip.get(FOURTH_TICKET_INDEX);
        for (int i = 0; i < NUMBERS_TO_DISTRIBUTE; i++) {
            int columnNumber = getFourthTicketColumnNumber(ticket);
            insertNumberToColumn(availableNumbers.get(columnNumber), ticket.get(columnNumber));
        }
    }

    private void processFifthTicket() {
        List<List<Integer>> ticket = ticketsStrip.get(FIFTH_TICKET_INDEX);
        int distributedNumbers = 0;

        // take 2 numbers from each column of available numbers where there are 4 numbers left
        for (int index : getIndicesWithSize(4)) {
            // Distribute two numbers
            for (int i = 0; i < 2; i++) {
                insertNumberToColumn(availableNumbers.get(index), ticket.get(index));
                distributedNumbers++;
            }
        }

        // take at least 1 number from columns that has 3 left
        for (int index : getIndicesWithSize(3)) {
            if (distributedNumbers < NUMBERS_TO_DISTRIBUTE) {
                insertNumberToColumn(availableNumbers.get(index), ticket.get(index));
                distributedNumbers++;
            } else {
                break;
            }
        }

        // distribute normally what's left
        while (distributedNumbers < NUMBERS_TO_DISTRIBUTE) {
            int columnNumber = getRandomAvailableColumnNumber(ticket);
            insertNumberToColumn(availableNumbers.get(columnNumber), ticket.get(columnNumber));
            distributedNumbers++;
        }
    }

    private void processSixthTicket() {
        processNthTicket(ticketsStrip.get(SIXTH_TICKET_INDEX));
    }

    private void insertNumberToColumn(List<Integer> availableNumbersColumn, List<Integer> selectedColumn) {
        int number = availableNumbersColumn.remove(0);
        // Find the correct position to insert the number in ascending order
        int index = 0;
        while (index < selectedColumn.size() && selectedColumn.get(index) < number) {
            index++;
        }
        selectedColumn.add(index, number);
    }

    private int getFourthTicketColumnNumber(List<List<Integer>> ticket) {
        int columnNumber;
        if (availableNumbers.get(LAST_COLUMN_INDEX).size() >= 5) {
            columnNumber = LAST_COLUMN_INDEX;
        } else {
            columnNumber = getRandomAvailableColumnNumber(ticket);
        }
        return columnNumber;
    }

    private int getRandomAvailableColumnNumber(List<List<Integer>> ticket) {
        int randomizedColumnNumber;
        boolean columnHasSpaceForNewNumber;
        // we only take columns indices that have existing numbers to distribute
        List<Integer> availableColumnNumbers = getAvailableColumnNumbers();
        do {
            randomizedColumnNumber = getRandomNumberFromList(availableColumnNumbers);
            columnHasSpaceForNewNumber = ticket.get(randomizedColumnNumber).size() < TICKET_COLUMN_SIZE;
        } while (!columnHasSpaceForNewNumber);
        return randomizedColumnNumber; // return number only if there is space in ticket column to be added
    }

    private List<Integer> getIndicesWithSize(int size) {
        return IntStream.range(0, availableNumbers.size()).filter(i -> availableNumbers.get(i).size() == size).boxed().toList();
    }

    private List<Integer> getAvailableColumnNumbers() {
        return IntStream.range(0, availableNumbers.size())
                .filter(i -> !availableNumbers.get(i).isEmpty())
                .boxed()
                .collect(Collectors.toList());
    }

    private int getRandomNumberFromList(List<Integer> numbers) {
        int index = ThreadLocalRandom.current().nextInt(0, numbers.size());
        return numbers.get(index);
    }
}

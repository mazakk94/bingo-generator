package com.mrq.bingo.core.blanks;

import java.util.Collections;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BlanksProcessor {

    public static final int TICKET_COLUMN_SIZE = 3;
    private static final int MIDDLE_INDEX = 1;
    private static final int BLANK_FIELD = 0;
    private static final int EXPECTED_BLANKS_COUNT = 4;

    public List<List<List<Integer>>> fillTicketsBlanks(List<List<List<Integer>>> strip) {
        for (List<List<Integer>> ticket : strip) {
            fillBlanks(ticket);
        }
        return strip;
    }

    private void fillBlanks(List<List<Integer>> ticket) {
        fillFirstRowWithBlanks(ticket);
        fillSizeOneColumnsWithBlanks(ticket);
        fillMiddleRowWithBlanks(ticket);
        fillLastRowWithBlanks(ticket);
    }

    private void fillFirstRowWithBlanks(List<List<Integer>> ticket) {
        List<Integer> nonFullColumnsIndices = getShuffledColumnIndicesByRule(ticket, i -> ticket.get(i).size() < TICKET_COLUMN_SIZE);
        for (int i = 0; i < EXPECTED_BLANKS_COUNT; i++) {
            int index = nonFullColumnsIndices.remove(0);
            ticket.get(index).add(0, BLANK_FIELD); // insert blank space as first element
        }
    }

    private void fillSizeOneColumnsWithBlanks(List<List<Integer>> ticket) {
        for (int index : getColumnIndicesByRule(ticket, i -> ticket.get(i).size() == 1)) {
            List<Integer> column = ticket.get(index);
            column.add(BLANK_FIELD);
            column.add(BLANK_FIELD);
        }
    }

    private void fillMiddleRowWithBlanks(List<List<Integer>> ticket) {
        List<Integer> indicesWithTwoNumbers = getShuffledColumnIndicesByRule(ticket, i -> ticket.get(i).size() == 2);
        IntPredicate hasBlankInTheMiddle = index -> ticket.get(index).size() >= 2 && ticket.get(index).get(MIDDLE_INDEX) == BLANK_FIELD;
        List<Integer> indicesWithBlankInTheMiddle = getColumnIndicesByRule(ticket, hasBlankInTheMiddle);
        for (int i = indicesWithBlankInTheMiddle.size(); i < EXPECTED_BLANKS_COUNT; i++) {
            if (!indicesWithTwoNumbers.isEmpty()) {
                int index = indicesWithTwoNumbers.remove(0);
                ticket.get(index).add(MIDDLE_INDEX, BLANK_FIELD); // add blank field in the middle
            }
        }
    }

    private void fillLastRowWithBlanks(List<List<Integer>> ticket) {
        for (List<Integer> column : ticket) {
            if (column.size() < TICKET_COLUMN_SIZE) {
                column.add(BLANK_FIELD);
            }
        }
    }

    private List<Integer> getColumnIndicesByRule(List<List<Integer>> ticket, IntPredicate predicate) {
        return IntStream.range(0, ticket.size())
                .filter(predicate)
                .boxed()
                .collect(Collectors.toList());
    }

    private List<Integer> getShuffledColumnIndicesByRule(List<List<Integer>> ticket, IntPredicate predicate) {
        return IntStream.range(0, ticket.size())
                .filter(predicate)
                .boxed()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> {
                            Collections.shuffle(list);
                            return list;
                        }
                ));
    }
}

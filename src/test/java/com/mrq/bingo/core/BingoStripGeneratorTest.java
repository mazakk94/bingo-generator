package com.mrq.bingo.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BingoStripGeneratorTest {

    @Test
    public void testEachStripContainsSixTickets() {
        List<List<List<Integer>>> bingoStrip = new BingoStripGenerator().generateBingoStrip();
        assertEquals(6, bingoStrip.size(), "Each strip must contain 6 tickets.");
    }

    @Test
    public void testEachTicketContainsNineColumns() {
        List<List<List<Integer>>> bingoStrip = new BingoStripGenerator().generateBingoStrip();
        for (List<List<Integer>> ticket : bingoStrip) {
            assertEquals(9, ticket.size(), "Each ticket must contain 9 columns.");
        }
    }

    @Test
    public void testEachColumnInTicketContainsThreeSortedElements() {
        List<List<List<Integer>>> bingoStrip = new BingoStripGenerator().generateBingoStrip();
        for (List<List<Integer>> ticket : bingoStrip) {
            for (List<Integer> column : ticket) {
                assertEquals(3, column.size(), "Each ticket column must contain 3 elements.");
                assertTrue(isOrderedOmittingZeros(column), "Each ticket column must be ordered ascending.");
            }
        }
    }

    private boolean isOrderedOmittingZeros(List<Integer> list) {
        List<Integer> nonZeroElements = list.stream()
                .filter(num -> num != 0)
                .toList();
        return IntStream.range(0, nonZeroElements.size() - 1)
                .allMatch(i -> nonZeroElements.get(i) <= nonZeroElements.get(i + 1));
    }

    @Test
    public void testTicketColumnsContainsOneTwoOrThreeNumbers() {
        List<List<List<Integer>>> bingoStrip = new BingoStripGenerator().generateBingoStrip();
        for (List<List<Integer>> ticket : bingoStrip) {
            for (List<Integer> column : ticket) {
                long nonZeroCount = column.stream().filter(num -> num != 0).count();
                assertTrue(nonZeroCount >= 1 && nonZeroCount <= 3,
                        "Each ticket column must consist of 1, 2, or 3 numbers.");
            }
        }
    }

    @Test
    public void testEachRowHasFiveNumbersAndFourBlankSpaces() {
        List<List<List<Integer>>> bingoStrip = new BingoStripGenerator().generateBingoStrip();
        for (List<List<Integer>> ticket : bingoStrip) {
            for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
                List<Integer> row = getRow(ticket, rowIndex);
                long nonZeroCount = row.stream()
                        .filter(num -> num != 0)
                        .count();
                assertEquals(5, nonZeroCount, "Each row must have exactly 5 non-zero numbers.");
                long zeroCount = row.stream()
                        .filter(num -> num == 0)
                        .count();
                assertEquals(4, zeroCount, "Each row must have exactly 4 blank spaces.");
            }
        }
    }

    private List<Integer> getRow(List<List<Integer>> columns, int rowIndex) {
        List<Integer> row = new ArrayList<>();
        for (List<Integer> column : columns) {
            if (rowIndex < column.size()) {
                row.add(column.get(rowIndex));
            }
        }
        return row;
    }


    @Test
    public void testColumnsContainProperNumbers() {
        List<List<List<Integer>>> bingoStrip = new BingoStripGenerator().generateBingoStrip();
        List<List<Integer>> columns = transformBingoStripToColumns(bingoStrip);
        validateColumn(columns.get(0), 1, 9);
        validateColumn(columns.get(1), 10, 19);
        validateColumn(columns.get(2), 20, 29);
        validateColumn(columns.get(3), 30, 39);
        validateColumn(columns.get(4), 40, 49);
        validateColumn(columns.get(5), 50, 59);
        validateColumn(columns.get(6), 60, 69);
        validateColumn(columns.get(7), 70, 79);
        validateColumn(columns.get(8), 80, 90);
    }

    private static List<List<Integer>> transformBingoStripToColumns(List<List<List<Integer>>> bingoStrip) {
        // There are always 9 columns in a ticket
        return IntStream.range(0, 9)
                .mapToObj(columnIndex -> bingoStrip.stream()
                        .flatMap(ticket -> ticket.get(columnIndex).stream())
                        .filter(number -> number != 0)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private static void validateColumn(List<Integer> column, int start, int end) {
        List<Integer> expectedNumbers = IntStream.rangeClosed(start, end).boxed().toList();
        assertTrue(column.containsAll(expectedNumbers), "Column is missing numbers. Expected: " + expectedNumbers + ", Actual: " + column);
        assertEquals(expectedNumbers.size(), column.size(), "Column size mismatch. Expected size: " + expectedNumbers.size() + ", Actual size: " + column.size());
    }

    @Test
    public void testNoDuplicates() {
        List<List<List<Integer>>> bingoStrip = new BingoStripGenerator().generateBingoStrip();
        List<Integer> allNumbers = flattenBingoStrip(bingoStrip);
        assertEquals(allNumbers.size(), 90, "The list should have exactly size of 90");
        assertEquals(allNumbers.size(), allNumbers.stream().distinct().count(), "The list contains duplicate elements: " + allNumbers);
    }

    private static List<Integer> flattenBingoStrip(List<List<List<Integer>>> bingoStrip) {
        return bingoStrip.stream()
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .filter(number -> number != 0)
                .collect(Collectors.toList());
    }

}
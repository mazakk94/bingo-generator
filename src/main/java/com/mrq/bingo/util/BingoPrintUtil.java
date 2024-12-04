package com.mrq.bingo.util;

import java.util.List;

public class BingoPrintUtil {

    public static void printBingoStrip(List<List<List<Integer>>> tickets) {
        //  Output the results rows
        for (int i = 0; i < tickets.size(); i++) {
            System.out.println("Ticket " + (i + 1) + ":");
            List<List<Integer>> ticket = tickets.get(i);
            printRow(ticket, 0);
            printRow(ticket, 1);
            printRow(ticket, 2);
            System.out.println();
        }
    }

    private static void printRow(List<List<Integer>> ticket, int row) {
        for (List<Integer> ticketColumn : ticket) {
            int columnValue = ticketColumn.size() > row ? ticketColumn.get(row) : 0;
            String ticketColumnValueString = columnValue < 10 ? " " + columnValue : String.valueOf(columnValue);
            System.out.print(ticketColumnValueString + " ");
        }
        System.out.println();
    }

}



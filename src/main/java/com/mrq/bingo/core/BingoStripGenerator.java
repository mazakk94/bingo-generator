package com.mrq.bingo.core;

import com.mrq.bingo.core.blanks.BlanksProcessor;
import com.mrq.bingo.core.columns.ColumnsBuilder;
import com.mrq.bingo.core.ticket.TicketBuilder;

import java.util.List;


public class BingoStripGenerator {
    public static final int TICKET_COLUMNS_COUNT = 9;
    public static final int TICKETS_COUNT = 6;

    private static final int FOURTH_TICKET_INDEX = 3;
    private static final int FIFTH_TICKET_INDEX = 4;
    private static final int SIXTH_TICKET_INDEX = 5;

    public static List<List<List<Integer>>> generateBingoStrip() {
        List<List<Integer>> availableNumbers = prepareNumbers();
        List<List<List<Integer>>> ticketsStrip = initializeTickets();
        distributeNumbers(availableNumbers, ticketsStrip);
        fillBlanks(ticketsStrip);
        return ticketsStrip;
    }

    private static List<List<Integer>> prepareNumbers() {
        return ColumnsBuilder.prepareShuffledNumbersInColumns();
    }

    private static List<List<List<Integer>>> initializeTickets() {
        return ColumnsBuilder.initializeEmptyTickets();
    }

    private static void distributeNumbers(List<List<Integer>> availableNumbers, List<List<List<Integer>>> ticketsStrip) {
        TicketBuilder.fillEachTicketColumnWithOneNumber(availableNumbers, ticketsStrip);
        for (int n = 0; n < 3; n++) {
            TicketBuilder.processNthTicket(availableNumbers, ticketsStrip.get(n));
        }
        TicketBuilder.processFourthTicket(availableNumbers, ticketsStrip.get(FOURTH_TICKET_INDEX));
        TicketBuilder.processFifthTicket(availableNumbers, ticketsStrip.get(FIFTH_TICKET_INDEX));
        TicketBuilder.processNthTicket(availableNumbers, ticketsStrip.get(SIXTH_TICKET_INDEX));
    }

    private static void fillBlanks(List<List<List<Integer>>> ticketsStrip) {
        BlanksProcessor.fillTicketsBlanks(ticketsStrip);
    }


}

package com.mrq.bingo.core;

import com.mrq.bingo.core.blanks.BlanksProcessor;
import com.mrq.bingo.core.columns.ColumnsBuilder;
import com.mrq.bingo.core.ticket.TicketBuilder;

import java.util.List;

public class BingoStripGenerator {
    public static final int TICKET_COLUMNS_COUNT = 9;
    public static final int TICKETS_COUNT = 6;

    public List<List<List<Integer>>> generateBingoStrip() {
        ColumnsBuilder columnsBuilder = new ColumnsBuilder();
        List<List<List<Integer>>> ticketsStrip = columnsBuilder.initializeEmptyTickets();
        ticketsStrip = new TicketBuilder(columnsBuilder.prepareShuffledNumbersInColumns(), ticketsStrip).distributeNumbers();
        ticketsStrip = new BlanksProcessor().fillTicketsBlanks(ticketsStrip);
        return ticketsStrip;
    }
}

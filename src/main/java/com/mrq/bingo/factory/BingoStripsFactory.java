package com.mrq.bingo.factory;

import com.mrq.bingo.core.BingoStripGenerator;
import com.mrq.bingo.util.BingoPrintUtil;

import java.util.List;

public class BingoStripsFactory {

    public static void generateBingoStrips(int count) {
        List<List<List<Integer>>> bingoStrip;
        for (int i = 0; i < count; i++) {
            if (i == 0) {
                bingoStrip = BingoStripGenerator.generateBingoStrip();
                BingoPrintUtil.printBingoStrip(bingoStrip);
            } else {
                BingoStripGenerator.generateBingoStrip();
            }
        }
    }
}

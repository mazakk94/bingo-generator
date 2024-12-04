package com.mrq.bingo.factory;

import com.mrq.bingo.core.BingoStripGenerator;
import com.mrq.bingo.util.BingoPrintUtil;

import java.util.List;

public class BingoStripsFactory {

    public void generateBingoStrips(int count) {
        List<List<List<Integer>>> bingoStrip;
        for (int i = 0; i < count; i++) {
            if (i == 0) {
                bingoStrip = new BingoStripGenerator().generateBingoStrip();
                BingoPrintUtil.printBingoStrip(bingoStrip);
            } else {
                new BingoStripGenerator().generateBingoStrip();
            }
        }
    }
}

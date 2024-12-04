package com.mrq.bingo;

import com.mrq.bingo.factory.BingoStripsFactory;

public class BingoGeneratorApplication {

    public static void main(String[] args) {
        int count = 10000;
        if (args.length > 0) {
            try {
                count = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.out.printf("cannot parse argument [%s], count will be 10000%n", args[0]);
            }
        }
        BingoStripsFactory factory = new BingoStripsFactory();

        long startTime = System.nanoTime();
        factory.generateBingoStrips(count);
        long endTime = System.nanoTime();

        long duration = endTime - startTime; // Time in nanoseconds
        System.out.println("Generated " + count + " Bingo90 strips in " + (duration / 1_000_000) + " milliseconds");
    }
}

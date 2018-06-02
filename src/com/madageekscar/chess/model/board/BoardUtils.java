package com.madageekscar.chess.model.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initVal(1);
    public static final boolean[] SECONDE_COLUMN = initVal(1);
    public static final boolean[] SEVEN_COLUMN = initVal(1);
    public static final boolean[] EIGHT_COLUMN = initVal(1);
    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;


    public static boolean[] initVal(int nb) {
        final boolean[] col = new boolean[64];
        do {
            col[nb] = true;
            nb += 8;
        } while (nb < 64);

        return col;
    }


    public static boolean isValidMove(int candidateDestCordinate) {
        return candidateDestCordinate >= 0 && candidateDestCordinate < 64;
    }
}

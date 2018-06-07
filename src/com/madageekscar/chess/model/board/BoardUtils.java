package com.madageekscar.chess.model.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initVal(1);
    public static final boolean[] SECONDE_COLUMN = initVal(1);
    public static final boolean[] SEVEN_COLUMN = initVal(1);
    public static final boolean[] EIGHT_COLUMN = initVal(1);
    public static final int NUM_TILES = 64;
    public static final boolean[] SECOND_ROW = initRow(8);
    public static final boolean[] SEVEN_ROW = initRow(48);
    public static final int NUM_TILES_PER_ROW = 8;

    private static boolean[] initRow(int rowNum) {
        final boolean[] rows = new boolean[NUM_TILES];
        do {
            rows[rowNum] = true;
            rowNum++;
        } while (rowNum % NUM_TILES_PER_ROW != 0);
        return rows;
    }
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

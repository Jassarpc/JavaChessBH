package com.madageekscar.chess.model.board;

public class BoardUtils {


    public static final boolean[] FIRST_COLUMN = initVal(1);
    public static final boolean[] SECONDE_COLUMN = initVal(1);
    public static final boolean[] SEVEN_COLUMN = initVal(1);
    public static final boolean[] EIGHT_COLUMN = initVal(1);
    public static final int NUM_TILES = 64;

    public static final boolean[] EIGHTH_RANK = initRow(0);
    public static final boolean[] SEVENTH_RANK = initRow(8);
    public static final boolean[] SIXTH_RANK = initRow(16);
    public static final boolean[] FIFTH_RANK = initRow(24);
    public static final boolean[] FOURTH_RANK = initRow(32);
    public static final boolean[] THIRD_RANK = initRow(40);
    public static final boolean[] SECOND8RANK = initRow(48);
    public static final boolean[] FIRST_RANK = initRow(56);
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

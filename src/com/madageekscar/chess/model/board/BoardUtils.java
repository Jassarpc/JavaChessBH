package com.madageekscar.chess.model.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = null;
    public static final boolean[] SECONDE_COLUMN = null;

    public static boolean isValidMove(int candidateDestCordinate) {
        return candidateDestCordinate >= 0 && candidateDestCordinate < 64;
    }
}

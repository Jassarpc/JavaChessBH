package com.madageekscar.chess.main;

import com.madageekscar.chess.model.board.Board;

public class Launcher {
    public static void main(String[] i) {
        Board board = Board.createStandardBoard();
        System.out.println(board);
        System.out.println(board.getTile(2).getPiece().calculateLegalMove(board).toArray().length);
    }

}

package com.madageekscar.chess.model.board;

import com.madageekscar.chess.model.pieces.Piece;

public class Move {
    final Board board;
    final Piece movedPiece;
    final int destCordinate;

    private Move(Board board, Piece movedPiece, int destCordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destCordinate = destCordinate;
    }

    public final static class MajorMove extends Move {

        public MajorMove(final Board board, final Piece movedPiece, final int destCordinate) {
            super(board, movedPiece, destCordinate);
        }
    }

    public final static class AttackMove extends Move {
        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece, final int destCordinate, final Piece attackedPiece) {
            super(board, movedPiece, destCordinate);
            this.attackedPiece = attackedPiece;
        }
    }
}

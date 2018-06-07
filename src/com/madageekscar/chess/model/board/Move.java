package com.madageekscar.chess.model.board;

import com.madageekscar.chess.model.pieces.Piece;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destCordinate;

    private Move(Board board, Piece movedPiece, int destCordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destCordinate = destCordinate;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public int getDestCordinate() {
        return destCordinate;
    }

    public abstract Board execute();

    public final static class MajorMove extends Move {

        public MajorMove(final Board board, final Piece movedPiece, final int destCordinate) {
            super(board, movedPiece, destCordinate);
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            for (final Piece piece : this.board.currentPlayer().getActivePiece()) {
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePiece()) {
                builder.setPiece(piece);
            }
            builder.setPiece(null);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }

    public final static class AttackMove extends Move {
        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece, final int destCordinate, final Piece attackedPiece) {
            super(board, movedPiece, destCordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
}

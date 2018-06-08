package com.madageekscar.chess.model.board;

import com.madageekscar.chess.model.pieces.Piece;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destCordinate;

    //public final
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
        builder.setPiece(this.movedPiece.movedPiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    public int getCurrentCord() {
        return this.movedPiece.getCordinate();
    }

    public final static class MajorMove extends Move {

        public MajorMove(final Board board, final Piece movedPiece, final int destCordinate) {
            super(board, movedPiece, destCordinate);
        }

    }

    public static class AttackMove extends Move {
        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece, final int destCordinate, final Piece attackedPiece) {
            super(board, movedPiece, destCordinate);
            this.attackedPiece = attackedPiece;
        }
    }

    public static abstract class CasteleMove extends Move {

        private CasteleMove(Board board, Piece movedPiece, int destCordinate) {
            super(board, movedPiece, destCordinate);
        }
    }

    public static final class PawnMove extends Move {

        private PawnMove(Board board, Piece movedPiece, int destCordinate) {
            super(board, movedPiece, destCordinate);
        }
    }

    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(Board board, Piece movedPiece, int destCordinate, Piece attackedPiece) {
            super(board, movedPiece, destCordinate, attackedPiece);
        }
    }

    public static final class PawnEnPassantAttackMove extends PawnAttackMove {

        public PawnEnPassantAttackMove(Board board, Piece movedPiece, int destCordinate, Piece attackedPiece) {
            super(board, movedPiece, destCordinate, attackedPiece);
        }
    }

    public static final class PawnJump extends Move {

        private PawnJump(Board board, Piece movedPiece, int destCordinate) {
            super(board, movedPiece, destCordinate);
        }
    }

    public static final class KingCastleMove extends CasteleMove {

        private KingCastleMove(Board board, Piece movedPiece, int destCordinate) {
            super(board, movedPiece, destCordinate);
        }
    }

    public static final class QueenCastleMove extends CasteleMove {

        private QueenCastleMove(Board board, Piece movedPiece, int destCordinate) {
            super(board, movedPiece, destCordinate);
        }
    }

    public static final class NullMove extends Move {

        private NullMove(Board board, Piece movedPiece, int destCordinate) {
            super(board, movedPiece, destCordinate);
        }
    }

    public static class MoveFactory {
        private MoveFactory() {
            throw new RuntimeException("Not instanciable");
        }

        public static Move createMove(final Board board, final int cordinate, final int destcord) {
            for (final Move move : board.getAllLegalMoves()) {
                if (move.g etCurrentCord() == 0){

                }
            }
            return null;
        }

    }
}

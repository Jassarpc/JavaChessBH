package com.madageekscar.chess.model.board;

import com.madageekscar.chess.model.pieces.Pawn;
import com.madageekscar.chess.model.pieces.Piece;
import com.madageekscar.chess.model.pieces.Rook;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destCordinate;
    public static final Move NULL_MOVE = new NullMove();
    //public final
    private Move(Board board, Piece movedPiece, int destCordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destCordinate = destCordinate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.destCordinate;
        result = prime * result + this.movedPiece.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Move)) {
            return false;
        }
        final Move oth = (Move) obj;
        return getDestCordinate() == oth.getDestCordinate() && getMovedPiece() == oth.getMovedPiece();
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public boolean isAttack() {
        return false;
    }

    public boolean isCatlingMove() {
        return false;
    }

    public Piece getAttackedPiece() {
        return null;
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

        @Override
        public Board execute() {
            return null;
        }


        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AttackMove)) {
                return false;
            }
            final AttackMove oth = (AttackMove) obj;
            return super.equals(oth) && getAttackedPiece().equals(obj);
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }

        @Override
        public boolean isAttack() {
            return true;
        }

    }

    public static abstract class CasteleMove extends Move {
        protected final Rook castleRook;
        protected final int castleRookDest;
        protected final int castleStart;

        private CasteleMove(Board board, Piece movedPiece, int destCordinate, int castleRookDest, Rook castleRook, int castleStart) {
            super(board, movedPiece, destCordinate);
            this.castleRook = castleRook;
            this.castleRookDest = castleRookDest;
            this.castleStart = castleStart;
        }

        public Rook getCastleRook() {
            return castleRook;
        }

        @Override
        public Board execute() {
            Board.Builder builder = new Board.Builder();
            for (final Piece piece : this.board.currentPlayer().getActivePiece()) {
                if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePiece()) {
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movedPiece(this));
            builder.setPiece(new Rook(this.castleRook.getAlliance(), this.castleRookDest));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

            return builder.build();
        }

        @Override
        public boolean isCatlingMove() {
            return true;
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
            final Pawn movedPawn = (Pawn) this.movedPiece.movedPiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }

    public static final class KingSideCastleMove extends CasteleMove {


        public KingSideCastleMove(Board board, Piece movedPiece, int destCordinate, int castleRookDest, Rook castleRook, int castleStart) {
            super(board, movedPiece, destCordinate, castleRookDest, castleRook, castleStart);
        }

    }

    public static final class QueenSideCastleMove extends CasteleMove {

        public QueenSideCastleMove(Board board, Piece movedPiece, int destCordinate, int castleRookDest, Rook castleRook, int castleStart) {
            super(board, movedPiece, destCordinate, castleRookDest, castleRook, castleStart);
        }
    }

    public static final class NullMove extends Move {

        private NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Not executable invalid destination");
        }
    }


    public static class MoveFactory {
        private MoveFactory() {
            throw new RuntimeException("Not instanciable");
        }

        public static Move createMove(final Board board, final int cordinate, final int destcord) {
            for (final Move move : board.getAllLegalMoves()) {
                if (move.getCurrentCord() == cordinate && move.destCordinate == destcord) {
                    return move;
                }
            }
            return NULL_MOVE;
        }

    }
    //public abstract Collection<Move> calculateKingCastle();
}

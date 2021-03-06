package com.madageekscar.chess.model.pieces;

import com.madageekscar.chess.model.Alliance;
import com.madageekscar.chess.model.board.Board;
import com.madageekscar.chess.model.board.Move;

import java.util.Collection;

public abstract class Piece {
    protected final int cordinate;
    protected final Alliance alliance;
    protected final boolean isFirstMove;
    protected final PieceType pieceType;
    private final int cachedHashCode;

    Piece(int cordinate, Alliance alliance, PieceType pieceType) {
        this.cordinate = cordinate;
        this.alliance = alliance;
        this.isFirstMove = true;
        this.pieceType = pieceType;
        this.cachedHashCode = computeHashCode();
    }

    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + alliance.hashCode();
        result = 31 * result + cordinate;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    public int getCachedHashCode() {
        return cachedHashCode;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public abstract Piece movedPiece(Move move);

    public boolean isFirstMove() {
        return this.isFirstMove;
    }
    public abstract Collection<Move> calculateLegalMove(final Board board);

    public int getCordinate() {
        return cordinate;
    }

    @Override
    public int hashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + alliance.hashCode();
        result = 31 * result + cordinate;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) obj;
        return cordinate == ((Piece) obj).cordinate &&
                pieceType == ((Piece) obj).getPieceType() &&
                alliance == ((Piece) obj).getAlliance() &&
                isFirstMove == ((Piece) obj).isFirstMove();
    }


    public enum PieceType {
        PAWN("P") {
            @Override
            public boolean isPawn() {
                return true;
            }

            @Override
            public boolean isBishop() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return true;
            }
        };

        private final String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public abstract boolean isPawn();

        public abstract boolean isBishop();

        public abstract boolean isRook();

        public abstract boolean isKing();
    }

    public Alliance getAlliance() {
        return alliance;
    }

}

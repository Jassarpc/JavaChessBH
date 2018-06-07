package com.madageekscar.chess.model.pieces;

import com.google.common.collect.ImmutableList;
import com.madageekscar.chess.model.Alliance;
import com.madageekscar.chess.model.board.Board;
import com.madageekscar.chess.model.board.BoardUtils;
import com.madageekscar.chess.model.board.Move;
import com.madageekscar.chess.model.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece {
    private final static int[] CANDIDATE_MOVE = {-9, -8, -1, 1, 8, 9, 7};

    public King(final Alliance alliance, final int cordinate) {
        super(cordinate, alliance, PieceType.KING);
    }

    private static boolean isFirstColumn(final int cordinate, final int candidateCordinate) {
        return BoardUtils.FIRST_COLUMN[cordinate] && ((candidateCordinate == -9) ||
                (candidateCordinate == -1) || (candidateCordinate == 7));
    }

    private static boolean isEightColumn(final int cordinate, final int candidateCordinate) {
        return BoardUtils.EIGHT_COLUMN[cordinate] && ((candidateCordinate == -7) ||
                (candidateCordinate == 1) || (candidateCordinate == 9));
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    @Override
    public Piece movedPiece(Move move) {
        return new King(this.alliance, move.getDestCordinate());
    }

    @Override
    public Collection<Move> calculateLegalMove(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE) {
            final int candidateDestCordinate = this.cordinate + currentCandidateOffset;
            if (BoardUtils.isValidMove(candidateDestCordinate)) {


                if (isEightColumn(cordinate, candidateDestCordinate) || isEightColumn(cordinate, candidateDestCordinate)) {
                    continue;
                }
                final Tile candidateDestTile = board.getTile(candidateDestCordinate);
                if (!candidateDestTile.isOccuped()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestCordinate));
                } else {
                    final Piece pieceAtDest = candidateDestTile.getPiece();
                    final Alliance pAlliance = pieceAtDest.getAlliance();
                    if (this.getAlliance() != pAlliance) {
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestCordinate, pieceAtDest));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}


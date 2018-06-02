package com.madageekscar.chess.model.pieces;

import com.google.common.collect.ImmutableList;
import com.madageekscar.chess.model.Alliance;
import com.madageekscar.chess.model.board.Board;
import com.madageekscar.chess.model.board.BoardUtils;
import com.madageekscar.chess.model.board.Move;
import com.madageekscar.chess.model.board.Tile;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    private final static int[] CANDIDATE_MOVE_CORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(int cordinate, Alliance alliance) {
        super(cordinate, alliance);
    }

    private static boolean isFirstColumn(final int cordinate, final int candidateCordinate) {
        return BoardUtils.FIRST_COLUMN[cordinate] && ((candidateCordinate == -17) ||
                (candidateCordinate == -10) || (candidateCordinate == 6) ||
                (candidateCordinate == 15));
    }

    private static boolean isSecondeColumn(final int cordinate, final int candidateCordinate) {
        return BoardUtils.FIRST_COLUMN[cordinate] && ((candidateCordinate == -17) ||
                (candidateCordinate == -10) || (candidateCordinate == 6) ||
                (candidateCordinate == 15));
    }

    @Override
    public List<Move> calculateLegalMove(Board board) {
        int candidateDestCordinate;
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidate : CANDIDATE_MOVE_CORDINATES) {
            candidateDestCordinate = this.cordinate + currentCandidate;

            if (BoardUtils.isValidMove(candidateDestCordinate)) {
                final Tile candidateDestTile = board.getTile(candidateDestCordinate);

                if (isFirstColumn(cordinate, candidateDestCordinate)) {
                    continue;
                }
                if (!candidateDestTile.isOccuped()) {
                    legalMoves.add(new Move());
                } else {
                    final Piece pieceAtDest = candidateDestTile.getPiece();
                    final Alliance pAlliance = pieceAtDest.getAlliance();
                    if (this.getAlliance() != pAlliance) {
                        legalMoves.add(new Move());
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}

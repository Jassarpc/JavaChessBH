package com.madageekscar.chess.model.pieces;

import com.google.common.collect.ImmutableList;
import com.madageekscar.chess.model.Alliance;
import com.madageekscar.chess.model.board.Board;
import com.madageekscar.chess.model.board.BoardUtils;
import com.madageekscar.chess.model.board.Move;
import com.madageekscar.chess.model.board.Move.AttackMove;
import com.madageekscar.chess.model.board.Move.MajorMove;
import com.madageekscar.chess.model.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {
    private final static int[] CANDIDATE_MOVE_CORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(Alliance alliance, int cordinate) {
        super(cordinate, alliance, PieceType.KNIGHT);
    }


    private static boolean isFirstColumn(final int cordinate, final int candidateCordinate) {
        return BoardUtils.FIRST_COLUMN[cordinate] && ((candidateCordinate == -17) ||
                (candidateCordinate == -10) || (candidateCordinate == 6) ||
                (candidateCordinate == 15));
    }


    private static boolean isSecondeColumn(final int cordinate, final int candidateCordinate) {
        return BoardUtils.SECONDE_COLUMN[cordinate] && ((candidateCordinate == -10) || (candidateCordinate == 6));
    }

    private static boolean isEightColumn(final int cordinate, final int candidateCordinate) {
        return BoardUtils.EIGHT_COLUMN[cordinate] && ((candidateCordinate == 17) ||
                (candidateCordinate == 10) || (candidateCordinate == -6) ||
                (candidateCordinate == -15));
    }
    private static boolean isSevenColumn(final int cordinate, final int candidateCordinate) {
        return BoardUtils.SEVEN_COLUMN[cordinate] && ((candidateCordinate == 10) || (candidateCordinate == -6));
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

    @Override
    public Piece movedPiece(Move move) {
        return new Knight(this.alliance, move.getDestCordinate());
    }

    @Override
    public Collection<Move> calculateLegalMove(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidate : CANDIDATE_MOVE_CORDINATES) {
            final int candidateDestCordinate = this.cordinate + currentCandidate;

            if (BoardUtils.isValidMove(candidateDestCordinate)) {
                final Tile candidateDestTile = board.getTile(candidateDestCordinate);

                if (isFirstColumn(cordinate, candidateDestCordinate)
                        || isSecondeColumn(cordinate, candidateDestCordinate)
                        || isEightColumn(cordinate, candidateDestCordinate)
                        || isSevenColumn(cordinate, candidateDestCordinate)) {
                    continue;
                }
                if (!candidateDestTile.isOccuped()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestCordinate));
                } else {
                    final Piece pieceAtDest = candidateDestTile.getPiece();
                    final Alliance pAlliance = pieceAtDest.getAlliance();
                    if (this.getAlliance() != pAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestCordinate, pieceAtDest));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}

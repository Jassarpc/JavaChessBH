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

public class Rook extends Piece {
    private final static int[] CANDIDATE_MOVE = {-8, -1, 1, 8};

    public Rook(Alliance alliance, int cordinate, PieceType pieceType) {
        super(cordinate, alliance, pieceType);
    }

    public static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
    }

    public static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == 1);
    }

    @Override
    public Piece movedPiece(Move move) {
        return new Rook(alliance, move.getDestCordinate(), PieceType.ROOK);
    }

    @Override
    public Collection<Move> calculateLegalMove(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();
        for (final int candidateCordinateOffset : CANDIDATE_MOVE) {
            int candidateDestCordinate = this.cordinate;

            while (BoardUtils.isValidMove(candidateDestCordinate)) {
                candidateDestCordinate += candidateCordinateOffset;

                if (isFirstColumnExclusion(cordinate, candidateCordinateOffset) && isEightColumnExclusion(cordinate, candidateCordinateOffset))
                    if (BoardUtils.isValidMove(candidateDestCordinate)) {
                        final Tile candidateDestTile = board.getTile(candidateDestCordinate);
                        if (!candidateDestTile.isOccuped()) {
                            legalMoves.add(new Move.MajorMove(board, this, candidateDestCordinate));
                        } else {
                            final Piece pieceAtDest = candidateDestTile.getPiece();
                            final Alliance pAlliance = pieceAtDest.getAlliance();
                            if (this.getAlliance() != pAlliance) {
                                legalMoves.add(new Move.AttackMove(board, this, candidateDestCordinate, pieceAtDest));
                            }
                            break;
                        }
                    }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }
}

package com.madageekscar.chess.model.pieces;

import com.google.common.collect.ImmutableList;
import com.madageekscar.chess.model.Alliance;
import com.madageekscar.chess.model.board.Board;
import com.madageekscar.chess.model.board.BoardUtils;
import com.madageekscar.chess.model.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {
    private final static int[] CANDIDATE_MOVE = {8, 16, 9, 7};

    public Pawn(Alliance alliance, int cordinate) {
        super(cordinate, alliance, PieceType.PAWN);
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }

    @Override
    public Piece movedPiece(Move move) {
        return new Pawn(alliance, move.getDestCordinate());
    }

    @Override
    public Collection<Move> calculateLegalMove(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE) {
            int candidateDestCordinate = cordinate + (this.getAlliance().getDirection() * currentCandidateOffset);

            if (!BoardUtils.isValidMove(candidateDestCordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && !board.getTile(candidateDestCordinate).isOccuped()) {
                //TODO ----
                legalMoves.add(new Move.MajorMove(board, this, candidateDestCordinate));
            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    (BoardUtils.SEVENTH_RANK[this.cordinate] && this.getAlliance().isBlack()) ||
                    ((BoardUtils.SECOND8RANK[this.cordinate] && this.getAlliance().isWhite()))) {
                final int behindCandidateDestCordinate = cordinate + (this.getAlliance().getDirection() * 8);
                if (!board.getTile(behindCandidateDestCordinate).isOccuped() && !board.getTile(candidateDestCordinate).isOccuped()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestCordinate));
                }
            } else if (currentCandidateOffset == 9 && !(BoardUtils.FIRST_COLUMN[cordinate] && this.getAlliance().isWhite() ||
                    BoardUtils.EIGHT_COLUMN[cordinate] && this.getAlliance().isBlack())) {
                final Piece pieceOncandidate = board.getTile(candidateDestCordinate).getPiece();
                if (pieceOncandidate != null && pieceOncandidate.getAlliance() != this.getAlliance()) {
                    legalMoves.add(new Move.AttackMove(board, this, candidateDestCordinate, pieceOncandidate));
                }

            } else if (currentCandidateOffset == 7 && !(BoardUtils.EIGHT_COLUMN[cordinate] && this.getAlliance().isWhite() ||
                    BoardUtils.FIRST_COLUMN[cordinate] && this.getAlliance().isBlack())) {
                final Piece pieceOncandidate = board.getTile(candidateDestCordinate).getPiece();
                if (pieceOncandidate != null && pieceOncandidate.getAlliance() != this.getAlliance()) {
                    legalMoves.add(new Move.AttackMove(board, this, candidateDestCordinate, pieceOncandidate));
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}

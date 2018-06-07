package com.madageekscar.chess.model.player;

import com.madageekscar.chess.model.Alliance;
import com.madageekscar.chess.model.board.Board;
import com.madageekscar.chess.model.board.Move;
import com.madageekscar.chess.model.pieces.Piece;

import java.util.Collection;

public class BlackPlayer extends Player {
    public BlackPlayer(Board board, Collection<Move> whiteStdLegalMoves, Collection<Move> blackStdLegalMoves) {
        super(board, blackStdLegalMoves, whiteStdLegalMoves);

    }

    @Override
    public Collection<Piece> getActivePiece() {
        return this.board.getBlackPieaces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }
}

package com.madageekscar.chess.model.pieces;

import com.madageekscar.chess.model.Alliance;
import com.madageekscar.chess.model.board.Board;
import com.madageekscar.chess.model.board.Move;

import java.util.Collection;

public abstract class Piece {
    protected final int cordinate;
    protected final Alliance alliance;

    Piece(int cordinate, Alliance alliance) {
        this.cordinate = cordinate;
        this.alliance = alliance;
    }

    public abstract Collection<Move> calculateLegalMove(final Board board);

    public Alliance getAlliance() {
        return alliance;
    }
}

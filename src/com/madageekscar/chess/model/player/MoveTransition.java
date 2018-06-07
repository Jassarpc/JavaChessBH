package com.madageekscar.chess.model.player;

import com.madageekscar.chess.model.board.Board;
import com.madageekscar.chess.model.board.Move;

public class MoveTransition {
    private final Board board;
    private final Move move;
    private final MoveStatus status;

    public MoveTransition(final Board board, final Move move, MoveStatus status) {
        this.board = board;
        this.move = move;
        this.status = status;
    }

    public MoveStatus getMoveStatus() {
        return this.status;
    }

}

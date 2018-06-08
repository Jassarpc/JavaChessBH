package com.madageekscar.chess.model.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.madageekscar.chess.model.Alliance;
import com.madageekscar.chess.model.board.Board;
import com.madageekscar.chess.model.board.Move;
import com.madageekscar.chess.model.pieces.King;
import com.madageekscar.chess.model.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    protected final boolean isIncheck;


    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));
        this.isIncheck = !Player.calculateAttackOnTile(this.playerKing.getCordinate(), opponentMoves).isEmpty();

    }

    public static Collection<Move> calculateAttackOnTile(int cordinate, Collection<Move> moves) {
        List<Move> attacks = new ArrayList<>();
        for (final Move move : moves) {
            if (cordinate == move.getDestCordinate()) {
                attacks.add(move);
            }
        }
        return ImmutableList.copyOf(attacks);
    }

    public King getPlayerKing() {
        return playerKing;
    }

    public Collection<Move> getLegalMoves() {
        return legalMoves;
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isIncheck() {
        return isIncheck;
    }

    public boolean isInCheckMate() {
        return this.isIncheck && !hasEscapeMoves();
    }

    protected boolean hasEscapeMoves() {
        for (final Move move : this.legalMoves) {
            MoveTransition transition = makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    public boolean isInStaleMate() {
        return !this.isIncheck && !hasEscapeMoves();
    }

    public boolean isInCastle() {
        return false;
    }

    private King establishKing() {
        for (final Piece piece : getActivePiece()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Should not reach here now");
    }

    public MoveTransition makeMove(final Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transBoard = move.execute();
        final Collection<Move> kingAttacks = Player.calculateAttackOnTile(transBoard.currentPlayer().getPlayerKing().getCordinate(), transBoard.currentPlayer().getLegalMoves());
        if (kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transBoard, move, MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePiece();

    public abstract Alliance getAlliance();

    public abstract Player getOpponent();

    protected abstract Collection<Move> calculateKingCastles(Collection<Move> legalMoves, Collection<Move> opponentsLegMoves);
}

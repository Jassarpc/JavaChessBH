package com.madageekscar.chess.model.player;

import com.google.common.collect.ImmutableList;
import com.madageekscar.chess.model.Alliance;
import com.madageekscar.chess.model.board.Board;
import com.madageekscar.chess.model.board.Move;
import com.madageekscar.chess.model.board.Tile;
import com.madageekscar.chess.model.pieces.Piece;
import com.madageekscar.chess.model.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> legalMoves, final Collection<Move> opponentsLegMoves) {
        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isIncheck()) {
            if (!this.board.getTile(5).isOccuped() &&
                    !this.board.getTile(6).isOccuped()) {
                final Tile rookTile = this.board.getTile(7);
                if (rookTile.isOccuped() && rookTile.getPiece().isFirstMove()) {
                    // TODO ADD A KING SI CASTLE MOVE
                    if (Player.calculateAttackOnTile(6, opponentsLegMoves).isEmpty() &&
                            Player.calculateAttackOnTile(7, opponentsLegMoves).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(
                                new Move.KingSideCastleMove(this.board, this.playerKing,
                                        6, rookTile.getTileCordinate(),
                                        (Rook) rookTile.getPiece(), 5));

                    }
                }
            }
            if (!this.board.getTile(1).isOccuped() &&
                    !this.board.getTile(2).isOccuped() &&
                    !this.board.getTile(3).isOccuped()) {
                final Tile rookTile = this.board.getTile(0);
                if (rookTile.isOccuped() && rookTile.getPiece().isFirstMove()) {
                    kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing, 2, rookTile.getTileCordinate(), (Rook) rookTile.getPiece(), 3));

                }
            }
        }


        return ImmutableList.copyOf(kingCastles);
    }
}

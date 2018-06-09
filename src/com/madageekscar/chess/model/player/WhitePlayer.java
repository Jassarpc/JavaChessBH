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

public class WhitePlayer extends Player {
    public WhitePlayer(Board board, Collection<Move> whiteStdLegalMoves, Collection<Move> blackStdLegalMoves) {
        super(board, whiteStdLegalMoves, blackStdLegalMoves);

    }

    @Override
    public Collection<Piece> getActivePiece() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> legalMoves, final Collection<Move> opponentsLegMoves) {
        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isIncheck()) {
            if (!this.board.getTile(61).isOccuped() && !this.board.getTile(62).isOccuped()) {
                final Tile rookTile = this.board.getTile(63);
                if (rookTile.isOccuped() && rookTile.getPiece().isFirstMove()) {
                    // TODO ADD A KING SI CASTLE MOVE
                    if (Player.calculateAttackOnTile(61, opponentsLegMoves).isEmpty() &&
                            Player.calculateAttackOnTile(62, opponentsLegMoves).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing, 62, rookTile.getTileCordinate(), (Rook) rookTile.getPiece(), 61));
                    }
                }
            }
            if (!this.board.getTile(59).isOccuped() &&
                    !this.board.getTile(58).isOccuped() &&
                    !this.board.getTile(57).isOccuped()) {
                final Tile rookTile = this.board.getTile(56);
                if (rookTile.isOccuped() && rookTile.getPiece().isFirstMove() &&
                        Player.calculateAttackOnTile(58, opponentsLegMoves).isEmpty() &&
                        Player.calculateAttackOnTile(59, opponentsLegMoves).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                    kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing, 58, rookTile.getTileCordinate(), (Rook) rookTile.getPiece(), 59));
                }
            }
        }


        return ImmutableList.copyOf(kingCastles);
    }
}

package com.madageekscar.chess.model.board;

import com.google.common.collect.ImmutableList;
import com.madageekscar.chess.model.Alliance;
import com.madageekscar.chess.model.pieces.*;
import com.madageekscar.chess.model.player.BlackPlayer;
import com.madageekscar.chess.model.player.Player;
import com.madageekscar.chess.model.player.WhitePlayer;

import java.util.*;

public class Board {
    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieaces;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    public Board(Builder b) {
        this.gameBoard = createGameBoard(b);
        this.blackPieaces = calculateActivePieaces(this.gameBoard, Alliance.BLACK);
        this.whitePieces = calculateActivePieaces(this.gameBoard, Alliance.WHITE);
        final Collection<Move> whiteStdLegalMoves = calculateLegalMove(this.whitePieces);
        final Collection<Move> blackStdLegalMoves = calculateLegalMove(this.blackPieaces);
        this.whitePlayer = new WhitePlayer(this, whiteStdLegalMoves, blackStdLegalMoves);
        this.blackPlayer = new BlackPlayer(this, blackStdLegalMoves, blackStdLegalMoves);
        this.currentPlayer = b.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    private static Collection<Piece> calculateActivePieaces(final List<Tile> gameBoard, final Alliance alliance) {
        final List<Piece> activePieces = new ArrayList<>();
        for (final Tile tile : gameBoard) {
            if (tile.isOccuped()) {
                final Piece piece = tile.getPiece();
                if (piece.getAlliance() == alliance) {
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        //
        builder.setPiece(new Rook(Alliance.BLACK, 0, Piece.PieceType.ROOK));
        builder.setPiece(new Knight(Alliance.BLACK, 1, Piece.PieceType.KNIGHT));
        builder.setPiece(new Bishop(Alliance.BLACK, 2, Piece.PieceType.BISHOP));
        builder.setPiece(new Queen(Alliance.BLACK, 3, Piece.PieceType.QUEEN));
        builder.setPiece(new King(Alliance.BLACK, 4, Piece.PieceType.KING));
        builder.setPiece(new Bishop(Alliance.BLACK, 5, Piece.PieceType.BISHOP));
        builder.setPiece(new Knight(Alliance.BLACK, 6, Piece.PieceType.KNIGHT));
        builder.setPiece(new Rook(Alliance.BLACK, 7, Piece.PieceType.ROOK));
        builder.setPiece(new Pawn(Alliance.BLACK, 8, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.BLACK, 9, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.BLACK, 10, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.BLACK, 11, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.BLACK, 12, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.BLACK, 13, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.BLACK, 14, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.BLACK, 15, Piece.PieceType.PAWN));
        //
        builder.setPiece(new Pawn(Alliance.WHITE, 48, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.WHITE, 49, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.WHITE, 50, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.WHITE, 51, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.WHITE, 52, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.WHITE, 53, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.WHITE, 54, Piece.PieceType.PAWN));
        builder.setPiece(new Pawn(Alliance.WHITE, 55, Piece.PieceType.PAWN));
        builder.setPiece(new Rook(Alliance.WHITE, 56, Piece.PieceType.ROOK));
        builder.setPiece(new Knight(Alliance.WHITE, 57, Piece.PieceType.KNIGHT));
        builder.setPiece(new Bishop(Alliance.WHITE, 58, Piece.PieceType.BISHOP));
        builder.setPiece(new Queen(Alliance.WHITE, 59, Piece.PieceType.QUEEN));
        builder.setPiece(new King(Alliance.WHITE, 60, Piece.PieceType.KING));
        builder.setPiece(new Bishop(Alliance.WHITE, 61, Piece.PieceType.BISHOP));
        builder.setPiece(new Knight(Alliance.WHITE, 62, Piece.PieceType.KNIGHT));
        builder.setPiece(new Rook(Alliance.WHITE, 63, Piece.PieceType.ROOK));
        builder.setMoveMaker(Alliance.WHITE);
        return builder.build();
    }

    public Collection<Piece> getWhitePieces() {
        return whitePieces;
    }

    public Collection<Piece> getBlackPieaces() {
        return blackPieaces;
    }

    public WhitePlayer getWhitePlayer() {
        return whitePlayer;
    }

    public BlackPlayer getBlackPlayer() {
        return blackPlayer;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if ((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private Collection<Move> calculateLegalMove(final Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece : pieces) {
            legalMoves.addAll(piece.calculateLegalMove(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private List<Tile> createGameBoard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public Tile getTile(final int cordinate) {
        return this.gameBoard.get(cordinate);
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }

    public static class Builder {
        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        public Builder() {
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getCordinate(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance alliance) {
            this.nextMoveMaker = alliance;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }
}

package com.madageekscar.chess.model.board;

import com.google.common.collect.ImmutableMap;
import com.madageekscar.chess.model.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {
    protected  final int cordinate;

    private Tile(final int cordinate) {
        this.cordinate=cordinate;
    }
    private final static Map<Integer,EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTile();
    private static Map<Integer,EmptyTile> createAllPossibleEmptyTile(){
        Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int x = 0; x < BoardUtils.NUM_TILES; x++) {
            emptyTileMap.put(x,new EmptyTile(x));
        }
        //        Collections.unmodifiableMap()
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int cordinate, final Piece piece) {
        return piece !=null ? new OccupiedTile(cordinate, piece): EMPTY_TILES_CACHE.get(cordinate);
    }

    @Override
    public String toString() {
        return "-";
    }

    public abstract boolean isOccuped();
    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile{
        private EmptyTile(final int cordinate){
            super(cordinate);
        }

        @Override
        public boolean isOccuped() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }
    public static final class OccupiedTile extends Tile{
        private final Piece pieceOnTile;
        private OccupiedTile(final int cordinate, Piece pieceOnTile){
            super(cordinate);
            this.pieceOnTile=pieceOnTile;
        }

        @Override
        public boolean isOccuped() {
            return true;
        }

        @Override
        public String toString() {
            return getPiece().getAlliance().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }

        @Override
        public Piece getPiece() {
            return pieceOnTile;
        }
    }
}

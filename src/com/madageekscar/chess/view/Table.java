package com.madageekscar.chess.view;

import com.madageekscar.chess.model.board.Board;
import com.madageekscar.chess.model.board.BoardUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSEION = new Dimension(10, 10);
    private static Dimension FRAME_DIM = new Dimension(700, 700);
    private final JFrame gameFrame;
    private final String pieceIconPath = "";
    private final BoardPanel boardPanel;
    private Color lightTileColor = Color.decode("#FFFFFF");
    private Color darkTileColor = Color.decode("#000000");

    public Table() {
        this.gameFrame = new JFrame("IA CHESS");
        final JMenuBar jMenuBar = createJMenubar();
        this.gameFrame.setJMenuBar(jMenuBar);
        this.gameFrame.setSize(FRAME_DIM);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.gameFrame.setLocationRelativeTo(null);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createJMenubar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        final JMenuItem exit = new JMenuItem("Exit");

        openPGN.addActionListener(e -> System.out.println("Open up that pgn file"));
        exit.addActionListener(e -> System.exit(0));

        fileMenu.add(openPGN);
        fileMenu.add(exit);

        return fileMenu;
    }

    private class BoardPanel extends JPanel {

        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(boardPanel, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
                setPreferredSize(BOARD_PANEL_DIMENSION);
            }
        }
    }

    private class TilePanel extends JPanel {
        private final int tileId;

        public TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSEION);
            assignTileColor();
            validate();
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if (board.getTile(this.tileId).isOccuped()) {
                try {
                    final BufferedImage image = ImageIO.read(
                            new File(pieceIconPath + board.getTile(this.tileId).getPiece().getAlliance().toString().toUpperCase().substring(0, 1) + board.getTile(this.tileId).getPiece().toString() + "png"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void assignTileColor() {
            if (BoardUtils.EIGHTH_RANK[this.tileId] ||
                    BoardUtils.SIXTH_RANK[this.tileId] ||
                    BoardUtils.FOURTH_RANK[this.tileId] ||
                    BoardUtils.SECOND8RANK[this.tileId]) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (BoardUtils.SEVENTH_RANK[this.tileId] ||
                    BoardUtils.FIFTH_RANK[this.tileId] ||
                    BoardUtils.THIRD_RANK[this.tileId] ||
                    BoardUtils.FIRST_RANK[this.tileId]) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }
}

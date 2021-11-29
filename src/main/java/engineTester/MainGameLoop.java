package engineTester;

import org.lwjgl.system.CallbackI.F;

import chess.*;
import gameLogic.pieces.King;
import gameLogic.pieces.Rook;
import gameLogic.util.GameManager;
import gameLogic.util.TranspositionTable;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {
    private static boolean isDraw = false;
    private static Piece[] possibleEnPassantPieces = new Piece[2]; // only 2 pieces can en passant at each moment
    private static TranspositionTable table = new TranspositionTable();

    public static void main(String[] args) {
        DisplayManager.createDisplay(1250, 1000, "Dice Chess");

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        ModelTexture textureAtlas = new ModelTexture(loader.loadTexture("res/chess_texture_atlas.png"));
        ModelTexture diceAtlas = new ModelTexture(loader.loadTexture("res/dice_atlas.png"));
        Board board = new Board(textureAtlas);
        MoveFinder mf = new MoveFinder(board);
        Dice dice = new Dice(board, mf, textureAtlas, diceAtlas);

        ModelTexture titleTexture = new ModelTexture(loader.loadTexture("res/title.png"));
        ModelTexture playTexture = new ModelTexture(loader.loadTexture("res/button_play.png"));
        ModelTexture exitTexture = new ModelTexture(loader.loadTexture("res/button_exit.png"));

        ModelTexture modeTexture = new ModelTexture(loader.loadTexture("res/title_mode.png"));
        ModelTexture pvpTexture = new ModelTexture(loader.loadTexture("res/button_pvp.png"));
        ModelTexture aiTexture = new ModelTexture(loader.loadTexture("res/button_ai.png"));

        ModelTexture colorTexture = new ModelTexture(loader.loadTexture("res/title_color.png"));
        ModelTexture blackTexture = new ModelTexture(loader.loadTexture("res/button_black.png"));
        ModelTexture whiteTexture = new ModelTexture(loader.loadTexture("res/button_white.png"));

        ModelTexture playAgainTextureW = new ModelTexture(loader.loadTexture("res/play_againW.png"));
        ModelTexture playAgainTextureB = new ModelTexture(loader.loadTexture("res/play_againB.png"));
        ModelTexture playAgainTextureDraw = new ModelTexture(loader.loadTexture("res/play_againDraw.png"));
        ModelTexture playAgainTexture = new ModelTexture(loader.loadTexture("res/replayButton.png"));

        Button title = new Button(250, 850, 750, 150, titleTexture);
        Button playButton = new Button(375, 600, 500, 100, playTexture);
        Button exitButton = new Button(375, 450, 500, 100, exitTexture);

        Button modeButton = new Button(250, 850, 750, 150, modeTexture);
        Button pvpButton = new Button(375, 600, 500, 100, pvpTexture);
        Button aiButton = new Button(375, 450, 500, 100, aiTexture);

        Button colorButton = new Button(250, 850, 750, 150, colorTexture);
        Button blackButton = new Button(375, 600, 500, 100, blackTexture);
        Button whiteButton = new Button(375, 450, 500, 100, whiteTexture);

        Button playAgainButtonW = new Button(375, 550, 500, 150, playAgainTextureW);
        Button playAgainButtonB = new Button(375, 550, 500, 150, playAgainTextureB);
        Button playAgainButtonDraw = new Button(375, 550, 500, 150, playAgainTextureDraw);
        Button replayButton = new Button(1090, 540, 70, 70, playAgainTexture);

        title.setEnabled(false);
        modeButton.setEnabled(false);
        colorButton.setEnabled(false);
        playAgainButtonW.setEnabled(false);
        playAgainButtonB.setEnabled(false);
        playAgainButtonDraw.setEnabled(false);
        replayButton.setEnabled(true);

        int scene = 0;

        initBoard(board);
        Color turn = Color.White;
        Color winner = null;
        int diceRoll = dice.getValue(turn);
        int move50rule = 0;

        while (!DisplayManager.isCloseRequested()) {
            if (DisplayManager.isClicked()) {
                DisplayManager.setClick(false);
                float x = DisplayManager.getXPos();
                float y = DisplayManager.getYPos();

                float xRatio = x / DisplayManager.getWidth();
                int xIndex = (int) (xRatio * 10);

                float yRatio = y / DisplayManager.getHeight();
                int yIndex = (int) (8 - (yRatio * 8));
                if (scene == 0) {
                    if (playButton.isClicked(x, y)) {
                        scene = 1;
                    } else if (exitButton.isClicked(x, y)) {
                        DisplayManager.requestClose();
                    }
                } else if (scene == 1) {
                    if (pvpButton.isClicked(x, y)) {
                        scene = 2;
                    }
                    if (aiButton.isClicked(x, y)) {
                        scene = 3;
                    }
                } else if (winner == null && !isDraw && scene == 2) {
                    if (replayButton.isClicked(x, y)) {
                        winner = null;
                        isDraw = false;
                        initBoard(board);
                        turn = Color.White;
                        diceRoll = dice.getValue(turn);
                    }
                    if (xIndex < 8 && yIndex < 8) {
                        Square selectedSquare = board.getSquares()[yIndex][xIndex];
                        if (selectedSquare.getPossibleMove()) {
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    if (board.getSquares()[i][j].getHighlight()) {
                                        Piece removedPiece = board.getSquares()[i][j].getPiece();// needed for 50 move
                                                                                                 // rule
                                        Piece piece = board.getSquares()[i][j].removePiece();
                                        piece.setHasMoved();
                                        if (selectedSquare.getPiece() != null) {
                                            if (selectedSquare.getPiece().getPieceType() == PieceType.King) {
                                                if (turn == Color.White) {
                                                    playAgainButtonW.setEnabled(true);
                                                }
                                                if (turn == Color.Black) {
                                                    playAgainButtonB.setEnabled(true);
                                                }
                                                winner = turn;
                                            }
                                        }
                                        selectedSquare.setPiece(piece);
                                        if (piece.getPieceType() != PieceType.Pawn && removedPiece == null) {
                                            move50rule++;
                                        } else {
                                            // if a pawn has been moved or if a piece has been captured we reset the
                                            // counter
                                            move50rule = 0;
                                        }
                                        boolean rEnpassant = piece.getRightEnpassant();
                                        boolean lEnpassant = piece.getLeftEnpassant();
                                        resetEnPassant();
                                        if (piece.getPieceType() == PieceType.Pawn) {
                                            if ((j - xIndex == -1 && rEnpassant) || (j - xIndex == 1 && lEnpassant)) {
                                                board.getSquares()[i][xIndex].removePiece();
                                            }
                                            if (Math.abs(i - yIndex) == 2) {
                                                enableEnPassant(yIndex, xIndex, board.getSquares());
                                            }
                                            if (yIndex == 0 || yIndex == 7) {
                                                selectedSquare.setPiece(promotePawn(piece, diceRoll));
                                            }
                                        }
                                        // if piece type is king and the movement is two squares then castle
                                        if (piece.getPieceType() == PieceType.King) {
                                            if (j + 2 == xIndex) {
                                                Piece rook = board.getSquares()[i][7].removePiece();
                                                board.getSquares()[i][j + 1].setPiece(rook);
                                                piece.setHasMoved();
                                            }
                                            if (j - 2 == xIndex) {
                                                Piece rook = board.getSquares()[i][0].removePiece();
                                                board.getSquares()[i][j - 1].setPiece(rook);
                                                piece.setHasMoved();
                                            }
                                        }
                                        turn = (turn == Color.White) ? Color.Black : Color.White;
                                        diceRoll = dice.getValue(turn);
                                        int positionCount = table.add(board.getSquares(), turn == Color.White);
                                        if (positionCount >= 3 || move50rule >= 50)// if there is 3 repetition of a
                                                                                   // position
                                        {
                                            setDraw();// let the game be a draw
                                        }
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                board.getSquares()[i][j].setHighlight(false);
                                board.getSquares()[i][j].setPossibleMove(false);
                            }
                        }
                        selectedSquare.setHighlight(true);
                        if (selectedSquare.getPiece() != null) {
                            if (selectedSquare.getPiece().getColor() == turn
                                    && (selectedSquare.getPiece().getPieceType().getValue() == diceRoll
                                            || (canPromote(selectedSquare.getPiece(), yIndex) && diceRoll != 5))) {
                                Square[] moves = selectedSquare.getMoves(mf);
                                for (Square square : moves) {
                                    square.setPossibleMove(true);
                                }
                            }
                        }
                    }
                } else {
                    if (playAgainButtonW.isClicked(x, y) || playAgainButtonB.isClicked(x, y)
                            || playAgainButtonDraw.isClicked(x, y)) {
                        winner = null;
                        isDraw = false;
                        initBoard(board);
                        turn = Color.White;
                        diceRoll = dice.getValue(turn);
                    }
                }
            }
            renderer.prepare();
            shader.start();
            if (scene == 0) {
                renderer.render(title.getTexturedModel());
                renderer.render(playButton.getTexturedModel());
                renderer.render(exitButton.getTexturedModel());
            } else if (scene == 1) {
                renderer.render(modeButton.getTexturedModel());
                renderer.render(pvpButton.getTexturedModel());
                renderer.render(aiButton.getTexturedModel());
            } else if (scene == 3) {
                renderer.render(colorButton.getTexturedModel());
                renderer.render(blackButton.getTexturedModel());
                renderer.render(whiteButton.getTexturedModel());
            } else if (winner == null && !isDraw) {
                for (Square squares[] : board.getSquares()) {
                    for (Square square : squares) {
                        shader.setHighlight(square.getHighlight());
                        shader.setPossibleMove(square.getPossibleMove());
                        renderer.render(square.getBackground());
                        shader.setPossibleMove(false);
                        shader.setHighlight(false);
                        if (square.getPieceModel() != null) {
                            renderer.render(square.getPieceModel());
                        }
                    }
                }
                renderer.render(dice.getPieceModel());
                renderer.render(dice.getDiceModel());
                renderer.render(replayButton.getTexturedModel());
            } else if (winner == Color.White) {
                renderer.render(playAgainButtonW.getTexturedModel());
            } else if (winner == Color.Black) {
                renderer.render(playAgainButtonB.getTexturedModel());
            } else if (isDraw) {
                playAgainButtonDraw.setEnabled(true);
                renderer.render(playAgainButtonDraw.getTexturedModel());
            }
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    private static Piece promotePawn(Piece piece, int diceRoll) {
        Color col = piece.getColor();
        switch (diceRoll) {
            case 1:
                return new Piece(col, PieceType.Knight);
            case 2:
                return new Piece(col, PieceType.Bishop);
            case 3:
                return new Piece(col, PieceType.Rook);
            case 4:
                return new Piece(col, PieceType.Queen);
            case 5:
            case 0:
                PromotionChooser2D pc = new PromotionChooser2D(piece);
                return pc.getNewPiece();
            default:
                return new Piece(col, PieceType.Queen);

        }
    }

    public static void initBoard(Board board) {
        for (int i = 0; i < board.getSquares().length; i++) {
            for (int j = 0; j < board.getSquares().length; j++) {
                board.getSquares()[i][j].removePiece();
            }
        }
        board.getSquares()[0][0].setPiece(new Piece(Color.White, PieceType.Rook));
        board.getSquares()[0][1].setPiece(new Piece(Color.White, PieceType.Knight));
        board.getSquares()[0][2].setPiece(new Piece(Color.White, PieceType.Bishop));
        board.getSquares()[0][3].setPiece(new Piece(Color.White, PieceType.Queen));
        board.getSquares()[0][4].setPiece(new Piece(Color.White, PieceType.King));
        board.getSquares()[0][5].setPiece(new Piece(Color.White, PieceType.Bishop));
        board.getSquares()[0][6].setPiece(new Piece(Color.White, PieceType.Knight));
        board.getSquares()[0][7].setPiece(new Piece(Color.White, PieceType.Rook));
        for (int i = 0; i < 8; i++) {
            board.getSquares()[1][i].setPiece(new Piece(Color.White, PieceType.Pawn));
            board.getSquares()[6][i].setPiece(new Piece(Color.Black, PieceType.Pawn));
        }
        board.getSquares()[7][0].setPiece(new Piece(Color.Black, PieceType.Rook));
        board.getSquares()[7][1].setPiece(new Piece(Color.Black, PieceType.Knight));
        board.getSquares()[7][2].setPiece(new Piece(Color.Black, PieceType.Bishop));
        board.getSquares()[7][3].setPiece(new Piece(Color.Black, PieceType.Queen));
        board.getSquares()[7][4].setPiece(new Piece(Color.Black, PieceType.King));
        board.getSquares()[7][5].setPiece(new Piece(Color.Black, PieceType.Bishop));
        board.getSquares()[7][6].setPiece(new Piece(Color.Black, PieceType.Knight));
        board.getSquares()[7][7].setPiece(new Piece(Color.Black, PieceType.Rook));
    }

    private static void enableEnPassant(int row, int column, Square[][] board) {
        if (column - 1 >= 0) {
            Piece leftPiece = board[row][column - 1].getPiece();
            if (leftPiece != null && leftPiece.getPieceType() == PieceType.Pawn) {
                leftPiece.rightEnpassant = true;
                possibleEnPassantPieces[0] = board[row][column - 1].getPiece();
            }
        }

        if (column + 1 <= 7) {
            Piece rightPiece = board[row][column + 1].getPiece();
            if (rightPiece != null && rightPiece.getPieceType() == PieceType.Pawn) {
                rightPiece.leftEnpassant = true;
                possibleEnPassantPieces[1] = board[row][column + 1].getPiece();
            }
        }
    }

    private static void resetEnPassant() {
        for (int i = 0; i < possibleEnPassantPieces.length; i++) {
            Piece piece = possibleEnPassantPieces[i];
            if (piece != null) {
                piece.resetEnPassant();
            }
        }
    }

    public static void setDraw() {
        isDraw = true;
    }

    private static boolean canPromote(Piece piece, int row) {
        if (piece.getPieceType() != PieceType.Pawn) {
            return false;
        }
        return piece.getColor() == Color.White ? row == 6 : row == 1;
    }
}
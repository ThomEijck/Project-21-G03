package engineTester;

import chess.Board;
import chess.Piece;
import chess.Square;
import chess.Dice;
import chess.Color;
import chess.MoveFinder;
import chess.PieceType;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {
    private static int gameState = 0;//0 = ongoing, 1= white win,2= black win, 3 = draw

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

        Color turn = Color.White;
        int diceRoll = dice.getValue(turn);
        while (!DisplayManager.isCloseRequested()) {
            if (DisplayManager.isClicked()) {
                DisplayManager.setClick(false);
                float x = DisplayManager.getXPos();
                float y = DisplayManager.getYPos();

                float xRatio = x / DisplayManager.getWidth();
                int xIndex = (int) (xRatio * 10);

                float yRatio = y / DisplayManager.getHeight();
                int yIndex = (int) (8 - (yRatio * 8));

                if (xIndex < 8 && yIndex < 8) {
                    Square selectedSquare = board.getSquares()[yIndex][xIndex];
                    if (selectedSquare.getPossibleMove()) {
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                if (board.getSquares()[i][j].getHighlight()) {
                                    Piece piece = board.getSquares()[i][j].removePiece();
                                    piece.setHasMoved();
                                    selectedSquare.setPiece(piece);
                                    turn = (turn == Color.White) ? Color.Black : Color.White;
                                    diceRoll = dice.getValue(turn);
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
                                && selectedSquare.getPiece().getPieceType().getValue() == diceRoll) {
                            Square[] moves = selectedSquare.getMoves(mf);
                            for (Square square : moves) {
                                square.setPossibleMove(true);
                            }
                        }
                    }
                }
            }
            renderer.prepare();
            shader.start();
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
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    public static void setGameState(int gameState) {
        MainGameLoop.gameState = gameState;
    }

    public static int getGameState() {
        return gameState;
    }
}
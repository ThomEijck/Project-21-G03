package engineTester;

import chess.*;
import gameLogic.pieces.Bishop;
import gameLogic.pieces.Knight;
import gameLogic.pieces.Queen;
import gameLogic.pieces.Rook;
import gameLogic.util.PromotionChooser;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {
    private static int gameState = 0;//0 = ongoing, 1= white win,2= black win, 3 = draw
    private static Piece[] possibleEnPassantPieces = new Piece[2]; //only 2 pieces can en passant at each moment

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

        ModelTexture playAgainTexture = new ModelTexture(loader.loadTexture("res/play_again.png"));
        Button playAgainButton = new Button(375, 550, 500, 100, playAgainTexture);
        playAgainButton.setEnabled(false);

        initBoard(board);
        Color turn = Color.White;
        Color winner = null;
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

                if (winner == null) {
                    if (xIndex < 8 && yIndex < 8) {
                        Square selectedSquare = board.getSquares()[yIndex][xIndex];
                        if (selectedSquare.getPossibleMove()) {
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    if (board.getSquares()[i][j].getHighlight()) {
                                        resetEnPassant();
                                        Piece piece = board.getSquares()[i][j].removePiece();
                                        piece.setHasMoved();

                                        if (selectedSquare.getPiece() != null) {
                                            if (selectedSquare.getPiece().getPieceType() == PieceType.King) {
                                                winner = turn;
                                                playAgainButton.setEnabled(true);
                                            }
                                        }

                                        selectedSquare.setPiece(piece);

                                        boolean rEnpassant = piece.getRightEnpassant();
                                        boolean lEnpassant = piece.getLeftEnpassant();

                                        System.out.println(i + "," + j  + ":" + yIndex + "," + xIndex);
                                        if(piece.getPieceType() == PieceType.Pawn)
                                        {
                                            if((j - xIndex == -1 && rEnpassant)
                                                    || (j - xIndex == 1 &&lEnpassant))
                                            {
                                                board.getSquares()[i][xIndex].removePiece();
                                                System.out.println("removed piece from: " + i + "," + xIndex);
                                            }

                                            if( Math.abs(i - yIndex) == 2)
                                            {
                                                enableEnPassant(yIndex,xIndex,board.getSquares());
                                            }

                                            if(yIndex == 0 || yIndex == 7)
                                            {
                                                selectedSquare.setPiece(promotePawn(piece, diceRoll));
                                            }
                                        }

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
                                    && (selectedSquare.getPiece().getPieceType().getValue() == diceRoll
                                            || (selectedSquare.getPiece().getPieceType() == PieceType.Pawn && (yIndex == 0 || yIndex == 7)))) {
                                Square[] moves = selectedSquare.getMoves(mf);
                                for (Square square : moves) {
                                    square.setPossibleMove(true);
                                }
                            }
                        }
                    }
                } else {
                    if (playAgainButton.isClicked(x, y)) {
                        winner = null;
                        initBoard(board);
                    }
                }
            }
            renderer.prepare();
            shader.start();
            if (winner == null) {
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
            } else {
                renderer.render(playAgainButton.getTexturedModel());
            }
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    private static Piece promotePawn(Piece piece, int diceRoll)
    {
        Color col = piece.getColor();
        switch (diceRoll) {
            case 1:
                return new Piece(col, PieceType.Knight);
            case 2:
                return new Piece(col, PieceType.Bishop);
            case 3:
                return new Piece(col, PieceType.Rook);
            case 4:
                //System.out.println("Pawn Promoted!!!");
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

    private static void enableEnPassant(int row , int column , Square[][] board)
    {
        if (column - 1 >= 0){
            Piece leftPiece = board[row][column - 1].getPiece();
            if(leftPiece != null && leftPiece.getPieceType() == PieceType.Pawn)
            {
                leftPiece.rightEnpassant = true;
                possibleEnPassantPieces[0] = board[row][column - 1].getPiece();
            }
        }

        if(column + 1 <= 7){
            Piece rightPiece = board[row][column + 1].getPiece();
            if(rightPiece != null && rightPiece.getPieceType() == PieceType.Pawn)
            {
                rightPiece.leftEnpassant = true;
                possibleEnPassantPieces[1] = board[row][column + 1].getPiece();
            }
        }
    }

    private static void resetEnPassant()
    {
        for (int i = 0; i < possibleEnPassantPieces.length; i++)
        {
            Piece piece = possibleEnPassantPieces[i];
            if(piece != null){piece.resetEnPassant();}
        }
    }
}
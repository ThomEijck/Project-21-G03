package chess;

import java.util.*;

import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import textures.ModelTexture;

public class Dice {

    Random random;
    Board board;
    MoveFinder mf;
    TexturedModel tmPiece;
    Loader loader = new Loader();
    ModelTexture pieceAtlas;
    ModelTexture diceAtlas;
    TexturedModel tmDice;

    public Dice(Board board, MoveFinder mf, ModelTexture pieceAtlas, ModelTexture diceAtlas) {
        random = new Random();
        this.board = board;
        this.mf = mf;
        this.pieceAtlas = pieceAtlas;
        this.diceAtlas = diceAtlas;
    }

    public int getValue(Color turn) {

        int[] availablePieces = new int[6];
        Square[][] squares = board.getSquares();

        int count = 0;

        // check which pieces can be moved
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                Square square = squares[i][j];
                if (square.getPiece() == null) {
                    continue;
                } // if there is an empty square, dont check it

                if (square.getPiece().getColor() != turn) {
                    continue;
                }

                Square[] moves = square.getMoves(mf);
                // check if possible moves can be made
                if (moves.length > 0) {
                    if (square.getPiece().getPieceType() == PieceType.Pawn && canPromote(moves)) {
                        // all pieces except king can at least be rolled if a pawn can be promoted
                        for (int k = 0; k < moves.length - 1; k++) {
                            availablePieces[square.getPiece().getPieceType().getValue()] = 1;
                        }
                    }
                    availablePieces[square.getPiece().getPieceType().getValue()] = 1;
                } else {
                    continue;
                }
            }
        }
        for (int i = 0; i < availablePieces.length; i++) {
            if (availablePieces[i] > 0) {
                count++;
            }
        }
        int[] options = new int[count];
        int index = 0;
        // only add pieces that can be moved to the die
        for (int i = 0; i < availablePieces.length; i++) {
            if (availablePieces[i] > 0) {
                options[index++] = i;
            }
        }
        int piece = options[random.nextInt(options.length)];
        generatePieceModel(piece, turn);
        generateDiceModel(piece);
        return piece;
    }

    private boolean canPromote(Square[] moves) {
        for (Square square : moves) {
            if (square.getPosition().row == 7 || square.getPosition().row == 0) {
                return true;
            }
        }
        return false;
    }

    private void generatePieceModel(int type, Color color) {
        float[] vertices = { 0.92f, -0.1f, 0, 0.92f, -0.4f, 0, 0.68f, -0.4f, 0, 0.68f, -0.1f, 0 };
        float[] textureCoords = { type / 8.0f, color.getColorValue() + 0.5f, type / 8.0f, color.getColorValue(),
                (1 + type) / 8.0f, color.getColorValue(), (1 + type) / 8.0f, color.getColorValue() + 0.5f };
        int[] indices = { 0, 1, 3, 1, 2, 3 };
        RawModel pieceRaw = loader.loadToVAO(vertices, textureCoords, indices);
        tmPiece = new TexturedModel(pieceRaw, pieceAtlas);
    }

    public TexturedModel getPieceModel() {
        return tmPiece;
    }

    private void generateDiceModel(int type) {
        float[] vertices = { 0.92f, 0.1f, 0, 0.92f, 0.4f, 0, 0.68f, 0.4f, 0, 0.68f, 0.1f, 0 };
        float[] textureCoords = { type / 6.0f, 1, type / 6.0f, 0, (1 + type) / 6.0f, 0, (1 + type) / 6.0f, 1 };
        int[] indices = { 0, 1, 3, 1, 2, 3 };
        RawModel diceRaw = loader.loadToVAO(vertices, textureCoords, indices);
        tmDice = new TexturedModel(diceRaw, diceAtlas);
    }

    public TexturedModel getDiceModel() {
        return tmDice;
    }
}

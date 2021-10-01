package chess;

import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import textures.ModelTexture;

import java.util.Arrays;

import gameLogic.util.Position;

public class Square {

    private Loader loader = new Loader();

    private TexturedModel bg;
    private TexturedModel pieceModel;
    private ModelTexture textureAtlas;
    private float[] positions;
    private int[] indices;
    private Piece piece;
    private boolean highlight = false;
    private Position position;
    private boolean possibleMove = false;

    public Square(float[] vertices, float[] textureCoords, int[] indices, ModelTexture textureAtlas,
            Position position) {
        positions = vertices;
        RawModel bgRaw = loader.loadToVAO(vertices, textureCoords, indices);
        this.textureAtlas = textureAtlas;
        bg = new TexturedModel(bgRaw, textureAtlas);
        this.indices = indices;
        this.position = position;
    }

    public TexturedModel getBackground() {
        return bg;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (piece.getPieceType() != PieceType.None) {

            int val = piece.getPieceType().getValue();
            float col = piece.getColor().getColorValue();
            float[] textureCoords = { val / 8.0f, col + 0.5f, val / 8.0f, col, (1 + val) / 8.0f, col, (1 + val) / 8.0f,
                    col + 0.5f };

            RawModel pieceRaw = loader.loadToVAO(positions, textureCoords, indices);
            pieceModel = new TexturedModel(pieceRaw, textureAtlas);
        }
    }

    public Piece removePiece() {
        Piece temp = this.piece;
        this.piece = null;
        this.pieceModel = null;
        return temp;
    }

    public TexturedModel getPieceModel() {
        return pieceModel;
    }

    public void setHighlight(boolean b) {
        highlight = b;
    }

    public boolean getHighlight() {
        return highlight;
    }

    public Position getPosition() {
        return position;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean getPossibleMove() {
        return possibleMove;
    }

    public void setPossibleMove(boolean b) {
        possibleMove = b;
    }

    public Square[] getMoves(MoveFinder mf) {
        if (piece == null) {
            Square res[] = {};
            return res;
        }
        switch (piece.getPieceType()) {
            case Queen: {
                Square diag[] = mf.moveDiagonal(this, 8);
                Square ortho[] = mf.moveOrthogonal(this, 8);
                Square[] squares = Arrays.copyOf(diag, diag.length + ortho.length);
                System.arraycopy(ortho, 0, squares, diag.length, ortho.length);
                return squares;
            }
            case King: {
                Square diag[] = mf.moveDiagonal(this, 1);
                Square ortho[] = mf.moveOrthogonal(this, 1);
                Square[] squares = Arrays.copyOf(diag, diag.length + ortho.length);
                System.arraycopy(ortho, 0, squares, diag.length, ortho.length);
                return squares;
            }
            case Rook: {
                Square ortho[] = mf.moveOrthogonal(this, 8);
                return ortho;
            }
            case Bishop: {
                Square diag[] = mf.moveDiagonal(this, 8);
                return diag;
            }
            case Knight: {
                Square moves[] = mf.moveKnight(this);
                return moves;
            }
            case Pawn: {
                Square moves[] = mf.movePawn(this);
                return moves;
            }
            default:
                Square res[] = {};
                return res;
        }
    }
}

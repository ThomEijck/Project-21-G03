package chess;

import java.util.Arrays;

public class Piece {

    PieceType type;
    Color color;

    public Piece(Color color, PieceType type) {
        this.type = type;
        this.color = color;
    }

    public PieceType getPieceType() {
        return type;
    }

    public Color getColor() {
        return color;
    }
}

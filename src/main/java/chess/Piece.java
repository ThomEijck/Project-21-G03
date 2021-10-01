package chess;

public class Piece {

    PieceType type;
    Color color;
    boolean hasMoved = false;

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

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved() {
        hasMoved = true;
    }
}

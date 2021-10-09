package chess;

public class Piece {

    PieceType type;
    Color color;
    boolean hasMoved = false;
    boolean canPromote;
    boolean leftEnpassant;
    boolean rightEnpassant;

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

    public boolean canPromote() {
        return canPromote;
    }

    public void setPromote(boolean b) {
        canPromote = b;
    }

    public boolean getLeftEnpassant() {
        return leftEnpassant;
    }

    public void setLeftEnpassant(boolean b) {
        leftEnpassant = b;
    }

    public boolean getRightEnpassant() {
        return rightEnpassant;
    }

    public void setRightEnpassant(boolean b) {
        rightEnpassant = b;
    }
}

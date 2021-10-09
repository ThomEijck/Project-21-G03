package chess;

public class Piece {

    PieceType type;
    Color color;
    boolean hasMoved = false;
    //enpassant variables
    public boolean leftEnpassant;
    public boolean rightEnpassant;


    public Piece(Color color, PieceType type) {
        this.type = type;
        this.color = color;
        leftEnpassant = false;
        rightEnpassant = false;
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

    public boolean getLeftEnpassant() {
        return leftEnpassant;
    }

    public boolean getRightEnpassant() {
        return rightEnpassant;
    }

    public void resetEnPassant()
    {
        leftEnpassant = false;
        rightEnpassant = false;
    }
}

package chess;

public enum PieceType {
    None(-1), Pawn(0), Knight(1), Bishop(2), Rook(3), Queen(4), King(5);

    private int value;

    private PieceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

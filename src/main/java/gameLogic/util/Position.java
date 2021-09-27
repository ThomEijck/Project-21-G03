package gameLogic.util;

public class Position {
    public int row;
    public int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean isEqual(Position other)
    {
        return (other.row == row && other.column == column);
    }

    @Override
    public String toString() {
        return "row=" + row + ", column=" + column;
    }
}
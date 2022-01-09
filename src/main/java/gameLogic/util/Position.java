package gameLogic.util;

public class Position {
    public int row;
    public int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position(Position pos) {
        this.row = pos.row;
        this.column = pos.column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isEqual(Position other)
    {
        return (other.row == row && other.column == column);
    }

    @Override
    public String toString() {
        return "(" + (row+1) + ", " + (column+1) + ")";
    }
}
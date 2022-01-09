package gameLogic.util;

import gameLogic.pieces.Piece;

public class Move {

    private Piece piece;
    private Position start;
    private Position end;

    public Move(Piece piece, Position start, Position end)
    {
        this.piece = piece;
        this.start = start;
        this.end = end;
    }

    public Position getStart(){return start;}
    public Position getEnd(){return end;}
    public Piece getPiece(){return piece;}

    @Override
    public String toString() {
        return "Move{" +
                "piece=" + piece +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}

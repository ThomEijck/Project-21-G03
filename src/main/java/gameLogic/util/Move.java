package gameLogic.util;

public class Move {

    private Position start;
    private Position end;

    public Move(Position start, Position end)
    {
        this.start = start;
        this.end = end;
    }

    public Position getStart(){return start;}
    public Position getEnd(){return end;}

    @Override
    public String toString() {
        return "Move{" +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}

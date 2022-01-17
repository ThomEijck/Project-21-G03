package gameLogic.pieces;

import gameLogic.util.Position;

public interface ChessPiece {
    public int getPlayer();
    public Position getPos();
    public boolean setPos(Position pos);
    public int getPieceNumber();
}
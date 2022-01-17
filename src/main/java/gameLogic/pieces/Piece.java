package gameLogic.pieces;

import gameLogic.util.*;

public class Piece implements ChessPiece {

    private Position pos;
    private final int player;
    private int moveCount = 0;
    private int pieceNumber;
    public Piece(Position pos, int player, int pieceNumber) {
        this.pos = pos;
        this.player = player;
        this.pieceNumber = pieceNumber;
    }

    @Override
    public int getPlayer() {
        return player;
    }

    @Override
    public Position getPos() {
        return this.pos;
    }

    @Override
    public boolean setPos(Position pos) {
        this.pos = pos;
        return true;
    }

    @Override
    public int getPieceNumber() {
        return this.pieceNumber;
    }

    public int getInt() {
    	return 0;	
    }
    
    public Position[] findMoves(Piece[][] board){
    	System.out.println("Piece class");
    	return null;	
    }

    public boolean isFirstMove() {
        return moveCount == 0;
    }

    public void hasBeenMoved(){
        moveCount++;
    }

    public void hasBeenUnMoved(){
        moveCount--;
    }

    public void setMoveCount(int count){moveCount = count;}

    public int getMoveCount() {return moveCount;}
}
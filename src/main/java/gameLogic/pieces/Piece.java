package gameLogic.pieces;

import gameLogic.util.*;

public class Piece implements ChessPiece {

    private Position pos;
    private final int player;

    public Piece(Position pos, int player) {
        this.pos = pos;
        this.player = player;
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
    
    public int getInt() {
    	return 0;	
    }
    
    public Position[] findMoves(Piece[][] board){
    	System.out.println("Piece class");
    	return null;	
    }
    
}
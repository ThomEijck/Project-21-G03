package gameLogic.pieces;

import gameLogic.util.*;

public class Bishop extends Piece{

    private static String name = "Bishop";

    public Bishop(Position pos, int player) {
        super(pos, player);
    }

    @Override
    public String toString() {
        return name;
    }          
    
    @Override
    public int getInt(){
    	return 3;	
    }
    
    public Position[] findMoves(Piece[][] board){
    	System.out.println(super.getPos().row + " " + super.getPos().column);
    	return null;
    }
}
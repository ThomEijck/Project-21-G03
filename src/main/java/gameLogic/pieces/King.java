package gameLogic.pieces;

import gameLogic.util.*;

public class King extends Piece{

    private static String name = "King";

    public King(Position pos, int player) {
        super(pos, player);
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public int getInt(){
    	return 6;	
    }
    
    public Position[] findMoves(Piece[][] board){
    	System.out.println(super.getPos().row + " " + super.getPos().column);
    	return null;
    }
}
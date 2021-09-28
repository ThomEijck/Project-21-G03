package gameLogic.pieces;

import gameLogic.util.*;

public class Queen extends Piece{

    private static String name = "Queen";

    public Queen(Position pos, int player) {
        super(pos, player);
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public int getInt(){
    	return 5;	
    }
    
    public Position[] findMoves(Piece[][] board){
    	//System.out.println(super.getPos().row + " " + super.getPos().column);
    	return null;
    }
    
}
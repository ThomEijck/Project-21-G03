package gameLogic.pieces;

import gameLogic.util.*;

public class Peasant extends Piece{

    private static String name = "Pawn";

    public Peasant(Position pos, int player) {
        super(pos, player);
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public int getInt(){
    	return 1;	
    }
    
    //TODO: 
    //Make array of possible moves?
    //First move = 2 steps ahead 
    //En passant
    //Promotion
    public boolean findMoves(Piece[][] board){
    	if (super.getPlayer() == 1){		
    	//Check the player to find the pawn's direction	
			if (super.getPos().row != 0){
			//Make sure the pawn can't go off the board
				if (board[super.getPos().row-1][super.getPos().column] == null){
				//Check the square in front of the pawn
					System.out.println("EMPTY SQUARE AHEAD!");
					System.out.println("Can move to: " + (super.getPos().row-1) + " " + super.getPos().column);
					return true;	
				}
			}
			return false;
    	}
    	else{
    		if (super.getPos().row != 7){
			//Make sure the pawn can't go off the board
				if (board[super.getPos().row+1][super.getPos().column] == null){
				//Check the square in front of the pawn
					System.out.println("EMPTY SQUARE AHEAD!");
					System.out.println("Can move to: " + (super.getPos().row+1) + " " + super.getPos().column);
					return true;	
				}
			}
			return false;
    	}
    }
}
package gameLogic.pieces;

import gameLogic.util.*;

public class Rook extends Piece{

    private static String name = "Rook";

    public Rook(Position pos, int player) {
        super(pos, player);
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public int getInt(){
    	return 4;	
    }
    
    public Position[] findMoves(Piece[][] board){
    	Position[] possibleMoves = new Position[16];
    	for (int i = 0; i < 16; i++){
    		possibleMoves[i] = new Position(-1, -1);	
    	}
    	//Create a temporarily filled array with illegal co�rdinates
    	
    	int counter = 0;
		for (int i = super.getPos().row; i < board.length; i++){
		//First check all of the above in the same column
			if (i != super.getPos().row){
			//Don't check your own square for a piece
				if (board[i][super.getPos().column] == null){
				//Count if we find an empty square upwards, increasingly further away from the rook
						//System.out.println("Can move to: " + i + " " + super.getPos().column);
					possibleMoves[counter++] = new Position(i, super.getPos().column);
				}
				else{
				//Run into another piece, which breaks the loop because we can't go any further
						//System.out.println("Can't move to: " + i + " " + super.getPos().column);
					break;	
				}
			}
		}
		for (int i = super.getPos().row; i > -1; i--){
		//First check all of the above in the same column
			if (i != super.getPos().row){
			//Don't check your own square for a piece
				if(board[i][super.getPos().column] == null){
				//Count if we find an empty square upwards, increasingly further away from the rook
						//System.out.println("Can move to: " + i + " " + super.getPos().column);
					possibleMoves[counter++] = new Position(i, super.getPos().column);
				}
				else{
				//Run into another piece, which breaks the loop because we can't go any further
						//System.out.println("Can't move to: " + i + " " + super.getPos().column);
					break;	
				}
			}
		}
		
		if (counter == 0){
			Position[] tempArray = new Position[1];
			tempArray[0] = new Position(-1,-1);
			return tempArray;
		}
		Position[] finalMoves = new Position[counter-1];
		for (int i = 0; i < finalMoves.length; i++){
			finalMoves[i] = possibleMoves[i];	
		}
		
		return finalMoves;
    }
}
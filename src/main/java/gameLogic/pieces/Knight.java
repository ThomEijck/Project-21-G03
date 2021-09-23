package gameLogic.pieces;

import gameLogic.util.*;

public class Knight extends Piece{

    private static String name = "Knight";

    public Knight(Position pos, int player) {
        super(pos, player);
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public int getInt(){
    	return 2;	
    }
    
    public Position[] findMoves(Piece[][] board){
    	Position[] possibleMoves = new Position[8];
    	
        //possible moves the knight can make
        int[][] possibleMovement = {{-1,+2},{-2,+1},{-1,-2},{-2,-1},{+1,+2},{+2,+1},{+1,-2},{+2,-1}};
        
        //current position
        int row = getPos().row;
        int collumn = getPos().column;

        //calculate target positions
        for (int i = 0; i < possibleMoves.length; i++){
    		possibleMoves[i] = new Position(row + possibleMovement[i][0], collumn + possibleMovement[i][1]);	
    	}


        //check target position validity
        for (int i = 0; i < possibleMoves.length; i++)
        {
            Position pos = possibleMoves[i];
            if(pos.column < 0 || pos.column > 7 || pos.row < 0 || pos.row > 7 || board[pos.row][pos.column].getPlayer() != getPlayer())
            {
                possibleMoves[i] = new Position(-1, -1);
            }
        }


    	return possibleMoves;	
    }
}
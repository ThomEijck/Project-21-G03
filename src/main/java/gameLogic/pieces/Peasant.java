package gameLogic.pieces;

import gameLogic.util.*;

public class Peasant extends Piece{

    private static String name = "Pawn";
	boolean firstMove;
    public Peasant(Position pos, int player) {
        super(pos, player);
        firstMove = true;
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
    //Make array of possible moves? (done)
    //First move = 2 steps ahead (done)
    //En passant
    //Promotion
    public Position[] findMoves(Piece[][] board){
    	Position[] targetPositions;
    	int[][] possibleMoves;

		//if player = 1 then (1*2 - 3 = -1), if player = 2 then (2*2-3 = 4)
		//maps the player number to the correct direction of the pawn movement
		int moveDirection = getPlayer() * 2 - 3;

		//list the possible moves the pawn can make
		if(firstMove)
		{
			possibleMoves = new int[][]{{moveDirection,-1},{moveDirection,0},{moveDirection,-1},{2* moveDirection,0}};
		}else
		{
			possibleMoves = new int[][]{{moveDirection,-1},{moveDirection,0},{moveDirection,-1}};
		}
		targetPositions = new Position[possibleMoves.length];

		//current position
		int row = getPos().row;
		int column = getPos().column;

		//generate possible target positions
		for (int i = 0; i < targetPositions.length; i++)
		{
			targetPositions[i] = new Position(row + possibleMoves[i][0], column + possibleMoves[i][1]);
		}

		//validate target positions
		for (int i = 0; i < possibleMoves.length; i++)
		{
			Position pos = targetPositions[i];
			if(pos.column < 0 || pos.column > 7 || pos.row < 0 || pos.row > 7 || board[pos.row][pos.column].getPlayer() != getPlayer())
			{
				targetPositions[i] = new Position(-1, -1);
			}
		}
		return targetPositions;
    }

    //call this function after the pawn has been moved to prevent it to be able to move two spaces all the time
    public void hasMoved()
	{
		firstMove = false;
	}
}
//just saving this code in case the other implementation does not work
/*
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
			if(firstMove)
			{
				possibleMoves = new int[][]{{1,-1},{1,0},{1,-1},{2,0}};
			}else
			{
				possibleMoves = new int[][]{{1,-1},{1,0},{1,-1}};
			}




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
 */
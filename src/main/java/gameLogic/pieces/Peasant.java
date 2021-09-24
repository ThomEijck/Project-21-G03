package gameLogic.pieces;

import gameLogic.util.*;

public class Peasant extends Piece{

    private static String name = "Pawn";
	boolean firstMove;
	boolean leftEnpassent;
	boolean rightEnpassent;
    public Peasant(Position pos, int player) {
        super(pos, player);
        firstMove = true;
		boolean leftEnpassent = false;
		boolean rightEnpassent = false;
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
			possibleMoves = new int[][]{{moveDirection,-1},{moveDirection,1},{moveDirection,0},{2* moveDirection,0}};
		}else
		{
			possibleMoves = new int[][]{{moveDirection,-1},{moveDirection,1},{moveDirection,0}};
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
			boolean correctTarget = false;

			if(pos.column < 0 || pos.column > 7 || pos.row < 0 || pos.row > 7)
			{
				targetPositions[i] = new Position(-1, -1);
			}

			//knowing that the target is on the board
			if(i <= 1)//targeting top left/right
			{
				correctTarget = (board[pos.row][pos.column].getPlayer() != getPlayer());
			}
			else//targeting straight ahead
			{
				correctTarget = (board[pos.row][pos.column] == null);
			}

			if(!correctTarget)
			{
				targetPositions[i] = new Position(-1, -1);
			}
		}
		return targetPositions;
    }

    //call this function after the pawn has been moved to prevent it to be able to move to the wrong spaces
    public void hasMoved()
	{
		firstMove = false;
		rightEnpassent = true;
		leftEnpassent = true;
	}

	public void setRightEnpassent()
	{
		rightEnpassent = true;
	}

	public void setLeftEnpassent()
	{
		leftEnpassent = true;
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
package gameLogic.pieces;

import gameLogic.util.*;

public class Peasant extends Piece{

    private static String name = "Pawn";
	boolean leftEnpassant;
	boolean rightEnpassant;
    public Peasant(Position pos, int player) {
        super(pos, player);
		leftEnpassant = false;
		rightEnpassant = false;
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public int getInt(){
    	return 1;	
    }

    public Position[] findMoves(Piece[][] board){
    	Position[] targetPositions;
    	int[][] possibleMoves;

		//if player = 1 then (1*2 - 3 = -1), if player = 2 then (2*2-3 = 4)
		//maps the player number to the correct direction of the pawn movement
		int moveDirection = getPlayer() * 2 - 3;

		//list the possible moves the pawn can make
		if(isFirstMove())
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
				continue;//position is invalid, so check next position
			}

			//knowing that the target is on the board
			if(i <= 1)//targeting top left/right
			{
				//let the pawn be able to move when doing en passant
				if((i == 0 && leftEnpassant) || (i == 1 && rightEnpassant))
				{
					correctTarget = true;
				}	
				else
				{
					correctTarget = (board[pos.row][pos.column] != null
					 && board[pos.row][pos.column].getPlayer() != getPlayer());
				}
			}
			else//targeting straight ahead
			{
				if(i == 2)
					correctTarget = (board[pos.row][pos.column] == null);
				if(i == 3)
					correctTarget = (board[pos.row][pos.column] == null 
					&& board[pos.row - moveDirection][pos.column] == null);

			}

			if(!correctTarget)
			{
				targetPositions[i] = new Position(-1, -1);
			}
		}

		return targetPositions;
    }

	public void resetEnPassant()
	{
		leftEnpassant = false;
		rightEnpassant = false;
	}

	//two functions to make the pawn able to use en passant
	public void setRightEnpassant()
	{
		rightEnpassant = true;
	}

	public void setLeftEnpassant()
	{
		leftEnpassant = true;
	}

	public boolean getLeftEnPassant()
	{
		return leftEnpassant;
	}

	public boolean getRightEnPassant()
	{
		return rightEnpassant;
	}
}
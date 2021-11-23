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
        Position[] possibleMoves = new Position[10];
        // 8 and 9 are for the castling. By default not possible
        possibleMoves[8] = new Position(-1,-1);
        possibleMoves[9] = new Position(-1,-1);

        //possible moves the king can make
        int[][] possibleMovement = {{+1,-1},{+1,0},{+1,+1},{0,-1},{0,+1},{-1,-1},{-1,0},{-1,+1}};

        //current position
        int row = getPos().row;
        int column = getPos().column;

        //calculate target positions
        for (int i = 0; i < possibleMovement.length; i++){
            possibleMoves[i] = new Position(row + possibleMovement[i][0], column + possibleMovement[i][1]);
        }


        //check target position validity
        for (int i = 0; i < possibleMovement.length; i++)
        {
            Position pos = possibleMoves[i];
            //check if the target is not on the board
            if(pos.column < 0 || pos.column > 7 || pos.row < 0 || pos.row > 7)
            {
                possibleMoves[i] = new Position(-1, -1);
                continue;//position is invalid, so check next position
            }
            //knowing that the target is on the board
            boolean correctTarget = board[pos.row][pos.column] == null
                    || ( board[pos.row][pos.column] != null && board[pos.row][pos.column].getPlayer() != getPlayer());

            if(!correctTarget)
            {
                possibleMoves[i] = new Position(-1, -1);
            }
        }

        if (isFirstMove()){
            Piece rook1 = board[row][column-4];
            if(rook1 != null && board[row][column-4].getInt() == 4){
                Rook rook = (Rook) board[row][column-4];
                if(rook.isFirstMove() && board[row][column-3] == null && board[row][column-2] == null && board[row][column-1] == null){
                    possibleMoves[8] = new Position(row, column-2);
                }
            }
            Piece rook2 = board[row][column+3];
            if(rook2 != null && board[row][column+3].getInt() == 4){
                Rook rook = (Rook) board[row][column+3];
                if(rook.isFirstMove() && board[row][column+2] == null && board[row][column+1] == null){
                    possibleMoves[9] = new Position(row, column+2);
                }
            }
        }
        return possibleMoves;
    }
}

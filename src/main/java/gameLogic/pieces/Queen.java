package gameLogic.pieces;

import gameLogic.util.*;

import java.util.ArrayList;

public class Queen extends Piece{

    private static String name = "Queen";

    public Queen(Position pos, int player, int pieceNumber) {
        super(pos, player, pieceNumber);
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

        ArrayList<Position> legalMoves = new ArrayList<Position>();

        // Horizontal left
        for(int i = super.getPos().column-1; i >= 0; i--) {
            Position curPos = new Position(super.getPos().row, i);
            addPosToArray(legalMoves,curPos,board);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Horizontal right
        for(int i = super.getPos().column+1; i < 8; i++) {
            Position curPos = new Position(super.getPos().row, i);
            addPosToArray(legalMoves,curPos,board);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Vertical up
        for(int i = super.getPos().row-1; i >= 0; i--) {
            Position curPos = new Position(i, super.getPos().column);
            addPosToArray(legalMoves,curPos,board);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Vertical down
        for(int i = super.getPos().row+1; i < 8; i++) {
            Position curPos = new Position(i, super.getPos().column);
            addPosToArray(legalMoves,curPos,board);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Diagonal up-right
        for(int i = super.getPos().row-1, j = super.getPos().column+1; i >= 0 && j < 8; i--, j++) {
            Position curPos = new Position(i, j);
            addPosToArray(legalMoves,curPos,board);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Diagonal up-left
        for(int i = super.getPos().row-1, j = super.getPos().column-1; i >= 0 && j >= 0; i--, j--) {
            Position curPos = new Position(i, j);
            addPosToArray(legalMoves,curPos,board);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Diagonal down-right
        for(int i = super.getPos().row+1, j = super.getPos().column+1; i < 8 && j < 8; i++, j++) {
            Position curPos = new Position(i, j);
            addPosToArray(legalMoves,curPos,board);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Diagonal down-left
        for(int i = super.getPos().row+1, j = super.getPos().column-1; i < 8 && j >= 0; i++, j--) {
            Position curPos = new Position(i, j);
            addPosToArray(legalMoves,curPos,board);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        return legalMoves.toArray(new Position[0]);
    }

    private void addPosToArray(ArrayList<Position> list, Position pos,Piece[][] board)
    {
        if(board[pos.row][pos.column] == null)
        {

            list.add(pos);

        }
        else if(board[pos.row][pos.column] != null)
        if(board[pos.row][pos.column].getPlayer() != getPlayer())
        {
            list.add(pos);
        }
    }
    
}
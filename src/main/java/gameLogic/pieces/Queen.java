package gameLogic.pieces;

import gameLogic.util.*;

import java.util.ArrayList;

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

        ArrayList<Position> legalMoves = new ArrayList();

        // Horizontal left
        for(int i = super.getPos().column-1; i >= 0; i--) {
            Position curPos = new Position(super.getPos().row, i);
            legalMoves.add(curPos);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Horizontal right
        for(int i = super.getPos().column+1; i < 8; i++) {
            Position curPos = new Position(super.getPos().row, i);
            legalMoves.add(curPos);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Vertical up
        for(int i = super.getPos().row-1; i >= 0; i--) {
            Position curPos = new Position(i, super.getPos().column);
            legalMoves.add(curPos);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Vertical down
        for(int i = super.getPos().row+1; i < 8; i++) {
            Position curPos = new Position(i, super.getPos().column);
            legalMoves.add(curPos);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Diagonal up-right
        for(int i = super.getPos().row-1, j = super.getPos().column+1; i >= 0 && j < 8; i--, j++) {
            Position curPos = new Position(i, j);
            legalMoves.add(curPos);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Diagonal up-left
        for(int i = super.getPos().row-1, j = super.getPos().column-1; i >= 0 && j >= 0; i--, j--) {
            Position curPos = new Position(i, j);
            legalMoves.add(curPos);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Diagonal down-right
        for(int i = super.getPos().row+1, j = super.getPos().column+1; i < 8 && j < 8; i++, j++) {
            Position curPos = new Position(i, j);
            legalMoves.add(curPos);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        // Diagonal down-left
        for(int i = super.getPos().row+1, j = super.getPos().column-1; i < 8 && j >= 0; i++, j--) {
            Position curPos = new Position(i, j);
            legalMoves.add(curPos);
            if(board[curPos.row][curPos.column] != null) {
                break;
            }
        }

        return legalMoves.toArray(new Position[0]);
    }
    
}
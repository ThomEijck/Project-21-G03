package gameLogic.pieces;

import gameLogic.util.*;

public class Bishop extends Piece{

    private static String name = "Bishop";

    public Bishop(Position pos, int player, int pieceNumber) {
        super(pos, player, pieceNumber);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int getInt(){
    	return 3;
    }

    public Position[] findMoves(Piece[][] board){
        int posRow = super.getPos().row;
        int posColumn = super.getPos().column;

        int counter = 0;
        Position[] possibleMoves = new Position[13];
        for (int i = 0; i < 13; i++){
            possibleMoves[i] = new Position(-1, -1);
        }

        // Add all possible positions diagonally up and to the right of the current position
        for(int i = (posRow + 1), j = (posColumn + 1); i < board.length && j < board.length; i++, j++) {
            if (board[i][j] == null) {
                possibleMoves[counter++] = new Position(i, j);
            } else if (board[i][j].getPlayer() != getPlayer()){
                possibleMoves[counter++] = new Position(i, j);
                break;
            } else {
                break;
            }
        }

        // Add all possible positions diagonally up and to the left of the current position
        for(int i = (posRow + 1), j = (posColumn - 1); i < board.length && j >= 0; i++, j--) {
            if (board[i][j] == null) {
                possibleMoves[counter++] = new Position(i, j);
            } else if (board[i][j].getPlayer() != getPlayer()){
                possibleMoves[counter++] = new Position(i, j);
                break;
            } else {
                break;
            }
        }

        // Add all possible positions diagonally down and to the right of the current position
        for(int i = (posRow - 1), j = (posColumn + 1); i >= 0 && j < board.length; i--, j++) {
            if (board[i][j] == null) {
                possibleMoves[counter++] = new Position(i, j);
            } else if (board[i][j].getPlayer() != getPlayer()){
                possibleMoves[counter++] = new Position(i, j);
                break;
            } else {
                break;
            }
        }

        // Add all possible positions diagonally down and to the left of the current position
        for(int i = (posRow - 1), j = (posColumn - 1); i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == null) {
                possibleMoves[counter++] = new Position(i, j);
            } else if (board[i][j].getPlayer() != getPlayer()){
                possibleMoves[counter++] = new Position(i, j);
                break;
            } else {
                break;
            }
        }

        // If no positions are possible, create array of length 1 with an illegal coordinate
        if (counter == 0){
            Position[] tempArray = new Position[1];
            tempArray[0] = new Position(-1,-1);
            return tempArray;
        }

        Position[] finalMoves = new Position[counter];
        for (int i = 0; i < finalMoves.length; i++){
            finalMoves[i] = possibleMoves[i];
        }

        return finalMoves;

    }
}

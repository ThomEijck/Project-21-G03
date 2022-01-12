package gameLogic.util.MiniMax;

import gameLogic.util.*;
import gameLogic.pieces.*;

public class MoveMakerUtil implements MoveExecutorUtil
{
    @Override
    public int movePiece(Board board, Move move) {
        Piece piece = board.getChessBoard()[move.getStart().row][move.getStart().column];
        int value = -1;
        try {
            value = board.movePiece(move,piece.getInt(), piece.getPlayer(),true);
        }catch (Exception e)
        {
            System.out.println("\n");
            board.printBoard();
            System.out.println("Error causing move: " + move);
            System.exit(0);
        }
        //System.out.println("Move: " + move);
        return value;
    }

    @Override
    public boolean unMovePiece(Board board, Move move) {
        return board.revertMove();
    }
}

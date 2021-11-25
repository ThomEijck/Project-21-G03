package gameLogic.util.MiniMax;

import gameLogic.util.*;
import gameLogic.pieces.*;

public class MoveMakerUtil implements MoveExecutorUtil
{
    @Override
    public boolean movePiece(Board board, Move move) {
        Piece piece = board.getChessBoard()[move.getStart().row][move.getStart().column];
        return board.movePiece(move,piece.getInt(), piece.getPlayer(),true);
    }

    @Override
    public boolean unMovePiece(Board board, Move move) {
        return board.revertMove();
    }
}

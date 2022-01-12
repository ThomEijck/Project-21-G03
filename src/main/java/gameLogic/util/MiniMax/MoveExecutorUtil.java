package gameLogic.util.MiniMax;

import gameLogic.util.*;

// for the minimax algorithm to find the new state of the board after a move
// also useful for main in MainGameLoop when a piece is moved
public interface MoveExecutorUtil {
    public int movePiece(Board board,Move move);
    public boolean unMovePiece(Board board, Move move);
}

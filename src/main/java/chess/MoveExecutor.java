package chess;

// for the minimax algorithm to find the new state of the board after a move
// also useful for main in MainGameLoop when a piece is moved
public interface MoveExecutor {
    public Board movePiece(Board board, Square from, Square to);
}

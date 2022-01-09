package gameLogic.util.MiniMax;

import gameLogic.util.*;
import gameLogic.pieces.*;
import java.util.*;

public class MiniMaxExecutorUtil {
    protected BoardEvaluatorUtil evaluator;
    protected MoveExecutorUtil moveExecutor;
    private float curBestValue;

    public MiniMaxExecutorUtil(BoardEvaluatorUtil evaluator, MoveExecutorUtil moveExecutor) {
        this.evaluator = evaluator;
        this.moveExecutor = moveExecutor;
    }

    public Move findBestMove(Board currentBoard, int player, int depth, int diceValue) {

        // first iteration of minimax done separately, to use the value of the dice
        float bestValue = -Float.MAX_VALUE;
        if (player == 2) {
            bestValue = Float.MAX_VALUE;
        }

        float a = Integer.MIN_VALUE;
        float b = Integer.MAX_VALUE;
        Move bestMove = null;
        Move[] moves = getMoves(currentBoard, diceValue, player);
        for (int i = 0; i < moves.length; i++) {
            if (moves[i].getEnd().row == -1)
                continue;
            // System.out.println(i);
            moveExecutor.movePiece(currentBoard, moves[i]);
            float newValue = minimax(currentBoard, depth - 1, nextPlayer(player), a, b);
            moveExecutor.unMovePiece(currentBoard, moves[i]);
            if (player == 2 && newValue < bestValue) {
                bestValue = newValue;
                bestMove = moves[i];
                b = Float.max(newValue, b);
            }

            if (player == 1 && newValue > bestValue) {
                bestValue = newValue;
                bestMove = moves[i];
                a = Float.max(newValue, a);
            }
        }
        curBestValue = bestValue;
        // System.out.println("Best move: " + bestMove + " Best value: " + bestValue);
        return bestMove;
    }

    // NOTE: if you want to turn this into a expectiminimax you need an extra
    // parameter for dice value
    protected float minimax(Board board, int depth, int currentPlayer, float a, float b) {
        if (depth == 0) {
            return evaluator.evaluateBoard(board);
        }
        // board.printBoard();
        List<Move> movesList = new ArrayList<Move>();
        for (int i = 1; i <= 6; i++) {// get moves of all pieces
            addMoves(movesList, board, i, currentPlayer);
        }

        Move[] moves = movesList.toArray(new Move[0]);

        if (moves.length == 0) {
            return evaluator.evaluateBoard(board);// its a draw
        }

        if (currentPlayer == 1) {
            float value = Integer.MIN_VALUE;
            for (int i = 0; i < moves.length; i++) {
                if (moves[i].getEnd().row == -1)
                    continue;
                moveExecutor.movePiece(board, moves[i]);
                value = Float.max(value, minimax(board, depth - 1, nextPlayer(currentPlayer), a, b));
                moveExecutor.unMovePiece(board, moves[i]);

                // alpha beta stuff
                if (value >= b) {
                    return value;
                }
                a = Float.max(a, value);
            }
            return value;
        } else {
            float value = Integer.MAX_VALUE;
            for (int i = 0; i < moves.length; i++) {
                if (moves[i].getEnd().row == -1)
                    continue;
                moveExecutor.movePiece(board, moves[i]);
                value = Float.min(value, minimax(board, depth - 1, nextPlayer(currentPlayer), a, b));
                moveExecutor.unMovePiece(board, moves[i]);

                // alpha beta stuff
                if (value <= a) {
                    return value;
                }
                b = Float.min(b, value);
            }
            return value;
        }
    }

    protected Move[] getMoves(Board board, int diceValue, int player) {
        Piece[][] pieces = board.getChessBoard();
        List<Move> moves = new ArrayList<Move>();
        for (int i = 0; i < pieces.length; i++) {
            for (int k = 0; k < pieces[i].length; k++) {

                Piece piece = pieces[i][k];
                if (piece == null) {
                    continue;
                }
                if ((piece.getInt() != diceValue && piece.getInt() != 1) || piece.getPlayer() != player) {
                    continue;// only get the relevant pieces
                }
                Position[] targets = piece.findMoves(board.getChessBoard());
                for (int l = 0; l < targets.length; l++) {
                    if (targets[l].row == -1) {
                        continue;
                    }
                    if (piece.getInt() == 1 && piece.getInt() != diceValue
                            && !(targets[l].row == 0 || targets[l].row == 7)) {
                        continue;
                    }
                    // add the move to the list
                    Position pos = new Position(piece.getPos());
                    moves.add(new Move(piece, pos, targets[l]));
                }
            }
        }
        return moves.toArray(new Move[0]);// not sure if this works
    }

    protected int nextPlayer(int currPlayer) {
        if (currPlayer == 1) {
            return 2;
        } else
            return 1;
    }

    protected void addMoves(List<Move> moves, Board board, int diceValue, int player) {
        Move[] pieceMoves = getMoves(board, diceValue, player);
        for (int i = 0; i < pieceMoves.length; i++) {
            moves.add(pieceMoves[i]);
        }
    }

    public float getCurBestValue() {
        return curBestValue;
    }
}

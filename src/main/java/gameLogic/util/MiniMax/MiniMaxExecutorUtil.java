package gameLogic.util.MiniMax;

import gameLogic.util.*;

public class MiniMaxExecutorUtil {
    private BoardEvaluatorUtil evaluator;
    private MoveExecutorUtil moveExecutor;


    public MiniMaxExecutorUtil(BoardEvaluatorUtil evaluator, MoveExecutorUtil moveExecutor){
        this.evaluator = evaluator;
        this.moveExecutor = moveExecutor;
    }


    public Move findBestMove(Board currentBoard, int player, int depth, int diceValue){

        // first iteration of minimax done separately, to use the value of the dice
        float bestValue = Integer.MIN_VALUE;
        Move bestMove = null;
        Move[] moves = getMoves(currentBoard, diceValue, player);
        for(int i = 0; i < moves.length; i++){
            moveExecutor.movePiece(currentBoard, moves[i]);
            float newValue = minimax(currentBoard, depth - 1, nextPlayer(player));
            moveExecutor.unMovePiece(currentBoard, moves[i]);
            if (newValue > bestValue){
                bestValue = newValue;
                bestMove = moves[i];
            }
        }
        return bestMove;
    }

    private float minimax(Board board, int depth, int currentPlayer){
        if (depth == 0){
            return evaluator.evaluateBoard(board);
        }

        Move[] moves = getMoves(board,-1,currentPlayer);
        if (moves.length == 0){
            return 0;
        }

        if (currentPlayer == 1){
            float value = Integer.MIN_VALUE;
            for(int i = 0; i < moves.length; i++){
                moveExecutor.movePiece(board,moves[i]);
                value = Float.max(value, minimax(board, depth - 1, nextPlayer(currentPlayer) ));
                moveExecutor.unMovePiece(board,moves[i]);
            }
            return value;
        }
        else{
            float value = Integer.MAX_VALUE;
            for(int i = 0; i < moves.length; i++){
                moveExecutor.movePiece(board,moves[i]);
                value = Float.min(value, minimax(board, depth - 1, nextPlayer(currentPlayer) ));
                moveExecutor.unMovePiece(board,moves[i]);
            }
            return value;
        }
    }

    private Move[] getMoves(Board board, int diceValue, int player)
    {
        return null;
    }


    private int nextPlayer(int currPlayer){
        if (currPlayer == 1){
            return 2;
        }
        else return 1;
    }


}


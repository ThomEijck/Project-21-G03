package gameLogic.util.MiniMax;

import gameLogic.util.*;
import gameLogic.pieces.*;
import java.util.*;

public class ExpectiMiniMaxExecutorUtil extends  MiniMaxExecutorUtil{

    private float curBestValue;

    public ExpectiMiniMaxExecutorUtil(BoardEvaluatorUtil evaluator, MoveExecutorUtil moveExecutor){
        super(evaluator, moveExecutor);
    }

    public Move findBestMove(Board currentBoard, int player, int depth, int diceValue){

        // first iteration of minimax done separately, to use the value of the dice
        float bestValue = -Float.MAX_VALUE;
        if(player == 2)
        {
            bestValue = Float.MAX_VALUE;
        }

        Move bestMove = null;
        Move[] moves = getMoves(currentBoard, diceValue, player);
        for(int i = 0; i < moves.length; i++){
            if(moves[i].getEnd().row == -1)
                continue;
            moveExecutor.movePiece(currentBoard, moves[i]);
            float newValue = minimax(currentBoard, depth - 1, nextPlayer(player));
            moveExecutor.unMovePiece(currentBoard, moves[i]);

            if (player == 2 && newValue < bestValue){
                bestValue = newValue;
                bestMove = moves[i];
            }

            if (player == 1 && newValue > bestValue){
                bestValue = newValue;
                bestMove = moves[i];
            }
        }
        //System.out.println("Best move: " + bestMove + " Best value: " + bestValue);
        curBestValue = bestValue;
        return bestMove;
    }

    public float getCurBestValue() {
        return curBestValue;
    }

    //NOTE: if you want to turn this into a expectiminimax you need an extra parameter for dice value
    protected float minimax(Board board, int depth, int currentPlayer){
        if (depth == 0){
            float value = evaluator.evaluateBoard(board);
            return value;
        }
        //board.printBoard();
        List<Move>[] movesList = new List[6];
        for(int i = 0; i<6; i++){
            movesList[i] = new ArrayList<Move>();
        }

        for (int i = 1; i <= 6; i++) {//get moves of all pieces
            addMoves(movesList[i-1],board,i,currentPlayer);
        }

        if (currentPlayer == 1){
            float totalValue = 0;
            int possibleDiceValues = 0;
            for(int diceValue = 1; diceValue <=6; diceValue++) {
                Move[] moves = movesList[diceValue-1].toArray(new Move[0]);
                if (moves.length > 0){
                    possibleDiceValues++;
                    float value = Integer.MIN_VALUE;
                    for (int i = 0; i < moves.length; i++) {
                        if (moves[i].getEnd().row == -1)
                            continue;
                        if(moveExecutor.movePiece(board, moves[i]) >= 1)
                        {
                            value = Float.max(value, evaluator.evaluateBoard(board));
                        }else
                        {
                            value = Float.max(value, minimax(board, depth - 1, nextPlayer(currentPlayer)));
                        }

                        moveExecutor.unMovePiece(board, moves[i]);

                    }
                    totalValue += value;
                }
            }
            if(possibleDiceValues>0) {
                return totalValue / possibleDiceValues;
            }
            else {
                return 0;//its a draw
            }
        }
        else{
            float totalValue = 0;
            int possibleDiceValues = 0;
            for(int diceValue = 1; diceValue <=6; diceValue++) {
                Move[] moves = movesList[diceValue-1].toArray(new Move[0]);
                if (moves.length > 0){
                    possibleDiceValues++;
                    float value = Integer.MAX_VALUE;
                    for (int i = 0; i < moves.length; i++) {
                        if (moves[i].getEnd().row == -1)
                            continue;
                        if(moveExecutor.movePiece(board, moves[i]) >= 1)
                        {
                            value = Float.min(value, evaluator.evaluateBoard(board));
                        }else
                        {
                            value = Float.min(value, minimax(board, depth - 1, nextPlayer(currentPlayer)));
                        }
                        moveExecutor.unMovePiece(board, moves[i]);
                    }
                    totalValue += value;
                }
            }
            if(possibleDiceValues>0) {
                return totalValue / possibleDiceValues;
            }
            else {
                return 0;//its a draw
            }
        }
    }

}


package gameLogic.util.MiniMax;

import gameLogic.util.*;
import gameLogic.pieces.*;
import java.util.*;

public class ExpectiMiniMaxExecutorUtil extends  MiniMaxExecutorUtil{

    public ExpectiMiniMaxExecutorUtil(BoardEvaluatorUtil evaluator, MoveExecutorUtil moveExecutor){
        super(evaluator, moveExecutor);
    }
    //NOTE: if you want to turn this into a expectiminimax you need an extra parameter for dice value
    protected float minimax(Board board, int depth, int currentPlayer){
        if (depth == 0){
            return evaluator.evaluateBoard(board);
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
                        moveExecutor.movePiece(board, moves[i]);
                        value = Float.max(value, minimax(board, depth - 1, nextPlayer(currentPlayer)));
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
                        moveExecutor.movePiece(board, moves[i]);
                        value = Float.min(value, minimax(board, depth - 1, nextPlayer(currentPlayer)));
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


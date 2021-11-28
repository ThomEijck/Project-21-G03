package gameLogic.util;

import gameLogic.pieces.*;
import gameLogic.util.MiniMax.*;
public class TestFile {
    
    public static void main(String[] args) {
        Board board = new Board();
        MoveMakerUtil moveMaker = new MoveMakerUtil();
        MatrixEvaluatorUtil evaluator =new MatrixEvaluatorUtil();
        MiniMaxExecutorUtil minimax = new MiniMaxExecutorUtil(evaluator,moveMaker);
        Move bestMove = minimax.findBestMove(board,1,6,2);
        System.out.println(bestMove);
    }
}

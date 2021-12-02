package gameLogic.util;

import gameLogic.pieces.*;
import gameLogic.util.MiniMax.*;
public class TestFile {
    
    public static void main(String[] args) {
        double start = System.nanoTime();
        Board board = new Board();
        MoveMakerUtil moveMaker = new MoveMakerUtil();
        MatrixEvaluatorUtil evaluator =new MatrixEvaluatorUtil();
        //MiniMaxExecutorUtil minimax = new MiniMaxExecutorUtil(evaluator,moveMaker);
        ExpectiMiniMaxExecutorUtil minimax = new ExpectiMiniMaxExecutorUtil(evaluator,moveMaker);
        int depth = 5;
        Move bestMove = minimax.findBestMove(board,1,depth,2);
        double end = System.nanoTime();
        System.out.println(bestMove);
        System.out.println("Time for depth " + depth + " = " + ((end - start)/1e9));
    }
}

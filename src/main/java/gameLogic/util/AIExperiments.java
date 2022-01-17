package gameLogic.util;

import gameLogic.pieces.*;
import gameLogic.util.MiniMax.*;
import java.io.*;
public class AIExperiments {

    public static void main(String[] args) {

        TDMatrixEvaluatorUtil evaluator = new TDMatrixEvaluatorUtil();
        /*
        System.out.println(TDMatrixEvaluatorUtil.getWeightIndex(new Peasant(new Position(7,0),2)));
        System.out.println(TDMatrixEvaluatorUtil.getWeightIndex(new Knight(new Position(7,0),2)));
        System.out.println(TDMatrixEvaluatorUtil.getWeightIndex(new Rook(new Position(7,0),2)));
        System.out.println(TDMatrixEvaluatorUtil.getWeightIndex(new Bishop(new Position(7,0),2)));
        System.out.println(TDMatrixEvaluatorUtil.getWeightIndex(new Queen(new Position(7,0),2)));
        System.out.println(TDMatrixEvaluatorUtil.getWeightIndex(new King(new Position(7,0),2)));
        */
        int simAmount = 2000;
        double maxTime = 0.1;
        long start = System.nanoTime();
        for (int i = 0; i < simAmount; i++) {
            runSim(4,maxTime,evaluator);
        }
        long end = System.nanoTime();
        double delta = (end - start)/1e9;
        String average = evaluator.printAverage();
        String w = evaluator.printWeights();
        System.out.println(w);
        System.out.println(average);
        printString(average);
        printString(w);
        System.out.println("total time :" + delta);
    }

    private static void runSim(int d,double maxTime,TDMatrixEvaluatorUtil TDevaluator)
    {
        final boolean DEBUG = true;

        GameManager g = new GameManager();
        MoveMakerUtil moveMaker = new MoveMakerUtil();
        MatrixEvaluatorUtil evaluator =new MatrixEvaluatorUtil();
        ExpectiMiniMaxExecutorUtil emm = new ExpectiMiniMaxExecutorUtil(TDevaluator,moveMaker);

        TDLearner learner = new TDLearner();

        int depth = d;
        double[] moveCount = {0.0,0.0};
        double[] totalDepth = {0.0,0.0};
        while(GameManager.getGameState() == 0)
        {
            Move m = null;
            int player = GameManager.getCurrPlayer();
            int dice = GameManager.getDiceValue();

            learner.updateWeightTrace(g.getBoard());//update weight trace up until P_t

            m = emm.findBestMove(g.getBoard(), player, depth, dice);
            g.movePiece(m, true);
            GameManager.pieceMoved();

            if(GameManager.getGameState() == 0) {
                learner.updateDelta(TDevaluator.evaluateBoard(g.getBoard()));//update weight delta knowing P_t+1
            }else if(GameManager.getGameState() == 1)
            {
                learner.updateDelta(100);
            }
            else if(GameManager.getGameState() == 2)
            {
                learner.updateDelta(-100);
            }
            else if(GameManager.getGameState() == 3)
            {
                learner.updateDelta(0);
            }
            moveCount[player - 1]++;
            totalDepth[player - 1]+= depth-1;
            if(DEBUG) {
                System.out.println("=================================================================");
                System.out.println("Depth: " + depth);
                System.out.println("Move: " + m);
                System.out.println("Dice value: " + dice);
                System.out.println("GameState " + GameManager.getGameState());
                g.getBoard().printBoard();
                System.out.println("\n");
            }
        }
        double wAvg = totalDepth[0]/moveCount[0];
        double bAvg = totalDepth[1]/moveCount[1];
        //System.out.println("GameState " + GameManager.getGameState());
        TDevaluator.updateWeights(learner.getWeightDelta());
        String average = TDevaluator.printAverage();

        printString(average);
        System.out.println(average);
    }

    public static void printString(String string)
    {
        try
        {
            File file = new File("results2.txt");
            file.createNewFile();
            FileWriter fw = new FileWriter(file,true);
            fw.write("\n" + string);
            fw.close();
        }catch (Exception e)
        {
            System.out.println("Error in file or something!");
        }
    }
}

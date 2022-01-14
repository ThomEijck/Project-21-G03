package gameLogic.util;

import gameLogic.util.MiniMax.*;

public class AIExperiments {

    public static void main(String[] args) {
        int simAmount = 100;
        double maxTime = 0.1;
        long start = System.nanoTime();
        TDMatrixEvaluatorUtil TDevaluator = new TDMatrixEvaluatorUtil();
        for (int i = 0; i < simAmount; i++) {
            runSim(4,maxTime,TDevaluator);
        }
        long end = System.nanoTime();
        double delta = (end - start)/1e9;
        TDevaluator.printPSTs();
        System.out.println("total time :" + delta);
    }

    private static void runSim(int d,double maxTime,TDMatrixEvaluatorUtil TDevaluator)
    {
        final boolean DEBUG = false;
        TDevaluator.newGame();
        GameManager g = new GameManager();
        MoveMakerUtil moveMaker = new MoveMakerUtil();
        MatrixEvaluatorUtil evaluator = new MatrixEvaluatorUtil();
        MiniMaxExecutorUtil mm = new MiniMaxExecutorUtil(TDevaluator,moveMaker);
        ExpectiMiniMaxExecutorUtil emm = new ExpectiMiniMaxExecutorUtil(TDevaluator,moveMaker);
        TDLearner learner = new TDLearner();

        int depth = d;
        double[] moveCount = {0.0,0.0};
        double[] totalDepth = {0.0,0.0};
        while(GameManager.getGameState() == 0)
        {
            int player = GameManager.getCurrPlayer();
            Move m = null;
            int dice = GameManager.getDiceValue();

            m = emm.findBestMove(g.getBoard(), player, depth, dice);

            double evaluation = TDevaluator.evaluateBoard(g.getBoard());
            if(player==1) {
                learner.addEvaluation(evaluation);
                learner.updatePST(TDevaluator, g.getBoard().getChessBoard());
            }

            g.movePiece(m, true);
            GameManager.pieceMoved();


            moveCount[player - 1]++;
            totalDepth[player - 1]+= depth-1;
            if(DEBUG) {
                System.out.println("=================================================================");

                System.out.println("Board evaluation: " + evaluation);

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
        //System.out.println(GameManager.getGameState() + ";" + wAvg + ";" + bAvg);


        if(GameManager.getGameState() == 1)//white win
        {
            System.out.println("White Won");
            learner.addEvaluation(100);
        }else if(GameManager.getGameState() == 2)//black win
        {
            System.out.println("black Won");
            learner.addEvaluation(-100);
        }else//draw
        {
            System.out.println("draw");
            learner.addEvaluation(0);
        }
        learner.updatePST(TDevaluator,g.getBoard().getChessBoard());

        learner.gameEnd(TDevaluator);

        double[] average = TDevaluator.calcuateAverage();
        double[] lRateAvg = TDevaluator.calcuateLRateAverage();

        System.out.println(lRateAvg[0] + "," + lRateAvg[1] + "," + lRateAvg[2] + "," +lRateAvg[3] + "," +lRateAvg[4] + "," +lRateAvg[5]);
        System.out.println(average[0] + "," + average[1] + "," + average[2] + "," +average[3] + "," +average[4] + "," +average[5]);
        System.out.println(" ============================== ");
    }
}

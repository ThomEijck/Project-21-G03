package gameLogic.util;

import gameLogic.util.MiniMax.*;

public class AIExperiments {

    public static void main(String[] args) {
        int simAmount = 40;
        double maxTime = 0.1;
        for (int i = 0; i < simAmount; i++) {
            runSim(4,maxTime);
        }

    }

    private static void runSim(int d,double maxTime)
    {
        final boolean DEBUG = false;

        GameManager g = new GameManager();
        MoveMakerUtil moveMaker = new MoveMakerUtil();
        TDMatrixEvaluatorUtil TDevaluator = new TDMatrixEvaluatorUtil();
        MatrixEvaluatorUtil evaluator =new MatrixEvaluatorUtil();
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

            learner.addEvaluation(TDevaluator.evaluateBoard(g.getBoard()));

            learner.updatePST(TDevaluator,g.getBoard().getChessBoard());
            g.movePiece(m, true);
            GameManager.pieceMoved();
            moveCount[player - 1]++;
            totalDepth[player - 1]+= depth-1;
            if(DEBUG) {
                System.out.println("=================================================================");

                System.out.println("Best value belonging to move shown for player " + player + ": " + emm.getCurBestValue());

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
        System.out.println(GameManager.getGameState() + ";" + wAvg + ";" + bAvg);
        TDevaluator.printPSTs();
    }
}

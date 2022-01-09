package gameLogic.util;

import gameLogic.util.MiniMax.*;

public class AIExperiments {

    public static void main(String[] args) {
        int simAmount = 40;
        double maxTime = 0.1;
        for (int i = 0; i < simAmount; i++) {
            runSim(1,maxTime);
        }

    }

    private static void runSim(int d,double maxTime)
    {
        final boolean DEBUG = true;

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
            Move newMove = null;
            int dice = GameManager.getDiceValue();
            double start = System.nanoTime();
            double end = System.nanoTime();
            depth = 1;
            while((end - start) / 1e9 <= maxTime){
                m = newMove;
                start = System.nanoTime();

                if (player == 1) {
                    newMove = emm.findBestMove(g.getBoard(), player, depth++, dice);

                } else {
                    newMove = mm.findBestMove(g.getBoard(), player, depth++, dice);
                }

                end = System.nanoTime();
                m = newMove;
            }
            if (player == 1) {
                learner.addMoveP1(m, emm.getCurBestValue());
            } else {
                learner.addMoveP2(m, mm.getCurBestValue());
            }
            learner.updatePST(player, TDevaluator);
            g.movePiece(m, true);
            GameManager.pieceMoved();
            moveCount[player - 1]++;
            totalDepth[player - 1]+= depth-1;
            if(DEBUG) {
                System.out.println("=================================================================");
                if (player == 1) {
                    System.out.println("Best value belonging to move shown for player " + player + ": " + emm.getCurBestValue());

                } else {
                    System.out.println("Best value belonging to move shown for player " + player + ": " + mm.getCurBestValue());
                }
                learner.calculateError(player);
                System.out.println("Depth: " + depth);
                System.out.println("Move: " + m);
                System.out.println("Dice value: " + dice);
                System.out.println("GameState " + GameManager.getGameState());
                g.getBoard().printBoard();
            }
        }
        double wAvg = totalDepth[0]/moveCount[0];
        double bAvg = totalDepth[1]/moveCount[1];
        System.out.println(GameManager.getGameState() + ";" + wAvg + ";" + bAvg);
        TDevaluator.printPSTs();
    }
}

package gameLogic.util;

import gameLogic.util.MiniMax.*;

public class AIExperiments {

    public static void main(String[] args) {
        GameManager g = new GameManager();
        MoveMakerUtil moveMaker = new MoveMakerUtil();
        MatrixEvaluatorUtil evaluator =new MatrixEvaluatorUtil();
        MiniMaxExecutorUtil mm = new MiniMaxExecutorUtil(evaluator,moveMaker);
        ExpectiMiniMaxExecutorUtil emm = new ExpectiMiniMaxExecutorUtil(evaluator,moveMaker);

        int depth = 5;

        while(GameManager.getGameState() == 0)
        {
            int player = GameManager.getCurrPlayer();
            Move m;
            int dice = GameManager.getDiceValue();

            if(player == 1)
            {
                m = emm.findBestMove(g.getBoard(),player,depth,dice);
            }else
            {
                m = mm.findBestMove(g.getBoard(),player,depth,dice);
            }
            System.out.println("=================================================================");
            System.out.println("Move: " + m);
            System.out.println("Dice value: " + dice);
            System.out.println(g.movePiece(m,true));
            GameManager.pieceMoved();
            System.out.println("GameState " + GameManager.getGameState());
            g.getBoard().printBoard();

        }
    }
}

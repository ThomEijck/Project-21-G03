package gameLogic.util.MiniMax;

import gameLogic.pieces.Piece;
import gameLogic.util.*;
public class TDLearner
{
    float[] weightDelta;
    float[] weightTrace;//i'th value is the partial derivative with respect to the i'th weight
    float prevEval;
    final float ALPHA = 0.005F;//learning rate
    final float LAMBDA = 0.3F;//
    public TDLearner()
    {
        weightDelta = new float[6*64];
        weightTrace = new float[6*64];
    }

    public void updateDelta(float newEval)
    {
        float error = (newEval - prevEval);
        for (int i = 0; i < weightDelta.length; i++) {

            weightDelta[i] += ALPHA * error * weightTrace[i];
        }
        //System.out.println("error: " + error);
        prevEval = newEval;
    }

    public void updateWeightTrace(Board board)
    {
        for (int i = 0; i < weightTrace.length; i++) {
            weightTrace[i] *= LAMBDA;
        }

        Piece[][] pieces = board.getChessBoard();

        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces.length; j++) {
                Piece piece = pieces[i][j];
                if(piece == null){continue;}

                weightTrace[TDMatrixEvaluatorUtil.getWeightIndex(piece)] += TDMatrixEvaluatorUtil.getFeatureValue(piece);
            }
        }

        //printTrace();
        //board.printBoard();
    }

    public float[] getWeightDelta() {
        return weightDelta;
    }

    public void printTrace()
    {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    System.out.print(weightTrace[i * 64 + j* 8 + k]);
                    if(k!= 7)
                        System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println("\n");
        }
    }
}

package gameLogic.util.MiniMax;

import gameLogic.pieces.Piece;
import gameLogic.util.GameManager;

public class TDLearner {

    private double currEvaluation;
    private double prevEvaluation;
    private double derivative;
    private double eTrace;
    private int index;
    private double constant = 0.5;

    private float steepness = 0.5F;
    private float lambda = 0.5F;

    public TDLearner() {
        index = -1;
    }

    public void updatePST(TDMatrixEvaluatorUtil evaluator, Piece[][] board) {
        if(index<1) return;

        double error = calculateError();
        derivative = derivativeOfSigmoidFunction();
        //eTrace = eligibilityTrace(derivative);
        evaluator.updateWeights(error, derivative, board);
    }

    public void gameEnd(TDMatrixEvaluatorUtil evaluator){
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {

                        evaluator.sumOfNetChange[i][j][k] += evaluator.PSTsDelta[i][j][k];
                        evaluator.sumOfAbsoluteChange[i][j][k] += Math.abs(evaluator.PSTsDelta[i][j][k]);

                        evaluator.PSTsDelta[i][j][k] *= evaluator.learningRateTable[i][j][k]*constant;
                        evaluator.PSTs[i][j][k] += evaluator.PSTsDelta[i][j][k];

                        if(evaluator.sumOfAbsoluteChange[i][j][k]!= 0){
                            evaluator.learningRateTable[i][j][k] = ((Math.abs(evaluator.sumOfNetChange[i][j][k])/evaluator.sumOfAbsoluteChange[i][j][k]));
                        }
                        else{
                            evaluator.learningRateTable[i][j][k] = 1;
                        }

                    }
                }
            }
    }

    public double eligibilityTrace(double derivative){
        if (index==1){
            eTrace=derivative;
        }
        else{
            double prevTrace = eTrace;
            eTrace= derivative+(lambda*prevTrace);
           // System.out.println(eTrace);
            //System.out.println(derivative);
        }
        return eTrace;
    }

    public Double derivativeOfSigmoidFunction(){
        if(index < 1) {
            return null;
        }
        double sigmoidValue;
        double value;

        sigmoidValue =(1/(1 + Math.exp(-steepness*prevEvaluation)));
        value = (sigmoidValue*(1-sigmoidValue));
        derivative=value;
        return derivative;
    }

    public Double calculateError() {
        if(index < 1) {
            return null;
        }
        double error = 0;

        error = currEvaluation - prevEvaluation;
        //System.out.println("Error: " + error);
        return error;
    }

    public void addEvaluation(double evaluation) {
        prevEvaluation = currEvaluation;
        currEvaluation = evaluation;
        index++;
    }

}

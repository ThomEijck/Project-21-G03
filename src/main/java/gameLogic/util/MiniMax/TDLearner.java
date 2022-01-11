package gameLogic.util.MiniMax;

import gameLogic.pieces.Piece;
import gameLogic.util.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TDLearner {

    private double currEvaluation;
    private double prevEvaluation;
    private double derivative;
    private double eTrace;
    private int index;

    private float steepness = 0.5F;
    private float lambda = 0.5F;

    public TDLearner() {
        index = -1;
    }

    public void updatePST(TDMatrixEvaluatorUtil evaluator, Piece[][] board) {
        if(index<1) return;

        double error = calculateError();
        derivative = derivativeOfSigmoidFunction();
        eTrace = eligibilityTrace(derivative);
        evaluator.updateWeights(error, eTrace, index, board);
    }

    public double eligibilityTrace(double derivative){
        if (index==1){
            eTrace=derivative;
        }
        else{
            double prevTrace = eTrace;
            eTrace= derivative+(lambda*prevTrace);
            System.out.println(eTrace);
            System.out.println(derivative);
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
        System.out.println("Error: " + error);
        return error;
    }

    public void addEvaluation(float evaluation) {
        prevEvaluation = currEvaluation;
        currEvaluation = evaluation;
        index++;
    }
}

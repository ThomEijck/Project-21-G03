package gameLogic.util.MiniMax;

import gameLogic.pieces.Piece;
import gameLogic.util.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TDLearner {

    private List<Float> evaluations = new ArrayList<>();
    private List<Move> moves = new ArrayList<>();
    private Vector<Float> derivatives = new Vector<>();
    private Vector<Float> eTrace = new Vector<>();
    private int index;

    private float steepness = 0.5F;
    private float lambda = 0.5F;

    public TDLearner() {
        index = -1;
    }

    public void updatePST(TDMatrixEvaluatorUtil evaluator, Piece[][] board) {
        if(index<1) return;

        float error = calculateError();
        derivatives = derivativeOfSigmoidFunction();
        eTrace = eligibilityTrace(derivatives);
        Move move;
        move = moves.get(index);
        evaluator.updateWeights(move, error, eTrace, index, board);
    }

    public Vector<Float> eligibilityTrace(Vector<Float> derivatives){
        float prevTrace;

        if (index==1){
            eTrace.add(0,(float) (derivatives.elementAt(0)*Math.pow(lambda, 0)));
        }
        else{
            prevTrace = eTrace.get(index-2);
            eTrace.add(index-1,derivatives.elementAt(index-1)+(lambda*prevTrace));
            System.out.println(eTrace.toString());
            System.out.println(derivatives.toString());
        }
        return eTrace;
    }

    public Vector<Float> derivativeOfSigmoidFunction(){
        if(index < 1) {
            return null;
        }
        float sigmoidValue;
        float value;

        sigmoidValue = (float) (1/(1 + Math.exp(-steepness*evaluations.get(index-1))));
        value = (float) (sigmoidValue*(1-sigmoidValue));
        derivatives.insertElementAt(value,index-1);
        return derivatives;
    }

    public Float calculateError() {
        if(index < 1) {
            return null;
        }
        float error = 0;

        error = evaluations.get(index) - evaluations.get(index-1);
        System.out.println("Error: " + error);
        return error;
    }

    public void addMove(Move move, float evaluation) {
        this.moves.add(move);
        this.evaluations.add(evaluation);
        index++;
    }
}

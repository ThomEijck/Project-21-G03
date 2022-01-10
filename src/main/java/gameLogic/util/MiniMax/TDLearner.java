package gameLogic.util.MiniMax;

import gameLogic.pieces.Piece;
import gameLogic.util.Move;

import java.util.ArrayList;
import java.util.List;

public class TDLearner {

    private List<Float> evaluations = new ArrayList<>();
    private List<Move> moves = new ArrayList<>();
    private int index;
    private float steepness = 0.5F;
    private float sumOfEvalUntilNow;

    public TDLearner() {
        index = -1;
    }

    public void updatePST( TDMatrixEvaluatorUtil evaluator, Piece[][] board) {
        if(index<1) return;

        float error = calculateError();
        sumOfEvalUntilNow += derivativeOfSigmoidFunction();
        Move move;

        move = moves.get(index);
        evaluator.updateWeights(move, error, sumOfEvalUntilNow,board);
    }

    public float derivativeOfSigmoidFunction(){
        float sigmoidValue = 0;
        float value = 0;

        sigmoidValue = (float) (1/(1 + Math.exp(-steepness*evaluations.get(index-1))));
        value = sigmoidValue*(1-sigmoidValue);
        return value;
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

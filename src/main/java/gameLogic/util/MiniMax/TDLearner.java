package gameLogic.util.MiniMax;

import gameLogic.util.Move;

import java.util.ArrayList;
import java.util.List;

public class TDLearner {

    private List<Float> evaluationsP1 = new ArrayList<>();
    private List<Float> evaluationsP2 = new ArrayList<>();
    private List<Move> movesP1 = new ArrayList<>();
    private List<Move> movesP2 = new ArrayList<>();
    private int indexP1;
    private int indexP2;
    private float steepness = 0.5F;
    private float sumOfEvalUntilNow;

    public TDLearner() {
        indexP1 = -1;
        indexP2 = -1;
    }

    public void updatePST(int player, TDMatrixEvaluatorUtil evaluator) {
        if(indexP1<1) return;
        float error = calculateError(player);
        sumOfEvalUntilNow += derivativeOfSigmoidFunction(player);
        Move move;
        if(player == 1) {
            move = movesP1.get(indexP1-1);
        }
        else {
            move = movesP2.get(indexP2-1);
        }
        evaluator.updateWeights(move, error, sumOfEvalUntilNow);
    }

    public float derivativeOfSigmoidFunction(int player){
        float sigmoidValue = 0;
        float value = 0;
        if(player == 1){
            sigmoidValue = (float) (1/(1 + Math.exp(-steepness*evaluationsP1.get(indexP1))));
            value = sigmoidValue*(1-sigmoidValue);
        }
        else{
            sigmoidValue = (float) (1/(1 + Math.exp(-steepness*evaluationsP2.get(indexP2))));
            value = sigmoidValue*(1-sigmoidValue);
        }
        return value;
    }

    public Float calculateError(int player) {
        if(indexP1 < 1) {
            return null;
        }
        float errorP1 = 0;
        float errorP2 = 0;

        if(player == 1) {
            errorP1 = evaluationsP1.get(indexP1-1) - evaluationsP1.get(indexP1);
            System.out.println("Error for P1: " + errorP1);
            return errorP1;
        }
        else {
            errorP2 = evaluationsP2.get(indexP2-1) - evaluationsP2.get(indexP2);
            System.out.println("Error for P2: " + errorP2);
            return errorP2;
        }
    }

    public void addMoveP1(Move move, float evaluation) {
        this.movesP1.add(move);
        this.evaluationsP1.add(evaluation);
        indexP1++;
    }

    public void addMoveP2(Move move, float evaluation) {
        this.movesP2.add(move);
        this.evaluationsP2.add(evaluation);
        indexP2++;
    }
}

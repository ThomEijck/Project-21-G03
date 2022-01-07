package gameLogic.util.MiniMax;

import java.util.ArrayList;
import java.util.List;

public class TDLearner {

    private BoardEvaluatorUtil evaluator;
    private List<Float> evaluationsP1 = new ArrayList<>();
    private List<Float> evaluationsP2 = new ArrayList<>();
    private int indexP1;
    private int indexP2;

    public TDLearner(BoardEvaluatorUtil evaluator) {
        this.evaluator = evaluator;
        indexP1 = -1;
        indexP2 = -1;
    }

    public void updatePST(int player) {
        float error = calculateError(player);


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

    public void addEvaluationP1(float evaluation) {
        this.evaluationsP1.add(evaluation);
        indexP1++;
    }

    public void addEvaluationP2(float evaluation) {
        this.evaluationsP2.add(evaluation);
        indexP2++;
    }
}

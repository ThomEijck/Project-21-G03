package gameLogic.util.MiniMax;

import gameLogic.pieces.Piece;
import gameLogic.util.Board;
import gameLogic.util.Move;

import java.util.Random;

public class TDMatrixEvaluatorUtil implements BoardEvaluatorUtil
{

    private double[][][] PSTs = generateRandomPSTs();
    private double[][][] lambdaTable = generateRandomLambdaTables();
    private float derivateOfSigmoid;
    private double[] pieceValues = {1,1,1,1,1,100};
    private final double ALPHA = 0.5;
    private float sumOfEvaluations;

    public TDMatrixEvaluatorUtil() {

    }

    // NOTE: need to make weight update depend on player
    public void updateWeights(Move move, float error, float sumOfEvaluations) {
        System.out.println(move);
        int pieceInt = move.getPiece().getInt();
        int row = -1;
        if(move.getPiece().getPlayer() == 1) {
            row = move.getEnd().getRow();
        }
        else {
            row = 7 - move.getEnd().getRow();
        }
        float lambdaValue = 0.5F;

        PSTs[pieceInt-1][row][move.getEnd().getColumn()] += ALPHA * error *lambdaValue*sumOfEvaluations;
        float change = (float) (ALPHA * error *lambdaValue*sumOfEvaluations);
        System.out.println("Updated weight at (" + (row+1) + ", " + (move.getEnd().getColumn()+1) + ") with: " + (double)(change));
    }

    public float evaluateBoard(Board board)
    {
        float evaluation = 0;
        Piece[][] squares = board.getChessBoard();

        for(int i = 0; i < squares.length; i++) {
            for(int j = 0; j < squares[0].length; j++) {
                Piece piece = squares[i][j];
                if(piece == null) continue;
                int pieceNum = piece.getInt() - 1;
                if(piece.getPlayer() == 1) {
                    evaluation += pieceValues[pieceNum] + PSTs[pieceNum][i][j];
                }
                else {
                    evaluation -= pieceValues[pieceNum] + PSTs[pieceNum][(squares.length-1)-i][j];
                }
            }
        }

        return evaluation;
    }

    /***
     * @return a matrix containing random weights
     */
    public static double[][] generateRandomMatrix() {
        Random random = new Random();
        double[][] matrix = new double[8][8];

        for(int i = 0; i < matrix.length; i++){
           matrix[i] = generateRandomArray(8);
        }
        return matrix;
    }

    /**
     * @return an array containing random weights between 1.0 and 10.0
     */
    public static double[] generateRandomArray(int size) {
        Random r = new Random();
        double[] array = new double[size];

        for(int i = 0; i < array.length; i++) {
            array[i] = 1.0 + (10.0-1.0) * r.nextDouble();
        }

        return array;
    }

    /**
     * @return a 3d matrix containing 8 PSTs with weights initialized randomly between 0.0 and 0.5
     */
    public static double[][][] generateRandomPSTs() {
        double[][][] pieceSquareTables = new double[6][8][8];

        for(int i = 0; i < pieceSquareTables.length; i++) {
            pieceSquareTables[i] = generateRandomMatrix();
        }
        return pieceSquareTables;
    }

    public static double[][][] generateRandomLambdaTables() {
        double[][][] lambdaTables = new double[6][8][8];

        for(int i = 0; i < lambdaTables.length; i++) {
            lambdaTables[i] = generateRandomMatrix();
        }
        return lambdaTables;
    }

    public void printPSTs() {
        for(int i = 0; i < PSTs.length; i++) {
            for(int j = 0; j < PSTs[0].length; j++) {
                for(int k = 0; k < PSTs[0][0].length; k++) {
                    System.out.print(PSTs[i][j][k]);
                    if(k != 7) System.out.print(" - ");
                }
                System.out.println();
            }
            System.out.println("\n");
        }
    }
}

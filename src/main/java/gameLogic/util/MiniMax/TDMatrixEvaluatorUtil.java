package gameLogic.util.MiniMax;

import gameLogic.pieces.Piece;
import gameLogic.util.Board;
import gameLogic.util.Move;

import java.util.Random;

public class TDMatrixEvaluatorUtil implements BoardEvaluatorUtil
{

    private double[][][] PSTs = generateRandomPSTs();
    private double[][][] learningRateTable = generateRandomLearningRateTables();
    private float derivateOfSigmoid;
    private double[] pieceValues = {1,1,1,1,1,100};
    private final double ALPHA = 0.5;
    private float sumOfEvaluations;
    private float sumOfNetChangeToWeight;
    private float sumOfAbsoluteChangeToWeight;
    private int totalNumberOfAdjustableWeights = 6*8*8;

    public TDMatrixEvaluatorUtil() {

    }

    // NOTE: need to make weight update depend on player
    public void updateWeights(Move move, float error, float sumOfEvaluations, Piece[][] board) {
        System.out.println(move);
        Piece piece = board[move.getStart().row][move.getStart().column];
        int pieceInt = piece.getInt();

        int row = -1;
        if(piece.getPlayer() == 1) {
            row = move.getEnd().getRow();
        }
        else {
            row = 7 - move.getEnd().getRow();
        }
        float lambdaValue = 0.5F;


      //  sumOfNetChangeToWeight += error*lambdaValue*sumOfEvaluations;
      //  sumOfAbsoluteChangeToWeight += Math.abs(error*lambdaValue*sumOfEvaluations);
      //  learningRateTable[pieceInt-1][row][move.getEnd().getColumn()] = (1/totalNumberOfAdjustableWeights)*(sumOfNetChangeToWeight/sumOfAbsoluteChangeToWeight);


        double change2 = ALPHA*error;

       // float change = (float) (learningRateTable[pieceInt-1][row][move.getEnd().getColumn()] * error *lambdaValue*sumOfEvaluations);
       // PSTs[pieceInt-1][row][move.getEnd().getColumn()] += change;
        PSTs[pieceInt-1][row][move.getEnd().getColumn()] += change2;
        System.out.println("Updated weight at (" + (row+1) + ", " + (move.getEnd().getColumn()+1) + ") with: " + (double)(change2));
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
                    evaluation += PSTs[pieceNum][i][j];
                }
                else {
                    evaluation -= PSTs[pieceNum][(squares.length-1)-i][j];
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

    public static double[][][] generateRandomLearningRateTables() {
        double[][][] alphaTables = new double[6][8][8];

        for(int i = 0; i < alphaTables.length; i++) {
            alphaTables[i] = generateRandomMatrix();
        }
        return alphaTables;
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

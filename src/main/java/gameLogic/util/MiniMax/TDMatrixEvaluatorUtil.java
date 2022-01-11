package gameLogic.util.MiniMax;

import gameLogic.pieces.Piece;
import gameLogic.util.Board;
import gameLogic.util.Move;
import gameLogic.util.Position;

import java.util.Random;
import java.util.Vector;

public class TDMatrixEvaluatorUtil implements BoardEvaluatorUtil
{

    private double[][][] PSTs = generateRandomPSTs();
    private double[][][] learningRateTable = generateRandomLearningRateTables();
    private double[][][] weightUpdateTable = new double[6][8][8];
    private float derivateOfSigmoid;
    private double[] pieceValues = {1,1,1,1,1,100};
    private final double ALPHA = 0.50;
    private float sumOfEvaluations;
    private float sumOfNetChangeToWeight;
    private float sumOfAbsoluteChangeToWeight;
    private int totalNumberOfAdjustableWeights = 6*8*8;

    public TDMatrixEvaluatorUtil() {

    }

    // NOTE: need to make weight update depend on player
    public void updateWeights(double error, double derivative, int index, Piece[][] board) {
        double lambdaValue = 0.5;


      //  sumOfNetChangeToWeight += error*lambdaValue*sumOfEvaluations;
      //  sumOfAbsoluteChangeToWeight += Math.abs(error*lambdaValue*sumOfEvaluations);
      //  learningRateTable[pieceInt-1][row][move.getEnd().getColumn()] = (1/totalNumberOfAdjustableWeights)*(sumOfNetChangeToWeight/sumOfAbsoluteChangeToWeight);

       // float change = (float) (learningRateTable[pieceInt-1][row][move.getEnd().getColumn()] * error *lambdaValue*sumOfEvaluations);
       // PSTs[pieceInt-1][row][move.getEnd().getColumn()] += change;

        updateWeightUpdateTable(lambdaValue,board,derivative);


        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                Piece currPiece = board[i][j];
                if (currPiece == null) {
                    continue;
                }
                int row, column;
                if (currPiece.getPlayer() == 1) {
                    row = currPiece.getPos().getRow();
                } else {
                    row = 7 - currPiece.getPos().getRow();
                }
                column = currPiece.getPos().getColumn();

                Position piecePos = currPiece.getPos();
                double f = getFeatureValue(currPiece);
                double delta = ALPHA * error * f * weightUpdateTable[currPiece.getInt() - 1][row][column];

                if (currPiece.getPlayer() == 1 && currPiece.getInt() == 5) {
                    System.out.println("delta: " + delta + " - f: " + f);

                }

                PSTs[currPiece.getInt() - 1][row][column] += delta;

            }
        }



        //PSTs[pieceInt-1][row][move.getEnd().getColumn()] += change2;
    }

    public void updateWeightUpdateTable(double lambdaValue, Piece[][] board, double derivative)
    {
        //update eligibility trace
        lambdaMult(lambdaValue);

        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++) {
                Piece currPiece = board[i][j];
                if(currPiece == null){continue;}
                int row,column;
                if(currPiece.getPlayer() == 1) {
                    row = currPiece.getPos().getRow();
                }
                else {
                    row = 7 - currPiece.getPos().getRow();
                }
                column = currPiece.getPos().getColumn();
                double addition =  derivative*getFeatureValue(currPiece);
                weightUpdateTable[currPiece.getInt()-1][row][column] += addition;

                if (currPiece.getPlayer() == 1 && currPiece.getInt() == 5) {
                    System.out.println("derivative: " + derivative + " - trace: " + weightUpdateTable[currPiece.getInt()-1][row][column] + " - addition: " + addition);

                }

            }
        }
        System.out.println("--------------------------------");
    }

    private void lambdaMult(double lambdaValue) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    weightUpdateTable[i][j][k] *= lambdaValue;
                }
            }
        }

    }


    private int getFeatureValue(Piece piece)
    {
        if(piece == null){
            return 0;
        }
        if(piece.getPlayer() == 1)
            return 1;
        else
            return -1;
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
                    evaluation -= PSTs[pieceNum][(squares.length-1)-i][(squares.length-1)-j];
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
            array[i] = 1.5 - r.nextDouble();
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

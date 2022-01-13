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
    private double[][][] learningRateTable = generateLearningTables();
    private double[][][] weightUpdateTable = new double[6][8][8];
    private double[][][] sumOfNetChange = new double[6][8][8];
    private double[][][] sumOfAbsoluteChange = new double[6][8][8];
    private final double ALPHA = 0.5;


    public TDMatrixEvaluatorUtil() {

    }

    // NOTE: need to make weight update depend on player
    public void updateWeights(double error, double derivative, Piece[][] board) {
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

                //Temporal Coherence (Adjusting learning Rates)
                //sumOfNetChange[currPiece.getInt()-1][row][column] += error * f * weightUpdateTable[currPiece.getInt() - 1][row][column];
                //sumOfAbsoluteChange[currPiece.getInt()-1][row][column] += Math.abs(error * f * weightUpdateTable[currPiece.getInt() - 1][row][column]);
                //double numberOfLearningRates = 6 * 8 * 8;
                //if(sumOfAbsoluteChange[currPiece.getInt()-1][row][column]!= 0){
                //    learningRateTable[currPiece.getInt()-1][row][column] = (Math.abs(sumOfNetChange[currPiece.getInt()-1][row][column])/sumOfAbsoluteChange[currPiece.getInt()-1][row][column]);
                //}
                //else{
                //    learningRateTable[currPiece.getInt()-1][row][column] = ALPHA;
                //}

                double delta = ALPHA * error * f * weightUpdateTable[currPiece.getInt() - 1][row][column];;//learningRateTable[currPiece.getInt()-1][row][column] * error * f * weightUpdateTable[currPiece.getInt() - 1][row][column];

                if (currPiece.getPlayer() == 1) {
                    //System.out.println("delta: " + delta + " - f: " + f);
                    //System.out.println("net change : " + (sumOfNetChange[currPiece.getInt()-1][row][column] + " abs Change: " + sumOfAbsoluteChange[currPiece.getInt()-1][row][column]));
                    //System.out.println("learning rate: " + learningRateTable[currPiece.getInt()-1][row][column]);
                    //System.out.println("");
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

                if (currPiece.getPlayer() == 1) {
                    //System.out.println("derivative: " + derivative + " - trace: " + weightUpdateTable[currPiece.getInt()-1][row][column] + " - addition: " + addition);

                }

            }
        }
        //System.out.println("--------------------------------");
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
            array[i] = 0;//.5 - r.nextDouble();
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

    /***
     * @return a matrix containing random weights
     */
    public static double[][][] generateLearningTables() {
        Random random = new Random();
        double[][][] alphaTables = new double[6][8][8];

        for(int i = 0; i < alphaTables.length; i++){
            for (int j = 0; j < 8 ; j++) {
                for (int k = 0; k < 8 ; k++) {
                    alphaTables[i][j][k] = 0.5;

                }
            }
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

    public void printLearningTable() {
        for(int i = 0; i < learningRateTable.length; i++) {
            for(int j = 0; j < learningRateTable[0].length; j++) {
                for(int k = 0; k < learningRateTable[0][0].length; k++) {
                    System.out.print(learningRateTable[i][j][k]);
                    if(k != 7) System.out.print(" - ");
                }
                System.out.println();
            }
            System.out.println("\n");
        }
    }

    public void newGame()
    {
        //learningRateTable = generateLearningTables();
        weightUpdateTable = new double[6][8][8];
        sumOfNetChange = new double[6][8][8];
        sumOfAbsoluteChange = new double[6][8][8];
    }
}

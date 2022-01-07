package gameLogic.util.MiniMax;

import gameLogic.pieces.Piece;
import gameLogic.util.Board;

import java.util.Random;

public class TDMatrixEvaluatorUtil implements BoardEvaluatorUtil
{

    private double[][][] PSTs = generateRandomPSTs();
    private double[] pieceValues = generateRandomArray(6);

    public TDMatrixEvaluatorUtil() {
        pieceValues[pieceValues.length-1] = 1000;
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
     * @return a array containing random weights between 0.0 and 0.5
     */
    public static double[] generateRandomArray(int size) {
        Random r = new Random();
        double[] array = new double[size];

        for(int i = 0; i < array.length; i++) {
            array[i] = r.nextDouble() * 0.05;
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
}

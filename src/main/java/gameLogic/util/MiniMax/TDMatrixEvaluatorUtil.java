package gameLogic.util.MiniMax;

import gameLogic.pieces.Piece;
import gameLogic.util.Board;

import java.util.Random;

public class TDMatrixEvaluatorUtil implements BoardEvaluatorUtil
{

    private double[][][] PSTs = generateRandomPSTs();
    private double[] pieceValues = generateRandomArray(6);

    public float evaluateBoard(Board board)
    {
        //weight for all the pieces for each position on the board
        //weights of a pawn

        /*
        float value = 0;
        Piece[][] squares = board.getChessBoard();
        for (int i = 0; i < squares.length; i++) {
            for (int k = 0; k < squares[i].length; k++) {
                Piece piece = squares[i][k];
                if(piece == null)
                    continue;
                int player =  piece.getPlayer() == 1? 1: -1;
                int pieceNumber = piece.getInt() - 1;
                if(player > 0)
                    value += player * (pieceValues[pieceNumber] + pieceTables[pieceNumber][8*i + k]);//white value
                else
                    value += player * (pieceValues[pieceNumber] + pieceTables[pieceNumber][63 -  (8*i + k)]);//black value,with flipped piece square values
            }
        }
        return value;
         */
        return 0;
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
     * @return a array containing random weights
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
     * @return a 3d matrix containing 8 PSTs with weights initialized randomly between 0 and 0.5
     */
    public static double[][][] generateRandomPSTs() {
        double[][][] pieceSquareTables = new double[6][8][8];

        for(int i = 0; i < pieceSquareTables.length; i++) {
            pieceSquareTables[i] = generateRandomMatrix();
        }

        return pieceSquareTables;
    }
}

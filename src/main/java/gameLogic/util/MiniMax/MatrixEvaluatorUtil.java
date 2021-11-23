package gameLogic.util.MiniMax;

import gameLogic.pieces.*;
import gameLogic.util.Board;

public class MatrixEvaluatorUtil implements BoardEvaluatorUtil
{

    //following this article: https://www.chessprogramming.org/Simplified_Evaluation_Function

    public float evaluateBoard(Board board)
    {
        //weight for all the pieces for each position on the board
        //weights of a pawn
        float[] pawnWeights = new float[]{  0,  0,  0,  0,  0,  0,  0,  0,
                                            50, 50, 50, 50, 50, 50, 50, 50,
                                            10, 10, 20, 30, 30, 20, 10, 10,
                                            5,  5, 10, 25, 25, 10,  5,  5,
                                            0,  0,  0, 20, 20,  0,  0,  0,
                                            5, -5,-10,  0,  0,-10, -5,  5,
                                            5, 10, 10,-20,-20, 10, 10,  5,
                                            0,  0,  0,  0,  0,  0,  0,  0};
        //weights of a knight
        float[] knightWeights = new float[]{-50,-40,-30,-30,-30,-30,-40,-50,
                                            -40,-20,  0,  0,  0,  0,-20,-40,
                                            -30,  0, 10, 15, 15, 10,  0,-30,
                                            -30,  5, 15, 20, 20, 15,  5,-30,
                                            -30,  0, 15, 20, 20, 15,  0,-30,
                                            -30,  5, 10, 15, 15, 10,  5,-30,
                                            -40,-20,  0,  5,  5,  0,-20,-40,
                                            -50,-40,-30,-30,-30,-30,-40,-50,};
        //weights of a bishop
        float[] bishopWeights = new float[]{-20,-10,-10,-10,-10,-10,-10,-20,
                                            -10,  0,  0,  0,  0,  0,  0,-10,
                                            -10,  0,  5, 10, 10,  5,  0,-10,
                                            -10,  5,  5, 10, 10,  5,  5,-10,
                                            -10,  0, 10, 10, 10, 10,  0,-10,
                                            -10, 10, 10, 10, 10, 10, 10,-10,
                                            -10,  5,  0,  0,  0,  0,  5,-10,
                                            -20,-10,-10,-10,-10,-10,-10,-20,};
        //weights of a rook
        float[] rookWeights = new float[]{  0,  0,  0,  0,  0,  0,  0,  0,
                                            5, 10, 10, 10, 10, 10, 10,  5,
                                            -5,  0,  0,  0,  0,  0,  0, -5,
                                            -5,  0,  0,  0,  0,  0,  0, -5,
                                            -5,  0,  0,  0,  0,  0,  0, -5,
                                            -5,  0,  0,  0,  0,  0,  0, -5,
                                            -5,  0,  0,  0,  0,  0,  0, -5,
                                            0,  0,  0,  5,  5,  0,  0,  0,};
        //weights of a queen
        float[] queenWeights = new float[]{ -20,-10,-10, -5, -5,-10,-10,-20,
                                            -10,  0,  0,  0,  0,  0,  0,-10,
                                            -10,  0,  5,  5,  5,  5,  0,-10,
                                            -5,  0,  5,  5,  5,  5,  0, -5,
                                            0,  0,  5,  5,  5,  5,  0, -5,
                                            -10,  5,  5,  5,  5,  5,  0,-10,
                                            -10,  0,  5,  0,  0,  0,  0,-10,
                                            -20,-10,-10, -5, -5,-10,-10,-20};
        //weights of a king
        float[] kingWeights = new float[]{  -30,-40,-40,-50,-50,-40,-40,-30,
                                            -30,-40,-40,-50,-50,-40,-40,-30,
                                            -30,-40,-40,-50,-50,-40,-40,-30,
                                            -30,-40,-40,-50,-50,-40,-40,-30,
                                            -20,-30,-30,-40,-40,-30,-30,-20,
                                            -10,-20,-20,-20,-20,-20,-20,-10,
                                            20, 20,  0,  0,  0,  0, 20, 20,
                                            20, 30, 10,  0,  0, 10, 30, 20};

        float[][] pieceTables = {pawnWeights,knightWeights,bishopWeights,rookWeights,queenWeights,kingWeights};
        float[] pieceValues = {100,320,330,500,900,20000};
        float value = 0;
        Piece[][] squares = board.getChessBoard();
        for (int i = 0; i < squares.length; i++) {
            for (int k = 0; k < squares[i].length; k++) {
                Piece piece = squares[i][k];
                int player =  piece.getPlayer();
                int pieceNumber = piece.getInt() - 1;
                if(player > 0)
                    value += player * (pieceValues[pieceNumber] + pieceTables[pieceNumber][8*i + k]);//white value
                else
                    value += player * (pieceValues[pieceNumber] + pieceTables[pieceNumber][63 -  8*i + k]);//black value,with flipped piece square values
            }
        }
        return value;
    }
}

package gameLogic.util.MiniMax;

import gameLogic.pieces.Piece;
import gameLogic.util.Board;
import gameLogic.util.Move;
import gameLogic.util.Position;

import java.util.Random;
import java.util.Vector;

public class TDMatrixEvaluatorUtil implements BoardEvaluatorUtil
{
    float[] weights = new float[64*6];

    public TDMatrixEvaluatorUtil()
    {
        initWeights();
    }

    public void updateWeights(float[] delta)
    {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += delta[i];
        }
    }

    private void initWeights()
    {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = 1;//equal weight for all pieces
        }
    }

    public float evaluateBoard(Board board)
    {
        Piece[][] pieces = board.getChessBoard();

        float value = 0;

        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces.length; j++) {
                Piece piece = pieces[i][j];
                if(piece == null){continue;}

                value += weights[getWeightIndex(piece)] * getFeatureValue(piece);
            }
        }

        return value;
    }

    public static int getWeightIndex(Piece piece)
    {
        int pieceIndex = (piece.getInt()-1) * 64;
        int row=-1;
        if(piece.getPlayer() == 1)
        {
            row = piece.getPos().getRow();
        }else
        {
            row = 7 - piece.getPos().getRow();
        }
        int positionIndex = row * 8 + piece.getPos().getColumn();
        return pieceIndex + positionIndex;
    }

    public static float getFeatureValue(Piece piece)
    {
        if (piece == null)
        {
            return 0;
        }
        else if(piece.getPlayer() == 1)
        {
            return 1;
        }else
        {
            return -1;
        }

    }


    public void printWeights()
    {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    System.out.print(weights[i * 64 + j* 8 + k]);
                    if(k!= 7)
                        System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println("\n");
        }
    }

    public void printAverage()
    {
        float[] values = new float[6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    values[i] += weights[64*i + 8*j + k];
                }
            }
            values[i] /= 64;
        }
        System.out.println(values[0] + ", " + values[1] + ", "+ values[2] + ", " + values[3] + ", " +values[4] + ", " + values[5]);
    }
}

package chess;

public class SimpleEvaluator implements BoardEvaluator
{

    public float evaluateBoard(Board board)
    {
        float[] weights = {1,3,3,5,9,200};
        float value = 0;
        Square[][] squares = board.getSquares();
        for (int i = 0; i < squares.length; i++) {
            for (int k = 0; k < squares[i].length; k++) {
                Piece piece = squares[i][k].getPiece();
                int player =  piece.getColor() == Color.White? 1 : -1;
                value += player * weights[piece.getPieceType().getValue()];
            }
        }
        return value;
    }
}

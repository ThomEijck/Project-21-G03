package chess;

public class MiniMaxExecutor {
    private BoardEvaluator evaluator;
    private MoveExecutor moveExecutor;


    public MiniMaxExecutor(BoardEvaluator evaluator, MoveExecutor moveExecutor){
        this.evaluator = evaluator;
        this.moveExecutor = moveExecutor;
    }


    public Board findBestMove(Board currentBoard, Color maxPlayer, int depth, int diceValue){

        // first iteration of minimax done separately, to use the value of the dice
        Board[] children = findChildren(currentBoard, maxPlayer, diceValue);
        float bestValue = Integer.MIN_VALUE;
        Board bestBoard = null;
        for(int i = 0; i < children.length; i++){
            float newValue = minimax(children[i], depth - 1, false, nextPlayer(maxPlayer));
            if (newValue > bestValue){
                bestValue = newValue;
                bestBoard = children[i];
            }
        }
        return bestBoard;
    }

    private float minimax(Board board, int depth, boolean maximizingPlayer, Color currentPlayer){
        if (depth == 0){
            return evaluator.evaluateBoard(board);
        }

        Board[] children = findChildren(board, currentPlayer, -1);
        if (children.length == 0){
            return evaluator.evaluateBoard(board);
        }

        if (maximizingPlayer){
            float value = Integer.MIN_VALUE;
            for(int i = 0; i < children.length; i++){
                value = Float.max(value, minimax(children[i], depth - 1, false, nextPlayer(currentPlayer) ));
            }
            return value;
        }
        else{
            float value = Integer.MAX_VALUE;
            for(int i = 0; i < children.length; i++){
                value = Float.min(value, minimax(children[i], depth - 1, true, nextPlayer(currentPlayer) ));
            }
            return value;
        }
    }

    // diceValue = -1 if it is not the first move, so you can move any piece
    private Board[] findChildren(Board board, Color currentPlayer, int diceValue){
        Square[][] squares = board.getSquares();
        Board[] children = new Board[63];
        MoveFinder mf = new MoveFinder(board);
        int numberOfChildren = 0;

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                if(squares[i][j].getPiece() != null && squares[i][j].getPiece().getColor() == currentPlayer) {
                    if(diceValue < 0 || squares[i][j].getPiece().getPieceType().getValue() == diceValue){
                        Square[] moves = squares[i][j].getMoves(mf);
                        for (int k = 0; k < moves.length; k++) {
                            children[numberOfChildren++] = moveExecutor.movePiece(board, squares[i][j], moves[k]);
                        }
                    }
                }
            }
        }

        Board[] finalChildren = new Board[numberOfChildren];
        for(int i = 0; i < finalChildren.length; i++){
            finalChildren[i] = children[i];
        }
        return finalChildren;
    }


    private Color nextPlayer(Color currPlayer){
        if (currPlayer == Color.White){
            return Color.Black;
        }
        else return Color.White;
    }


}


package gameLogic.util.MiniMax;

import gameLogic.util.*;
import gameLogic.pieces.*;
import java.util.*;

public class MiniMaxExecutorUtil {
    private BoardEvaluatorUtil evaluator;
    private MoveExecutorUtil moveExecutor;


    public MiniMaxExecutorUtil(BoardEvaluatorUtil evaluator, MoveExecutorUtil moveExecutor){
        this.evaluator = evaluator;
        this.moveExecutor = moveExecutor;
    }


    public Move findBestMove(Board currentBoard, int player, int depth, int diceValue){

        // first iteration of minimax done separately, to use the value of the dice
        float bestValue = Integer.MIN_VALUE;
        Move bestMove = null;
        Move[] moves = getMoves(currentBoard, diceValue, player);
        for(int i = 0; i < moves.length; i++){
            if(moves[i].getEnd().row == -1)
                continue;
            //System.out.println(i);
            moveExecutor.movePiece(currentBoard, moves[i]);
            float newValue = minimax(currentBoard, depth - 1, nextPlayer(player));
            moveExecutor.unMovePiece(currentBoard, moves[i]);
            if (newValue > bestValue){
                bestValue = newValue;
                bestMove = moves[i];
            }
        }
        return bestMove;
    }
    //NOTE: if you want to turn this into a expectiminimax you need an extra parameter for dice value
    private float minimax(Board board, int depth, int currentPlayer){
        if (depth == 0){
            return evaluator.evaluateBoard(board);
        }
        //board.printBoard();
        List<Move> movesList = new ArrayList<Move>();
        for (int i = 1; i <= 6; i++) {//get moves of all pieces
            addMoves(movesList,board,i,currentPlayer);
        }

        Move[] moves = movesList.toArray(new Move[0]);

        if (moves.length == 0){
            return 0;//its a draw
        }

        if (currentPlayer == 1){
            float value = Integer.MIN_VALUE;
            for(int i = 0; i < moves.length; i++){
                if(moves[i].getEnd().row == -1)
                    continue;
                moveExecutor.movePiece(board,moves[i]);
                value = Float.max(value, minimax(board, depth - 1, nextPlayer(currentPlayer) ));
                moveExecutor.unMovePiece(board,moves[i]);
            }
            return value;
        }
        else{
            float value = Integer.MAX_VALUE;
            for(int i = 0; i < moves.length; i++){
                if(moves[i].getEnd().row == -1)
                    continue;
                moveExecutor.movePiece(board,moves[i]);
                value = Float.min(value, minimax(board, depth - 1, nextPlayer(currentPlayer) ));
                moveExecutor.unMovePiece(board,moves[i]);
            }
            return value;
        }
    }

    private Move[] getMoves(Board board, int diceValue, int player)
    {
        Piece[][] pieces = board.getChessBoard();
        List<Move> moves = new ArrayList<Move>();
        for (int i = 0; i < pieces.length; i++)
        {
            for (int k = 0; k < pieces[i].length; k++)
            {

                Piece piece = pieces[i][k];
                if(piece == null){continue;}
                if((piece.getInt() != diceValue && piece.getInt() != 1) || piece.getPlayer() != player)
                {
                    continue;//only get the relevant pieces
                }
                Position[] targets = piece.findMoves(board.getChessBoard());
                for (int l = 0; l < targets.length; l++)
                {
                    if(piece.getInt() == 1 && !(targets[l].row == 0 || targets[l].column == 7))
                    {
                        //if its a pawn and we cant promote we cant move it
                           continue;
                    }
                    //add the move to the list
                    Position pos = new Position(piece.getPos());
                    moves.add(new Move(pos,targets[l]));
                }
            }
        }
        return moves.toArray(new Move[0]);//not sure if this works
    }


    private int nextPlayer(int currPlayer){
        if (currPlayer == 1){
            return 2;
        }
        else return 1;
    }

    private void addMoves(List<Move> moves,Board board, int diceValue,int player)
    {
        Move[] pieceMoves = getMoves(board,diceValue,player);
        for (int i = 0; i < pieceMoves.length; i++)
        {
            moves.add(pieceMoves[i]);
        }
    }


}


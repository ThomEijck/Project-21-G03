package gameLogic.util;

import java.util.*;
import gameLogic.pieces.Piece;
public class Dice
{

    Random random;
    Board board;
    TranspositionTable table;
    public Dice(Board board)
    {
        random = new Random();
        this.board = board;
        table = new TranspositionTable();
    }

    public int getValue(int player)
    {
        int positionCount = table.add(board.getChessBoard(),player != 1);
        if(positionCount >= 3)//if there is 3 repetition of a position
        {
            System.out.println("3 repetition draw");
            GameManager.setGameState(3);//let the game be a draw
        }

        int[] availablePieces = new int[6];
        Piece[][] pieces = board.getChessBoard();
        
        int count = 0;
        boolean whiteKingAlive = false;
        boolean blackKingAlive = false;

        //check which pieces can be moved
        for (int i = 0; i < pieces.length; i++)
        {
            for (int j = 0; j < pieces.length; j++)
            {
                Piece piece = pieces[i][j];
                if(piece == null){continue;}//if there is an empty square, dont check it
                if(piece.getInt() == 6)
                {
                    if(piece.getPlayer() == 1)
                    {
                        whiteKingAlive = true;
                    }else
                    {
                        blackKingAlive = true;
                    }

                }
                if(piece.getPlayer() != player){continue;}

                Position[] moves = piece.findMoves(pieces);
                int index = piece.getInt();
                //check if possible moves can be made
                if(index >= 0 && availablePieces[index - 1] == 0 && hasValidMoves( moves))
                {
                    if(index == 1 && canPromote(moves))
                    {
                        //all pieces except king can at least be rolled if a pawn can be promoted
                        for (int k = 0; k < moves.length - 1; k++)
                        {
                            availablePieces[index - 1] = 1;
                        }
                    }
                    availablePieces[index - 1] = 1;
                    count++;
                }
                else
                {
                    continue;
                }
            }
        }

        if(count == 0 || board.getMove50rule() >= 50)//no pieces can be moved or if 50 move rule has been activated
        {
            System.out.println("50 move rule!!!!!!!!");
            GameManager.setGameState(3);//its a draw if a player cant move
            return 6;//exit out of the function
        }

        System.out.println("White: " + whiteKingAlive +  " - black: " + blackKingAlive);

        if(whiteKingAlive != blackKingAlive)//one king is dead and one is alive
        {
            if(whiteKingAlive)//assign the winner
            {
                GameManager.setGameState(1);
            }else
            {
                GameManager.setGameState(2);
            }
            return 6;//exit out of the function
        }
        int[] options = new int[count];
        int index = 0;
        //only add pieces that can be moved to the die
        for (int i = 0; i < availablePieces.length; i++)
        {
            if(availablePieces[i] > 0)
            {
                options[index++] = i + 1;
            }
        }

        //System.out.println("Options: " + Arrays.toString(options));
        
        int piece = options[random.nextInt(options.length)];
        
        return piece;
    }

    private boolean canPromote(Position[] moves) {
        for (Position position : moves) {
            if(position.row == 7 || position.row == 0)
            {
                return true;
            }
        }
        return false;
    }

    //function to check if a piece has valid moves
    public boolean hasValidMoves(Position[] moves)
    {
        if(moves == null)
            return false;

        for (int i = 0; i < moves.length; i++) 
        {
            //invalid moves will have the target -1,-1 
            //So to check validity we just have to check if the target row != -1
            if(moves[i].row >= 0)
            {
                return true;
            }    
        }
        return false;
    }
}
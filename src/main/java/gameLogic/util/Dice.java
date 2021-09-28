package gameLogic.util;

import java.util.*;
import gameLogic.pieces.Piece;
public class Dice
{

    Random random;
    Board board;
    public Dice(Board board)
    {
        random = new Random();
        this.board = board;
    }

    public int getValue()
    {
        
        int[] availablePieces = new int[6];
        Piece[][] pieces = board.getChessBoard();
        
        int count = 0;

        //check which pieces can be moved
        for (int i = 0; i < pieces.length; i++)
        {
            for (int j = 0; j < pieces.length; j++)
            {
                Piece piece = pieces[i][j];
                if(piece == null){continue;}//if there is an empty square, dont check it


                Position[] moves = piece.findMoves(pieces);
                int index = nameToNum(piece.toString());
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

        System.out.println("Options: " + Arrays.toString(options));
        
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

    //function that converts the name of a piece to its index
    private int nameToNum(String name)
    {
        switch (name) {
            case "Pawn":
                return 1;
                
            case "Knight":
                return 2;
                
            case "Bishop":
                return 3;
                
            case "Rook":
                return 4;
                
            case "Queen":
                return 5;
                
            case "King":
                return 6;
                
            default:
                return -1;//empty square
                
        }
    }
}
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
                int index = nameToNum(piece.toString());
                //check if possible moves can be made
                if(index >= 0 && availablePieces[index - 1] == 0 && hasValidMoves(piece.findMoves(pieces)))
                {
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

        
        int piece = options[random.nextInt(options.length)];
        
        return piece;
    }

    //function to check if a piece has valid moves
    public boolean hasValidMoves(Position[] moves)
    {
        for (int i = 0; i < moves.length; i++) 
        {
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
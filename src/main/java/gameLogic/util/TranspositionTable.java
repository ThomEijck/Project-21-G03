package gameLogic.util;


import java.util.*;

import chess.Square;
import gameLogic.pieces.*;

public class TranspositionTable
{
    HashMap<Long,Integer> table;
    Hashing hashing;
    public TranspositionTable(){
        table = new HashMap<Long, Integer>();
        hashing = new Hashing();
    }


    public int add(Piece[][] board, boolean blackToMove)
    {
        long hash = hashing.calculateHash(board,blackToMove);
        Integer value = table.get(hash);
        if(value == null)
        {
            table.put(hash,1);
            return 1;
        }else
        {
            table.put(hash,value + 1);
            return value + 1;
        }
    }

    public int add(Square[][] board, boolean blackToMove)
    {
        long hash = hashing.calculateHash(board,blackToMove);
        Integer value = table.get(hash);
        if(value == null)
        {
            table.put(hash,1);
            return 1;
        }else
        {
            table.put(hash,value + 1);
            return value + 1;
        }
    }

    public void add(Piece[][] board, int value,boolean blackToMove)
    {
        table.put(hashing.calculateHash(board,blackToMove), value);
    }

    public void add(Square[][] board, int value,boolean blackToMove)
    {
        table.put(hashing.calculateHash(board,blackToMove), value);
    }
}
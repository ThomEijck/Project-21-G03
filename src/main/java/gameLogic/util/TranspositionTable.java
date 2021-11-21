package gameLogic.util;


import java.util.*;

public class TranspositionTable
{
    HashMap<Long,Integer> table;
    Hashing hashing;
    Board board;
    public TranspositionTable(Board board, boolean blackToMove)
    {
        table = new HashMap<Long, Integer>();
        hashing = new Hashing(board,blackToMove);
        this.board = board;
    }


    public int add(boolean blackToMove)
    {
        long hash = hashing.calculateHash(board.getChessBoard(),blackToMove);
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

    public void add(int value,boolean blackToMove)
    {
        table.put(hashing.calculateHash(board.getChessBoard(),blackToMove), value);
    }
}
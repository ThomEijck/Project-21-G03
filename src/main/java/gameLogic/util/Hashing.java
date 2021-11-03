package gameLogic.util;

import java.util.Random;

import gameLogic.pieces.*;

//class implementing zobrist hashing
public class Hashing
{

    long seed = 6942069;//nice
    long hash;
    Random random;
    long[] keyValues;
    public Hashing(Piece[] pieces, boolean blackToMove)
    {   
        random = new Random(seed);
        keyValues = new long[781];//781 is the amount of numbers needed for zobrist hashing
        for (int i = 0; i < keyValues.length; i++) {
            keyValues[i] = random.nextLong();
        }
        hash = calculateHash(pieces,blackToMove);
    }

    private long calculateHash(Piece[] pieces, boolean blackToMove)
    {
        long hash = 0;

        int enPassentColumn = -1;//store the column of a valid en passant square, if any

        for (int i = 0; i < pieces.length; i++)
        {
            Position pos = pieces[i].getPos();
            hash ^= keyValues[(pieces[i].getInt() - 1)* pieces[i].getPlayer() *64 + pos.row * 8 + pos.column];
            if(pieces[i].getInt() == 1)//check for en passant
            {
                Peasant p = (Peasant) pieces[i];

                if (p.getLeftEnPassant())
                {
                    enPassentColumn = pos.row -1;
                } 
                if(p.getRightEnPassant())
                {
                    enPassentColumn = pos.row + 1;
                }
            }
        }

        if(enPassentColumn >= 0)
        {
            hash ^= keyValues[64*12 + enPassentColumn];
        }

        //TODO: add castling to hashing

        if(blackToMove){hash ^= keyValues[781];}
        return hash;
    }

    public void updateHash(Position source1,Position source2, Piece piece1, Piece piece2)
    {
        if(source1 != null)
            hash ^= keyValues[(piece1.getInt() - 1)*piece1.getPlayer() *64 + source1.row * 8 + source1.column];//remove piece 1
        if(source2 != null)
            hash ^= keyValues[(piece2.getInt() - 1)*piece2.getPlayer() *64 + source2.row * 8 + source2.column];//remove piece 2
        if(piece1 != null) {
            Position pos = piece1.getPos();
            hash ^= keyValues[(piece1.getInt() - 1) * piece1.getPlayer() * 64 + source1.row * 8 + source1.column];//add piece 1
        }
        if(piece2 != null) {
            Position pos = piece2.getPos();
            hash ^= keyValues[(piece2.getInt() - 1) * piece2.getPlayer() * 64 + pos.row * 8 + pos.column];//add piece 2
        }

    }

    public long getHash(){return hash;}
}
package gameLogic.util;

import java.util.Random;

import chess.Color;
import chess.Square;
import gameLogic.pieces.*;

//class implementing zobrist hashing
public class Hashing
{

    long seed = 6942069;//nice
    long hash;
    Random random;
    long[] keyValues;
    boolean[] castling;


    public Hashing()
    {
        castling = new boolean[4];
        for (int i = 0; i < castling.length; i++) {
            castling[i] =false;
        }
        random = new Random(seed);
        keyValues = new long[781];//781 is the amount of numbers needed for zobrist hashing
        for (int i = 0; i < keyValues.length; i++) {
            keyValues[i] = random.nextLong();
        }
        //hash = calculateHash(board.getChessBoard(),blackToMove);
    }

    public long calculateHash(Piece[][] board, boolean blackToMove)
    {
        long hash = 0;

        int enPassentColumn = -1;//store the column of a valid en passant square, if any

        boolean castling00 = false;
        boolean castling07 = false;
        boolean castling70 = false;
        boolean castling77 = false;
        boolean castlingBlack = false;
        boolean castlingWhite = false;


        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++) {
                Position pos = board[i][j].getPos();
                hash ^= keyValues[(board[i][j].getInt() - 1)* board[i][j].getPlayer() *64 + pos.row * 8 + pos.column];
                switch (board[i][j].getInt())//check for en passant
                {
                    case 1:
                        Peasant p = (Peasant) board[i][j];

                        if (p.getLeftEnPassant()) {
                            enPassentColumn = pos.row - 1;
                        }
                        if (p.getRightEnPassant()) {
                            enPassentColumn = pos.row + 1;
                        }
                        break;
                    case 4:
                        Rook rookPiece = (Rook) board[i][j];
                        if (rookPiece.isFirstMove()) {
                            if (rookPiece.getPos().row == 0) {
                                if (rookPiece.getPos().column == 0) {
                                    castling00 = true;
                                } else {
                                    castling07 = true;
                                }
                            } else if (rookPiece.getPos().row == 7) {
                                if (rookPiece.getPos().column == 0) {
                                    castling70 = true;
                                } else {
                                    castling77 = true;
                                }
                            }
                        }
                        break;
                    case 6:
                        King kingPiece = (King) board[i][j];
                        if (kingPiece.firstMove) {
                            if (kingPiece.getPlayer() == 1) {
                                castlingWhite = true;
                            } else {
                                castlingBlack = true;
                            }
                        }
                        break;
                }
            }
        }

        if(enPassentColumn >= 0)
        {
            hash ^= keyValues[64*12 + enPassentColumn];
        }

        if(castling00 && castlingBlack)
        {
            hash ^= keyValues[64*12 + 8];
            castling[0] = true;
        }
        if(castling07 && castlingBlack)
        {
            hash ^= keyValues[64*12 + 8 + 1];
            castling[1] = true;
        }
        if(castling70 && castlingWhite)
        {
            hash ^= keyValues[64*12 + 8 + 2];
            castling[2] = true;
        }
        if(castling77 && castlingWhite)
        {
            hash ^= keyValues[64*12 + 8 + 3];
            castling[4] = true;
        }

        if(blackToMove){hash ^= keyValues[780];}
        return hash;
    }

    public long calculateHash(Square[][] board, boolean blackToMove)
    {
        long hash = 0;

        int enPassentColumn = -1;//store the column of a valid en passant square, if any

        boolean castling00 = false;
        boolean castling07 = false;
        boolean castling70 = false;
        boolean castling77 = false;
        boolean castlingBlack = false;
        boolean castlingWhite = false;


        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j].getPiece() == null) {continue;}
                Position pos = new Position(i,j);
                int player = board[i][j].getPiece().getColor() == Color.White? 1:2;
                int pieceValue = board[i][j].getPiece().getPieceType().getValue();
                hash ^= keyValues[(pieceValue - 1)* player *64 + pos.row * 8 + pos.column];
                switch (pieceValue)//check for en passant
                {
                    case 1:
                        chess.Piece p = board[i][j].getPiece();

                        if (p.getLeftEnpassant()) {
                            enPassentColumn = pos.row - 1;
                        }
                        if (p.getRightEnpassant()) {
                            enPassentColumn = pos.row + 1;
                        }
                        break;
                    case 4:
                        chess.Piece rookPiece = board[i][j].getPiece();
                        if (rookPiece.hasMoved()) {
                            if (pos.row == 0) {
                                if (pos.column == 0) {
                                    castling00 = true;
                                } else {
                                    castling07 = true;
                                }
                            } else if (pos.row == 7) {
                                if (pos.column == 0) {
                                    castling70 = true;
                                } else {
                                    castling77 = true;
                                }
                            }
                        }
                        break;
                    case 6:
                        chess.Piece kingPiece = board[i][j].getPiece();
                        if (kingPiece.hasMoved()) {
                            if (kingPiece.getColor() == Color.White) {
                                castlingWhite = true;
                            } else {
                                castlingBlack = true;
                            }
                        }
                        break;
                }
            }
        }

        if(enPassentColumn >= 0)
        {
            hash ^= keyValues[64*12 + enPassentColumn];
        }

        if(castling00 && castlingBlack)
        {
            hash ^= keyValues[64*12 + 8];
            castling[0] = true;
        }
        if(castling07 && castlingBlack)
        {
            hash ^= keyValues[64*12 + 8 + 1];
            castling[1] = true;
        }
        if(castling70 && castlingWhite)
        {
            hash ^= keyValues[64*12 + 8 + 2];
            castling[2] = true;
        }
        if(castling77 && castlingWhite)
        {
            hash ^= keyValues[64*12 + 8 + 3];
            castling[4] = true;
        }

        if(blackToMove){hash ^= keyValues[780];}
        return hash;
    }



    public void updateHash(Position source1,Position source2, Position target1,Position target2, Piece piece1, Piece piece2)
    {
        int piece1Index = (piece1.getInt() - 1)*piece1.getPlayer() *64;
        int piece2Index = (piece2.getInt() - 1)*piece2.getPlayer() *64;
        update(source1, target1, piece1Index);//update piece 1
        update(source2, target2, piece2Index);//update piece 2
    }

    private void update(Position position1, Position position2, int pieceIndex) {
        if(position1 != null)
            hash ^= keyValues[pieceIndex + position1.row * 8 + position1.column];//remove piece 1
        if(position2 != null)
            hash ^= keyValues[pieceIndex + position2.row * 8 + position2.column];//remove piece 2
    }

    public long getHash(){return hash;}
}
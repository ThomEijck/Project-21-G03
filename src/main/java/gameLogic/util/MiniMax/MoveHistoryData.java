package gameLogic.util.MiniMax;

import gameLogic.util.*;
import gameLogic.pieces.*;
public class MoveHistoryData
{
    private Move madeMove;
    private Piece capturedPiece;
    private Piece movedPiece;
    private boolean castling;
    private Position leftEPPos;//position of the piece that has left en passant rights
    private Position rightEPPos;//position of the piece that has right en passant rights
    private Position capturedPos;
    private int move50;
    public MoveHistoryData(Move move,Position capturedPos, Piece toMove, Piece capture,boolean castling, Position leftEPPos,Position rightEPPos,int move50)
    {

        madeMove = move;
        this.capturedPos = capturedPos;
        this.leftEPPos = leftEPPos;
        this.rightEPPos = rightEPPos;
        this.castling = castling;//if castling has been done we need to move another piece, the king
        //create copies of the pieces to prevent funny stuff with object references
        capturedPiece = createPiece(capture);
        movedPiece = createPiece(toMove);
    }

    public int getMove50() {return move50;}

    public Move getMadeMove() {
        return madeMove;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public Position getCapturedPos() {return capturedPos;}

    public Position getLeftEPPos() {return leftEPPos;}

    public Position getRightEPPos() {return rightEPPos;}

    public boolean isCastling() {
        return castling;
    }

    //method to make a copy of a piece
    private Piece createPiece(Piece toCopy)
    {
        if(toCopy == null)
        {
            return null;
        }
        Piece piece;
        if(toCopy.getClass() == Peasant.class)
        {
            Peasant pawn = new Peasant(new Position(toCopy.getPos()),toCopy.getPlayer());
            Peasant og = (Peasant) toCopy;
            if(og.getLeftEnPassant())
                pawn.setLeftEnpassant();
            if(og.getRightEnPassant())
                pawn.setRightEnpassant();
            piece = pawn;
        }else if(toCopy.getClass() == Rook.class)
        {
            piece = new Rook(new Position(toCopy.getPos()),toCopy.getPlayer());
        }else if(toCopy.getClass() == King.class)
        {
            piece = new King(new Position(toCopy.getPos()),toCopy.getPlayer());
        }else if(toCopy.getClass() == Bishop.class)
        {
            piece = new Bishop(new Position(toCopy.getPos()),toCopy.getPlayer());
        }else if(toCopy.getClass() == Queen.class)
        {
            piece = new Queen(new Position(toCopy.getPos()),toCopy.getPlayer());
        }else if(toCopy.getClass() == Knight.class)
        {
            piece = new Knight(new Position(toCopy.getPos()),toCopy.getPlayer());
        }else
        {
            piece = new Piece(new Position(toCopy.getPos()),toCopy.getPlayer());
        }


        piece.setMoveCount(toCopy.getMoveCount());
        return piece;
    }
}

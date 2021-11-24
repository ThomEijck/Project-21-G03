package gameLogic.util.MiniMax;

import gameLogic.util.*;
import gameLogic.pieces.*;
public class MoveHistoryData
{
    private Move madeMove;
    private Piece capturedPiece;
    private Piece movedPiece;
    private boolean castling;

    public MoveHistoryData(Move move, Piece toMove, Piece capture,boolean castling)
    {

        madeMove = move;
        this.castling = castling;//if castling has been done we need to move another piece, the king
        //create copies of the pieces to prevent funny stuff with object references
        capturedPiece = createPiece(capture);
        movedPiece = createPiece(toMove);
    }

    public Move getMadeMove() {
        return madeMove;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

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
            piece = new Knight(new Position(toCopy.getPos()),toCopy.getPlayer());
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
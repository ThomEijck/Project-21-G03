package gameLogic.util;

import chess.Square;
import gameLogic.pieces.*;
import gameLogic.util.MiniMax.MoveHistoryData;

import java.util.*;


public class Board {

    private Piece[][] chessBoard = new Piece[8][8];
    private Stack<MoveHistoryData> moveHistory;
    private Piece[] possibleEnPassantPieces = new Piece[2];//always only 2 pieces can do enpassant
    private int move50rule = 0;//counter to keep track of the 50 move rule

    public Board() {
        this.chessBoard = createBoard(chessBoard);
        moveHistory = new Stack<MoveHistoryData>();

    }

    public int getMove50rule() {return move50rule;}

    public Piece[][] getChessBoard() {
        return this.chessBoard;
    }

    private Piece[][] createBoard(Piece[][] chessBoard) {
        for(int i = 0; i < 8; i++) {
            chessBoard[1][i] = new Peasant(new Position(1,i), 2);
            chessBoard[6][i] = new Peasant(new Position(6,i), 1);
        }

        chessBoard[0][0] = new Rook(new Position(0,0), 2);
        chessBoard[0][7] = new Rook(new Position(0,7), 2);
        chessBoard[7][0] = new Rook(new Position(7,0), 1);
        chessBoard[7][7] = new Rook(new Position(7,7), 1);

        chessBoard[0][1] = new Knight(new Position(0,1), 2);
        chessBoard[0][6] = new Knight(new Position(0,6), 2);
        chessBoard[7][1] = new Knight(new Position(7,1), 1);
        chessBoard[7][6] = new Knight(new Position(7,6), 1);

        chessBoard[0][2] = new Bishop(new Position(0,2), 2);
        chessBoard[0][5] = new Bishop(new Position(0,5), 2);
        chessBoard[7][2] = new Bishop(new Position(7,2), 1);
        chessBoard[7][5] = new Bishop(new Position(7,5), 1);

        chessBoard[0][3] = new Queen(new Position(0,3), 2);
        chessBoard[7][3] = new Queen(new Position(7,3), 1);

        chessBoard[0][4] = new King(new Position(0,4), 2);
        chessBoard[7][4] = new King(new Position(7,4), 1);

        return chessBoard;
    }

    public void printBoard() {

        for(int i = 0; i < chessBoard.length; i++) {
            for(int j = 0; j < chessBoard.length; j++) {
                if(this.chessBoard[i][j] != null) {
                    System.out.print(chessBoard[i][j] + "" +chessBoard[i][j].getPlayer() + "\t");
                }else
                {
                    System.out.print("  -  \t");
                }
            }
            System.out.println();
        }
    }
    
    public boolean movePiece(Move move, int diceValue, int playerTurn,boolean isAI)
    {
        Position start = move.getStart();
        Position end = move.getEnd();
        Piece piece = chessBoard[start.row][start.column];
        Piece captured = chessBoard[end.row][end.column];//can be null but that does not matter;
        Position capturedPos = move.getEnd();
        //cant move the other players pieces
        if(piece.getPlayer() != playerTurn){return  false;}

        //if the piece that wants to be moved is not allowed by the dice
        if(piece.getInt() != 1 && piece.getInt() != diceValue)
        {
            return false;
        }
        //if a pawn can be promoted, a different number than 1 can be used
        else if(piece.getInt() == 1 && piece.getInt() != diceValue && !(end.row == 0 || end.row == 7))
        {
            return false;
        }

        if(piece == null)
        {
            System.out.println("No piece selected");
            return false;
        }

        if(validTarget(piece, end))
        {
            chessBoard[end.row][end.column] = piece;
            chessBoard[start.row][start.column] = null;
        }else
        {
            System.out.println("Invalid target");
            System.out.println(start);
            System.out.println(end);
            System.out.println(piece);
            System.out.println(piece.getPos());
            Position[] moves = piece.findMoves(getChessBoard());
            if(piece.getClass() == Peasant.class)
            {
                System.out.println(((Peasant)piece).getLeftEnPassant());
                System.out.println(((Peasant)piece).getRightEnPassant());
            }
            System.out.println(Arrays.toString(moves));
            printBoard();
            revertMove();
            printBoard();
            return false;
        }

        boolean rEnpassant = false;
        boolean lEnpassant = false;
        if(piece.getInt() == 1) {
            rEnpassant = ((Peasant) piece).getRightEnPassant();
            lEnpassant = ((Peasant) piece).getLeftEnPassant();
        }

        //do special cases of the pawn
        if(piece.getInt() == 1)
        {
            Peasant pawn = (Peasant) piece;
            if((start.column - end.column == -1 && rEnpassant)
            || (start.column - end.column == 1 && lEnpassant))
            {
                captured = chessBoard[start.row][end.column];
                capturedPos = new Position(start.row,end.column);
                chessBoard[start.row][end.column] = null;
                //System.out.println(new Position(start.row, end.column));
            }

            if(end.row == 0 || end.row == 7)
            {
                //if pawn can choose its resulting piece we default to queen for simplicity
                //we would need to change too much in our implementation for the algorithm to support promoting to other pieces
                if(isAI && (diceValue == 1 || diceValue == 6))
                {
                    chessBoard[end.row][end.column] = promotePawn(piece, 5);
                }else
                {
                    chessBoard[end.row][end.column] = promotePawn(piece, diceValue);
                }

            }

        }
        //add move to move history
        Position lEPos = null;
        Position rEPos = null;
        if(possibleEnPassantPieces[0] != null) {
            lEPos = possibleEnPassantPieces[0].getPos();
            lEPos = new Position(lEPos.row,lEPos.column);//make a copy to prevent funny java stuff
        }
        if(possibleEnPassantPieces[1] != null) {
            rEPos = possibleEnPassantPieces[1].getPos();
            rEPos = new Position(rEPos.row,rEPos.column);
        }
        moveHistory.push(new MoveHistoryData(move,capturedPos,piece,captured,false,lEPos,rEPos,move50rule));
        piece.setPos(end);

        resetEnPassant();
        //if the pawn has moved two spaces make the adjacent enemy pawns be able to use en passant
        if( piece.getInt() == 1 && Math.abs(start.row - end.row) == 2)
        {
            enableEnPassant(end);
        }


        // Check if castling is done
        if(piece.getInt() == 6){
            Position startPos = null;
            Position endPos = null;
            Piece rook = null;
            // Check if castling is done on queen side
            if (start.row == end.row && start.column-2 == end.column) {
                Rook rook1 = (Rook) chessBoard[start.row][start.column - 4];
                rook = rook1;
                startPos = new Position(start.row,start.column-4);
                endPos = new Position(start.row,start.column-1);
                chessBoard[start.row][start.column - 1] = rook1;
                chessBoard[start.row][start.column - 4] = null;
            }

            // Check if castling is done on king side

            if(start.row == end.row && start.column+2 == end.column){
                Rook rook2 = (Rook) chessBoard[start.row][start.column+3];
                rook = rook2;
                startPos = new Position(start.row,start.column+3);
                endPos = new Position(start.row,start.column+1);
                chessBoard[start.row][start.column+1] = rook2;
                chessBoard[start.row][start.column+3] = null;
            }
            if(startPos != null)
            {
                //add movement of the rook to move history
                moveHistory.push(new MoveHistoryData(new Move(startPos,endPos),capturedPos,rook,null,true,null,null,move50rule));
                rook.setPos(endPos);
            }

        }
        if(piece.getInt() != 1 && captured == null)
        {
            move50rule++;//if no pawn has been moved and if no piece has been captured we increase the move50rule variable
        }
        else
        {
            move50rule = 0;//reset the variable otherwise
        }
        piece.hasBeenMoved();
        //notify the game manager that a piece has been moved
        //GameManager.pieceMoved();
        return true;
    }

    public boolean revertMove()
    {

        if(moveHistory.empty())
        {
            return false;
        }
        MoveHistoryData toRevert = moveHistory.pop();
        Move move = toRevert.getMadeMove();
        Position start = move.getStart();
        Position end = move.getEnd();
        Position capture = toRevert.getCapturedPos();

        move50rule = toRevert.getMove50();

        chessBoard[end.row][end.column] = null;
        chessBoard[capture.row][capture.column] = toRevert.getCapturedPiece();
        chessBoard[start.row][start.column] = toRevert.getMovedPiece();

        //en passant stuff, if you encounter problems with en passant, good luck
        //im pretty sure en passant is evil

        //pieces that have en passant due to this move will not have it anymore
        resetEnPassant();

        Position leftPos = toRevert.getLeftEPPos();
        Position rightPos = toRevert.getRightEPPos();

        //add en passant to the correct pieces
        if(leftPos != null)
        {
            possibleEnPassantPieces[0] = chessBoard[leftPos.row][leftPos.column];
            ((Peasant)possibleEnPassantPieces[0]).setRightEnpassant();
        }
        if(rightPos != null)
        {
            possibleEnPassantPieces[1] = chessBoard[rightPos.row][rightPos.column];
            ((Peasant)possibleEnPassantPieces[1]).setLeftEnpassant();
        }

        //if the current move was a castling move, we need to revert another move
        if(toRevert.isCastling())
        {
            revertMove();
        }

        return true;
    }

    private boolean validTarget(Piece piece, Position target)
    {
        Position[] targets = piece.findMoves(chessBoard);
        for (Position position : targets) {
            if(position.isEqual(target))
            {
                return true;
            }
        }
        return false;
    }

    private void enableEnPassant(Position pos)
    {
        if (pos.column - 1 >= 0){
            Piece leftPiece = chessBoard[pos.row][pos.column - 1];
            if(leftPiece != null && leftPiece.getInt() == 1)
            {
                ((Peasant) leftPiece).setRightEnpassant();
                possibleEnPassantPieces[0] = leftPiece;
            }
        }

        if(pos.column + 1 <= 7){
            Piece rightPiece = chessBoard[pos.row][pos.column + 1];
            if(rightPiece != null && rightPiece.getInt() == 1)
            {
                ((Peasant) rightPiece).setLeftEnpassant();
                possibleEnPassantPieces[1] = rightPiece;
            }
        }
    }

    private void resetEnPassant()
    {
        for (int i = 0; i < possibleEnPassantPieces.length; i++)
        {
            Peasant piece = (Peasant) possibleEnPassantPieces[i];
            if(piece != null){piece.resetEnPassant();}
            possibleEnPassantPieces[i] = null;
        }
    }

    private Piece promotePawn(Piece pawn, int diceResult)
    {
        Position pos = pawn.getPos();
        int player = pawn.getPlayer();
        switch (diceResult) {
            case 1://we let the player choose in this case
            case 6:
                PromotionChooser pc = new PromotionChooser(pawn);
                return pc.getNewPiece();
            case 2:
                return new Knight(pos, player);
            case 3:
                return new Bishop(pos, player);
            case 4:
                return new Rook(pos, player);//System.out.println("Pawn Promoted!!!");
            default://will also take a value of 5 into account which is the queen
                return new Queen(pos, player);
                
        }
    }



}

package gameLogic.util;

import chess.Square;
import gameLogic.pieces.*;

public class Board {

    private Piece[][] chessBoard = new Piece[8][8];

    public Board() {
        this.chessBoard = createBoard(chessBoard);
    }

    public Piece[][] getChessBoard() {
        return this.chessBoard;
    }
    private Piece[] possibleEnPassantPieces = new Piece[2];//always only 2 pieces can do enpassant
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
                    System.out.print(chessBoard[i][j] + "\t");
                }else
                {
                    System.out.print("  -\t\t");
                }
            }
            System.out.println();
        }
    }
    
    public boolean movePiece(Position start, Position end, int diceValue, int playerTurn)
    {

        Piece piece = chessBoard[start.row][start.column];

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
            return false;
        }
        
        piece.setPos(end);

        boolean rEnpassant = false;
        boolean lEnpassant = false;
        if(piece.getInt() == 1) {
            rEnpassant = ((Peasant) piece).getRightEnPassant();
            lEnpassant = ((Peasant) piece).getLeftEnPassant();
        }
        resetEnPassant();

        //do special cases of the pawn
        if(piece.getInt() == 1)
        {
            Peasant pawn = (Peasant) piece;
            if((start.column - end.column == -1 && rEnpassant)
            || (start.column - end.column == 1 && lEnpassant))
            {
                chessBoard[start.row][end.column] = null;
                //System.out.println(new Position(start.row, end.column));
            }

            pawn.hasMoved();
            //if the pawn has moved two spaces make the adjacent enemy pawns be able to use en passant
            if( Math.abs(start.row - end.row) == 2)
            {
                enableEnPassant(end);
            }

            if(end.row == 0 || end.row == 7)
            {
                chessBoard[end.row][end.column] = promotePawn(piece, diceValue);
            }

        }

        if(piece.getInt() == 4){
            Rook rook = (Rook) piece;
            rook.hasMoved();
        }

        // Check if castling is done
        if(piece.getInt() == 6){
            // Check if castling is done on queen side
            if (start.row == end.row && start.column-2 == end.column) {
                Rook rook1 = (Rook) chessBoard[start.row][start.column - 4];
                chessBoard[start.row][start.column - 1] = rook1;
                chessBoard[start.row][start.column - 4] = null;
                rook1.hasMoved();
            }

            // Check if castling is done on king side

            if(start.row == end.row && start.column+2 == end.column){
                Rook rook2 = (Rook) chessBoard[start.row][start.column+3];
                chessBoard[start.row][start.column+1] = rook2;
                chessBoard[start.row][start.column+3] = null;
                rook2.hasMoved();
            }

            ((King) piece).hasMoved();
        }

        //notify the game manager that a piece has been moved
        GameManager.pieceMoved();
        return true;
    }

    private boolean validTarget(Piece piece, Position target)
    {
        Position[] targets = piece.findMoves(chessBoard);
        //System.out.println(java.util.Arrays.toString(targets));
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
        }
    }

    private Piece promotePawn(Piece pawn, int diceResult)
    {
        Position pos = pawn.getPos();
        int player = pawn.getPlayer();
        switch (diceResult) {
            case 1:
                PromotionChooser pc = new PromotionChooser(pawn);
                return pc.getNewPiece();
            case 2:
                return new Knight(pos, player);
            case 3:
                return new Bishop(pos, player);
            case 4:
                return new Rook(pos, player);
            case 5:
                //System.out.println("Pawn Promoted!!!");
                return new Queen(pos, player);
            case 6:
                //dafuq
                //yeah this shit not possible bro
                break;
            default:
                return new Queen(pos, player);
                
        }
        return null;
    }



}

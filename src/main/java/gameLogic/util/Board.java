package gameLogic.util;

import gameLogic.pieces.*;

public class Board {

    private Piece[][] chessBoard = new Piece[8][8];

    public Board() {
        this.chessBoard = createBoard(chessBoard);
        printBoard();
    }

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

        chessBoard[0][2] = new Bishop(new Position(0,1), 2);
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
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }
    
    public boolean movePiece(Position start, Position end)
    {
        Piece piece = chessBoard[start.row][start.column];
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
        
        //enable en passant
        if(piece.getInt() == 1)
        {
            Peasant pawn = (Peasant) piece;
            if((start.column - end.column == -1 && pawn.getRightEnPassant()) 
            || (start.column - end.column == 1 && pawn.getLeftEnPassant()))
            {
                chessBoard[start.row][end.column] = null;
                System.out.println(new Position(start.row, end.column));
            }

            pawn.hasMoved();
            //if the pawn has moved two spaces make the adjacent enemy pawns be able to use en passant
            if( Math.abs(start.row - end.row) == 2)
            {
                enableEnPassant(end);
            }
        }

        piece.setPos(end);

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
            }
        }

        if(pos.column + 1 <= 7){
            Piece rightPiece = chessBoard[pos.row][pos.column + 1];
            if(rightPiece != null && rightPiece.getInt() == 1)
            {
                ((Peasant) rightPiece).setLeftEnpassant();
            }
        }
    }

}

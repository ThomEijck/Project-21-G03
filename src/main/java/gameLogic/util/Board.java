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
                    System.out.print(this.chessBoard[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}

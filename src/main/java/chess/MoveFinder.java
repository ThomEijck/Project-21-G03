package chess;

import gameLogic.pieces.Rook;
import gameLogic.util.Position;

public class MoveFinder {
    private Square[][] squares;

    public MoveFinder(Board board) {
        squares = board.getSquares();
    }

    public Square[] moveDiagonal(Square s, int maxD) {

        int posColumn = s.getPosition().column;
        int posRow = s.getPosition().row;
        Piece piece = s.getPiece();
        Position[] possibleMoves = new Position[13];

        int counter = 0;

        // Add all possible positions diagonally up and to the right of the current
        // position
        for (int i = (posRow + 1), j = (posColumn + 1), d = 0; i < squares.length && j < squares.length
                && d < maxD; i++, j++, d++) {
            if (squares[i][j].getPiece() == null) {
                possibleMoves[counter++] = new Position(i, j);
            } else if (squares[i][j].getPiece().getColor() != piece.getColor()) {
                possibleMoves[counter++] = new Position(i, j);
                break;
            } else {
                break;
            }
        }

        // Add all possible positions diagonally up and to the left of the current
        // position
        for (int i = (posRow + 1), j = (posColumn - 1), d = 0; i < squares.length && j >= 0
                && d < maxD; i++, j--, d++) {
            if (squares[i][j].getPiece() == null) {
                possibleMoves[counter++] = new Position(i, j);
            } else if (squares[i][j].getPiece().getColor() != piece.getColor()) {
                possibleMoves[counter++] = new Position(i, j);
                break;
            } else {
                break;
            }
        }

        // Add all possible positions diagonally down and to the right of the current
        // position
        for (int i = (posRow - 1), j = (posColumn + 1), d = 0; i >= 0 && j < squares.length
                && d < maxD; i--, j++, d++) {
            if (squares[i][j].getPiece() == null) {
                possibleMoves[counter++] = new Position(i, j);
            } else if (squares[i][j].getPiece().getColor() != piece.getColor()) {
                possibleMoves[counter++] = new Position(i, j);
                break;
            } else {
                break;
            }
        }

        // Add all possible positions diagonally down and to the left of the current
        // position
        for (int i = (posRow - 1), j = (posColumn - 1), d = 0; i >= 0 && j >= 0 && d < maxD; i--, j--, d++) {
            if (squares[i][j].getPiece() == null) {
                possibleMoves[counter++] = new Position(i, j);
            } else if (squares[i][j].getPiece().getColor() != piece.getColor()) {
                possibleMoves[counter++] = new Position(i, j);
                break;
            } else {
                break;
            }
        }
        Square[] finalMoves = new Square[counter];
        for (int i = 0; i < finalMoves.length; i++) {
            finalMoves[i] = squares[possibleMoves[i].row][possibleMoves[i].column];
        }

        return finalMoves;

    }

    public Square[] moveOrthogonal(Square s, int maxD) {

        int posColumn = s.getPosition().column;
        int posRow = s.getPosition().row;
        Piece piece = s.getPiece();
        Position[] possibleMoves = new Position[63];
        int counter = 0;

        // Check above
        for (int i = posRow + 1, d = 0; i < squares.length && d < maxD; i++, d++) {
            if (squares[i][posColumn].getPiece() == null) {
                possibleMoves[counter++] = new Position(i, posColumn);
            } else if (squares[i][posColumn].getPiece().getColor() != piece.getColor()) {
                possibleMoves[counter++] = new Position(i, posColumn);
                break;
            } else {
                break;
            }
        }
        // Check below
        for (int i = posRow - 1, d = 0; i >= 0 && d < maxD; i--, d++) {
            if (squares[i][posColumn].getPiece() == null) {
                possibleMoves[counter++] = new Position(i, posColumn);
            } else if (squares[i][posColumn].getPiece().getColor() != piece.getColor()) {
                possibleMoves[counter++] = new Position(i, posColumn);
                break;
            } else {
                break;
            }
        }
        // Check right
        for (int j = posColumn + 1, d = 0; j < squares.length && d < maxD; j++, d++) {
            if (squares[posRow][j].getPiece() == null) {
                possibleMoves[counter++] = new Position(posRow, j);
            } else if (squares[posRow][j].getPiece().getColor() != piece.getColor()) {
                possibleMoves[counter++] = new Position(posRow, j);
                break;
            } else {
                break;
            }

        }
        // Check left
        for (int j = posColumn - 1, d = 0; j >= 0 && d < maxD; j--, d++) {
            if (squares[posRow][j].getPiece() == null) {
                possibleMoves[counter++] = new Position(posRow, j);
            } else if (squares[posRow][j].getPiece().getColor() != piece.getColor()) {
                possibleMoves[counter++] = new Position(posRow, j);
                break;
            } else {
                break;
            }
        }
        Square[] finalMoves = new Square[counter];
        for (int i = 0; i < finalMoves.length; i++) {
            finalMoves[i] = squares[possibleMoves[i].row][possibleMoves[i].column];
        }

        return finalMoves;

    }

    public Square[] moveKnight(Square s) {
        Position[] possibleMoves = new Position[8];

        // possible moves the knight can make
        int[][] possibleMovement = { { -1, +2 }, { -2, +1 }, { -1, -2 }, { -2, -1 }, { +1, +2 }, { +2, +1 }, { +1, -2 },
                { +2, -1 } };

        // current position
        int row = s.getPosition().row;
        int column = s.getPosition().column;

        // calculate target positions
        for (int i = 0; i < possibleMoves.length; i++) {
            possibleMoves[i] = new Position(row + possibleMovement[i][0], column + possibleMovement[i][1]);
        }

        // check target position validity
        int counter = 0;
        for (int i = 0; i < possibleMoves.length; i++) {
            Position pos = possibleMoves[i];
            // check if the target is not on the board
            if (pos.column < 0 || pos.column > 7 || pos.row < 0 || pos.row > 7) {
                possibleMoves[i] = new Position(-1, -1);
                continue; // position is invalid, so check next position
            }
            // knowing that the target is on the board
            boolean correctTarget = squares[pos.row][pos.column].getPiece() == null
                    || (squares[pos.row][pos.column].getPiece() != null
                            && squares[pos.row][pos.column].getPiece().getColor() != s.getPiece().getColor());

            if (!correctTarget) {
                possibleMoves[i] = new Position(-1, -1);
            } else {
                counter++;
            }
        }

        Square[] finalMoves = new Square[counter];
        for (int i = 0, c = 0; i < possibleMoves.length; i++) {
            if (possibleMoves[i].row != -1) {
                finalMoves[c++] = squares[possibleMoves[i].row][possibleMoves[i].column];
            }
        }
        return finalMoves;
    }

    public Square[] movePawn(Square s) {
        Position[] targetPositions;
        int[][] possibleMoves;

        // maps the player number to the correct direction of the pawn movement
        int moveDirection = s.getPiece().getColor() == Color.White ? 1 : -1;

        // list the possible moves the pawn can make
        if (!s.getPiece().hasMoved()) {
            possibleMoves = new int[][] { { moveDirection, -1 }, { moveDirection, 1 }, { moveDirection, 0 },
                    { 2 * moveDirection, 0 } };
        } else {
            possibleMoves = new int[][] { { moveDirection, -1 }, { moveDirection, 1 }, { moveDirection, 0 } };
        }
        targetPositions = new Position[possibleMoves.length];

        // current position
        int row = s.getPosition().row;
        int column = s.getPosition().column;

        // generate possible target positions
        for (int i = 0; i < targetPositions.length; i++) {
            targetPositions[i] = new Position(row + possibleMoves[i][0], column + possibleMoves[i][1]);
        }

        // validate target positions
        int counter = 0;
        for (int i = 0; i < possibleMoves.length; i++) {
            Position pos = targetPositions[i];
            boolean correctTarget = false;

            if (pos.column < 0 || pos.column > 7 || pos.row < 0 || pos.row > 7) {
                targetPositions[i] = new Position(-1, -1);
                continue;// position is invalid, so check next position
            }

            Square ts = squares[pos.row][pos.column];

            // knowing that the target is on the board
            if (i <= 1)// targeting top left/right
            {
                // let the pawn be able to move when doing en passant
                if ((i == 0 && s.getPiece().getLeftEnpassant()) || (i == 1 && s.getPiece().getRightEnpassant())) {
                    correctTarget = true;
                } else {
                    correctTarget = (ts.getPiece() != null && ts.getPiece().getColor() != s.getPiece().getColor());
                }
            } else// targeting straight ahead
            {
                if (i == 2)
                    correctTarget = (ts.getPiece() == null);
                if (i == 3)
                    correctTarget = (ts.getPiece() == null
                            && squares[pos.row - moveDirection][pos.column].getPiece() == null);

            }

            if (!correctTarget) {
                targetPositions[i] = new Position(-1, -1);
            } else {
                counter++;
            }
        }
        Square[] finalMoves = new Square[counter];
        for (int i = 0, c = 0; i < targetPositions.length; i++) {
            if (targetPositions[i].row != -1) {
                finalMoves[c++] = squares[targetPositions[i].row][targetPositions[i].column];
            }
        }
        return finalMoves;
    }

    public Square[] moveCastling(Square s){
        int posColumn = s.getPosition().column;
        int posRow = s.getPosition().row;
        Position[] possibleMoves = new Position[2];


        int counter = 0;
        if(squares[posRow][posColumn-4].getPiece().getPieceType() == PieceType.Rook){
            Square rook = squares[posRow][posColumn-4];
            if(!rook.getPiece().hasMoved && squares[posRow][posColumn-3].getPiece() == null && squares[posRow][posColumn-2].getPiece() == null && squares[posRow][posColumn-1].getPiece() == null){
                possibleMoves[0] = new Position(posRow, posColumn-2);
                counter++;
            }
        }

        if(squares[posRow][posColumn+3].getPiece().getPieceType() == PieceType.Rook){
            Square rook =  squares[posRow][posColumn+3];
            if(!rook.getPiece().hasMoved && squares[posRow][posColumn+2].getPiece() == null && squares[posRow][posColumn+1].getPiece() == null){
                possibleMoves[1] = new Position(posRow, posColumn+2);
                counter++;
            }
        }

        Square[] finalMoves = new Square[counter];
        if(counter!=0) {
            for (int i = 0; i < finalMoves.length; i++) {
                finalMoves[i] = squares[possibleMoves[i].row][possibleMoves[i].column];
            }
        }

        return finalMoves;
    }

}

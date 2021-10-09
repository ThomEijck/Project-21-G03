package chess;

import javax.swing.*;

public class PromotionChooser2D {

    Piece piece;

    public PromotionChooser2D(Piece piece)
    {
        this.piece = piece;
    }

    public Piece getNewPiece()
    {
        Color col = piece.getColor();

        Object[] possibleValues = { "Queen", "Knight", "Bishop","Rook"};
        Object selectedValue = JOptionPane.showOptionDialog(null,"To which piece do you want to promote the pawn", "Promotion",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                null,possibleValues,possibleValues[0]);

        int result = (int)selectedValue;

        PieceType[] types = {PieceType.Queen,PieceType.Knight,PieceType.Bishop,PieceType.Rook};

        return result >= 0? new Piece(col,types[result]):new Piece(col,types[0]);
    }
}

package gameLogic.util;


import gameLogic.pieces.*;

import javax.swing.*;

public class PromotionChooser {

    Piece piece;

    public PromotionChooser(Piece piece)
    {
        this.piece = piece;
    }

    public Piece getNewPiece()
    {
        Position pos = piece.getPos();
        int player = piece.getPlayer();
        Rook rook = new Rook(pos, player);
        rook.hasMoved();
        Piece[] possibleValues = { new Queen(pos, player), new Knight(pos, player), new Bishop(pos, player), rook};
        Object selectedValue = JOptionPane.showOptionDialog(null,"To which piece do you want to promote the pawn", "Promotion",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                null,possibleValues,possibleValues[0]);
        return possibleValues[(int)selectedValue];
    }
}

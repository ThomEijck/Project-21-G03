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
        Rook rook = new Rook(pos, player,3);
        rook.hasBeenMoved();
        Piece[] possibleValues = { new Queen(pos, player,2), new Knight(pos, player,3), new Bishop(pos, player,3), rook};
        Object selectedValue = JOptionPane.showOptionDialog(null,"To which piece do you want to promote the pawn", "Promotion",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                null,possibleValues,possibleValues[0]);
        return possibleValues[(Integer)selectedValue];
    }
}

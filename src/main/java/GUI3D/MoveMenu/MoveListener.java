package GUI3D.MoveMenu;


import GUI3D.main.Main;
import gameLogic.util.GameManager;
import gameLogic.util.Move;
import gameLogic.util.Position;
import org.lwjgl.system.CallbackI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class MoveListener implements ActionListener{

    private JTextField rowStart;
    private JTextField columnStart;
    private JTextField rowEnd;
    private JTextField columnEnd;
    private JFrame frame;

    public MoveListener(JTextField textfield1, JTextField textfield2,JTextField textfield3,JTextField textfield4, JFrame frame){
        this.rowStart = textfield1;
        this.columnStart = textfield2;
        this.rowEnd = textfield3;
        this.columnEnd = textfield4;
        this.frame = frame;
    }

    // reads textfields from manual shot menu and send information of new shot to simulator
    public void actionPerformed(ActionEvent event){
        try {
            String xPosStart = rowStart.getText();
            String yPosStart = columnStart.getText();
            String xPosEnd = rowEnd.getText();
            String yPosEnd = columnEnd.getText();
            int xStart = Integer.parseInt(xPosStart)-1;
            int yStart = Integer.parseInt(yPosStart)-1;
            int xEnd = Integer.parseInt(xPosEnd)-1;
            int yEnd = Integer.parseInt(yPosEnd)-1;

            Main.g.movePiece(new Move(new Position(xStart, yStart), new Position(xEnd,yEnd)),false);
            GameManager.pieceMoved();

            Main.appliedMove = true;
            Main.isOpenMoveBox = false;

            frame.dispose();

        }
        catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(frame, "Fill all the fields");
        }
    }

}
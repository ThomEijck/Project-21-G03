package GUI3D.MoveMenu;


import GUI3D.main.Main;
import gameLogic.util.Move;
import gameLogic.util.Position;

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

    public void actionPerformed(ActionEvent event){
        try {
            String xPosStart = rowStart.getText();
            String yPosStart = columnStart.getText().toLowerCase();
            String xPosEnd = rowEnd.getText();
            String yPosEnd = columnEnd.getText().toLowerCase();

            int xStart= 8 - Integer.parseInt(xPosStart);
            int yStart = 0;
            int xEnd= 8 - Integer.parseInt(xPosEnd);
            int yEnd = 0;

            for (char c : yPosStart.toCharArray()) {
                yStart = (char)(c - 'a');
            }
            for (char c : yPosEnd.toCharArray()) {
                yEnd = (char)(c - 'a');
            }

            Main.playerMove = new Move(new Position(xStart, yStart), new Position(xEnd, yEnd));
            System.out.println("before frame dispose");
            frame.dispose();
            System.out.println("after frame dispose");

        }
        catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(frame, "Fill all the fields");
        }
    }

}
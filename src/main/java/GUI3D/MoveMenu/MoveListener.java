package GUI3D.MoveMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveListener implements ActionListener{

    private JTextField x;
    private JTextField y;
    private JFrame theFrame;


    public MoveListener(JTextField textfield1, JTextField textfield2, JFrame frame){
        x = textfield1;
        y = textfield2;
        theFrame = frame;
    }


    public void actionPerformed(ActionEvent event){
        try {
            String xField = x.getText();
            String yField = y.getText();
            double xMove = Double.parseDouble(xField);
            double yMove = Double.parseDouble(yField);


        }
        catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(theFrame, "Fill all the fields");
        }
    }

}
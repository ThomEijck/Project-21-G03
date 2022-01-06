package GUI3D.MoveMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveListener implements ActionListener{

    private JTextField x;
    private JTextField y;
    private JFrame theFrame;

    /**
     * create listener for shot input
     * @param textfield1 JTextField, speed of shot
     * @param textfield2 JTextField, angle of shot
     * @param frame Frame
     */
    public MoveListener(JTextField textfield1, JTextField textfield2, JFrame frame){
        x = textfield1;
        y = textfield2;
        theFrame = frame;
    }

    /**
     * read velocity input from textfields and send to physics engine to take shot
     * @param event ActionEvent
     */
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
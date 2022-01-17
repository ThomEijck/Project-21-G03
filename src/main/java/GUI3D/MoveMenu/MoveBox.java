package GUI3D.MoveMenu;

import GUI3D.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// create menu for manual shot input
public class MoveBox {

    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 250;

    public void create(){
        Main.canOpenMoveBox = false;
        Main.isOpenMoveBox = true;
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setTitle("Player Move");
        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        frame.setLocation(0,0);
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();

        panel.setLayout(new BorderLayout());
        panel2.setLayout(new GridLayout(4, 2));

        JLabel label1 = new JLabel();
        label1.setText("Please make a move start position to end position, choose from 1-8");
        panel.add(label1,BorderLayout.NORTH);

        JLabel label2 = new JLabel();
        label2.setText("Start Row: ");
        panel2.add(label2);

        JTextField textfield1 = new JTextField(10);
        panel2.add(textfield1);

        JLabel label3 = new JLabel();
        label3.setText("Start Column: ");
        panel2.add(label3);

        JTextField textfield2 = new JTextField(10);
        panel2.add(textfield2);

        JLabel label4 = new JLabel();
        label4.setText("End Row: ");
        panel2.add(label4);

        JTextField textfield3 = new JTextField(10);
        panel2.add(textfield3);

        JLabel label5 = new JLabel();
        label5.setText("End Column: ");
        panel2.add(label5);

        JTextField textfield4 = new JTextField(10);
        panel2.add(textfield4);

        JButton button = new JButton("APPLY");
        panel.add(button, BorderLayout.SOUTH);

        frame.add(panel);
        frame.add(panel2);

        ActionListener moveListener = new MoveListener(textfield1, textfield2,textfield3,textfield4, frame);
        button.addActionListener(moveListener);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }
}
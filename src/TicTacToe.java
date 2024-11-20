import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TicTacToe extends JFrame implements ActionListener {

    JFrame frame = new JFrame();

        public TicTacToe(){
            frame.setTitle("Super Tic-Tac-Toe");
            frame.setSize(640,640);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.setVisible(true);
        }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
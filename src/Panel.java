import javax.swing.*;
import java.awt.*;

public class SuperTicTacToe {

    public static void main(String[] args) {
     
        JFrame frame = new JFrame("Super Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new GridLayout(3, 3)); 

      
        for (int i = 0; i < 9; i++) {
            JPanel subPanel = new JPanel(new GridLayout(3, 3));
            subPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            for (int j = 0; j < 9; j++) {
                JButton button = new JButton("");
                button.setFont(new Font("Arial", Font.BOLD, 20)); 
                subPanel.add(button);
            }

           
            frame.add(subPanel);
        }

        
        frame.setVisible(true);
    }
}


import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private JButton[][] buttons;
    private boolean isPlayable;

    public  Panel(){

        setLayout(new GridLayout(3, 3)); // 3x3 grid
        buttons = new JButton[3][3];
        isPlayable = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                buttons[i][j].setEnabled(true);
                buttons[i][j].addActionListener(e -> handleMove((JButton) e.getSource()));
                add(buttons[i][j]);
            }
        }
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }
    private void handleMove(JButton button) {
        if (isPlayable && button.getText().isEmpty()) {
            button.setText("X"); // Example move
            button.setEnabled(false);
            // Notify game logic if needed
        }
    }

    public void setPlayable(boolean playable) {
        this.isPlayable = playable;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(playable && buttons[i][j].getText().isEmpty());
            }
        }
    }
    public JButton[][] getButtons() {
        return buttons;
    }
}


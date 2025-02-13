import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private final JButton[][] buttons;
    private boolean isPlayable;
    private final SuperTicTacToe gameManager;
    private final int row;
    private final int col;

    public Panel(SuperTicTacToe gameManager, int row, int col) {
        this.gameManager = gameManager;
        this.row = row;
        this.col = col;

        setLayout(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        isPlayable = true;

        initializeButtons();
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    private void initializeButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                buttons[i][j].setPreferredSize(new Dimension(80, 80));
                buttons[i][j].setBackground(Color.WHITE);
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j].addActionListener(e -> handleMove(finalI, finalJ));
                add(buttons[i][j]);
            }
        }
    }

    private void handleMove(int buttonRow, int buttonCol) {
        if (isPlayable && buttons[buttonRow][buttonCol].getText().isEmpty() &&
                gameManager.isValidMove(row, col)) {
            gameManager.makeMove(row, col, buttonRow, buttonCol);
        }
    }

    public void setPlayable(boolean playable) {
        this.isPlayable = playable;
        for (JButton[] buttonRow : buttons) {
            for (JButton button : buttonRow) {
                button.setEnabled(playable && button.getText().isEmpty());
            }
        }
    }

    public void highlightPanel(boolean highlight) {
        setBackground(highlight ? new Color(230, 230, 255) : null);
        for (JButton[] buttonRow : buttons) {
            for (JButton button : buttonRow) {
                if (button.getText().isEmpty()) {
                    button.setBackground(highlight ? new Color(240, 240, 255) : Color.WHITE);
                }
            }
        }
    }

    public void markWinner(String winner) {
        for (JButton[] buttonRow : buttons) {
            for (JButton button : buttonRow) {
                if (button.getText().equals(winner)) {
                    button.setBackground(new Color(144, 238, 144));  // Light green
                }
            }
        }
        setPlayable(false);
    }

    public JButton getButton(int row, int col) {
        return buttons[row][col];
    }
}

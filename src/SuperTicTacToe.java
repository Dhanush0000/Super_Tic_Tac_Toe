import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuperTicTacToe extends JFrame implements ActionListener {
    // The game is a 3x3 grid of mini-Tic-Tac-Toe boards
    private JButton[][][] buttons = new JButton[3][3][9];  // 3x3 grid of mini-boards, each mini-board has 9 buttons
    private String currentPlayer = "X";  // Track current player
    private int nextBoard = -1;  // -1 means no board restriction, otherwise tracks where the opponent must play next
    private boolean[][] miniBoardWon = new boolean[3][3];  // Tracks if a mini-board is won
    private boolean gameOver = false;

    public SuperTicTacToe() {
        setTitle("Super Tic-Tac-Toe");
        setSize(720, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);  // Center the window
        setLayout(new GridLayout(3, 3));  // Main 3x3 grid layout for the mini-boards

        // Initialize mini-boards (9 buttons per mini-board)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JPanel miniBoardPanel = new JPanel(new GridLayout(3, 3));
                for (int k = 0; k < 9; k++) {
                    buttons[i][j][k] = new JButton("");
                    buttons[i][j][k].setFont(new Font("Arial", Font.PLAIN, 40));
                    buttons[i][j][k].setFocusPainted(false);
                    buttons[i][j][k].setBackground(Color.WHITE);
                    buttons[i][j][k].addActionListener(this);  // Add action listener to each button
                    miniBoardPanel.add(buttons[i][j][k]);
                }
                add(miniBoardPanel);
            }
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        // Determine which button was clicked
        JButton clickedButton = (JButton) e.getSource();
        int clickedIndex = -1;

        // Find the mini-board and the button index clicked
        outerLoop:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 9; k++) {
                    if (buttons[i][j][k] == clickedButton) {
                        clickedIndex = k;
                        break outerLoop;
                    }
                }
            }
        }

        int targetMiniBoardRow = clickedIndex % 3;
        int targetMiniBoardCol = clickedIndex / 3;

        // If the move is invalid (either the mini-board is restricted or already won), do nothing
        if (nextBoard != -1 && (targetMiniBoardRow != nextBoard % 3 || targetMiniBoardCol != nextBoard / 3 || miniBoardWon[targetMiniBoardRow][targetMiniBoardCol])) {
            return;
        }

        // If the button is already marked, do nothing
        if (!clickedButton.getText().equals("")) return;

        // Mark the button with the current player's symbol
        clickedButton.setText(currentPlayer);

        // Check if the current mini-board is won
        if (checkMiniBoardWin(targetMiniBoardRow, targetMiniBoardCol)) {
            miniBoardWon[targetMiniBoardRow][targetMiniBoardCol] = true;
        }

        // Check for overall game win (main 3x3 board)
        if (checkMainBoardWin()) {
            JOptionPane.showMessageDialog(this, currentPlayer + " Wins the game!");
            gameOver = true;
            return;
        }

        // If mini-board is won, reset the restriction to -1 (no restriction), else set the next move
        if (miniBoardWon[targetMiniBoardRow][targetMiniBoardCol]) {
            nextBoard = -1;  // opponent can play anywhere
        } else {
            nextBoard = targetMiniBoardRow * 3 + targetMiniBoardCol;  // set the restricted mini-board
        }

        // Switch players
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    private boolean checkMiniBoardWin(int row, int col) {
        // Check all win conditions (horizontal, vertical, diagonal) for the mini-board
        int[][] winPatterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},  // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},  // Columns
                {0, 4, 8}, {2, 4, 6}              // Diagonals
        };

        for (int[] pattern : winPatterns) {
            if (buttons[row][col][pattern[0]].getText().equals(currentPlayer) &&
                    buttons[row][col][pattern[1]].getText().equals(currentPlayer) &&
                    buttons[row][col][pattern[2]].getText().equals(currentPlayer)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkMainBoardWin() {
        // Check if any 3 mini-boards in a row are all won (horizontal, vertical, diagonal)
        for (int i = 0; i < 3; i++) {
            // Check horizontal, vertical, and diagonal
            if (miniBoardWon[i][0] && miniBoardWon[i][1] && miniBoardWon[i][2]) return true;
            if (miniBoardWon[0][i] && miniBoardWon[1][i] && miniBoardWon[2][i]) return true;
        }
        // Diagonal
        if (miniBoardWon[0][0] && miniBoardWon[1][1] && miniBoardWon[2][2]) return true;
        if (miniBoardWon[0][2] && miniBoardWon[1][1] && miniBoardWon[2][0]) return true;

        return false;
    }

    public static void main(String[] args) {
        new SuperTicTacToe();
    }
}

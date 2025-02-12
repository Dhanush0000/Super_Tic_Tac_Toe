import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuperTicTacToe extends JFrame implements ActionListener {
    // 3x3 grid of mini-Tic-Tac-Toe boards, each containing 9 buttons
    private JButton[][][] buttons = new JButton[3][3][9];
    private String currentPlayer = "X";  // The current player (X or O)
    private int nextBoard = -1;  // The mini-board that the next player must play in, -1 means no restriction
    private boolean[][] miniBoardWon = new boolean[3][3];  // Tracks whether a mini-board is won
    private boolean gameOver = false;  // Game over flag
    private boolean gameTied = false;  // Track if the game ends in a tie

    public SuperTicTacToe() {
        setTitle("Super Tic-Tac-Toe");
        setSize(720, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);  // Center the window
        setLayout(new GridLayout(3, 3));  // Main 3x3 grid layout for mini-boards

        // Initialize mini-boards (9 buttons per mini-board)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JPanel miniBoardPanel = new JPanel(new GridLayout(3, 3)); // 3x3 grid for each mini-board
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
        if (gameOver) return;  // Stop if the game is over

        JButton clickedButton = (JButton) e.getSource();

        // Find the mini-board and index clicked
        int clickedIndex = -1;
        int row = -1, col = -1;
        outerLoop:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 9; k++) {
                    if (buttons[i][j][k] == clickedButton) {
                        clickedIndex = k;
                        row = i;
                        col = j;
                        break outerLoop;
                    }
                }
            }
        }

        // Check if the mini-board is restricted and ensure it's not already won or tied
        int targetRow = clickedIndex / 3;
        int targetCol = clickedIndex % 3;

        if ((nextBoard != -1 && (nextBoard != targetRow * 3 + targetCol || miniBoardWon[targetRow][targetCol])) || !clickedButton.getText().equals("")) {
            return;  // Invalid move
        }

        // Mark the button with the current player's symbol
        clickedButton.setText(currentPlayer);

        // Check if the current mini-board is won
        if (checkMiniBoardWin(row, col)) {
            miniBoardWon[row][col] = true;
        }

        // Check if the entire game is won (main 3x3 grid)
        if (checkMainBoardWin()) {
            JOptionPane.showMessageDialog(this, currentPlayer + " Wins the game!");
            gameOver = true;
            return;
        }

        // Check if the game is a draw
        if (checkDraw()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            gameOver = true;
            return;
        }

        // Determine where the opponent must play next
        if (miniBoardWon[row][col]) {
            nextBoard = -1;  // No restrictions, opponent can play anywhere
        } else {
            nextBoard = targetRow * 3 + targetCol;  // Restrict opponent to play in the corresponding mini-board
        }

        // Switch the player
        currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
    }

    private boolean checkMiniBoardWin(int row, int col) {
        // Winning conditions for a mini-board (3 rows, 3 columns, 2 diagonals)
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
        // Check if three mini-boards in a row are won horizontally, vertically, or diagonally
        for (int i = 0; i < 3; i++) {
            if (miniBoardWon[i][0] && miniBoardWon[i][1] && miniBoardWon[i][2]) return true;  // Rows
            if (miniBoardWon[0][i] && miniBoardWon[1][i] && miniBoardWon[2][i]) return true;  // Columns
        }
        if (miniBoardWon[0][0] && miniBoardWon[1][1] && miniBoardWon[2][2]) return true;  // Diagonal
        if (miniBoardWon[0][2] && miniBoardWon[1][1] && miniBoardWon[2][0]) return true;  // Diagonal
        return false;
    }

    private boolean checkDraw() {
        // Check if the game is a draw (all mini-boards are filled but no one has won)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 9; k++) {
                    if (buttons[i][j][k].getText().equals("")) {
                        return false;  // Found an empty spot, so the game is not a draw
                    }
                }
            }
        }
        return true;  // No empty spots left, the game is a draw
    }

    public static void main(String[] args) {
        new SuperTicTacToe();
    }
}

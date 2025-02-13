import javax.swing.*;
import java.awt.*;

public class SuperTicTacToe extends JFrame {
    private final Panel[][] boards;
    private final Player[] players;
    private int currentPlayerIndex;
    private int nextBoardRow = -1;
    private int nextBoardCol = -1;
    private final boolean[][] miniBoardWon;
    private boolean gameOver;
    private JLabel statusLabel;

    public SuperTicTacToe() {
        setTitle("Super Tic-Tac-Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Initialize game components
        players = new Player[]{new Player("X"), new Player("O")};
        currentPlayerIndex = 0;
        boards = new Panel[3][3];
        miniBoardWon = new boolean[3][3];
        gameOver = false;

        // Setup UI
        setupUI();

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void setupUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Status panel at the top
        statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(statusLabel, BorderLayout.NORTH);

        // Game board panel
        JPanel gameBoardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        gameBoardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gameBoardPanel.setBackground(Color.DARK_GRAY);

        // Initialize panels
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boards[i][j] = new Panel(this, i, j);
                gameBoardPanel.add(boards[i][j]);
            }
        }

        mainPanel.add(gameBoardPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    public boolean isValidMove(int boardRow, int boardCol) {
        if (gameOver) return false;

        // First move or if next board is won
        if ((nextBoardRow == -1 && nextBoardCol == -1) ||
                (nextBoardRow != -1 && nextBoardCol != -1 && miniBoardWon[nextBoardRow][nextBoardCol])) {
            return !miniBoardWon[boardRow][boardCol];
        }

        // Must play in the specified board
        return boardRow == nextBoardRow && boardCol == nextBoardCol && !miniBoardWon[boardRow][boardCol];
    }

    public void makeMove(int boardRow, int boardCol, int buttonRow, int buttonCol) {
        Player currentPlayer = players[currentPlayerIndex];
        boards[boardRow][boardCol].getButton(buttonRow, buttonCol).setText(currentPlayer.getSymbol());

        // Check for win in mini-board
        if (checkMiniBoardWin(boardRow, boardCol, currentPlayer.getSymbol())) {
            miniBoardWon[boardRow][boardCol] = true;
            boards[boardRow][boardCol].markWinner(currentPlayer.getSymbol());

            // Check for main board win
            if (checkMainBoardWin()) {
                gameOver = true;
                statusLabel.setText("Player " + currentPlayer.getSymbol() + " wins the game!");
                return;
            }
        }

        // Check for draw
        if (checkDraw()) {
            gameOver = true;
            statusLabel.setText("Game Over - It's a draw!");
            return;
        }

        // Update next board to play
        updateNextBoard(buttonRow, buttonCol);

        // Switch players
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        updateBoardHighlights();
        statusLabel.setText("Player " + players[currentPlayerIndex].getSymbol() + "'s turn");
    }

    private void updateNextBoard(int buttonRow, int buttonCol) {
        if (miniBoardWon[buttonRow][buttonCol]) {
            nextBoardRow = -1;
            nextBoardCol = -1;
        } else {
            nextBoardRow = buttonRow;
            nextBoardCol = buttonCol;
        }
    }

    private void updateBoardHighlights() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boolean isPlayable = isValidMove(i, j);
                boards[i][j].highlightPanel(isPlayable);
                boards[i][j].setPlayable(isPlayable);
            }
        }
    }

    private boolean checkMiniBoardWin(int boardRow, int boardCol, String symbol) {
        Panel board = boards[boardRow][boardCol];

        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (checkLine(board, i, 0, i, 1, i, 2, symbol)) return true;  // Rows
            if (checkLine(board, 0, i, 1, i, 2, i, symbol)) return true;  // Columns
        }

        // Check diagonals
        return checkLine(board, 0, 0, 1, 1, 2, 2, symbol) ||  // Main diagonal
                checkLine(board, 0, 2, 1, 1, 2, 0, symbol);    // Other diagonal
    }

    private boolean checkLine(Panel board, int r1, int c1, int r2, int c2, int r3, int c3, String symbol) {
        return board.getButton(r1, c1).getText().equals(symbol) &&
                board.getButton(r2, c2).getText().equals(symbol) &&
                board.getButton(r3, c3).getText().equals(symbol);
    }

    private boolean checkMainBoardWin() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (checkMainLine(i, 0, i, 1, i, 2)) return true;  // Rows
            if (checkMainLine(0, i, 1, i, 2, i)) return true;  // Columns
        }

        // Check diagonals
        return checkMainLine(0, 0, 1, 1, 2, 2) ||  // Main diagonal
                checkMainLine(0, 2, 1, 1, 2, 0);    // Other diagonal
    }

    private boolean checkMainLine(int r1, int c1, int r2, int c2, int r3, int c3) {
        return miniBoardWon[r1][c1] && miniBoardWon[r2][c2] && miniBoardWon[r3][c3] &&
                boards[r1][c1].getButton(1, 1).getText().equals(
                        boards[r2][c2].getButton(1, 1).getText()) &&
                boards[r1][c1].getButton(1, 1).getText().equals(
                        boards[r3][c3].getButton(1, 1).getText());
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        if (boards[i][j].getButton(row, col).getText().isEmpty() && !miniBoardWon[i][j]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SuperTicTacToe::new);
    }
}

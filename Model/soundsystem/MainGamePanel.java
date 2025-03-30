package Model.soundsystem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainGamePanel extends JPanel implements ChessBoardListener {
    private ChessBoardPanel[] boards = new ChessBoardPanel[9];
    private PieceColor[][] boardWinners = new PieceColor[3][3]; // Hvem som vant hvert brett
    private PieceColor currentPlayer = PieceColor.WHITE;
    private JPanel boardGrid;

    // Player-specific timer labels
    private JLabel whiteTimerLabel = new JLabel("60", SwingConstants.CENTER);
    private JLabel blackTimerLabel = new JLabel("60", SwingConstants.CENTER);
    private JLabel turnLabel = new JLabel("WHITE", SwingConstants.CENTER); // Label for current turn

    private Timer whiteTimer;
    private Timer blackTimer;
    private int whiteTimeRemaining = 60; // 60 seconds for White
    private int blackTimeRemaining = 60; // 60 seconds for Black

    public MainGamePanel() {
        setLayout(new BorderLayout());

        boardGrid = new JPanel(new GridLayout(3, 3, 5, 5));
        for (int i = 0; i < 9; i++) {
            boards[i] = new ChessBoardPanel();
            boards[i].setListener(this, i); // viktig!
            boardGrid.add(boards[i]);
        }

        boardGrid.setOpaque(false);
        boardGrid.setBackground(Color.BLACK);

        // Wrap the boardGrid in a container panel
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.BLACK);
        container.add(boardGrid, BorderLayout.CENTER);

        // Add padding to the left to push the grid to the right
        JPanel leftPadding = new JPanel(new GridLayout(3, 1, 0, 0)); // Three rows: White Timer, Turn Label, Black Timer
        leftPadding.setPreferredSize(new Dimension(200, 0)); // Adjust width as needed

        // Add timer labels and turn label to the padding panel
        whiteTimerLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        whiteTimerLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 3)); // Red border for White
        blackTimerLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        blackTimerLabel.setForeground(Color.WHITE);
        blackTimerLabel.setBackground(Color.BLACK);
        blackTimerLabel.setOpaque(true);
        turnLabel.setFont(new Font("SansSerif", Font.BOLD, 30)); // Larger font for the turn label
        turnLabel.setOpaque(true);
        turnLabel.setBackground(Color.LIGHT_GRAY); // Background color for the turn label
        turnLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a border to the label

        leftPadding.add(whiteTimerLabel);
        leftPadding.add(turnLabel); // Add the turn label in the middle
        leftPadding.add(blackTimerLabel);

        // Add the padding panel to the container
        container.add(leftPadding, BorderLayout.WEST);

        add(container, BorderLayout.CENTER);

        // Initialize all boards as "no winner yet"
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                boardWinners[row][col] = PieceColor.NONE;
            }
        }

        initializeTimers();

    }


    @Override
    public void onBoardWon(PieceColor winner, int boardIndex) {
        int row = boardIndex / 3;
        int col = boardIndex % 3;
        boardWinners[row][col] = winner;

        // Sjekk om noen har vunnet Tic Tac Toe
        if (checkTicTacToeWin(winner)) {
            disableAllBoards();

            JOptionPane.showMessageDialog(this, winner + " wins the entire game!");
        } 
        else if (checkTicTacToeDraw()) {
            disableAllBoards();

            JOptionPane.showMessageDialog(this, "Draw. :-(");
        } else {
            // Fortsett spillet
            currentPlayer = (currentPlayer == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        }
    }

    private boolean checkTicTacToeDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardWinners[i][j] == PieceColor.NONE) {
                    return false;
                }
            }
        }
        stopTimer();
        return true;
    }


    // Sjekker om en spiller har fått tre på rad
    private boolean checkTicTacToeWin(PieceColor color) {
        for (int i = 0; i < 3; i++) {
            // Rader
            if (boardWinners[i][0] == color && boardWinners[i][1] == color && boardWinners[i][2] == color) {
                stopTimer();
                return true;
            }
            // Kolonner
            if (boardWinners[0][i] == color && boardWinners[1][i] == color && boardWinners[2][i] == color) {
                stopTimer();
                return true;
            }
        }
        // Diagonaler
        if (boardWinners[0][0] == color && boardWinners[1][1] == color && boardWinners[2][2] == color) {
            stopTimer();
            return true;
        }
        if (boardWinners[0][2] == color && boardWinners[1][1] == color && boardWinners[2][0] == color) {
            stopTimer();
            return true;
        }
        return false;
    }

    private void disableAllBoards() {
        for (ChessBoardPanel board : boards) {
            board.disableBoard(); // finnes allerede i ChessBoardPanel
        }
    }

    private void initializeTimers() {
        // Timer for White
        whiteTimer = new Timer(1000, e -> {
            whiteTimeRemaining--;
            whiteTimerLabel.setText("" + whiteTimeRemaining);
            if (whiteTimeRemaining <= 0) {
                whiteTimer.stop();
                JOptionPane.showMessageDialog(this, "White's time is up! Black wins!");
                disableAllBoards();
            }
        });

        // Timer for Black
        blackTimer = new Timer(1000, e -> {
            blackTimeRemaining--;
            blackTimerLabel.setText("" + blackTimeRemaining);
            if (blackTimeRemaining <= 0) {
                blackTimer.stop();
                JOptionPane.showMessageDialog(this, "Black's time is up! White wins!");
                disableAllBoards();
            }
        });

        // Start the timer for the first player (White)
        whiteTimer.start();
    }

    public void swapTurnOnAllBoards(PieceColor newPlayer) {
        for (int i = 0; i < 9; i++) {
            boards[i].setCurrentPlayer(newPlayer);
        }
    }

    public void onMoveMade(int boardIndex) {
        if (currentPlayer == PieceColor.WHITE) {
            whiteTimeRemaining += 5;
        } else {
            blackTimeRemaining += 5;
        }

        // Switch turns globally
        currentPlayer = (currentPlayer == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        swapTurnOnAllBoards(currentPlayer);

        // Update the turn label
        turnLabel.setText(currentPlayer == PieceColor.WHITE ? "WHITE" : "BLACK");

        // Highlight the current player's timer
        if (currentPlayer == PieceColor.WHITE) {
            whiteTimerLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 3)); // Red border for White
            blackTimerLabel.setBorder(BorderFactory.createEmptyBorder()); // Remove border for Black
        } else {
            blackTimerLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 3)); // Red border for Black
            whiteTimerLabel.setBorder(BorderFactory.createEmptyBorder()); // Remove border for White
        }

        // Stop the current player's timer and start the next player's timer
        if (currentPlayer == PieceColor.WHITE) {
            blackTimer.stop();
            whiteTimer.start();
        } else {
            whiteTimer.stop();
            blackTimer.start();
        }
    }

    public void stopTimer() {
        whiteTimer.stop();
        blackTimer.stop();
    }

}

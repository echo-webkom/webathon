package Model.soundsystem;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

enum PieceType {
    KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN, NONE
}

enum PieceColor {
    WHITE, BLACK, NONE
}

@SuppressWarnings("serial")
class ChessBoardPanel extends JPanel implements ActionListener {
    private JButton[][] squares = new JButton[8][8];
    private piece[][] board = new piece[8][8];
    private boolean pieceSelected = false;
    private int selectedRow, selectedCol;
    private boolean gameOver = false;
    private JLabel overlayLabel;
    private PieceColor currentPlayer;
    private JLayeredPane layeredPane;

    private ChessBoardListener listener;
    private int boardIndex;

    public ChessBoardPanel() {
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        initializeBoardState();
        initializeGUI(boardPanel);

        overlayLabel = new JLabel("", SwingConstants.CENTER);
        overlayLabel.setFont(new Font("SansSerif", Font.BOLD, 96));
        overlayLabel.setForeground(new Color(0, 0, 0, 150)); // semi-transparent svart
        overlayLabel.setOpaque(false);
        overlayLabel.setBackground(new Color(0, 0, 0, 0));
        overlayLabel.setVisible(false);

        layeredPane = new JLayeredPane();
        boardPanel.setBounds(0, 0, 600, 600);
        overlayLabel.setBounds(0, 0, 600, 600);
        layeredPane.setPreferredSize(new Dimension(600, 600));
        layeredPane.add(boardPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(overlayLabel, JLayeredPane.PALETTE_LAYER);

        removeAll();
        setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);

        currentPlayer = PieceColor.WHITE;

    }

    public void setListener(ChessBoardListener listener, int index) {
        this.listener = listener;
        this.boardIndex = index;
    }

    public void showOverlaySymbol(String symbol, int fontSize, Color color) {
        overlayLabel.setText(symbol); // Update the text of the overlay label
        overlayLabel.setFont(new Font("SansSerif", Font.BOLD, fontSize)); // Set the font size
        overlayLabel.setForeground(color); // Set the color of the symbol
        overlayLabel.setVisible(true); // Make the overlay label visible
        revalidate(); // Revalidate the panel to ensure layout updates
        repaint(); // Repaint the panel to reflect changes
    }

    // Set up the starting positions for all pieces.
    private void initializeBoardState() {
        // Fill all squares with an empty piece.
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new piece();
            }
        }
        // Black pieces (top of board)
        board[0][0] = new piece(PieceType.ROOK, PieceColor.BLACK);
        board[0][1] = new piece(PieceType.KNIGHT, PieceColor.BLACK);
        board[0][2] = new piece(PieceType.BISHOP, PieceColor.BLACK);
        board[0][3] = new piece(PieceType.QUEEN, PieceColor.BLACK);
        board[0][4] = new piece(PieceType.KING, PieceColor.BLACK);
        board[0][5] = new piece(PieceType.BISHOP, PieceColor.BLACK);
        board[0][6] = new piece(PieceType.KNIGHT, PieceColor.BLACK);
        board[0][7] = new piece(PieceType.ROOK, PieceColor.BLACK);
        for (int j = 0; j < 8; j++) {
            board[1][j] = new piece(PieceType.PAWN, PieceColor.BLACK);
        }
        // White pieces (bottom of board)
        board[7][0] = new piece(PieceType.ROOK, PieceColor.WHITE);
        board[7][1] = new piece(PieceType.KNIGHT, PieceColor.WHITE);
        board[7][2] = new piece(PieceType.BISHOP, PieceColor.WHITE);
        board[7][3] = new piece(PieceType.QUEEN, PieceColor.WHITE);
        board[7][4] = new piece(PieceType.KING, PieceColor.WHITE);
        board[7][5] = new piece(PieceType.BISHOP, PieceColor.WHITE);
        board[7][6] = new piece(PieceType.KNIGHT, PieceColor.WHITE);
        board[7][7] = new piece(PieceType.ROOK, PieceColor.WHITE);
        for (int j = 0; j < 8; j++) {
            board[6][j] = new piece(PieceType.PAWN, PieceColor.WHITE);
        }
    }

    // Set up the board GUI.
    private void initializeGUI(JPanel boardPanel) {
        Font font = new Font("Symbola", Font.PLAIN, 32);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton(board[row][col].getSymbol());
                button.setFont(font);
                button.setFocusPainted(false);
                button.setPreferredSize(new Dimension(60, 60));
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setOpaque(true);
                button.setBorderPainted(false);

                if ((row + col) % 2 == 0) {
                    button.setBackground(Color.WHITE);
                } else {
                    button.setBackground(Color.GRAY);
                }

                button.addActionListener(this);
                squares[row][col] = button;
                boardPanel.add(button);
            }
        }
    }

    // Handle a square click.
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            return;
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (e.getSource() == squares[row][col]) {
                    handleSquareClick(row, col);
                    return;
                }
            }
        }
    }

    // Process a click on square (row, col)
    // Reset the timer when the turn switches
    private void handleSquareClick(int row, int col) {
        if (!pieceSelected) {
            if (!board[row][col].getSymbol().equals("") && board[row][col].color == currentPlayer) {
                pieceSelected = true;
                selectedRow = row;
                selectedCol = col;
                resetAllSquareColors();
                squares[row][col].setBackground(Color.YELLOW);
                highlightValidMoves(row, col);
            }
        } else {
            if (isValidMove(board[selectedRow][selectedCol], selectedRow, selectedCol, row, col)) {
                board[row][col] = board[selectedRow][selectedCol];
                board[selectedRow][selectedCol] = new piece();
                updateSquare(selectedRow, selectedCol);
                updateSquare(row, col);

                // Notify MainGamePanel that a move was made
                if (listener != null) {
                    listener.onMoveMade(boardIndex);
                }

                checkGameOver();

            }
            resetAllSquareColors();
            pieceSelected = false;
        }
    }

    // Update a single square’s display.
    private void updateSquare(int row, int col) {
        squares[row][col].setText(board[row][col].getSymbol());
    }

    // Reset a square's background to its default.
    private void resetSquareColor(int row, int col) {
        if ((row + col) % 2 == 0)
            squares[row][col].setBackground(Color.WHITE);
        else
            squares[row][col].setBackground(Color.GRAY);
    }

    // Checks if moving the piece from (fromRow, fromCol) to (toRow, toCol) is
    // legal.
    private boolean isValidMove(piece piece, int fromRow, int fromCol, int toRow, int toCol) {
        // Cannot capture your own piece.
        if (board[toRow][toCol].color == piece.color) {
            return false;
        }
        int dRow = toRow - fromRow;
        int dCol = toCol - fromCol;

        switch (piece.type) {
            case PAWN:
                return isValidPawnMove(piece, fromRow, fromCol, toRow, toCol, dRow, dCol);
            case KNIGHT:
                // L-shaped move.
                if ((Math.abs(dRow) == 2 && Math.abs(dCol) == 1) ||
                        (Math.abs(dRow) == 1 && Math.abs(dCol) == 2)) {
                    return true;
                }
                break;
            case BISHOP:
                if (Math.abs(dRow) == Math.abs(dCol))
                    return clearDiagonal(fromRow, fromCol, toRow, toCol);
                break;
            case ROOK:
                if (dRow == 0 || dCol == 0)
                    return clearStraight(fromRow, fromCol, toRow, toCol);
                break;
            case QUEEN:
                if (dRow == 0 || dCol == 0)
                    return clearStraight(fromRow, fromCol, toRow, toCol);
                if (Math.abs(dRow) == Math.abs(dCol))
                    return clearDiagonal(fromRow, fromCol, toRow, toCol);
                break;
            case KING:
                // King moves one square in any direction.
                if (Math.abs(dRow) <= 1 && Math.abs(dCol) <= 1)
                    return true;
                break;
            default:
                break;
        }
        return false;
    }

    private void highlightValidMoves(int row, int col) {
    // Reset any previous highlights
    resetAllSquareColors();

    // Get the selected piece
    piece selectedPiece = board[row][col];

    Color yellowTint = new Color(246, 246, 117);

    // Check all squares on the board
    for (int targetRow = 0; targetRow < 8; targetRow++) {
        for (int targetCol = 0; targetCol < 8; targetCol++) {
            // If the move is valid, highlight the square
            if (isValidMove(selectedPiece, row, col, targetRow, targetCol)) {
                squares[targetRow][targetCol].setBackground(yellowTint); // Highlight color
            }
        }
    }
}

private void resetAllSquareColors() {
    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            if ((row + col) % 2 == 0) {
                squares[row][col].setBackground(Color.WHITE); // Default white square
            } else {
                squares[row][col].setBackground(Color.GRAY); // Default gray square
            }
        }
    }
}

    // Pawn movement validation.
    private boolean isValidPawnMove(piece piece, int fromRow, int fromCol, int toRow, int toCol, int dRow, int dCol) {
        int direction = (piece.color == PieceColor.WHITE) ? -1 : 1;
        // Standard one-square move forward.
        if (dCol == 0 && dRow == direction && board[toRow][toCol].type == PieceType.NONE) {
            return true;
        }
        // Double move from starting row.
        if (dCol == 0 && dRow == 2 * direction && board[toRow][toCol].type == PieceType.NONE) {
            if ((piece.color == PieceColor.WHITE && fromRow == 6)
                    || (piece.color == PieceColor.BLACK && fromRow == 1)) {
                // Ensure intermediate square is clear.
                if (board[fromRow + direction][fromCol].type == PieceType.NONE) {
                    return true;
                }
            }
        }
        // Diagonal capture.
        if (Math.abs(dCol) == 1 && dRow == direction && board[toRow][toCol].type != PieceType.NONE) {
            return true;
        }
        return false;
    }

    // Check if all squares between (fromRow,fromCol) and (toRow,toCol) are empty
    // for straight moves.
    private boolean clearStraight(int fromRow, int fromCol, int toRow, int toCol) {
        int stepRow = Integer.compare(toRow, fromRow);
        int stepCol = Integer.compare(toCol, fromCol);
        int currentRow = fromRow + stepRow, currentCol = fromCol + stepCol;
        while (currentRow != toRow || currentCol != toCol) {
            if (board[currentRow][currentCol].type != PieceType.NONE) {
                return false;
            }
            currentRow += stepRow;
            currentCol += stepCol;
        }
        return true;
    }

    // Check if all squares between (fromRow,fromCol) and (toRow,toCol) are empty
    // for diagonal moves.
    private boolean clearDiagonal(int fromRow, int fromCol, int toRow, int toCol) {
        int stepRow = (toRow - fromRow) > 0 ? 1 : -1;
        int stepCol = (toCol - fromCol) > 0 ? 1 : -1;
        int currentRow = fromRow + stepRow, currentCol = fromCol + stepCol;
        while (currentRow != toRow && currentCol != toCol) {
            if (board[currentRow][currentCol].type != PieceType.NONE) {
                return false;
            }
            currentRow += stepRow;
            currentCol += stepCol;
        }
        return true;
    }

    // Check if a king of the specified color is still present.
    private boolean isKingPresent(PieceColor color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col].type == PieceType.KING && board[row][col].color == color) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks if the game is over (if either king is missing) and disables the board
    // if so.
    private void checkGameOver() {
        if (!isKingPresent(PieceColor.WHITE) || !isKingPresent(PieceColor.BLACK)) {
            gameOver = true;
            PieceColor winnerColor = isKingPresent(PieceColor.WHITE) ? PieceColor.WHITE : PieceColor.BLACK;
            disableBoard();

            String winnerSymbol = (winnerColor == PieceColor.WHITE) ? "X" : "O";
            Color symbolColor;
            if (winnerSymbol == "X") {
                symbolColor = Color.BLUE;
            } else {
                symbolColor = Color.RED;
            }
            showOverlaySymbol(winnerSymbol, 256, symbolColor);

            // JOptionPane.showMessageDialog(this, "Game Over! " + winnerColor + " wins.");

            if (listener != null) {
                listener.onBoardWon(winnerColor, boardIndex);
            }
        }
    }

    // Disables all buttons to prevent further moves.
    void disableBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].setEnabled(false);
            }
        }
    }

    public void setCurrentPlayer(PieceColor pieceColor) {
        this.currentPlayer = pieceColor;
    }

    @Override
    public void doLayout() {
        super.doLayout(); // Let the default layout manager do its work first

        // Get the actual size allocated to this ChessBoardPanel by its parent layout manager
        int width = getWidth();
        int height = getHeight();

        // Make the layeredPane (which holds the board and overlay) fill the entire
        // space of this ChessBoardPanel, allowing it to stretch.
        if (layeredPane != null) { // Check if layeredPane is initialized
            layeredPane.setBounds(0, 0, width, height);

            // Make the components *inside* the layeredPane (the actual grid panel and the overlay label)
            // also fill the entire layeredPane.
            for (Component comp : layeredPane.getComponents()) {
                 if (comp != null) { // Check if component exists
                     comp.setBounds(0, 0, width, height);
                 }
            }

            // Optional: Adjust font size based on new dimensions if needed for better visuals
            // This is more complex and might require calculating font size based on square size.
            // For now, we are just stretching the layout.
        }
    }
}

public class ChessGame {
    public static void main(String[] args) {
        // Launch the persistent main menu that allows multiple boards.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createMainMenu();
            }
        });
    }

    // Creates the main menu window.
    private static void createMainMenu() {
        JFrame menuFrame = new JFrame("Chess Game - Main Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLayout(new FlowLayout());

        JButton newBoardButton = new JButton("New Board");
        newBoardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createNewBoard();
            }
        });
        menuFrame.add(newBoardButton);

        menuFrame.setSize(300, 100);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);
    }

    // Creates a new chess board window.
    public static void createNewBoard() {
        JFrame boardFrame = new JFrame("Chess Board");
        boardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ChessBoardPanel boardPanel = new ChessBoardPanel();
        boardFrame.add(boardPanel);
        boardFrame.setSize(600, 600);
        boardFrame.setLocationByPlatform(true);
        boardFrame.setVisible(true);
    }

}

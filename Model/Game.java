package Model;

// Game.java
import java.util.Scanner;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameOver;

    public Game(String player1Name, String player2Name) {
        board = new Board();
        player1 = new Player(player1Name, Piece.Color.WHITE);
        player2 = new Player(player2Name, Piece.Color.BLACK);
        currentPlayer = player1;
        gameOver = false;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        while (!gameOver) {
            board.printBoard();
            System.out.println(currentPlayer.getName() + "'s turn (" + currentPlayer.getColor() + ")");
            System.out.print("Enter move (e.g., 'a2 a4'): ");
            String moveStr = scanner.nextLine();

            if (moveStr.equalsIgnoreCase("quit")) {
                System.out.println("Game ended.");
                break;
            }

            try {
                Move move = parseMove(moveStr);

                if (isValidMove(move)) {
                    executeMove(move);
                    switchTurn();
                } else {
                    System.out.println("Invalid move.  Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid move format. Use 'a2 a4' format.");
            }
        }

        scanner.close();
    }

    private Move parseMove(String moveStr) {
        String[] parts = moveStr.split(" ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid move format");
        }

        String startSq = parts[0].trim().toLowerCase();
        String endSq = parts[1].trim().toLowerCase();

        int startCol = startSq.charAt(0) - 'a';
        int startRow = 8 - Integer.parseInt(startSq.substring(1)); // Assuming row input is 1-8
        int endCol = endSq.charAt(0) - 'a';
        int endRow = 8 - Integer.parseInt(endSq.substring(1));

        Piece movedPiece = board.getPiece(startRow, startCol);
        Piece capturedPiece = board.getPiece(endRow, endCol);

        if (movedPiece == null) {
            throw new IllegalArgumentException("No piece at starting square.");
        }
        return new Move(startRow, startCol, endRow, endCol, movedPiece, capturedPiece);
    }

    private boolean isValidMove(Move move) {
        int startRow = move.getStartRow();
        int startCol = move.getStartCol();
        int endRow = move.getEndRow();
        int endCol = move.getEndCol();
        Piece piece = move.getMovedPiece();

        if (piece.getColor() != currentPlayer.getColor()) {
            System.out.println("Not your piece.");
            return false;
        }

        if (endRow < 0 || endRow >= Board.getBoardSize() || endCol < 0 || endCol >= Board.getBoardSize()) {
            System.out.println("Out of bounds move.");
            return false;
        }

        return piece.isValidMove(startRow, startCol, endRow, endCol, board);
    }

    private void executeMove(Move move) {
        int startRow = move.getStartRow();
        int startCol = move.getStartCol();
        int endRow = move.getEndRow();
        int endCol = move.getEndCol();
        Piece piece = move.getMovedPiece();

        board.movePiece(startRow, startCol, endRow, endCol);

        //TODO:  Implement promotion logic

        //Check for game over conditions (checkmate, stalemate) - not implemented in this example
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}